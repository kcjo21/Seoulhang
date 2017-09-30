package com.hbag.seoulhang.appbase_package;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by cwh62 on 2017-09-10.
 */

public class BaseActivity extends AppCompatActivity {


    public void progressON() {
        RealmInit.getInstance().progressON(this, null);
    }

    public void progressON(String message) {
        RealmInit.getInstance().progressON(this, message);
    }

    public void progressOFF() {
        RealmInit.getInstance().progressOFF();
    }

}
