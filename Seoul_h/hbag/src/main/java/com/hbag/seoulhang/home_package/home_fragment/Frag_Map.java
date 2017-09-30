package com.hbag.seoulhang.home_package.home_fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hbag.seoulhang.R;

import kr.go.seoul.trafficsubway.TrafficSubwayButtonTypeB;


public class Frag_Map extends Fragment {

    private TrafficSubwayButtonTypeB typeB;
    private String OpenApikey = "6e494a63576b636a35325847444b6e";

    public Frag_Map() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.frag_map, container, false);

        typeB = (TrafficSubwayButtonTypeB)layout.findViewById(R.id.Subway);

        typeB.setOpenAPIKey(OpenApikey);
        typeB.setsubwayLocationAPIKey(OpenApikey);
        return layout;
    }

}
