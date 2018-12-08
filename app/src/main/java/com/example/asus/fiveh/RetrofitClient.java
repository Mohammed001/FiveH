package com.example.asus.fiveh;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private Retrofit mainRetrofit = null;
    private Retrofit loginRetrofit = null;

    private static final String BASE_ADS_URL = "http://services.hanselandpetal.com/";
    public static final String BASE_ADS_PHOTOS_URL = "http://services.hanselandpetal.com/photos/";

    private static final String BASE_FIVEH_AUTH_URL = "http://5h.sa/api/";

    Retrofit getMainClient() {
        if (mainRetrofit == null) {
            mainRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_ADS_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mainRetrofit;
    }

    Retrofit getAuthClient() {
        if (loginRetrofit == null) {
            loginRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_FIVEH_AUTH_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return loginRetrofit;
    }

}
