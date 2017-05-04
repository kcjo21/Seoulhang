package com.szb.ARMODULE.Home.frag;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.view.menu.MenuAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.szb.ARMODULE.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cwh62 on 2017-04-11.
 */

public class Frag_Quiz extends Fragment{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MyData> myDataset;

    public Frag_Quiz()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){super.onCreate(savedInstanceState);}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        RecyclerView layout = (RecyclerView) inflater.inflate(R.layout.frag_quiz,container, false);
        mRecyclerView = (RecyclerView)layout.findViewById(R.id.rv);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);  //레이아웃 매니저를 사용한다.
        myDataset = new ArrayList<>();
        mAdapter = new RecyclerAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);       //어탭더 정의

        myDataset.add(new MyData("번호"+"1","지역"+"본관","본관에 사는 사람은?"));
        myDataset.add(new MyData("번호"+"2","지역"+"정보대","정보대에 있는 사람은?"));
        myDataset.add(new MyData("번호"+"3","지역"+"공대","공대에 있는 사람은?"));
        return layout;

    }

}