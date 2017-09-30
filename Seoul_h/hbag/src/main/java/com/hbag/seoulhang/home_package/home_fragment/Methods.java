package com.hbag.seoulhang.home_package.home_fragment;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.hbag.seoulhang.R;

/**
 * Created by cwh62 on 2017-05-15.
 */

public class Methods {

    private AppCompatDialog progressDialog;

    public int imageSelector(int region){
        switch (region){
            case 1:
                return R.drawable.one;
            case 2:
                return R.drawable.one;
            case 3:
                return R.drawable.one;
            case 4:
                return R.drawable.one;
            case 5:
                return R.drawable.one;
            case 6:
                return R.drawable.one;
            case 7:
                return R.drawable.one;
            case 8:
                return R.drawable.one;
            case 9:
                return R.drawable.one;
            case 10:
                return R.drawable.one;
            case 11:
                return R.drawable.one;
            case 12:
                return R.drawable.one;
            case 13:
                return R.drawable.one;
            case 14:
                return R.drawable.one;
            case 15:
                return R.drawable.one;
            case 16:
                return R.drawable.one;
            case 17:
                return R.drawable.one;
            case 18:
                return R.drawable.one;
            case 19:
                return R.drawable.one;
            case 20:
                return R.drawable.one;
            case 21:
                return R.drawable.one;
            case 22:
                return R.drawable.one;
            case 23:
                return R.drawable.one;
            case 24:
                return R.drawable.one;
            case 25:
                return R.drawable.one;
            default:
                return 0;
        }

    }
    public int imageSelector_2(int region){
        switch (region){
            case 1:
                return R.drawable.one;
            case 2:
                return R.drawable.one;
            case 3:
                return R.drawable.one;
            case 4:
                return R.drawable.one;
            case 5:
                return R.drawable.one;
            case 6:
                return R.drawable.one;
            case 7:
                return R.drawable.one;
            case 8:
                return R.drawable.one;
            case 9:
                return R.drawable.one;
            case 10:
                return R.drawable.one;
            case 11:
                return R.drawable.one;
            case 12:
                return R.drawable.one;
            case 13:
                return R.drawable.one;
            case 14:
                return R.drawable.one;
            case 15:
                return R.drawable.one;
            case 16:
                return R.drawable.one;
            case 17:
                return R.drawable.one;
            case 18:
                return R.drawable.one;
            case 19:
                return R.drawable.one;
            case 20:
                return R.drawable.one;
            case 21:
                return R.drawable.one;
            case 22:
                return R.drawable.one;
            case 23:
                return R.drawable.one;
            case 24:
                return R.drawable.one;
            case 25:
                return R.drawable.one;
            default:
                return 0;
        }

    }
    public void progressON(Activity activity, String message) {

        if (activity == null || activity.isFinishing()) {
            return;
        }


        if (progressDialog != null && progressDialog.isShowing()) {
            progressSET(message);
        } else {

            progressDialog = new AppCompatDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.progress_loading);
            progressDialog.show();

        }


        final ImageView img_loading_frame = (ImageView) progressDialog.findViewById(R.id.runningman_iv);
        final AnimationDrawable frameAnimation = (AnimationDrawable) img_loading_frame.getBackground();
        img_loading_frame.post(new Runnable() {
            @Override
            public void run() {
                frameAnimation.start();
            }
        });

        TextView tv_progress_message = (TextView) progressDialog.findViewById(R.id.loading_message);
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message.setText(message);
        }


    }

    public void progressSET(String message) {

        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }


        TextView tv_progress_message = (TextView) progressDialog.findViewById(R.id.loading_message);
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message.setText(message);
        }

    }

    public void progressOFF() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
