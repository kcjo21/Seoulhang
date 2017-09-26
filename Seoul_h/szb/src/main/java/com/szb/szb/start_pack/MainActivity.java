package com.szb.szb.start_pack;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;

import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;
import com.szb.szb.BaseActivity;
import com.szb.szb.Home.BackPressCloseHandler;
import com.szb.szb.Home.Home_Main;
import com.szb.szb.R;
import com.szb.szb.model.retrofit.PlayerDTO;
import com.szb.szb.network.Ipm;
import com.szb.szb.network.NetworkClient;
import com.szb.szb.start_pack.loginpackage.Dialog_Login;
import com.szb.szb.start_pack.loginpackage.KakaoSignupActivity;
import com.szb.szb.start_pack.loginpackage.Logm;
import com.szb.szb.start_pack.loginpackage.UserProfileData;
import com.szb.szb.start_pack.registerpack.FindIdActivity;
import com.szb.szb.start_pack.registerpack.FindPassActivity;
import com.szb.szb.start_pack.registerpack.JoinActivity;

import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends BaseActivity {
    LinearLayout login_lay;
    LinearLayout regist_lay;
    LinearLayout syncbt_lay;
    Button login;
    Button set;
    Button loginSelect;
    Button logoutsync;
    Button logoutnormal;
    EditText loginId_tv;
    TextView join;
    TextView findid;
    TextView findPass;
    EditText password;
    String loginid;
    String loginpass;
    String LoginTypeChecker;
    ImageView run;
    com.kakao.usermgmt.LoginButton kakao;
    NetworkClient networkClient;
    Ipm ipm;
    Logm logm;
    private BackPressCloseHandler backPressCloseHandler;
    CallbackManager callbackManager;
    private SessionCallback kakaocallback;
    private Dialog_Login dialog_login;
    InputMethodManager keyboard;
    UserProfileData userProfileData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        callbackManager=CallbackManager.Factory.create();
        login_lay = (LinearLayout) findViewById(R.id.text_log_lay);
        kakaocallback =  new SessionCallback();
        regist_lay = (LinearLayout) findViewById(R.id.register_lay);
        syncbt_lay = (LinearLayout) findViewById(R.id.syncbtset_lay);
        loginId_tv = (EditText) findViewById(R.id.loginId);
        join = (TextView) findViewById(R.id.join_tm);
        kakao = (LoginButton) findViewById(R.id.bt_kakao_main);
        findid = (TextView) findViewById(R.id.find_id);
        findPass = (TextView) findViewById(R.id.find_pass_t);
        login = (Button) findViewById(R.id.Login);
        password = (EditText) findViewById(R.id.loginPass);
        set = (Button) findViewById(R.id.bt_set);
        logoutsync = (Button) findViewById(R.id.bt_logout_sync);
        logoutnormal = (Button) findViewById(R.id.bt_logout_n);
        ipm = new Ipm();
        logm = new Logm();
        userProfileData = new UserProfileData();
        backPressCloseHandler = new BackPressCloseHandler(this);
        final String ip = ipm.getip();
        networkClient = NetworkClient.getInstance(ip);
        login = (Button) findViewById(R.id.Login);
        loginSelect = (Button)findViewById(R.id.bt_loginselect);
        keyboard = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        Session.getCurrentSession().addCallback(kakaocallback);
        Session.getCurrentSession().checkAndImplicitOpen();

        SharedPreferences sharedPreferences = getSharedPreferences("log",MODE_PRIVATE);  //이전 로그인한 기록이 있으면 세션선택과정 생략
        final String loginPref = sharedPreferences.getString("logintype","logout");
        Log.d("Login",loginPref);
        if(!loginPref.equals("logout")){
            syncbt_lay.setVisibility(View.VISIBLE);
            loginSelect.setVisibility(View.GONE);
            LoginTypeChecker = loginPref;
        }
        else {
            LoginTypeChecker = loginPref;
        }


        dialog_login = new Dialog_Login(this, new Dialog_Login.DialogListener() {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("log",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            @Override
            public void customDialogListener(int loginType) {


                if(loginType==0) {

                    //LoginManager - 요청된 읽기 또는 게시 권한으로 페이스북 로그인 절차를 시작합니다.
                    LoginManager.getInstance().logInWithReadPermissions(MainActivity.this,
                            Arrays.asList("public_profile","email"));
                    LoginManager.getInstance().registerCallback(callbackManager,
                            new FacebookCallback<LoginResult>() {
                                @Override
                                public void onSuccess(LoginResult loginResult) {
                                    GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                            new GraphRequest.GraphJSONObjectCallback() {
                                                @Override
                                                public void onCompleted(JSONObject object, GraphResponse response) {
                                                    try {
                                                        logm.setPlayerid(object.getString("id"));
                                                        Log.d("페이스북 계정",logm.getPlayerid());
                                                        userProfileData.setEmail(object.getString("email"));
                                                        userProfileData.setName(object.getString("name"));
                                                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.loginsuccess,userProfileData.getName()), Toast.LENGTH_LONG);
                                                        toast.show();
                                                    }
                                                    catch (Exception e){
                                                        e.printStackTrace();
                                                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.계정연동실패), Toast.LENGTH_LONG);
                                                        toast.show();
                                                        dialog_login.show();

                                                    }
                                                }
                                            });
                                    Bundle parameters = new Bundle();
                                    parameters.putString("fields","id,name,email");
                                    request.setParameters(parameters);
                                    request.executeAsync();
                                    dialog_login.dismiss();
                                    loginSelect.setVisibility(View.GONE);
                                    login_lay.setVisibility(View.GONE);
                                    regist_lay.setVisibility(View.GONE);
                                    syncbt_lay.setVisibility(View.VISIBLE);
                                    Log.d("페이스북 계정 연동 : ", "성공");
                                }

                                @Override
                                public void onCancel() {

                                    Log.e("페이스북 계정 연동 : ", "취소됨");
                                    dialog_login.dismiss();
                                }
                                @Override
                                public void onError(FacebookException exception) {
                                    Log.e("페이스북 계정 연동 : ", "에러 " + exception.getLocalizedMessage());
                                    dialog_login.dismiss();
                                }
                    });
                    LoginTypeChecker = "facebook";
                }
                else if(loginType==1){ //카카오 로그인 연동
                    LoginTypeChecker = "kakao";
                    loginSelect.setVisibility(View.GONE);
                    login_lay.setVisibility(View.GONE);
                    regist_lay.setVisibility(View.GONE);
                    syncbt_lay.setVisibility(View.VISIBLE);
                    kakao.performClick();
                    dialog_login.dismiss();
                }
                else{
                    syncbt_lay.setVisibility(View.INVISIBLE);
                    loginSelect.setVisibility(View.GONE);
                    regist_lay.setVisibility(View.VISIBLE);
                    login_lay.setVisibility(View.VISIBLE);
                    dialog_login.dismiss();
                }

                editor.putString("logintype",LoginTypeChecker);   //로그인 상태 저장
                editor.apply();
                Log.d("페이스북 계정",LoginTypeChecker);
            }
        });

        logoutsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LoginTypeChecker.equals("facebook")){
                    if(AccessToken.getCurrentAccessToken() == null){
                        return; // 이미 로그아웃 되어 있음.
                    }
                    new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/",
                            null, HttpMethod.DELETE, new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                            LoginManager.getInstance().logOut();
                        }
                    });
                }
                else if(LoginTypeChecker.equals("kakao")){
                    UserManagement.requestLogout(new LogoutResponseCallback() { //카카오 로그아웃 요청
                        @Override
                        public void onCompleteLogout() {
                            redirectSignupActivity();
                        }
                    });
                }
                syncbt_lay.setVisibility(View.INVISIBLE);
                loginSelect.setVisibility(View.VISIBLE);
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("log",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("logintype","logout");   //로그인 상태 로그아웃으로 저장
                editor.apply();
                LoginTypeChecker = "logout";
            }

        });

        logoutnormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_lay.setVisibility(View.INVISIBLE);
                regist_lay.setVisibility(View.INVISIBLE);
                loginSelect.setVisibility(View.VISIBLE);
                LoginTypeChecker = "logout";

            }
        });


        loginSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_login.show();
                regist_lay.setVisibility(View.GONE);
                login_lay.setVisibility(View.GONE);
                syncbt_lay.setVisibility(View.GONE);
            }
        });





        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                   //게임자체 계정으로 로그인합니다.
                loginid = loginId_tv.getText().toString();
                loginpass = password.getText().toString();
                logm.setPlayerid(loginid);

                Log.e("ACC", "TEAM id IS !!! " + loginid);
               // progressON(getResources().getString(R.string.Loading)); //응답대기 애니메이션

                networkClient.login(loginid, loginpass, new Callback<PlayerDTO>() {
                    @Override
                    public void onResponse(Call<PlayerDTO> call, Response<PlayerDTO> response) {
                        Log.e("아이디", loginid);
                        Log.e("비밀번호", loginpass);
                        switch (response.code()) {
                            case 200:
                                LoginTypeChecker = "normal";
                                login_lay.setVisibility(View.GONE);
                                syncbt_lay.setVisibility(View.VISIBLE);
                                regist_lay.setVisibility(View.GONE);
                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.loginsuccess,loginid), Toast.LENGTH_LONG);
                                toast.show();
                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("log",MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("logintype",LoginTypeChecker);   //로그인 상태 저장
                                editor.apply();
                                progressOFF();
                                break;

                            default:
                                Log.e("TAG", "다른 아이디");
                                progressOFF();
                                Toast toast_2 = Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_check_id), Toast.LENGTH_LONG);
                                toast_2.show();
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<PlayerDTO> call, Throwable t) {
                        Log.e("ACC", "s?? " + t.getMessage());
                        progressOFF();
                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                        toast.show();

                    }
                });
            }
        });





        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JoinActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FindIdActivity.class);
                startActivity(intent);
                finish();
            }
        });
        findPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FindPassActivity.class);
                startActivity(intent);
            }
        });


        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                String email = userProfileData.getEmail();
                String name = userProfileData.getName();

                if (LoginTypeChecker.equals("facebook")) {

                    loginid = logm.getPlayerid();
                    networkClient.getjoin(loginid, " ", name, " ", 00, email, LoginTypeChecker, new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            switch (response.code()) {
                                case 200:
                                    Log.d("페이스북으로 회원가입",loginid);
                                    break;
                                default:
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });

                }
                else if(LoginTypeChecker.equals("kakao")){
                    Log.d("카카오",name);
                    loginid = userProfileData.getId();
                    logm.setPlayerid(loginid);
                    networkClient.getjoin(loginid, " ", name, " ", 00, email, LoginTypeChecker, new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            switch (response.code()){
                                case 200:
                                    Log.d("카카오로 회원가입",loginid);
                                    break;
                                default:
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });

                }

                networkClient.startgame(loginid, new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        switch (response.code()){
                            case 200:
                                Log.d("닉네임 입력 필요: ",loginid);
                                int hasNickname = response.body();
                                String nick = Integer.toString(hasNickname);
                                if(hasNickname == 1) {
                                    Log.d("닉네임 확인",nick);
                                    final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);

                                    alert.setCancelable(false);
                                    alert.setMessage(R.string.닉네임입력);

                                    final EditText nickname = new EditText(MainActivity.this);

                                    nickname.setInputType(InputType.TYPE_CLASS_TEXT);
                                    nickname.setMaxLines(1);

                                    alert.setView(nickname);
                                    alert.setPositiveButton(R.string.확인, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            String snickname = nickname.getText().toString();
                                            keyboard.hideSoftInputFromWindow(nickname.getWindowToken(), 0);
                                            networkClient.newnickname(loginid, snickname, new Callback<String>() {
                                                @Override
                                                public void onResponse(Call<String> call, Response<String> response) {
                                                    switch (response.code()) {
                                                        case 200:
                                                            Intent intent = new Intent(MainActivity.this, Home_Main.class);
                                                            startActivity(intent);
                                                            finish();
                                                            break;
                                                        default:
                                                            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.같은닉네임존재), Toast.LENGTH_LONG);
                                                            toast.show();
                                                            break;
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<String> call, Throwable t) {
                                                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                                                    toast.show();
                                                }
                                            });
                                        }
                                    });
                                    alert.create();
                                    alert.show();

                                }
                                else {
                                    Intent intent = new Intent(MainActivity.this, Home_Main.class);
                                    startActivity(intent);
                                    finish();
                                }

                                break;
                            default:
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                        toast.show();
                    }
                });


            }
        });


    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(kakaocallback);
    }


    private class SessionCallback implements ISessionCallback{
        @Override
        public void onSessionOpened(){

            redirectSignupActivity();
        }

        @Override
        public void onSessionOpenFailed(KakaoException e){
            if(e != null){
                Logger.e(e);
            }
        }
    }

    protected void redirectSignupActivity(){
        final Intent intent = new Intent(MainActivity.this, KakaoSignupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

}



