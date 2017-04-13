package com.szb.ARMODULE.setting;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szb.ARMODULE.R;

/**
 * Created by HwiRiRic on 2017. back_3. 26..
 */

public class Setting extends Fragment{

    public static Fragment newInstance(Context context) {
        Fragment settingfrag = new Setting();
        Log.d("debug", "setting fragment under construction");
        return settingfrag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_setting, null);

        Log.d("debug", "setting fragment created");
        return root;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("debug", "setting fragment destroyed");
    }

    @Override
    public void onDetach(){
        super.onDetach();
        Log.d("debug", "setting fragment Detached");
    }
}



