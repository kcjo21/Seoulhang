package com.szb.ARMODULE.information;

/**
 * Created by HwiRiRic on 2017. back_3. 26..
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szb.ARMODULE.R;

public class Information extends Fragment {

    public static Fragment newInstance(Context context) {
        Fragment infomationfrag = new Information();
        Log.d("debug", "information fragment under construction");
        return infomationfrag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_information, null);

        Log.d("debug", "information fragment created");
        return root;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("debug", "information fragment destroyed");
    }

    @Override
    public void onDetach(){
        super.onDetach();
        Log.d("debug", "information fragment Detached");
    }
}




