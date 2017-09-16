package com.szb.szb.Home.frag;

import android.app.Fragment;
import android.support.v7.app.AppCompatDialog;


import com.szb.szb.BaseApplication;
import com.szb.szb.R;

/**
 * Created by cwh62 on 2017-09-10.
 */

public class BaseFragment extends android.support.v4.app.Fragment{
    private static final BaseFragment ourInstance = new BaseFragment();

    static BaseFragment getInstance() {
        return ourInstance;
    }

    private AppCompatDialog progressDialog;

    public BaseFragment() {
    }

    public void progressON() { BaseApplication.getInstance().progressON(getActivity(), null); }
    public void progressON(String message) { BaseApplication.getInstance().progressON(getActivity(), message); }
    public void progressOFF() { BaseApplication.getInstance().progressOFF(); }
}
