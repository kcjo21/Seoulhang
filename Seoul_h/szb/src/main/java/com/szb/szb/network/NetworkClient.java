package com.szb.szb.network;

import android.util.Log;

import com.szb.szb.model.retrofit.HintDTO;
import com.szb.szb.model.retrofit.InventoryDTO;
import com.szb.szb.model.retrofit.ItemDTO;
import com.szb.szb.model.retrofit.PlayerDTO;
import com.szb.szb.model.retrofit.QuestDTO;
import com.szb.szb.model.retrofit.RankDTO;
import com.szb.szb.model.retrofit.RateDTO;

import java.util.List;
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

    public void getregioncode(String player, int region, Callback<QuestDTO> callback){
        Call <QuestDTO> call = service.getregioncode(player,region);
        call.enqueue(callback);
    }
    public void getstartitem(String player, Callback<List<ItemDTO>> callback){
        Call<List<ItemDTO>> call = service.getstartitem(player);
        call.enqueue(callback);
    }

    public void checkplayer(String player, Callback<Integer> callback){
        Call<Integer> call = service.checkplayer(player);
        call.enqueue(callback);
    }

    public void checkanswer(String player, int questioncode, Callback<Integer> callback){
        Call<Integer> call = service.checkanswer(player,questioncode);
        call.enqueue(callback);
    }
    public void getranking(String player , Callback<List<RankDTO>> callback){
        Call<List<RankDTO>> call = service.getranking(player);
        call.enqueue(callback);
    }

    public void getfinishitem(String player, Callback<List<InventoryDTO>> callback){
        Call<List<InventoryDTO>> call = service.getfinishitem(player);
        call.enqueue(callback);
    }
    public void getrate(String player, Callback<List<RateDTO>> callback){
        Call<List<RateDTO>> call = service.getrate(player);
        call.enqueue(callback);
    }

    public void gethint(String player, int questioncode, Callback<HintDTO> callback){
        Call<HintDTO> call = service.gethint(player,questioncode);
        call.enqueue(callback);
    }

    public void setlanguage(String player, int language, Callback<Integer> callback){
        Call<Integer> call = service.setlanguage(player, language);
        call.enqueue(callback);
    }

    public void getjoin(String player, String password, String name, String gender, Integer age, String phone, String email, Callback<String> callback){
        Call<String> call = service.getjoin(player, password, name, gender, age, phone, email);
        call.enqueue(callback);
    }
    public void checkid(String player,Callback<String> callback){
        Call<String> call = service.checkid(player);
        call.enqueue(callback);
    }
}
