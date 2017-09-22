package com.szb.szb.start_pack;


import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;

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
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends BaseActivity {
    LinearLayout  text_log;
    Button Login;
    Button set;
    EditText loginId_tv;
    TextView join;
    TextView findid;
    TextView findPass;
    EditText password;
    String loginid;
    String loginpass;
    String LoginTypeChecker;

    NetworkClient networkClient;
    Ipm ipm;
    Logm logm;
    private BackPressCloseHandler backPressCloseHandler;
    CallbackManager callbackManager;
    private SessionCallback kakaocallback;
    private Dialog_Login dialog_login;
    InputMethodManager keyboard;
    UserProfileData userProfileData;


    public static final int ran_bg[] = {
            R.drawable.namdaemoon, R.drawable.tajimahal,
            R.drawable.col, R.drawable.tower,
            R.drawable.eiffel, R.drawable.free
    };

    Random random = new Random();
    int rnd = random.nextInt(6);
    int bg = ran_bg[rnd];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_main);
        callbackManager=CallbackManager.Factory.create();
        text_log = (LinearLayout) findViewById(R.id.text_log_lay);
        loginId_tv = (EditText) findViewById(R.id.loginId);
        join = (TextView) findViewById(R.id.join_tm);
        findid = (TextView) findViewById(R.id.find_id);
        findPass = (TextView) findViewById(R.id.find_pass_t);
        Login = (Button) findViewById(R.id.Login);
        password = (EditText) findViewById(R.id.loginPass);
        set = (Button) findViewById(R.id.bt_set);
        ipm = new Ipm();
        logm = new Logm();
        userProfileData = new UserProfileData();
        backPressCloseHandler = new BackPressCloseHandler(this);
        String ip = ipm.getip();
        networkClient = NetworkClient.getInstance(ip);
        Login = (Button) findViewById(R.id.Login);
        keyboard = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);


        LoginManager.getInstance().logOut();

        dialog_login = new Dialog_Login(this, new Dialog_Login.DialogListener() {
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
                                                        LoginTypeChecker = "facebook";
                                                        userProfileData.setEmail(object.getString("email"));
                                                        userProfileData.setName(object.getString("name"));
                                                    }
                                                    catch (Exception e){
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                    Bundle parameters = new Bundle();
                                    parameters.putString("fields","id,name,email");
                                    request.setParameters(parameters);
                                    request.executeAsync();
                                    dialog_login.dismiss();
                                    text_log.setVisibility(View.GONE);
                                    Login.setVisibility(View.VISIBLE);
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
                }
                else if(loginType==1){
                    LoginTypeChecker = "kakao";
                    text_log.setVisibility(View.GONE);
                    Login.setVisibility(View.VISIBLE);
                    kakaocallback = new SessionCallback();
                    Session.getCurrentSession().addCallback(kakaocallback);
                    Session.getCurrentSession().checkAndImplicitOpen();
                    Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL,MainActivity.this);

                    dialog_login.dismiss();
                }
                else{
                    dialog_login.dismiss();
                }



            }
        });

        dialog_login.show();
        dialog_login.setCancelable(false);



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
                                text_log.setVisibility(View.GONE);
                                Login.setVisibility(View.VISIBLE);
                                progressOFF();
                                break;

                            default:
                                Log.e("TAG", "다른 아이디");
                                progressOFF();
                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_check_id), Toast.LENGTH_LONG);
                                toast.show();
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


        Login.setOnClickListener(new View.OnClickListener() {

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


    Animation tran_R;

    ViewPropertyAnimation.Animator animationObject = new ViewPropertyAnimation.Animator() {
        @Override
        public void animate(final View view) {
            tran_R = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_r);
            view.setScaleX(3f);
            view.setScaleY(3f);
            ObjectAnimator fadeoutX = ObjectAnimator.ofFloat(view, "ScaleX", 2f, 0f);
            ObjectAnimator fadeoutY = ObjectAnimator.ofFloat(view, "ScaleY", 2f, 0f);
            fadeoutX.setRepeatCount(-1);
            fadeoutY.setRepeatCount(-1);
            fadeoutX.setDuration(20000);
            fadeoutY.setDuration(20000);
            fadeoutX.start();
            fadeoutY.start();
            view.startAnimation(tran_R);
        }

    };

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
