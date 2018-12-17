package com.example.asus.fiveh.utils;

import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ASUS on 11/19/2018.
 */

public class AplicationData {

    public static final int NOT_MEMBER = 0;
    public static final int ADVERTISER = 1;
    public static final int GREED = 2;
    public static int USER_TYPE = NOT_MEMBER;
    public static final String LOGINUSERNAME_KEY = "saas";
    public static final String NOTLOGGEDIN = "saas";

    public static void displaybackarrow(AppCompatActivity context) {
        if (context.getSupportActionBar() != null) {
            context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }
}
