package com.szb.szb.start_pack;


import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
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
import com.facebook.login.widget.LoginButton;
import com.szb.szb.BaseActivity;
import com.szb.szb.Home.BackPressCloseHandler;
import com.szb.szb.Home.Home_Main;
import com.szb.szb.R;
import com.szb.szb.model.retrofit.PlayerDTO;
import com.szb.szb.network.Ipm;
import com.szb.szb.network.NetworkClient;
import com.szb.szb.start_pack.loginpackage.Logm;
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
    Button Login;
    TextView loginId;
    TextView join;
    TextView findid;
    TextView findPass;
    EditText password;
    Button faceboklogin;
    String loginid;
    String loginpass;
    NetworkClient networkClient;
    Ipm ipm;
    Logm logm;
    private BackPressCloseHandler backPressCloseHandler;
    CallbackManager callbackManager;
    LoginButton loginButton;



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
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        callbackManager=CallbackManager.Factory.create();
        loginId = (TextView) findViewById(R.id.loginId);
        join = (TextView) findViewById(R.id.join_tm);
        findid = (TextView) findViewById(R.id.find_id);
        findPass = (TextView) findViewById(R.id.find_pass_t);
        Login = (Button) findViewById(R.id.Login);
        password = (EditText) findViewById(R.id.loginPass);
        faceboklogin = (Button) findViewById(R.id.bt_facebook);
        ipm = new Ipm();
        logm = new Logm();


      /*  loginButton = (LoginButton)findViewById(R.id.fb_bt); //페이스북 로그인 버튼
        //유저 정보, 친구정보, 이메일 정보등을 수집하기 위해서는 허가(퍼미션)를 받아야 합니다.
        loginButton.setReadPermissions("public_profile", "user_friends","email");


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) { //로그인 성공시 호출되는 메소드
                Log.e("토큰",loginResult.getAccessToken().getToken());
                Log.e("유저아이디",loginResult.getAccessToken().getUserId());
                Log.e("퍼미션 리스트",loginResult.getAccessToken().getPermissions()+"");

                //loginResult.getAccessToken() 정보를 가지고 유저 정보를 가져올수 있습니다.
                GraphRequest request =GraphRequest.newMeRequest(loginResult.getAccessToken() ,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    Log.e("user profile",object.toString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                request.executeAsync();
            }

            @Override
            public void onError(FacebookException error) { }

            @Override
            public void onCancel() { }
        });*/


        faceboklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LoginManager - 요청된 읽기 또는 게시 권한으로 로그인 절차를 시작합니다.
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this,
                        Arrays.asList("public_profile", "user_friends"));
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                Log.e("onSuccess", "onSuccess");
                            }

                            @Override
                            public void onCancel() {
                                Log.e("onCancel", "onCancel");
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                Log.e("onError", "onError " + exception.getLocalizedMessage());
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
                loginid = loginId.getText().toString();
                loginpass = password.getText().toString();
                String ip = ipm.getip();
                logm.setPlayerid(loginid);
                networkClient = NetworkClient.getInstance(ip);
                Log.e("ACC", "TEAM id IS !!! " + loginid);
                // progressON(getResources().getString(R.string.Loading)); //응답대기 애니메이션

                Intent intent = new Intent(MainActivity.this, Home_Main.class);
                startActivity(intent);
                finish();


                networkClient.login(loginid, loginpass, new Callback<PlayerDTO>() {
                    @Override
                    public void onResponse(Call<PlayerDTO> call, Response<PlayerDTO> response) {
                        Log.e("아이디", loginid);
                        Log.e("비밀번호", loginpass);
                        switch (response.code()) {
                            case 200:
                                //json 데이터를 파싱하는 것을 수월하게 해준다.
                                Log.e("case200", loginid);

                                PlayerDTO playerDTO = response.body();

                                Log.e("TAG", "team dto : " + playerDTO.toString());
                                // teamDTO를 이용하여 realm에 team 데이터를 생성한다.
                                Intent intent = new Intent(MainActivity.this, Home_Main.class);
                                startActivity(intent);
                                finish();
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


        // Log.e("TAG", "login???? : " + loginmanager.toString());




        backPressCloseHandler = new BackPressCloseHandler(this);
        Login = (Button) findViewById(R.id.Login);
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
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
