package com.example.asus.fiveh;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private Retrofit retrofit = null;
    private static final String BASEURL = "http://services.hanselandpetal.com/";
    public static final String BASEPHOTOURL = "http://services.hanselandpetal.com/photos/";

    Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
