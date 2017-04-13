package com.szb.ARMODULE.Home;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.szb.ARMODULE.R;

/**
 * Created by cwh62 on 2017-04-11.
 */

public class Frag_Info extends Fragment
{
    public Frag_Info()
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
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.frag_info,container, false);
        return layout;
    }


}