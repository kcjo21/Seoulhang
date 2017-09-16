package com.szb.szb.Home.frag;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.szb.szb.R;
import com.szb.szb.model.retrofit.ItemDTO;
import com.szb.szb.network.Ipm;
import com.szb.szb.network.NetworkClient;
import com.szb.szb.start_pack.loginpackage.Logm;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cwh62 on 2017-04-11.
 */

public class Frag_Quiz extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MyData> myDataset;
    Button submit;
    Button gethint;
    EditText answer;
    LinearLayout noquiz;
    NetworkClient networkClient;
    private List<ItemDTO> inventories;
    Methods methods;
    Ipm ipm;
    Logm logm;


    public static Activity activity; //정적 프래그먼트 Context데이터

    public Frag_Quiz() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("퀴즈","");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.frag_quiz, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.rv);
        answer = (EditText) layout.findViewById(R.id.Answer);
        submit = (Button) layout.findViewById(R.id.Submit);
        gethint = (Button) layout.findViewById(R.id.button_hint);
        noquiz = (LinearLayout) layout.findViewById(R.id.no_quizlay);
        ipm = new Ipm();
        logm = new Logm();
        activity = getActivity();  //클래스로 전송하기위해 프래그먼트 Activity 저장
        String ip = ipm.getip();
        final String loginid = logm.getPlayerid();
        networkClient = NetworkClient.getInstance(ip);
        Log.e("ACC", "TEAM id IS !!! frag quiz" + loginid);

       /* mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);  //레이아웃 매니저를 사용한다.
        myDataset = new ArrayList<>();
        mAdapter = new RecyclerAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);       //어탭더 정의

        myDataset.add(new MyData("1","23","11", R.drawable.app_icon, "123","123","1234",networkClient,getActivity(), "1234", mRecyclerView));
        noquiz.setVisibility(View.GONE);*/



        networkClient.getstartitem(loginid, new Callback<List<ItemDTO>>() {
            @Override
            public void onResponse(Call<List<ItemDTO>> call, Response<List<ItemDTO>> response) {
                Log.d("퀴즈", "123");
                switch (response.code()) {
                    case 200:
                        Log.d("퀴즈", "어댑터 세팅");
                        inventories = response.body();
                        mRecyclerView.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(getActivity());
                        mRecyclerView.setLayoutManager(mLayoutManager);  //레이아웃 매니저를 사용한다.
                        myDataset = new ArrayList<>();
                        mAdapter = new RecyclerAdapter(myDataset);
                        mRecyclerView.setAdapter(mAdapter);       //어탭더 정의

                        ItemDTO check=inventories.get(0);
                        if(check.getQuestioncode()==0&&check.getRegionname().equals("0"))
                        noquiz.setVisibility(View.VISIBLE); //만약 풀 수 있는 문제가 없다면 "문제가 없습니다" 텍스트를 띄워준다.
                        else
                            noquiz.setVisibility(View.GONE);

                        methods = new Methods();

                        for (int i = 1; i < inventories.size(); i++) {
                            ItemDTO itemDTO = inventories.get(i);
                            String qt = itemDTO.getquestiontype();
                            String qc = Integer.toString(itemDTO.getQuestioncode()); //문제번호
                            String rn = itemDTO.getRegionname(); //지역번호
                            String quiz = itemDTO.getQuestion(); //문제
                            String answer_q = itemDTO.getAnswer(); //정답
                            String hint_q = itemDTO.getHint();
                            int image = methods.imageSelector(rn);

                            myDataset.add(new MyData(qt,qc,rn, image, quiz,answer_q,loginid,networkClient,getActivity(), hint_q, mRecyclerView));//각 인자들을 어댑터클래스의 데이터베이스에 전달.

                            Log.d("퀴즈번호", "" + itemDTO.getQuestioncode());
                            Log.d("퀴즈지역", "" + itemDTO.getRegionname());
                            Log.d("퀴즈", itemDTO.getQuestion());
                            Log.d("퀴즈타입",itemDTO.getquestiontype());
                            onResume();

                        }



                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<ItemDTO>> call, Throwable t) {
                Log.e("ACC", "s?? " + t.getMessage());

            }
        });

        return layout;


    }


}