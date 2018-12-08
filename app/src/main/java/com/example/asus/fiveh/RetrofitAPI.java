package com.example.asus.fiveh;

import com.example.asus.fiveh.models.Ad;
import com.example.asus.fiveh.models.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @GET("feeds/flowers.json")
    Call<List<Ad>> listAds();

    @GET("")
    Call<Response> call_5H_server(@Query("login") String user_name, @Query("pass") String password);


}

