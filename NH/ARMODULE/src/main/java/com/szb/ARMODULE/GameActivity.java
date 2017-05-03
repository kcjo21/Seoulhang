package com.szb.ARMODULE;

import android.os.Bundle;
import android.util.Log;

import com.szb.ARMODULE.model.retrofit.QuestDTO;
import com.szb.ARMODULE.network.NetworkClient;
import com.szb.ARMODULE.start_pack.loginpackage.LoginManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameActivity extends UnityPlayerActivity {
    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);



    }
    @Override
    public void onTouch(String ObjName){
        int region = Integer.parseInt(ObjName);

        loginmanager = LoginManager.getInstance();
        Bundle extras = getIntent().getExtras();
        String playerid=extras.getString("playerid");

        NetworkClient networkClient = NetworkClient.getInstance("http://192.168.0.23:5000");

        networkClient.getregioncode(playerid, region, new Callback<QuestDTO>() {
            @Override
            public void onResponse(Call<QuestDTO> call, Response<QuestDTO> response) {
                switch (response.code()){
                    case 200:
                        //json 데이터를 파싱하는 것을 수월하게 해준다.
                        QuestDTO questDTO = response.body();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<QuestDTO> call, Throwable t) {
                Log.e("ACC","s?? " + t.getMessage());

            }
        });


        Log.e("TAG", "login???? : " + loginmanager.toString());
        Log.e("HIHI: ",ObjName);
        Log.e("hihi: ",playerid);

    }
}
