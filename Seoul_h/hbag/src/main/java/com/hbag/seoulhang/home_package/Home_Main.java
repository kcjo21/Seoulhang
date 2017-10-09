package com.hbag.seoulhang.home_package;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.hbag.seoulhang.appbase_package.BaseActivity;
import com.hbag.seoulhang.appbase_package.GameActivity;
import com.hbag.seoulhang.home_package.home_fragment.Frag_Home;
import com.hbag.seoulhang.home_package.home_fragment.Frag_Info;
import com.hbag.seoulhang.home_package.home_fragment.Frag_Quiz;
import com.hbag.seoulhang.home_package.home_fragment.Frag_Rank;
import com.hbag.seoulhang.R;
import com.hbag.seoulhang.map_package.GooglemapsActivity;
import com.hbag.seoulhang.model.retrofit.InventoryDTO;
import com.hbag.seoulhang.model.retrofit.PlayerDTO;
import com.hbag.seoulhang.network.Ipm;
import com.hbag.seoulhang.network.NetworkClient;
import com.hbag.seoulhang.joinmanage_package.MainActivity;
import com.hbag.seoulhang.joinmanage_package.SettingActivity;
import com.hbag.seoulhang.joinmanage_package.TutorialActivity;
import com.hbag.seoulhang.joinmanage_package.login_package.UserProfileData_singleton;
import com.hbag.seoulhang.joinmanage_package.register_package.EditprofileActivity;
import com.takusemba.spotlight.OnSpotlightEndedListener;
import com.takusemba.spotlight.OnSpotlightStartedListener;
import com.takusemba.spotlight.OnTargetStateChangedListener;
import com.takusemba.spotlight.SimpleTarget;
import com.takusemba.spotlight.Spotlight;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home_Main extends BaseActivity implements
        GoogleApiClient.OnConnectionFailedListener{

    ViewPager viewpager;
    LinearLayout top;
    Toolbar toolbar;
    DrawerLayout dlDrawer;
    ActionBarDrawerToggle dtToggle;
    private BackPressCloseHandler backPressCloseHandler;
    UserProfileData_singleton profile;
    NetworkClient networkClient;
    private List<InventoryDTO> infos;
    Ipm ipm;
    TextView info_setting;
    TextView mapview;
    TextView move_camera;
    TextView grade;
    TextView setting;
    TextView hint_left;
    TextView player_grade;
    TextView player_point_home;
    TextView player_name;
    TextView tv_side_title;
    ImageView iv_title;
    ImageView bt_home;
    ImageView bt_quiz;
    ImageView bt_info;
    ImageView bt_rank;
    TextView drawerposition;
    TextView cameraposition;
    ImageButton side_logout_bt;
    private GradeDialog dialog_grade;
    private LogoutDialog logoutDialog;
    Handler handler;
    private GoogleApiClient mGoogleApiClient;
    private long mLastClickTime = 0;
    List<SimpleTarget> spotTarget;
    boolean firstwindow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);
        profile = UserProfileData_singleton.getInstance();
        ipm = new Ipm();
        String ip = ipm.getip();
        final String loginid = profile.getId();
        drawerposition = (TextView)findViewById(R.id.drawer_position);
        cameraposition = (TextView)findViewById(R.id.camera_position);
        info_setting = (TextView)findViewById(R.id.info_setting);
        mapview = (TextView)findViewById(R.id.map_view);
        move_camera = (TextView)findViewById(R.id.camera_on);
        grade = (TextView)findViewById(R.id.grade_chart);
        setting = (TextView)findViewById(R.id.setting);
        hint_left = (TextView)findViewById(R.id.hint_left);
        player_grade = (TextView)findViewById(R.id.player_grade);
        player_point_home = (TextView)findViewById(R.id.player_point_home);
        player_name = (TextView)findViewById(R.id.player_name);
        iv_title = (ImageView) findViewById(R.id.iv_title);
        tv_side_title = (TextView) findViewById(R.id.tv_side_title);
        side_logout_bt = (ImageButton) findViewById(R.id.logout_bt);
        handler = new Handler(Looper.getMainLooper());
        spotTarget = new ArrayList<>();

        final SharedPreferences pref = getSharedPreferences("lang",MODE_PRIVATE);
        final int setlang = pref.getInt("setlang",0);

        String logintype = profile.getLoginType();


        switch (logintype){       //로그인 매체에 따라 네비게이션 상단타이틀과 UI색상 변경
            case "facebook":
                tv_side_title.setText(getResources().getString(R.string.facebook_with));
                tv_side_title.setBackgroundResource(R.drawable.side_title_facebook);
                side_logout_bt.setImageResource(R.drawable.logout_bt_facebook);
                break;
            case "google":
                tv_side_title.setText(getResources().getString(R.string.google_with));
                tv_side_title.setBackgroundResource(R.drawable.side_title_google);
                side_logout_bt.setImageResource(R.drawable.logout_bt_google);

                GoogleSignInOptions gso = new GoogleSignInOptions.Builder
                        (GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                mGoogleApiClient = new GoogleApiClient.Builder(this)
                        .enableAutoManage(this, this)
                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                        .build();
                break;
            case "normal":
                tv_side_title.setText(getResources().getString(R.string.normal_with));
                tv_side_title.setBackgroundResource(R.drawable.side_title_normal);
                side_logout_bt.setImageResource(R.drawable.logout_bt_normal);
                break;
            default:
                break;
        }

        info_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(Home_Main.this);
                alert.setCancelable(false);
                alert.setMessage(R.string.정보수정질문);


                alert.setNegativeButton(R.string.취소, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
                alert.setPositiveButton(R.string.확인,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent intent = new Intent(Home_Main.this,EditprofileActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                final android.app.AlertDialog dialog = alert.create();
                dialog.show();
            }
        });


        mapview.setOnClickListener(new View.OnClickListener() {
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
                                Intent intent = new Intent(Home_Main.this,GooglemapsActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        Looper.loop();
                        handler.getLooper().quit();
                    }
                });
                t.start(); // 쓰레드 시작i
            }
        });
        move_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(Home_Main.this,GameActivity.class);
                intent.putExtra("playerid",loginid);
                startActivityForResult(intent,0);  //문제 소유여부 확인을 위해 forResult로 액티비티 실행

            }
        });
        grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                dialog_grade = new GradeDialog(Home_Main.this, clicklistener);
                dialog_grade.show();

            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Intent intent = new Intent(Home_Main.this,SettingActivity.class);
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });

        side_logout_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 600){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                logoutDialog = new LogoutDialog(Home_Main.this, clicklistener);
                logoutDialog.show();
            }
        });


        Log.e("세팅",""+setlang);


        networkClient = NetworkClient.getInstance(ip);
        networkClient.setlanguage(loginid,setlang, new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                switch (response.code()) {
                    case 200:
                        Log.e("세팅","홈");
                        break;
                    default:
                        break;
                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("ACC", "s?? " + t.getMessage());
            }
        });

        networkClient.getfinishitem(loginid, new Callback<List<InventoryDTO>>() {
            @Override
            public void onResponse(Call<List<InventoryDTO>> call, Response<List<InventoryDTO>> response) {
                Log.d("인포", "123");
                switch (response.code()) {
                    case 200:
                        Log.d("인포", "어댑터 세팅");
                        infos = response.body();

                        InventoryDTO inventoryDTO = infos.get(0);

                        String grade_i = inventoryDTO.getGrade();
                        String nickname = inventoryDTO.getNickname();
                        String email = inventoryDTO.getEmail();
                        String point_i = Integer.toString(inventoryDTO.getPoint());
                        String hint_i = Integer.toString(inventoryDTO.getHint());

                        player_name.setText(getResources().getString(R.string.side_닉네임,nickname));
                        hint_left.setText(getResources().getString(R.string.side_힌트수,hint_i));
                        player_grade.setText(getResources().getString(R.string.side_등급,grade_i));
                        player_point_home.setText(getResources().getString(R.string.side_점수,point_i));

                        profile.setId(loginid);
                        profile.setNickname(nickname);
                        profile.setEmail(email);


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

        SharedPreferences preferences = getSharedPreferences("a",MODE_PRIVATE);
        int first_flag = preferences.getInt("First",0);

        if(first_flag != 1) {
            Intent intent = new Intent(Home_Main.this, TutorialActivity.class);
            startActivity(intent);
            finish();
        }



        backPressCloseHandler = new BackPressCloseHandler(this); //뒤로가기 이벤트 핸들러

        toolbar = (Toolbar) findViewById(R.id.toolbar_title);
        dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout); //툴바 및 드로어레이아웃 초기화
        setSupportActionBar(toolbar);       //툴바를 액션바로 취급
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);    //네비게이션 아이콘 활성화
            actionBar.setDisplayShowTitleEnabled(false);  //앱 타이틀명 제거
        }

        networkClient = NetworkClient.getInstance(ip);




        dtToggle = new ActionBarDrawerToggle(this, dlDrawer,R.string.open_drawer,R.string.close_drawer);
        dlDrawer.addDrawerListener(dtToggle);            //토글 아이콘 및 드로어 리스너

        viewpager = (ViewPager)findViewById(R.id.viewpager);  //뷰 페이저 초기화
        top = (LinearLayout) findViewById(R.id.tap_bar);   //tap_bar 레이아웃 초기화

        bt_home = (ImageView)findViewById(R.id.bt_home);
        bt_quiz = (ImageView)findViewById(R.id.bt_quiz);
        bt_info = (ImageView)findViewById(R.id.bt_info);
        bt_rank = (ImageView)findViewById(R.id.bt_rank);

        viewpager.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        viewpager.setCurrentItem(0);
        viewpager.setOffscreenPageLimit(3);

        bt_home.setOnClickListener(movePageListener);
        bt_home.setTag(0);
        bt_quiz.setOnClickListener(movePageListener);
        bt_quiz.setTag(1);
        bt_info.setOnClickListener(movePageListener);
        bt_info.setTag(2);
        bt_rank.setOnClickListener(movePageListener);
        bt_rank.setTag(3);                                   //페이저 리스너에 태그에 할당된 버튼 추가

        bt_home.setSelected(true);  //뷰페이저 초기값 bt_home

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()   //뷰 페이저 리스너 추가
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
            }
            @Override
            public void onPageSelected(int position)
            {
                int i = 0;
                while(i<4)
                {
                    if(position==i)
                    {
                        switch (position){
                            case 0:
                                iv_title.setImageResource(R.drawable.title_home);
                                break;
                            case 1:
                                iv_title.setImageResource(R.drawable.title_quiz);
                                break;
                            case 2:
                                iv_title.setImageResource(R.drawable.title_info);
                                break;
                            case 3:
                                iv_title.setImageResource(R.drawable.title_rank);
                                break;
                        }
                        top.findViewWithTag(i).setSelected(true);
                    }
                    else
                    {
                        top.findViewWithTag(i).setSelected(false);
                    }
                    i++;
                }
            }                  //페이지 선택 메소드

            @Override
            public void onPageScrollStateChanged(int state)
            {
            }
        });

    }



    View.OnClickListener movePageListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {   //페이지 이동값 리스너
            int tag = (int) v.getTag();
            int i = 0;
            while(i<4)
            {
                if(tag==i)
                {
                    top.findViewWithTag(i).setSelected(true);
                }
                else
                {
                    top.findViewWithTag(i).setSelected(false);
                }
                i++;
            }

            viewpager.setCurrentItem(tag);
        }
    };

    private class pagerAdapter extends FragmentStatePagerAdapter {
        private pagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Frag_Home();
                case 1:
                    return new Frag_Quiz();
                case 2:
                    return new Frag_Info();
                case 3:
                    return new Frag_Rank();
                default:
                    return null;
            }
        }    //페이저 태그값 처리

        @Override
        public int getCount()
        {
            return 4;
        }

    }

    private View.OnClickListener clicklistener = new View.OnClickListener() { //로그아웃 다이알로그의 리스너
        @Override
        public void onClick(View v) {

            int tag = (int)v.getTag();     //다이알로그 버튼의 태그값을 이용하여 뷰 판단

            switch (tag){
                case 0:
                    dialog_grade.dismiss();
                    break;
                case 1:
                    logoutDialog.dismiss();
                    break;
                case 2:
                    Log.d("페이스북 로그아웃",profile.getLoginType());
                    if(profile.getLoginType().equals("facebook")){
                        LoginManager.getInstance().logOut();

                    }
                    else if(profile.getLoginType().equals("google")){

                        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                new ResultCallback<Status>() {
                                    @Override
                                    public void onResult(@NonNull Status status) {
                                        Log.d("구글 로그아웃",profile.getLoginType());
                                    }
                                }
                        );
                    }
                    profile.setLoginType("logout");         //로그인 상태 로그아웃으로 저장
                    SharedPreferences sharedPreferences = getSharedPreferences("log",MODE_PRIVATE);
                    SharedPreferences.Editor editor =  sharedPreferences.edit();
                    editor.putString("logintype",profile.getLoginType());   //로그인 상태 저장
                    editor.apply();

                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.Logout_msg), Toast.LENGTH_LONG);
                    toast.show();

                    cleanProfile(); //프로필 정보 제거

                    Log.d("로그아웃","Home_main"+profile.getLoginType());
                    Intent intent = new Intent(Home_Main.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }

        }
    };

    @Override public void onBackPressed() {
        if(dlDrawer.isDrawerOpen(GravityCompat.START)){
            dlDrawer.closeDrawers(); //네비게이션이 열려있는지 확인하고 열려있다면 back키로 닫기 가능.
        }
        else {
            backPressCloseHandler.onBackPressed(); //네비게이션이 닫혀있다면 backPressCloseHandler 정상작동.
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        dtToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dtToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String playerid=profile.getId();
        Log.d("게임아이디",playerid);

        if (id == R.id.camera_button) {
            Intent intent = new Intent(Home_Main.this,GameActivity.class);
            intent.putExtra("playerid",playerid);
            Log.d("게임아이디",playerid);
            startActivityForResult(intent,0);  //문제 소유여부 확인을 위해 forResult로 액티비티 실행
            return true;
        }
        else if(dtToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume(){
        super.onResume();
        viewpager.getAdapter().notifyDataSetChanged();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final String playerid=profile.getId();
        String ip =ipm.getip();
        Log.d("유니티 통신완료 ID : ",playerid);

        if(requestCode == 0){
            if(resultCode == RESULT_OK) {
                networkClient = NetworkClient.getInstance(ip);
                networkClient.checkplayer(playerid,new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        Log.d("유니티 통신완료 재확인 ID : ",playerid);
                        switch (response.code()){
                            case 200:
                                int check = response.body();
                                Log.d("확인","check"+check);
                                if (check == 1) {  //Responce의값이 1일 때 새 문제 획득
                                    AlertDialog.Builder alert = new AlertDialog.Builder(Home_Main.this);
                                    alert.setPositiveButton(getResources().getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(getApplicationContext(),Home_Main.class);
                                            startActivity(intent);
                                            finish();
                                            dialog.dismiss();     //닫기
                                        }
                                    });
                                    alert.setMessage(R.string.새문제);
                                    alert.show();
                                } else if (check == 0) { //Responce값이 0일 때 이미 가지고 있는 문제
                                    Log.d("확인"," "+check);
                                    AlertDialog.Builder alert = new AlertDialog.Builder(Home_Main.this);
                                    alert.setPositiveButton(getResources().getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {
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
                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_check_id), Toast.LENGTH_LONG);
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
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d("EditprofileActivity", "onConnectionFailed:" + connectionResult);
    }

    public void cleanProfile(){
        profile.setId("");
        profile.setNickname("");
        profile.setEmail("");
        profile.setName("");
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus){

        if(!firstwindow) {   //포커스 변경 시 중복 생성을 방지한다.

            targetMaker(bt_home, bt_home.getWidth(), getResources().getString(R.string.홈), getResources().getString(R.string.home_descrip));
            targetMaker(bt_quiz, bt_quiz.getWidth(), getResources().getString(R.string.퀴즈), getResources().getString(R.string.quiz_descrip));
            targetMaker(bt_info, bt_info.getWidth(), getResources().getString(R.string.내정보), getResources().getString(R.string.info_descrip));
            targetMaker(bt_rank, bt_rank.getWidth(), getResources().getString(R.string.순위표), getResources().getString(R.string.rank_descrip));
            targetMaker(drawerposition, bt_home.getWidth(), getResources().getString(R.string.메뉴버튼), getResources().getString(R.string.menu_descrip));
            targetMaker(cameraposition, bt_home.getWidth(), getResources().getString(R.string.카메라버튼), getResources().getString(R.string.camera_descrip));

            SharedPreferences pref = getSharedPreferences("spot", MODE_PRIVATE);
            if (pref.getInt("firstSpot", 0) == 0) {
                spotLightExcute(0);
            }
            firstwindow = true;
        }


    }

    public void targetMaker(View point, float radius, String title, String description){

        SimpleTarget simpleTarget = new SimpleTarget.Builder(this)
                .setPoint(point)
                .setRadius(radius)
                .setTitle(title)
                .setDescription(description)
                .setOnSpotlightStartedListener(new OnTargetStateChangedListener<SimpleTarget>() {
                    @Override
                    public void onStarted(SimpleTarget target) {

                    }

                    @Override
                    public void onEnded(SimpleTarget target) {

                    }
                }).build();
        spotTarget.add(simpleTarget);

    }

    public void spotLightExcute(int state){   //spotlight를 상황에 따라 실행
        switch(state) {
            case 0:
                Spotlight.with(this)
                        .setDuration(500L)
                        .setAnimation(new DecelerateInterpolator(2f))
                        .setTargets(spotTarget.get(2),spotTarget.get(3),spotTarget.get(4),spotTarget.get(5),spotTarget.get(6),spotTarget.get(7),spotTarget.get(0),spotTarget.get(1))
                        .setOnSpotlightStartedListener(new OnSpotlightStartedListener() {
                            @Override
                            public void onStarted() {
                                bt_home.setImageResource(R.drawable.home_2);
                                bt_quiz.setImageResource(R.drawable.quiz_2);
                                bt_info.setImageResource(R.drawable.info_2);
                                bt_rank.setImageResource(R.drawable.rank_2);
                            }
                        })
                        .setOnSpotlightEndedListener(new OnSpotlightEndedListener() {
                            @Override
                            public void onEnded() {
                                bt_home.setImageResource(R.drawable.selector_home);
                                bt_quiz.setImageResource(R.drawable.selector_quiz);
                                bt_info.setImageResource(R.drawable.selector_info);
                                bt_rank.setImageResource(R.drawable.selector_rank);
                                SharedPreferences sharedPreferences = getSharedPreferences("spot",MODE_PRIVATE);
                                SharedPreferences.Editor firststateEditor = sharedPreferences.edit();
                                firststateEditor.putInt("firstSpot",1);
                                firststateEditor.apply();
                                Intent intent = new Intent(Home_Main.this,Home_Main.class);
                                startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                overridePendingTransition(0, 0);
                                finish();

                            }
                        }).start();
                break;
            case 1:
                break;
            default:
                break;


        }

    }


}


