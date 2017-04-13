package com.szb.ARMODULE.network;

import android.util.Log;

import com.szb.ARMODULE.model.retrofit.PlayerDTO;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {
    private static NetworkClient instance = null;
    private Retrofit retrofit;
    private NetworkService service;

    private NetworkClient(String serverAddress) {
        Log.e("debug", "NetworkClient Constructor : " + serverAddress);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10,TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(serverAddress)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        service = retrofit.create(NetworkService.class);
    }

    public static NetworkClient getInstance(String serverAddress) {
        if (instance == null) {
            instance = new NetworkClient(serverAddress);
        }
        return instance;
    }

    public static NetworkClient getInstance(){
        return instance;
    }

    public void login(String player, Callback<PlayerDTO> cb){
        Call<PlayerDTO> call = service.login(player);
        call.enqueue(cb);
    }
}
