package com.szb.szb.Home;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.szb.szb.BaseActivity;
import com.szb.szb.GameActivity;
import com.szb.szb.Home.frag.Frag_Home;
import com.szb.szb.Home.frag.Frag_Info;
import com.szb.szb.Home.frag.Frag_Map;
import com.szb.szb.Home.frag.Frag_Quiz;
import com.szb.szb.Home.frag.Frag_Rank;
import com.szb.szb.R;
import com.szb.szb.model.retrofit.InventoryDTO;
import com.szb.szb.network.Ipm;
import com.szb.szb.network.NetworkClient;
import com.szb.szb.start_pack.SettingActivity;
import com.szb.szb.start_pack.TutorialActivity;
import com.szb.szb.start_pack.loginpackage.Dialog_grade;
import com.szb.szb.start_pack.loginpackage.Logm;
import com.szb.szb.start_pack.registerpack.EditprofileActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home_Main extends BaseActivity {

    ViewPager viewpager;
    LinearLayout top;
    Toolbar toolbar;
    DrawerLayout dlDrawer;
    ActionBarDrawerToggle dtToggle;
    private BackPressCloseHandler backPressCloseHandler;
    NetworkClient networkClient;
    private List<InventoryDTO> infos;
    Ipm ipm;
    Logm logm;
    TextView info_setting;
    TextView move_home;
    TextView move_camera;
    TextView grade;
    TextView setting;
    TextView hint_left;
    TextView player_grade;
    TextView player_point_home;
    TextView player_name;
    private Dialog_grade dialog_grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);
        ImageView run = (ImageView)findViewById(R.id.runing);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(run);
        Glide.with(this).load(R.raw.run).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageViewTarget);
        ipm = new Ipm();
        String ip = ipm.getip();
        logm = new Logm();
        final String loginid = logm.getPlayerid();
        final ArrayList<String> items = new ArrayList<String>();
        info_setting = (TextView)findViewById(R.id.info_setting);
        move_home = (TextView)findViewById(R.id.move_home);
        move_camera = (TextView)findViewById(R.id.camera_on);
        grade = (TextView)findViewById(R.id.grade_chart);
        setting = (TextView)findViewById(R.id.setting);
        hint_left = (TextView)findViewById(R.id.hint_left);
        player_grade = (TextView)findViewById(R.id.player_grade);
        player_point_home = (TextView)findViewById(R.id.player_point_home);
        player_name = (TextView)findViewById(R.id.player_name);

        SharedPreferences pref = getSharedPreferences("lang",MODE_PRIVATE);
        int setlang = pref.getInt("setlang",0);

        info_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


        move_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewpager.setCurrentItem(0);
                dlDrawer.closeDrawers();
            }
        });
        move_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Main.this,GameActivity.class);
                intent.putExtra("playerid",loginid);
                startActivityForResult(intent,0);  //문제 소유여부 확인을 위해 forResult로 액티비티 실행

            }
        });
        grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_grade = new Dialog_grade(Home_Main.this,commitlistener);
                dialog_grade.show();

            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Main.this,SettingActivity.class);
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
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
                        String playername = inventoryDTO.getPlayername();
                        String point_i = Integer.toString(inventoryDTO.getPoint());
                        String hint_i = Integer.toString(inventoryDTO.getHint());

                        player_name.setText(getResources().getString(R.string.side_닉네임,playername));
                        hint_left.setText(getResources().getString(R.string.side_힌트수,hint_i));
                        player_grade.setText(getResources().getString(R.string.side_등급,grade_i));
                        player_point_home.setText(getResources().getString(R.string.side_점수,point_i));


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
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }



        backPressCloseHandler = new BackPressCloseHandler(this); //뒤로가기 이벤트 핸들러

        toolbar = (Toolbar) findViewById(R.id.toolbar);
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

        ImageView bt_home = (ImageView)findViewById(R.id.bt_home);
        ImageView bt_quiz = (ImageView)findViewById(R.id.bt_quiz);
        ImageView bt_info = (ImageView)findViewById(R.id.bt_info);
        ImageView bt_rank = (ImageView)findViewById(R.id.bt_rank);
        ImageView bt_map = (ImageView)findViewById(R.id.bt_map);  //이미지 뷰에 각  레이아웃 할당

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
    private View.OnClickListener commitlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog_grade.dismiss();
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
        logm = new Logm();
        String playerid=logm.getPlayerid();

        if (id == R.id.camera_button) {
            Intent intent = new Intent(Home_Main.this,GameActivity.class);
            intent.putExtra("playerid",playerid);
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
        final String playerid=logm.getPlayerid();
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
                                    AlertDialog.Builder alert = new AlertDialog.Builder(Home_Main.this);
                                    alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
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
                                    Log.e("확인","응"+check);
                                    AlertDialog.Builder alert = new AlertDialog.Builder(Home_Main.this);
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

}


