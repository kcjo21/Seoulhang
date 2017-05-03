package com.szb.ARMODULE.Home.frag;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.szb.ARMODULE.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cwh62 on 2017-04-11.
 */

public class Frag_Quiz extends ListFragment
{
    public Frag_Quiz()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){super.onCreate(savedInstanceState);}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.frag_quiz,container, false);
        return layout;
    }
}