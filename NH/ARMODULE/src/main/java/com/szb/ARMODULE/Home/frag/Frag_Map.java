package com.szb.ARMODULE.Home.frag;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szb.ARMODULE.R;


public class Frag_Map extends Fragment {

    public Frag_Map() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_map, container, false);
    }

}
