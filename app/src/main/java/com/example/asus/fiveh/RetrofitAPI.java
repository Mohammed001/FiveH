package com.example.asus.fiveh;

import com.example.asus.fiveh.models.Ad;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitAPI {
    @GET("feeds/flowers.json")
    Call<List<Ad>> listAds();
}

