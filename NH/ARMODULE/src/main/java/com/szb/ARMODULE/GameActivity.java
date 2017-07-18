package com.szb.ARMODULE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.szb.ARMODULE.Home.Home_Main;
import com.szb.ARMODULE.Home.managerpackage.QuestManager;
import com.szb.ARMODULE.model.retrofit.QuestDTO;
import com.szb.ARMODULE.network.Ipm;
import com.szb.ARMODULE.network.NetworkClient;
import com.szb.ARMODULE.start_pack.loginpackage.Logm;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameActivity extends UnityPlayerActivity {
    Ipm ipm;
    NetworkClient networkClient;
    QuestManager questmanager;



    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);

    }

    @Override
    public void onTouch(String ObjName) {
        Intent id = getIntent();
        String playerid = id.getExtras().getString("playerid");
        questmanager = QuestManager.getInstance();
        int region = Integer.parseInt(ObjName);
        Log.e("게임액티비티확인",playerid);
        ipm = new Ipm();
        String ip = ipm.getip();
        networkClient = NetworkClient.getInstance(ip);
        Log.e ("확인",playerid+region);
        networkClient.getregioncode(playerid, region, new Callback<QuestDTO>() {
            @Override
            public void onResponse(Call<QuestDTO> call, Response<QuestDTO> response) {
                switch (response.code()) {
                    case 200: //json 데이터를 파싱하는 것을 수월하게 해준다.
                        QuestDTO questDTO = response.body();
                        questmanager.create(questDTO);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<QuestDTO> call, Throwable t) {
                Log.e("ACC", "유니티에러 " + t.getMessage());

            }

        });
        Log.e("HIHI: ",ObjName);
        Intent intent = new Intent(GameActivity.this,Home_Main.class);
        intent.putExtra("got",0);
        setResult(RESULT_OK,intent);
        finish();
    }



}
