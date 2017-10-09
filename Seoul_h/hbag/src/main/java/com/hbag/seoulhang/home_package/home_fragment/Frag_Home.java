package com.hbag.seoulhang.home_package.home_fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import static android.content.Context.MODE_PRIVATE;


/**
 * Created by cwh62 on 2017-04-11.
 */

public class Frag_Home extends BaseFragment {

    Home_Main home_main;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MyData_T> myDataset;
    private List<ItemDTO> inventories;
    NetworkClient networkClient;
    Methods methods;
    Button submit;
    Button gethint;
    EditText answer;
    ImageView map_b;
    ImageView tuto;
    ImageView camera_b;
    ImageView subway_b;
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
        map_b = (ImageView)layout.findViewById(R.id.mapview);
        camera_b = (ImageView) layout.findViewById(R.id.cameraon_b);
        tuto = (ImageView)layout.findViewById(R.id.tutorial_b);
        subway_b = (ImageView)layout.findViewById(R.id.subwayview);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.home_rev);
        answer = (EditText) layout.findViewById(R.id.Answer);
        submit = (Button) layout.findViewById(R.id.Submit);
        gethint = (Button) layout.findViewById(R.id.button_hint);
        home_main = (Home_Main)getActivity();
        handler = new Handler(Looper.getMainLooper());


        //튜토리얼 spotlight 타겟에 추가한다.
      map_b.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
          @Override
          public void onGlobalFocusChanged(View oldFocus, View newFocus) {
              ((Home_Main)getActivity()).targetMaker(map_b,map_b.getWidth(),getResources().getString(R.string.map_view),getResources().getString(R.string.map_view_descrip));
              ((Home_Main)getActivity()).targetMaker(tuto,tuto.getHeight(),getResources().getString(R.string.replay),getResources().getString(R.string.replay_descrip));
              map_b.getViewTreeObserver().removeOnGlobalFocusChangeListener(this);
          }
      });


        networkClient = NetworkClient.getInstance(ip);
        networkClient.getstartitem(loginid, new Callback<List<ItemDTO>>() {
            @Override
            public void onResponse(Call<List<ItemDTO>> call, Response<List<ItemDTO>> response) {
                Log.d("퀴즈", "123");
                switch (response.code()) {
                    case 200:
                        Log.d("퀴즈", "어댑터 세팅");
                        inventories = response.body();
                        mRecyclerView.setHasFixedSize(false);
                        mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
                        mLayoutManager.canScrollHorizontally();
                        mRecyclerView.setLayoutManager(mLayoutManager);  //레이아웃 매니저를 세팅한다.
                        myDataset = new ArrayList<>();
                        mAdapter = new RecyclerAdapter_T(myDataset);
                        mRecyclerView.setAdapter(mAdapter);       //어탭더 정의

                        methods = new Methods();


                        ItemDTO itemDTO = inventories.get(0);

                        String qt = itemDTO.getquestiontype();
                        String qc = Integer.toString(itemDTO.getQuestioncode()); //문제번호
                        String rn = itemDTO.getRegionname(); //지역번호
                        int rc = itemDTO.getRegioncode();
                        String quiz = itemDTO.getQuestion(); //문제
                        String answer_q = itemDTO.getAnswer(); //정답
                        String hint_q = itemDTO.getHint();
                        int image = methods.imageSelector(rc);

                        myDataset.add(new MyData_T("1","강화도",23));//각 인자들을 어댑터클래스의 데이터베이스에 전달.
                        myDataset.add(new MyData_T("2","영흥도",23));//각 인자들을 어댑터클래스의 데이터베이스에 전달.
                        myDataset.add(new MyData_T("3","영종도",23));//각 인자들을 어댑터클래스의 데이터베이스에 전달.
                        myDataset.add(new MyData_T("4","섹스도",23));//각 인자들을 어댑터클래스의 데이터베이스에 전달.

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
                    ((Home_Main) getActivity()).spotLightExcute(0);
            }
        });

        return layout;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        final String playerid=profile.getId();
        String ip =ipm.getip();

        if(requestCode == 0){
            if(resultCode == RESULT_OK) {
                networkClient = NetworkClient.getInstance(ip);
                networkClient.checkplayer(playerid,new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        Log.d("퀴즈획득 아이디 : ",playerid);
                        switch (response.code()){
                            case 200:
                                int check = response.body();
                                Log.d("퀴즈획득 체크코드 :","check"+check);
                                if (check == 1) {  //Responce의값이 1일 때 새 문제 획득
                                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                    alert.setPositiveButton(getString(R.string.ghkrdls), new DialogInterface.OnClickListener() {
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
                                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                    alert.setPositiveButton(getString(R.string.ghkrdls), new DialogInterface.OnClickListener() {
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