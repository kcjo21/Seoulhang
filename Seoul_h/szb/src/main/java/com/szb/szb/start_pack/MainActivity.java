package com.szb.szb.start_pack;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
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

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.szb.szb.BaseActivity;
import com.szb.szb.Home.BackPressCloseHandler;
import com.szb.szb.Home.Home_Main;
import com.szb.szb.R;
import com.szb.szb.model.retrofit.PlayerDTO;
import com.szb.szb.network.Ipm;
import com.szb.szb.network.NetworkClient;
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


public class MainActivity extends BaseActivity implements
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "MainActivity";
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;

    LinearLayout login_lay;
    LinearLayout regist_lay;
    LinearLayout syncbt_lay;
    LinearLayout loginbt_lay;
    ImageView title_login;
    Button login;
    Button set;
    Button logoutsync;
    Button bt_facebookLogin;
    Button bt_googleSignbt;
    EditText loginId_tv;
    TextView join;
    TextView findid;
    TextView findPass;
    EditText password;
    String loginid;
    String LoginTypeChecker;
    NetworkClient networkClient;
    Ipm ipm;
    Logm logm;
    private BackPressCloseHandler backPressCloseHandler;
    CallbackManager callbackManager;
    InputMethodManager keyboard;
    UserProfileData userProfileData;
    private long mLastClickTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callbackManager = CallbackManager.Factory.create();
        login_lay = (LinearLayout) findViewById(R.id.text_log_lay);
        regist_lay = (LinearLayout) findViewById(R.id.register_lay);
        syncbt_lay = (LinearLayout) findViewById(R.id.syncbtset_lay);
        loginbt_lay = (LinearLayout) findViewById(R.id.loginbutton_lay);
        title_login = (ImageView) findViewById(R.id.iv_title);
        password = (EditText) findViewById(R.id.loginPass);
        loginId_tv = (EditText) findViewById(R.id.loginId);
        join = (TextView) findViewById(R.id.join_tm);
        findid = (TextView) findViewById(R.id.find_id);
        findPass = (TextView) findViewById(R.id.find_pass_t);
        login = (Button) findViewById(R.id.Login);
        set = (Button) findViewById(R.id.bt_set);
        logoutsync = (Button) findViewById(R.id.bt_logout_sync);
        bt_facebookLogin = (Button) findViewById(R.id.bt_facebookLogin);
        bt_googleSignbt = (Button) findViewById(R.id.bt_googleLogin);
        ipm = new Ipm();
        logm = new Logm();
        userProfileData = new UserProfileData();
        backPressCloseHandler = new BackPressCloseHandler(this);
        final String ip = ipm.getip();
        networkClient = NetworkClient.getInstance(ip);
        login = (Button) findViewById(R.id.Login);
        keyboard = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        final SharedPreferences sharedPreferences = getSharedPreferences("log",MODE_PRIVATE);
        final String loginPref = sharedPreferences.getString("logintype","logout");
        Log.d("Login",loginPref);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder
                (GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        bt_googleSignbt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 600){
                    return;
                }
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });



        bt_facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 600){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();


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

                                                }
                                            }
                                        });
                                Bundle parameters = new Bundle();
                                parameters.putString("fields","id,name,email");
                                request.setParameters(parameters);
                                request.executeAsync();
                                Log.d("페이스북 계정 연동 : ", "성공");
                                UI_State1(1);
                            }

                            @Override
                            public void onCancel() {

                                Log.e("페이스북 계정 연동 : ", "취소됨");
                            }
                            @Override
                            public void onError(FacebookException exception) {
                                Log.e("페이스북 계정 연동 : ", "에러 " + exception.getLocalizedMessage());
                            }
                        });
                LoginTypeChecker = "facebook";
                editor.putString("logintype",LoginTypeChecker);   //로그인 상태 저장
                editor.apply();
            }
        });

        logoutsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 600){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Log.d("페이스북 로그아웃",LoginTypeChecker);
                if(LoginTypeChecker.equals("facebook")){
                    LoginManager.getInstance().logOut();

                }
                else if(LoginTypeChecker.equals("google")){
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(@NonNull Status status) {
                                    Log.d("구글 로그아웃",LoginTypeChecker);
                                }
                            }
                    );
                }
                UI_State1(0);
                editor.putString("logintype","logout");   //로그인 상태 로그아웃으로 저장
                editor.apply();
                LoginTypeChecker = "logout";
            }

        });


        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//게임자체 계정으로 로그인합니다.
                if (SystemClock.elapsedRealtime() - mLastClickTime < 600){
                    return;
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(password.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(loginId_tv.getWindowToken(), 0);

                mLastClickTime = SystemClock.elapsedRealtime();
                final String sLoginid = loginId_tv.getText().toString();
                final String sPassword = password.getText().toString();
                loginid = sLoginid;
                logm.setPlayerid(sLoginid);

                Log.e("아이디", sLoginid);
                Log.e("비밀번호", sPassword);

                Log.e("ACC", "TEAM id IS !!! " + loginid);
               // progressON(getResources().getString(R.string.Loading)); //응답대기 애니메이션

                networkClient.login(sLoginid, sPassword, new Callback<PlayerDTO>() {
                    @Override
                    public void onResponse(Call<PlayerDTO> call, Response<PlayerDTO> response) {
                        Log.e("아이디", sLoginid);
                        Log.e("비밀번호", sPassword);
                        switch (response.code()) {
                            case 200:
                                LoginTypeChecker = "normal";
                                editor.putString("logintype","normal");
                                editor.apply();
                                UI_State1(1);
                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.loginsuccess,loginid), Toast.LENGTH_LONG);
                                toast.show();
                                editor.putString("id",sLoginid);
                                editor.apply();             //디바이스 내부에 아이디를 저장시킵니다.
                                editor.apply();  //로그인 타입 상태를 저장시킵니다.
                              //  progressOFF();
                                break;

                            default:
                                Log.e("TAG", "다른 아이디");
                               // progressOFF();
                                Toast toast_2 = Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_check_id), Toast.LENGTH_LONG);
                                toast_2.show();
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<PlayerDTO> call, Throwable t) {
                        Log.e("ACC", "s?? " + t.getMessage());
                      //  progressOFF();
                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                        toast.show();

                    }
                });
            }
        });





        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 600){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                loginId_tv.setText("");
                password.setText("");
                Intent intent = new Intent(MainActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });

        findid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 600){
                    return;
                }
                loginId_tv.setText("");
                password.setText("");
                mLastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(MainActivity.this, FindIdActivity.class);
                startActivity(intent);
            }
        });
        findPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 600){
                    return;
                }
                loginId_tv.setText("");
                password.setText("");
                mLastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(MainActivity.this, FindPassActivity.class);
                startActivity(intent);
            }
        });


        login.setOnClickListener(new View.OnClickListener() { //페이스북 또는 네이버로 시작버튼 누를시 회원가입처리

            @Override
            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 300){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();


                String email = userProfileData.getEmail();
                String name = userProfileData.getName();

                switch (LoginTypeChecker){
                    case "normal":
                        loginid = sharedPreferences.getString("id","");
                        logm.setPlayerid(loginid);
                        break;
                    case "facebook":
                        loginid = logm.getPlayerid();
                        networkClient.getjoin(loginid, " ", name, email, LoginTypeChecker, new Callback<String>() {
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
                        break;
                    case "google":
                        Log.d("구글",name);
                        loginid = userProfileData.getId();
                        logm.setPlayerid(loginid);
                        networkClient.getjoin(loginid, " ", name, email, LoginTypeChecker, new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                switch (response.code()){
                                    case 200:
                                        Log.d("구글회원가입",loginid);
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
                        break;
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

        if(!loginPref.equals("logout")){   //이전 접속데이터로 즉시 로그인한다.
            LoginTypeChecker = loginPref;
            switch (LoginTypeChecker){
                case "facebook":
                    bt_facebookLogin.performClick();
                    break;
                case "google":
                    Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                    startActivityForResult(intent, RC_SIGN_IN);
                    UI_State1(1);
                    break;
                case "normal":
                    String sLoginid= sharedPreferences.getString("id","");
                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.loginsuccess,sLoginid), Toast.LENGTH_LONG);
                    toast.show();
                    UI_State1(1);
                    break;
                default:
                    break;
            }
        }
        else {
            LoginTypeChecker = loginPref;

        }



    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){                               //구글에서 유저정보를 받아온다.
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount acct = result.getSignInAccount();
                userProfileData.setName(acct.getDisplayName());
                userProfileData.setEmail(acct.getEmail());
                userProfileData.setId(acct.getId());

                Log.d("구글로그인",userProfileData.getId()
                        +" "+userProfileData.getName()
                        +" "+userProfileData.getEmail());
                LoginTypeChecker = "google";
                SharedPreferences sharedPreferences = getSharedPreferences("log",MODE_PRIVATE);
                SharedPreferences.Editor editor =  sharedPreferences.edit();
                editor.putString("logintype",LoginTypeChecker);   //로그인 상태 저장
                editor.apply();
                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.loginsuccess,userProfileData.getName()), Toast.LENGTH_LONG);
                toast.show();
                UI_State1(1);
            }
            else{
                Log.d("구글로그인","실패");
            }
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {   //구글 연동 실패 시 호출
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }


    public void UI_State1(int state) {
        switch (state) {
            case 0:                                           //초기 로그인선택화면 표시
                syncbt_lay.setVisibility(View.GONE);
                regist_lay.setVisibility(View.VISIBLE);
                login_lay.setVisibility(View.VISIBLE);
                loginbt_lay.setVisibility(View.VISIBLE);
                title_login.setVisibility(View.VISIBLE);
                break;
            case 1:                                           //최종시작화면 표시
                syncbt_lay.setVisibility(View.VISIBLE);
                regist_lay.setVisibility(View.GONE);
                login_lay.setVisibility(View.GONE);
                loginbt_lay.setVisibility(View.GONE);
                title_login.setVisibility(View.GONE);
                break;
            default:
                break;
        }

    }


}



