package com.example.asus.fiveh;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by ASUS on 11/19/2018.
 */

class AplicationData {
    static final int NOT_MEMBER = 0;
    static final int ADVERTISER_INT = 1;
    static final int GREED_INT = 2;
    static int USER_TYPE_INT = NOT_MEMBER;

    static final String APP_PREFERENCES_FILE = "app_preferences";
    static final String USER_JSON = "data";
    static String USER_TYPE = "user_type";
    static final String ADVERTISER = "advertiser";
    static final String GREED = "user";


    static void displaybackarrow(AppCompatActivity context) {
        if (context.getSupportActionBar() != null) {
            context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }
}
