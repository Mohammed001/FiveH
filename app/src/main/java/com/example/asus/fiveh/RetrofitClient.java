package com.example.asus.fiveh;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private Retrofit flowersRetrofit = null;
    private Retrofit adsRetrofit = null;
    private Retrofit loginRetrofit = null;

    private static final String BASE_FLOWERS_URL = "http://services.hanselandpetal.com/";
    public static final String BASE_FLOWERS_PHOTOS_URL = "http://services.hanselandpetal.com/photos/";

    private static final String BASE_ADS_URL = "http://5h.sa/api/";
    // todo
    public static final String BASE_ADS_PHOTOS_URL = "http://5h.sa/images/ads/";

    private static final String BASE_FIVEH_AUTH_URL = "http://5h.sa/api/";

    Retrofit getFlowersRetrofitClient() {
        if (flowersRetrofit == null) {
            flowersRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_FLOWERS_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return flowersRetrofit;
    }

    Retrofit getAdsRetrofitClient() {
        if (adsRetrofit == null) {
            adsRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_ADS_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return adsRetrofit;
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
