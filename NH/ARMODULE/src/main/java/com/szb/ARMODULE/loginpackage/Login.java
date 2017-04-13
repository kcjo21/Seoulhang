package com.szb.ARMODULE.loginpackage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.szb.ARMODULE.Home.Home_Main;
import com.szb.ARMODULE.R;
import com.szb.ARMODULE.model.retrofit.PlayerDTO;
import com.szb.ARMODULE.network.NetworkClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends Activity {

    Button btnSignin;
    LoginManager loginmanager;
    TextView loginId;
    String loginid;
    NetworkClient networkClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginId = (TextView)findViewById(R.id.LoginId);

        loginmanager = LoginManager.getInstance();

        btnSignin = (Button)findViewById(R.id.LoginSignin);



        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginid = loginId.getText().toString();

                networkClient = NetworkClient.getInstance("http://192.168.0.back_8:5000");

                Log.e("ACC","TEAM id IS !!! "+ loginid);
                networkClient.login(loginid,new Callback<PlayerDTO>() {
                    @Override
                    public void onResponse(Call<PlayerDTO> call, Response<PlayerDTO> response) {
                        switch (response.code()){
                            case 200:
                                //json 데이터를 파싱하는 것을 수월하게 해준다.

                                PlayerDTO playerDTO = response.body();

                                Log.e("TAG", "team dto : " + playerDTO.toString());
                                // teamDTO를 이용하여 realm에 team 데이터를 생성한다.
                                loginmanager.create(playerDTO);
                                Intent i = new Intent(getApplicationContext(), Home_Main.class);
                                startActivity(i);
                                finish();
                                break;

                            default:
                                Log.e("TAG", "다른 아이디");
                                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.please_check_id), Toast.LENGTH_LONG);
                                toast.show();
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<PlayerDTO> call, Throwable t) {
                        Log.e("ACC","s?? " + t.getMessage());

                    }
                });
            }
        });


        Log.e("TAG", "login???? : " + loginmanager.toString());


    }
}
