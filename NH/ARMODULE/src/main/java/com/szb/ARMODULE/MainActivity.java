package com.szb.ARMODULE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.szb.ARMODULE.Home.Home_Main;
import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;

public class MainActivity extends Activity {
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout  layout = (ConstraintLayout)findViewById(R.id.con);
        int ranvalue = (int) Math.floor((Math.random()*10));
        String str  = Integer.toString(ranvalue);

        switch (ranvalue) {

            case 0:
                layout.setBackgroundResource(R.drawable.bg_0);
                break;
            case 1:
                layout.setBackgroundResource(R.drawable.bg_1);
                break;
            case 2:
                layout.setBackgroundResource(R.drawable.bg_2);
                break;
            case 3:
                layout.setBackgroundResource(R.drawable.bg_3);
                break;
            case 4:
                layout.setBackgroundResource(R.drawable.bg_4);
                break;
            case 5:
                layout.setBackgroundResource(R.drawable.bg_5);
                break;
            case 6:
                layout.setBackgroundResource(R.drawable.bg_6);
                break;
            case 7:
                layout.setBackgroundResource(R.drawable.bg_7);
                break;
            case 8:
                layout.setBackgroundResource(R.drawable.bg_8);
                break;
            case 9:
                layout.setBackgroundResource(R.drawable.bg_9);
                break;
            case 10:
                layout.setBackgroundResource(R.drawable.bg_10);
                break;
            case 11:
                layout.setBackgroundResource(R.drawable.bg_11);
                break;

        } //배경화면 랜덤 선택 출력






        btnLogin = (Button)findViewById(R.id.Login);

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, Home_Main.class);
                startActivity(intent.addFlags(FLAG_ACTIVITY_NO_HISTORY));
            }
        });
    }

}
