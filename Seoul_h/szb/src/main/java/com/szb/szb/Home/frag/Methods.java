package com.szb.szb.Home.frag;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.szb.szb.R;

/**
 * Created by cwh62 on 2017-05-15.
 */

public class Methods {

    private AppCompatDialog progressDialog;

    public int imageSelector(String region){
        switch (region){
            case "서울역":
                return R.drawable.one;
            case "삼성역":
                return R.drawable.two;
            case "명동역":
                return R.drawable.three;
            case "안국역":
                return R.drawable.four;
            case "혜화역":
                return R.drawable.five;
            case "인천역":
                return R.drawable.six;
            case "잠실역":
                return R.drawable.seven;
            case "여의도역":
                return R.drawable.eight;
            case "동대문역":
                return R.drawable.nine;
            case "Seoul Station":
                return R.drawable.one;
            case "Samsung Station":
                return R.drawable.two;
            case "Myeongdong Station":
                return R.drawable.three;
            case "Anguk Station":
                return R.drawable.four;
            case "Hyehwa Station":
                return R.drawable.five;
            case "Incheon Station":
                return R.drawable.six;
            case "Jamsil Station":
                return R.drawable.seven;
            case "Yeouido Station":
                return R.drawable.eight;
            case "Dongdaemun Station":
                return R.drawable.nine;
            default:
                return 0;
        }

    }
    public int imageSelector_2(String region){
        switch (region){
            case "서울역":
                return R.drawable.new_one;
            case "삼성역":
                return R.drawable.new_two;
            case "명동역":
                return R.drawable.new_three;
            case "안국역":
                return R.drawable.new_four;
            case "혜화역":
                return R.drawable.new_five;
            case "인천역":
                return R.drawable.new_six;
            case "잠실역":
                return R.drawable.new_seven;
            case "여의도역":
                return R.drawable.new_eight;
            case "동대문역":
                return R.drawable.new_nine;
            case "Seoul Station":
                return R.drawable.new_one;
            case "Samsung Station":
                return R.drawable.new_two;
            case "Myeongdong Station":
                return R.drawable.new_three;
            case "Anguk Station":
                return R.drawable.new_four;
            case "Hyehwa Station":
                return R.drawable.new_five;
            case "Incheon Station":
                return R.drawable.new_six;
            case "Jamsil Station":
                return R.drawable.new_seven;
            case "Yeouido Station":
                return R.drawable.new_eight;
            case "Dongdaemun Station":
                return R.drawable.new_nine;
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
