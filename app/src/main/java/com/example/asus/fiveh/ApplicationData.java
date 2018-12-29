package com.example.asus.fiveh;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.asus.fiveh.models.User;

/**
 * Created by ASUS on 11/19/2018.
 */

public class ApplicationData {

    static User current_user;

    static final String APP_PREFERENCES_FILE = "app_preferences";
    static final String USER_DATA = "user_data";
    static final String USER_TYPE = "user_type";

    static final String ADVERTISER = "advertiser";
    static final String GREED = "user";

    public static final String TAG = ApplicationData.class.getSimpleName();

    static void displaybackarrow(AppCompatActivity context) {
        if (context.getSupportActionBar() != null) {
            context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    public static final String AUTHORITY = "com.example.asus.fiveh";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
}
