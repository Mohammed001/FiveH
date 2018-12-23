package com.example.asus.fiveh;

import com.example.asus.fiveh.models.Ad;
import com.example.asus.fiveh.models.Flower;
import com.example.asus.fiveh.models.FiveHResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitAPI {

    @GET("feeds/flowers.json")
    Call<List<Flower>> listFlowers();

    // _____________________ ((( start it )))) _____________________

    @GET("login.php")
    Call<FiveHResponse> call_5H_login(@Query("email") String user_name, @Query("pass") String password);

    //    signup.php
    @GET("signup.php")
    Call<FiveHResponse> call_5H_signup(@Query("user_email") String email, @Query("pass") String password);

    @GET("logout_icon.php")
    Call<FiveHResponse> call_5H_logout();

    @GET("ads.php")
    Call<List<Ad>> listAds(@Query("page") int page);

}

