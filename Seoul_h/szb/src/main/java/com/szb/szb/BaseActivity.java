package com.szb.szb;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by cwh62 on 2017-09-10.
 */

public class BaseActivity extends AppCompatActivity {


    public void progressON() {
        BaseApplication.getInstance().progressON(this, null);
    }

    public void progressON(String message) {
        BaseApplication.getInstance().progressON(this, message);
    }

    public void progressOFF() {
        BaseApplication.getInstance().progressOFF();
    }

}
