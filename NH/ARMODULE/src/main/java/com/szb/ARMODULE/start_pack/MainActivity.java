package com.szb.ARMODULE.start_pack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.szb.ARMODULE.Home.BackPressCloseHandler;
import com.szb.ARMODULE.Home.Home_Main;
import com.szb.ARMODULE.R;

import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;

public class MainActivity extends Activity {
    Button btnLogin;
    private BackPressCloseHandler backPressCloseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backPressCloseHandler = new BackPressCloseHandler(this); //뒤로가기 이벤트 핸들러
        btnLogin = (Button)findViewById(R.id.Login);


        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, Home_Main.class);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }


}
