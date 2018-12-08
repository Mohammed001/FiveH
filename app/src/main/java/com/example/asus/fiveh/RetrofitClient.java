package com.example.asus.fiveh;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private Retrofit retrofit = null;
    private static final String BASE_ADS_URL = "http://services.hanselandpetal.com/";
    public static final String BASE_ADS_PHOTOS_URL = "http://services.hanselandpetal.com/photos/";

    public static final String BASE_FIVEH_LOGIN_URL = "http://5h.sa/api/login.php";
    public static final String BASE_FIVEH_SIGNUP_URL = "http://5h.sa/api/signup.php";

    Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_ADS_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
