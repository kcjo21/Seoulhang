package com.szb.szb.network;

import com.szb.szb.model.retrofit.FindDTO;
import com.szb.szb.model.retrofit.HintDTO;
import com.szb.szb.model.retrofit.InventoryDTO;
import com.szb.szb.model.retrofit.ItemDTO;
import com.szb.szb.model.retrofit.PlayerDTO;
import com.szb.szb.model.retrofit.QuestDTO;
import com.szb.szb.model.retrofit.RankDTO;
import com.szb.szb.model.retrofit.RateDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NetworkService {
    @GET("/players/{player}/password/{password}")
    Call<PlayerDTO> login(@Path("player") String player,@Path("password") String password);

    @GET("/collect/{player}/call_code/{region}")
    Call<QuestDTO> getregioncode(@Path("player") String player, @Path("region") int region);

    @GET("/start_player/{player}")
    Call<List<ItemDTO>> getstartitem(@Path("player") String player);

    @GET("/finish_player/{player}")
    Call<List<InventoryDTO>> getfinishitem(@Path("player") String player);

    @GET("/check_player/{player}")
    Call<Integer> checkplayer(@Path("player") String player);

    @GET("/update_player/{player}/call_code/{questioncode}")
    Call<Integer> checkanswer(@Path("player") String player, @Path("questioncode") int questioncode);

    @GET("/ranking/{player}")
    Call <List<RankDTO>> getranking(@Path("player") String player);

    @GET("/rate/{player}")
    Call <List<RateDTO>> getrate(@Path("player") String plyer);

    @GET("/hint_player/{player}/call_code/{questioncode}")
    Call <HintDTO> gethint(@Path("player") String player, @Path("questioncode") int questioncode);

    @GET("/setting_language/{player}/language/{language}")
    Call <Integer> setlanguage(@Path("player") String player, @Path("language") int language);

    @GET("/id/{player}/pass/{password}/name/{name}/gender/{gender}/age/{age}/email/{email}")
    Call <String> getjoin(@Path("player") String player, @Path("password") String password, @Path("name") String name, @Path("gender") String gender, @Path("age") Integer age, @Path("email") String email);

    @GET("/checkid/{player}")
    Call<String> checkid(@Path("player") String player);

    @GET("/name/{name}/email/{email}")
    Call<FindDTO> findid(@Path("name") String name, @Path("email") String email);

    @GET("/name/{name}/email/{email}/id/{id}")
    Call<FindDTO> findpassword(@Path("name") String name, @Path("email") String email, @Path("id") String id);

}
