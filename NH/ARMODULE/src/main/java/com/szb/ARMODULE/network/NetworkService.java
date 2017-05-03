package com.szb.ARMODULE.network;

import com.szb.ARMODULE.model.retrofit.PlayerDTO;
import com.szb.ARMODULE.model.retrofit.QuestDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NetworkService {
    @GET("/players/{player}")
    Call<PlayerDTO> login(@Path("player") String player);

    @GET("/questioncollection/{player}/code/{region}")
    Call<QuestDTO> getregioncode(@Path("player") String player, @Path("region") int region);
}
