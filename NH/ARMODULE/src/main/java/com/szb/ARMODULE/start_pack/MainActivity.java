package com.szb.ARMODULE.start_pack;


import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.szb.ARMODULE.Home.BackPressCloseHandler;
import com.szb.ARMODULE.Home.Home_Main;
import com.szb.ARMODULE.R;

import java.util.Random;


public class MainActivity extends Activity {
    Button btnLogin;
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

        ImageView achi = (ImageView)findViewById(R.id.travel);
        Glide.with(this).load(bg).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).animate(R.anim.translate_l).into(achi);

        ImageView run = (ImageView)findViewById(R.id.running_man);
        GlideDrawableImageViewTarget ivt_1 = new GlideDrawableImageViewTarget(run);
        Glide.with(this).load(R.raw.run_gbg).diskCacheStrategy(DiskCacheStrategy.ALL).animate(animationObject).into(ivt_1);

        backPressCloseHandler = new BackPressCloseHandler(this);
        btnLogin = (Button)findViewById(R.id.Login);


        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, Home_Main.class);
                startActivity(intent);
                finish();
            }
        });

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
