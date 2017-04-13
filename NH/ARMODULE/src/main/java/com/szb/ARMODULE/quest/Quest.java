package com.szb.ARMODULE.quest;

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

public class Quest extends Fragment {

    public static Fragment newInstance(Context context) {
        Fragment questfrag = new Quest();
        Log.d("debug", "quest fragment under construction");
        return questfrag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_quest, null);

        Log.d("debug", "quest fragment created");
        return root;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("debug", "quest fragment destroyed");
    }

    @Override
    public void onDetach(){
        super.onDetach();
        Log.d("debug", "setting fragment Detached");
    }
}



