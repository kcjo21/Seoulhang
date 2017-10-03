package com.hbag.seoulhang.joinmanage_package;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hbag.seoulhang.home_package.Home_Main;
import com.hbag.seoulhang.R;
import com.hbag.seoulhang.joinmanage_package.tutorial_package.tuto_a;
import com.hbag.seoulhang.joinmanage_package.tutorial_package.tuto_b;
import com.hbag.seoulhang.joinmanage_package.tutorial_package.tuto_c;
import com.hbag.seoulhang.joinmanage_package.tutorial_package.tuto_d;
import com.nightonke.wowoviewpager.WoWoViewPagerAdapter;

public class TutorialActivity extends AppCompatActivity {
    ViewPager tpager;
    LinearLayout indi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        tpager = (ViewPager)findViewById(R.id.tutovp);

        int infoFirst = 1;
        SharedPreferences a = getSharedPreferences("a",MODE_PRIVATE);
        SharedPreferences.Editor editor = a.edit();
        editor.putInt("First",infoFirst);
        editor.apply();





        ImageView vp1 = (ImageView)findViewById(R.id.vp1);
        ImageView vp2 = (ImageView)findViewById(R.id.vp2);
        ImageView vp3 = (ImageView)findViewById(R.id.vp3);
        ImageView vp4 = (ImageView)findViewById(R.id.vp4);
        Button skip = (Button) findViewById(R.id.skip_b);



        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TutorialActivity.this, Home_Main.class);
                startActivity(intent);
                finish();
            }
        });

        tpager.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        tpager.setCurrentItem(0);

        vp1.setTag(0);
        vp2.setTag(1);
        vp3.setTag(2);
        vp4.setTag(3);

        vp1.setSelected(true);


        tpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
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
                        indi.findViewWithTag(i).setSelected(true);
                    }
                    else
                    {
                        indi.findViewWithTag(i).setSelected(false);
                    }
                    i++;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });



    }




    private class pagerAdapter extends FragmentStatePagerAdapter
    {
        public pagerAdapter(android.support.v4.app.FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {
            switch(position)
            {
                case 0:
                    return new tuto_a();
                case 1:
                    return new tuto_b();
                case 2:
                    return new tuto_c();
                case 3:
                    return new tuto_d();
                default:
                    return null;
            }
        }
        @Override
        public int getCount()
        {
            return 4;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }return super.onKeyDown(keyCode, event);}  //BACK버튼 비활성화

}


