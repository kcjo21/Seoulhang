package com.hbag.seoulhang.home_package.home_fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hbag.seoulhang.R;
import com.hbag.seoulhang.model.retrofit.InventoryDTO;
import com.hbag.seoulhang.model.retrofit.RateDTO;
import com.hbag.seoulhang.network.Ipm;
import com.hbag.seoulhang.network.NetworkClient;
import com.hbag.seoulhang.joinmanage_package.login_package.UserProfileData_singleton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cwh62 on 2017-04-11.
 */

public class Frag_Info extends Fragment
{
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    UserProfileData_singleton profile;
    ArrayList<MyData_I> myDataset;
    NetworkClient networkClient;
    TextView info_name;
    TextView info_point;
    TextView info_hint;
    ImageView info_grade;
    TextView no_quiz;
    public ExpandableListAdapter listAdapter;
    public ExpandableListView expListView;
    public ArrayList<MyData_G> groupData;
    private ArrayList<ArrayList<MyData_H>> childData;
    private List<RateDTO> rates;
    Ipm ipm;
    Methods methods;

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
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.frag_info,container, false);
        final View header = getActivity().getLayoutInflater().inflate(R.layout.exl_header, null);
        expListView = (ExpandableListView)layout.findViewById(R.id.exl); // 확장 리스트뷰를 가져온다.
        profile = UserProfileData_singleton.getInstance();
        expListView.addHeaderView(header);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.rvi);
        final String loginid = profile.getId();
        ipm = new Ipm();
        info_name = (TextView)layout.findViewById(R.id.info_name);
        info_point = (TextView)layout.findViewById(R.id.info_point);
        info_hint = (TextView)layout.findViewById(R.id.info_hint);
        info_grade = (ImageView)layout.findViewById(R.id.info_grade);
        no_quiz = (TextView)layout.findViewById(R.id.no_quiz);
        String ip = ipm.getip();


        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        myDataset=new ArrayList<>();
        mAdapter = new RecyclerAdapter_I(myDataset);
        mRecyclerView.setAdapter(mAdapter);


        myDataset.add(new MyData_I("12312","123123","12123123"));
        myDataset.add(new MyData_I("12312","123123","12123123"));
        myDataset.add(new MyData_I("12312","123123","12123123"));
        myDataset.add(new MyData_I("12312","123123","12123123"));
        myDataset.add(new MyData_I("12312","123123","12123123"));
        myDataset.add(new MyData_I("12312","123123","12123123"));
        myDataset.add(new MyData_I("12312","123123","12123123"));
        myDataset.add(new MyData_I("12312","123123","12123123"));
        myDataset.add(new MyData_I("12312","123123","12123123"));
        myDataset.add(new MyData_I("12312","123123","12123123"));
        myDataset.add(new MyData_I("12312","123123","12123123"));
        myDataset.add(new MyData_I("12312","123123","12123123"));
        myDataset.add(new MyData_I("12312","123123","12123123"));






       /* networkClient = NetworkClient.getInstance(ip);
        Log.d("인포",loginid);
        networkClient.getfinishitem(loginid, new Callback<List<InventoryDTO>>() {
            @Override
            public void onResponse(Call<List<InventoryDTO>> call, Response<List<InventoryDTO>> response) {
                Log.d("인포", "123");
                switch (response.code()) {
                    case 200:
                        Log.d("인포", "어댑터 세팅");
                        infos = response.body();

                        mRecyclerView.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(getActivity());
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        myDataset=new ArrayList<>();
                        mAdapter = new RecyclerAdapter_I(myDataset);
                        mRecyclerView.setAdapter(mAdapter);

                        for (int i = 0; i < infos.size(); i++) {
                            InventoryDTO inventoryDTO = infos.get(i);

                            String qcode = Integer.toString(inventoryDTO.getQuestioncode());
                            String rname = inventoryDTO.getRegionname();
                            String q = inventoryDTO.getQuestion();
                            if(!qcode.equals("0"))
                            myDataset.add(new MyData_I(qcode,rname,q));
                        }
                        InventoryDTO check=infos.get(0);
                        if(check.getQuestioncode()==0&&check.getRegionname().equals("0"))
                            no_quiz.setVisibility(View.VISIBLE);
                        else no_quiz.setVisibility(View.GONE);

                        InventoryDTO inventoryDTO = infos.get(0);
                        String grade_i = inventoryDTO.getGrade();
                        String playername = inventoryDTO.getPlayername();
                        String point_i = Integer.toString(inventoryDTO.getPoint());
                        String hint_i = Integer.toString(inventoryDTO.getHint());

                        info_name.setText(playername);
                        info_point.setText(point_i);
                        info_hint.setText(hint_i);
                        switch (grade_i){
                            case "Unrank":
                                info_grade.setImageResource(R.drawable.tier_unrank);
                                break;
                            case "Bronze":
                                info_grade.setImageResource(R.drawable.tier_bronze);
                                break;
                            case "Silver":
                                info_grade.setImageResource(R.drawable.tier_silver);
                                break;
                            case "Gold":
                                info_grade.setImageResource(R.drawable.tier_gold);
                                break;
                            case "Platinum":
                                info_grade.setImageResource(R.drawable.tier_platinum);
                                break;
                            case "Diamond":
                                info_grade.setImageResource(R.drawable.tier_diamond);
                                break;
                            case "Master":
                                info_grade.setImageResource(R.drawable.tier_master);
                                break;
                            case "Challenger":
                                info_grade.setImageResource(R.drawable.tier_challenger);
                                break;
                        }
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<InventoryDTO>> call, Throwable t) {
                Log.e("ACC", "s?? " + t.getMessage());

            }
        });*/

        networkClient = NetworkClient.getInstance(ip);

        networkClient.getrate(loginid, new Callback<List<RateDTO>>() {
            @Override
            public void onResponse(Call<List<RateDTO>> call, Response<List<RateDTO>> response) {
                Log.d("인포", "123");
                switch (response.code()) {
                    case 200:
                        Log.d("인포", "어댑터 세팅");
                        rates = response.body();
                        groupData = new ArrayList<>();
                        childData = new ArrayList<>();
                        methods = new Methods();

                        for (int i = 0; i < rates.size(); i++) {

                            RateDTO rateDTO = rates.get(i);

                            int rates_q = rateDTO.getRate();
                            String regionname = rateDTO.getRegionname();
                            String exp = rateDTO.getExplain();
                            int regioncode = rateDTO.getRegioncode();
                            int image = methods.imageSelector_2(regioncode);
                            // 그룹 생성
                            groupData.add(new MyData_G(regionname));
                            childData.add(new ArrayList<MyData_H>());
                            // 차일드 생성
                            Activity activity = getActivity();
                            if (isAdded() && activity != null) {
                                childData.get(i).add(new MyData_H(getResources().getString(R.string.달성률) + " " + rates_q + "%", rates_q, R.drawable.new_eight, exp, expListView));
                            }
                            Log.e("확인",""+rates_q);

                        }
                        listAdapter = new ExpandableListViewAdapter(getContext(), groupData, childData);
                        expListView.setAdapter(listAdapter);// 리스트어댑터 세팅

                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<RateDTO>> call, Throwable t) {
                Log.e("ACC", "s?? " + t.getMessage());

            }
        });
        return layout;
    }



}