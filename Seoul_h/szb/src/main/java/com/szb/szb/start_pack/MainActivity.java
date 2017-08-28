package com.szb.szb.start_pack;


import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
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

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends Activity {
    Button Login;
    TextView loginId;
    TextView join;
    TextView findid;
    TextView findPass;
    String loginid;
    NetworkClient networkClient;
    Ipm ipm;
    Logm logm;
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
        join = (TextView)findViewById(R.id.join_tm);
        findid = (TextView)findViewById(R.id.find_id);
        findPass = (TextView)findViewById(R.id.find_pass_t);
        Login = (Button)findViewById(R.id.Login);
        ipm = new Ipm();
        logm = new Logm();



        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,JoinActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FindIdActivity.class);
                startActivity(intent);
                finish();
            }
        });
        findPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FindPassActivity.class);
                startActivity(intent);
                finish();
            }
        });




        Login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                loginid = loginId.getText().toString();
                String ip = ipm.getip();
                logm.setPlayerid(loginid);
                networkClient = NetworkClient.getInstance(ip);
                Log.e("ACC","TEAM id IS !!! "+ loginid);

                loginid = "123123";
                Intent intent = new Intent(MainActivity.this,Home_Main.class);
                startActivity(intent);
                finish();

                 networkClient.login(loginid,new Callback<PlayerDTO>() {
                    @Override
                    public void onResponse(Call<PlayerDTO> call, Response<PlayerDTO> response) {
                        Log.e("아이디",loginid);
                        switch (response.code()){
                            case 200:
                                //json 데이터를 파싱하는 것을 수월하게 해준다.
                                Log.e("case200",loginid);

                                PlayerDTO playerDTO = response.body();

                                Log.e("TAG", "team dto : " + playerDTO.toString());
                                // teamDTO를 이용하여 realm에 team 데이터를 생성한다.
                                Intent intent = new Intent(MainActivity.this,Home_Main.class);
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
                        Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.can_not_connent_to_server), Toast.LENGTH_LONG);
                        toast.show();

                    }
                });
            }
        });


       // Log.e("TAG", "login???? : " + loginmanager.toString());


        ImageView achi = (ImageView)findViewById(R.id.travel);
        Glide.with(this).load(bg).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).animate(R.anim.translate_l).into(achi);

        ImageView run = (ImageView)findViewById(R.id.running_man);
        GlideDrawableImageViewTarget ivt_1 = new GlideDrawableImageViewTarget(run);
        Glide.with(this).load(R.raw.run_gbg).diskCacheStrategy(DiskCacheStrategy.ALL).animate(animationObject).into(ivt_1);

        ImageView sun = (ImageView)findViewById(R.id.the_sun);
        GlideDrawableImageViewTarget imageViewTarget_sun = new GlideDrawableImageViewTarget(sun);
        Glide.with(this).load(R.raw.cartoon_sun_vector).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageViewTarget_sun);

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
            fadeoutX.setDuration(20000);
            fadeoutY.setDuration(20000);
            fadeoutX.start();
            fadeoutY.start();
            view.startAnimation(tran_R);
        }

    };
}
