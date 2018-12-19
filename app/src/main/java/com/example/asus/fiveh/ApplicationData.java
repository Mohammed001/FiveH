package com.example.asus.fiveh;

import android.support.v7.app.AppCompatActivity;

import com.example.asus.fiveh.models.User;

/**
 * Created by ASUS on 11/19/2018.
 */

class ApplicationData {

    static User current_user;

    static final String APP_PREFERENCES_FILE = "app_preferences";
    static final String USER_DATA = "user_data";
    static final String USER_TYPE = "user_type";

    static final String ADVERTISER = "advertiser";
    static final String GREED = "user";

    static void displaybackarrow(AppCompatActivity context) {
        if (context.getSupportActionBar() != null) {
            context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }
}
