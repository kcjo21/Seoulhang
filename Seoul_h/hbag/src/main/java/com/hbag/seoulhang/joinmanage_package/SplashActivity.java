package com.hbag.seoulhang.joinmanage_package;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.hbag.seoulhang.R;
import com.hbag.seoulhang.model.database.Player;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.splash);
        super.onCreate(savedInstanceState);
        ImageView run = (ImageView)findViewById(R.id.running_man);
        ImageView load =  (ImageView)findViewById(R.id.load);
        GlideDrawableImageViewTarget ivt_1 = new GlideDrawableImageViewTarget(run);
        GlideDrawableImageViewTarget ivt_2 = new GlideDrawableImageViewTarget(load);
        Glide.with(this).load(R.raw.run_gbg).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivt_1);
        Glide.with(this).load(R.raw.title_loading).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivt_2);
        Handler s = new Handler();
        s.postDelayed(new splashhanddler(),2000);

        SharedPreferences pref = getSharedPreferences("lang",MODE_PRIVATE);


        if(pref.getInt("setlang",0)==0){
            Locale myLocale = new Locale("ko");
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
        }
        else {
            Locale myLocale = new Locale("en");
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
        }
    }
    private class splashhanddler implements Runnable{
        @Override
        public void run() {
            SharedPreferences first = getSharedPreferences("a",MODE_PRIVATE);
            if(first.getInt("First",0)!=1){
                startActivity(new Intent(getApplication(),SettingActivity.class));
                SplashActivity.this.finish();
            }
            else
                startActivity(new Intent(getApplication(),MainActivity.class));
                overridePendingTransition(R.anim.center_in,R.anim.center_out);
                SplashActivity.this.finish();

        }
    }
}



