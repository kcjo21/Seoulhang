package com.szb.szb.start_pack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.szb.szb.Home.Home_Main;
import com.szb.szb.R;

import java.util.Locale;

public class Setting extends AppCompatActivity {

    RadioButton ko;
    RadioButton eng;
    Button commit;
    Button cancel;
    TextView lang_text;
    int lang=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        ko = (RadioButton)findViewById(R.id.ko);
        eng = (RadioButton)findViewById(R.id.eng);
        commit = (Button)findViewById(R.id.commit);
        cancel = (Button)findViewById(R.id.cancel);
        lang_text = (TextView)findViewById(R.id.lang_text);


        SharedPreferences preferences = getSharedPreferences("a",MODE_PRIVATE);
        int first_flag = preferences.getInt("First",0);

        if(first_flag !=1){
            cancel.setVisibility(View.GONE);
        }
        else
            cancel.setVisibility(View.VISIBLE);

        SharedPreferences pref = getSharedPreferences("lang",MODE_PRIVATE);

        if(pref.getInt("setlang",0)==0){
            ko.setChecked(true);
            lang_text.setText("언어를 선택하세요.");
            lang = 0;
        }
        else {
            eng.setChecked(true);
            lang_text.setText("Select Your Language");
            lang = 1;
        }



        ko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lang_text.setText("언어를 선택하세요.");
                lang = 0;
                Log.e("세팅",""+lang);
            }
        });
        eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lang_text.setText("Select Your Language");
                lang = 1;
                Log.e("세팅",""+lang);
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences a = getSharedPreferences("lang",MODE_PRIVATE);
                SharedPreferences.Editor editor = a.edit();
                editor.putInt("setlang",lang);
                editor.apply();

                if(lang==0){
                    Locale myLocale = new Locale("ko");
                    Resources res = getResources();
                    DisplayMetrics dm = res.getDisplayMetrics();
                    Configuration conf = res.getConfiguration();
                    conf.locale = myLocale;
                    res.updateConfiguration(conf, dm);
                }
                else {
                    Locale myLocale = new Locale("en");
                    Resources res = getResources();
                    DisplayMetrics dm = res.getDisplayMetrics();
                    Configuration conf = res.getConfiguration();
                    conf.locale = myLocale;
                    res.updateConfiguration(conf, dm);
                }

                SharedPreferences first = getSharedPreferences("a",MODE_PRIVATE);
                if(first.getInt("First",0)!=1) {
                    Intent intent = new Intent(Setting.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(Setting.this, Home_Main.class);
                    startActivity(intent);
                    finish();
                }


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this, Home_Main.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }return super.onKeyDown(keyCode, event);}  //BACK버튼 비활성화

}
