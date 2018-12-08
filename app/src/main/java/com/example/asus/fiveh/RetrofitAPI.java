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

    // hazem@sadv.sa
    @GET("login.php")
    Call<Response> call_5H_signin(@Query("email") String user_name, @Query("pass") String password);

    @GET("logout.php")
    Call<Response> call_5H_logout();

    //    signup.php
    @GET("signup.php")
    void call_5H_signup(@Query("login") String user_name,
                                  @Query("pass") String password,
                                  @Query("user_first_name") String firstn,
                                  @Query("user_last_name") String lastn,
                                  @Query("user_email") String email
    );


}

