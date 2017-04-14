package com.szb.ARMODULE.start_pack;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.szb.ARMODULE.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.splash);
        super.onCreate(savedInstanceState);
        ImageView run = (ImageView)findViewById(R.id.run);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(run);
        Glide.with(this).load(R.raw.run_gbg).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageViewTarget);
        Handler s = new Handler();
        s.postDelayed(new splashhanddler(),3000);
    }
    class splashhanddler implements Runnable{
        @Override
        public void run() {
            startActivity(
                new Intent(getApplication(),MainActivity.class));
                SplashActivity.this.finish();

        }
    }
}




