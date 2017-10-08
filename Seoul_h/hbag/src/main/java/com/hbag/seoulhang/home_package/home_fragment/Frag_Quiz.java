package com.hbag.seoulhang.home_package.home_fragment;

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

import com.hbag.seoulhang.R;
import com.hbag.seoulhang.model.retrofit.ItemDTO;
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

public class Frag_Quiz extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MyData> myDataset;
    UserProfileData_singleton profile;
    Button submit;
    Button gethint;
    EditText answer;
    LinearLayout noquiz;
    NetworkClient networkClient;
    private List<ItemDTO> inventories;
    Methods methods;
    Ipm ipm;


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
        profile = UserProfileData_singleton.getInstance();
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.frag_quiz, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.rv);
        answer = (EditText) layout.findViewById(R.id.Answer);
        submit = (Button) layout.findViewById(R.id.Submit);
        gethint = (Button) layout.findViewById(R.id.button_hint);
        noquiz = (LinearLayout) layout.findViewById(R.id.no_quizlay);
        ipm = new Ipm();
        activity = getActivity();  //클래스로 전송하기위해 프래그먼트 Activity 저장
        String ip = ipm.getip();
        final String loginid = profile.getId();
        networkClient = NetworkClient.getInstance(ip);
        Log.e("ACC", "TEAM id IS !!! frag quiz" + loginid);

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
                            int rc = itemDTO.getRegioncode();
                            String quiz = itemDTO.getQuestion(); //문제
                            String answer_q = itemDTO.getAnswer(); //정답
                            String hint_q = itemDTO.getHint();
                            int image = methods.imageSelector(rc);

                            myDataset.add(new MyData(qt,qc,rn,rc, image, quiz,answer_q,loginid,networkClient,getActivity(), hint_q, mRecyclerView));//각 인자들을 어댑터클래스의 데이터베이스에 전달.

                            Log.d("퀴즈번호", "" + itemDTO.getQuestioncode());
                            Log.d("퀴즈지역", "" + itemDTO.getRegionname());
                            Log.d("퀴즈", itemDTO.getQuestion());
                            Log.d("퀴즈타입",itemDTO.getquestiontype());
                            Log.d("퀴즈정답",answer_q);
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