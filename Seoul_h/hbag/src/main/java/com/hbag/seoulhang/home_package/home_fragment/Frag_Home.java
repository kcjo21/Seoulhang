package com.hbag.seoulhang.home_package.home_fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hbag.seoulhang.appbase_package.GameActivity;
import com.hbag.seoulhang.home_package.Home_Main;
import com.hbag.seoulhang.R;
import com.hbag.seoulhang.map_package.GooglemapsActivity;
import com.hbag.seoulhang.model.retrofit.InventoryDTO;
import com.hbag.seoulhang.model.retrofit.ItemDTO;
import com.hbag.seoulhang.network.Ipm;
import com.hbag.seoulhang.network.NetworkClient;
import com.hbag.seoulhang.joinmanage_package.TutorialActivity;
import com.hbag.seoulhang.joinmanage_package.login_package.UserProfileData_singleton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


/**
 * Created by cwh62 on 2017-04-11.
 */

public class Frag_Home extends BaseFragment {

    Home_Main home_main;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MyData> myDataset;
    private List<ItemDTO> inventories;
    private List<InventoryDTO> infos;
    NetworkClient networkClient;
    Methods methods;
    TextView no_quiz;
    Button submit;
    Button gethint;
    EditText answer;
    TextView hello;
    Ipm ipm;
    Handler handler;
    private long mLastClickTime = 0;
    UserProfileData_singleton profile;


    public Frag_Home() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.frag_home, container, false);

        profile = UserProfileData_singleton.getInstance();
        ipm = new Ipm();
        String ip = ipm.getip();
        final String loginid = profile.getId();
        ImageView map_b = (ImageView)layout.findViewById(R.id.mapview);
        ImageView camera_b = (ImageView) layout.findViewById(R.id.cameraon_b);
        ImageView tuto = (ImageView)layout.findViewById(R.id.tutorial_b);
        ImageView subway_b = (ImageView)layout.findViewById(R.id.subwayview);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.home_rev);
        answer = (EditText) layout.findViewById(R.id.Answer);
        submit = (Button) layout.findViewById(R.id.Submit);
        gethint = (Button) layout.findViewById(R.id.button_hint);
        hello = (TextView)layout.findViewById(R.id.hello);
        home_main = (Home_Main)getActivity();
        no_quiz = (TextView)layout.findViewById(R.id.no_quiz);
        handler = new Handler(Looper.getMainLooper());

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity()){
            @Override
            public boolean canScrollVertically(){
                return false;
            }
        };


        networkClient = NetworkClient.getInstance(ip);
        networkClient.getstartitem(loginid, new Callback<List<ItemDTO>>() {
            @Override
            public void onResponse(Call<List<ItemDTO>> call, Response<List<ItemDTO>> response) {
                Log.d("퀴즈", "123");
                switch (response.code()) {
                    case 200:
                        Log.d("퀴즈", "어댑터 세팅");
                        inventories = response.body();
                        mRecyclerView.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(getActivity()){
                            @Override
                            public boolean canScrollVertically(){
                                return false;
                            }
                        };                                                       //리사이클러뷰가 단독 아이템이기 때문에 스크롤링을 방지한다.
                        mRecyclerView.setLayoutManager(mLayoutManager);  //레이아웃 매니저를 사용한다.
                        myDataset = new ArrayList<>();
                        mAdapter = new RecyclerAdapter(myDataset);
                        mRecyclerView.setAdapter(mAdapter);       //어탭더 정의
                        mRecyclerView.setNestedScrollingEnabled(false);

                        methods = new Methods();

                            ItemDTO itemDTO = inventories.get(0);

                        if((itemDTO.getQuestioncode()==0&&itemDTO.getRegionname().equals("0"))) {
                            no_quiz.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.GONE);
                        }
                        else{
                            mRecyclerView.setVisibility(View.VISIBLE); //만약 풀 수 있는 문제가 없다면 "문제가 없습니다" 텍스트를 띄워준다.
                            no_quiz.setVisibility(View.GONE);
                        }
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
                            onResume();





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

        networkClient = NetworkClient.getInstance(ip);
        Log.d("인포",loginid);
        networkClient.getfinishitem(loginid, new Callback<List<InventoryDTO>>() {
            @Override
            public void onResponse(Call<List<InventoryDTO>> call, Response<List<InventoryDTO>> response) {
                Log.d("인포", "123");
                switch (response.code()) {
                    case 200:
                        Log.d("인포", "어댑터 세팅");
                        infos = response.body();

                        InventoryDTO inventoryDTO = infos.get(0);
                        String playername = inventoryDTO.getPlayername();
                        Activity activity = getActivity();
                        if (isAdded() && activity != null) {
                            hello.setText(getResources().getString(R.string.환영,playername));
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
        });


        camera_b.setOnClickListener(new View.OnClickListener() {
            String playerid = profile.getId();


            @Override
            public void onClick(View v) {
                Log.d("게임아이디frag",playerid);
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(getActivity(),GameActivity.class);
                intent.putExtra("playerid",playerid);
                getActivity().startActivityForResult(intent,0);
            }
        });
        map_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                final Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() { // Thread 로 작업할 내용을 구현
                        Looper.prepare();
                        progressON();
                        Log.d("쓰레드 시작","시작");
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getActivity(),GooglemapsActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        });
                        Looper.loop();
                        handler.getLooper().quit();
                    }
                });
                t.start(); // 쓰레드 시작i
            }
        });
        tuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(getActivity(), TutorialActivity.class);
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                getActivity().finish();

            }
        });


        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        return layout;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        final String playerid=profile.getId();
        String ip =ipm.getip();
        Log.e("아이디ㅇㅇ",playerid);

        if(requestCode == 0){
            if(resultCode == RESULT_OK) {
                networkClient = NetworkClient.getInstance(ip);
                networkClient.checkplayer(playerid,new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        Log.e("아이디dd",playerid);
                        switch (response.code()){
                            case 200:
                                int check = response.body();
                                Log.e("확인","check"+check);
                                if (check == 1) {  //Responce의값이 1일 때 새 문제 획득
                                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(getActivity(),Home_Main.class);
                                            startActivity(intent);
                                            getActivity().finish();
                                            dialog.dismiss();     //닫기
                                        }
                                    });
                                    alert.setMessage(R.string.새문제);
                                    alert.show();
                                } else if (check == 0) { //Responce값이 2일 때 이미 가지고 있는 문제
                                    Log.e("확인","응"+check);
                                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();     //닫기
                                        }
                                    });
                                    alert.setMessage(R.string.이미가지고있는문제);
                                    alert.show();
                                }


                                break;
                            default:
                                Log.e("TAG", "다른 아이디");
                                Toast toast = Toast.makeText(getActivity(), getResources().getString(R.string.please_check_id), Toast.LENGTH_LONG);
                                toast.show();
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Log.e("ACC","s?? " + t.getMessage());

                    }
                });


            }

        }

    }

}