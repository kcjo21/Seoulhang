package com.szb.ARMODULE.Home;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.szb.ARMODULE.GameActivity;
import com.szb.ARMODULE.R;


public class Home_Main extends AppCompatActivity {

    ViewPager viewpager;
    ConstraintLayout top;

    Toolbar toolbar;
    DrawerLayout dlDrawer;
    ActionBarDrawerToggle dtToggle;
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);

        backPressCloseHandler = new BackPressCloseHandler(this); //뒤로가기 이벤트 핸들러

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout); //툴바 및 드로어레이아웃 초기화
        setSupportActionBar(toolbar);       //툴바를 액션바로 취급
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);    //네비게이션 아이콘 활성화
            actionBar.setDisplayShowTitleEnabled(false);  //앱 타이틀명 제거
        }

        dtToggle = new ActionBarDrawerToggle(this, dlDrawer,R.string.open_drawer,R.string.close_drawer);
        dlDrawer.addDrawerListener(dtToggle);            //토글 아이콘 및 드로어 리스너

        viewpager = (ViewPager)findViewById(R.id.viewpager);  //뷰 페이저 초기화
        top = (ConstraintLayout) findViewById(R.id.tap_bar);   //tap_bar 레이아웃 초기화

        ImageView bt_home = (ImageView)findViewById(R.id.bt_home);
        ImageView bt_quiz = (ImageView)findViewById(R.id.bt_quiz);
        ImageView bt_info = (ImageView)findViewById(R.id.bt_info);
        ImageView bt_rank = (ImageView)findViewById(R.id.bt_rank);    //이미지 뷰에 각  레이아웃 할당

        viewpager.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        viewpager.setCurrentItem(0);

        bt_home.setOnClickListener(movePageListener);
        bt_home.setTag(0);
        bt_quiz.setOnClickListener(movePageListener);
        bt_quiz.setTag(1);
        bt_rank.setOnClickListener(movePageListener);
        bt_rank.setTag(2);
        bt_info.setOnClickListener(movePageListener);
        bt_info.setTag(3);                                       //페이저 리스너에 태그에 할당된 버튼 추가

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
                    return new Frag_Rank();
                case 3:
                    return new Frag_Info();
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

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
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
        if (id == R.id.camera_button) {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            return true;
        }
        else if(dtToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

