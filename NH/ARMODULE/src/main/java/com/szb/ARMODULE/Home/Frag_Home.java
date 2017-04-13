package com.szb.ARMODULE.Home;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szb.ARMODULE.R;

/**
 * Created by cwh62 on 2017-04-11.
 */

public class Frag_Home extends Fragment
{
    public Frag_Home()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.frag_home,container, false);
        return layout;
    }


}