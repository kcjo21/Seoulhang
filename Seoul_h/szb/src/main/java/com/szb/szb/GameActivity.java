package com.szb.szb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import com.szb.szb.Home.Home_Main;
import com.szb.szb.model.retrofit.QuestDTO;
import com.szb.szb.network.Ipm;
import com.szb.szb.network.NetworkClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameActivity extends UnityPlayerActivity {
    Ipm ipm;
    NetworkClient networkClient;



    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);

        //GPS 여부 체크
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            createGpsDisabledAlert();
        }
    }

    private void createGpsDisabledAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.GPSMessage)
                .setCancelable(false).setPositiveButton(R.string.GPSY,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        showGpsOptions();
                    }
                })
                .setNegativeButton(R.string.GPSN,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();;
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private  void showGpsOptions(){
        Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(gpsOptionsIntent);
    }

    @Override
    public void onTouch(String questioncode) {
        Intent id = getIntent();
        String playerid = id.getExtras().getString("playerid");
        int q_code = Integer.parseInt(questioncode);
        Log.e("게임액티비티확인",playerid);
        ipm = new Ipm();
        String ip = ipm.getip();
        networkClient = NetworkClient.getInstance(ip);
        Log.e ("확인",playerid+q_code);
        networkClient.getquestioncode(playerid, q_code, new Callback<QuestDTO>() {
            @Override
            public void onResponse(Call<QuestDTO> call, Response<QuestDTO> response) {
                switch (response.code()) {
                    case 200:
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
        Log.e("QUSTIONNUM: ",questioncode);
        Intent intent = new Intent(GameActivity.this,Home_Main.class);
        intent.putExtra("got",0);
        setResult(RESULT_OK,intent);
        finish();
    }



}
