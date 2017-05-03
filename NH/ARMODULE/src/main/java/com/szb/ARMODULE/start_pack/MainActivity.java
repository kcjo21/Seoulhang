package com.szb.ARMODULE.start_pack;


import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.szb.ARMODULE.Home.BackPressCloseHandler;
import com.szb.ARMODULE.Home.Home_Main;
import com.szb.ARMODULE.UnityPlayerActivity;
import com.szb.ARMODULE.R;
import com.szb.ARMODULE.model.retrofit.PlayerDTO;
import com.szb.ARMODULE.network.NetworkClient;
import com.szb.ARMODULE.start_pack.loginpackage.LoginManager;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends Activity {
    Button Login;
    LoginManager loginmanager;
    TextView loginId;
    String loginid;
    NetworkClient networkClient;
    private BackPressCloseHandler backPressCloseHandler;

    public static final int ran_bg[]={
            R.drawable.namdaemoon, R.drawable.tajimahal,
            R.drawable.col, R.drawable.tower,
            R.drawable.eiffel,R.drawable.free
    };

    Random random = new Random();
    int rnd = random.nextInt(6);
    int bg = ran_bg[rnd];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginId = (TextView)findViewById(R.id.loginId);
        loginmanager = LoginManager.getInstance();
        Login = (Button)findViewById(R.id.Login);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginid = loginId.getText().toString();

                networkClient = NetworkClient.getInstance("http://192.168.0.23:5000");

                Log.e("ACC","TEAM id IS !!! "+ loginid);
                networkClient.login(loginid,new Callback<PlayerDTO>() {
                    @Override
                    public void onResponse(Call<PlayerDTO> call, Response<PlayerDTO> response) {
                        switch (response.code()){
                            case 200:
                                //json 데이터를 파싱하는 것을 수월하게 해준다.

                                PlayerDTO playerDTO = response.body();

                                Log.e("TAG", "team dto : " + playerDTO.toString());
                                // teamDTO를 이용하여 realm에 team 데이터를 생성한다.
                                loginmanager.create(playerDTO);
                                Intent intent = new Intent(MainActivity.this,Home_Main.class);
                                intent.putExtra("playerid",loginid);
                                startActivity(intent);
                                finish();
                                break;

                            default:
                                Log.e("TAG", "다른 아이디");
                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_check_id), Toast.LENGTH_LONG);
                                toast.show();
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<PlayerDTO> call, Throwable t) {
                        Log.e("ACC","s?? " + t.getMessage());

                    }
                });
            }
        });


        Log.e("TAG", "login???? : " + loginmanager.toString());


        ImageView achi = (ImageView)findViewById(R.id.travel);
        Glide.with(this).load(bg).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).animate(R.anim.translate_l).into(achi);

        ImageView run = (ImageView)findViewById(R.id.running_man);
        GlideDrawableImageViewTarget ivt_1 = new GlideDrawableImageViewTarget(run);
        Glide.with(this).load(R.raw.run_gbg).diskCacheStrategy(DiskCacheStrategy.ALL).animate(animationObject).into(ivt_1);

        backPressCloseHandler = new BackPressCloseHandler(this);
        Login = (Button)findViewById(R.id.Login);
    }
    @Override public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }



    Animation tran_R;

    ViewPropertyAnimation.Animator animationObject = new ViewPropertyAnimation.Animator() {
        @Override
        public void animate(final View view) {
            tran_R = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_r);
            view.setScaleX(3f);
            view.setScaleY(3f);
            ObjectAnimator fadeoutX = ObjectAnimator.ofFloat(view,"ScaleX",2f,0f);
            ObjectAnimator fadeoutY = ObjectAnimator.ofFloat(view,"ScaleY",2f,0f);
            fadeoutX.setRepeatCount(-1);
            fadeoutY.setRepeatCount(-1);
            fadeoutX.setDuration(25000);
            fadeoutY.setDuration(25000);
            fadeoutX.start();
            fadeoutY.start();
            view.startAnimation(tran_R);
        }

    };
}
