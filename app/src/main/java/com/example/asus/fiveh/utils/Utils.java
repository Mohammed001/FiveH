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

public class Utils {

    public static final int NOT_MEMBER = 0;
    public static final int ADVERTISER = 1;
    public static final int GREED = 2;
    public static int USER_TYPE = NOT_MEMBER;
    public static final String LOGINUSERNAME_KEY = "saas";
    public static final String NOTLOGGEDIN = "saas";


    public static String getJSONObjectFromURL(String urlString) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */);
        urlConnection.setConnectTimeout(15000 /* milliseconds */);
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();

        return sb.toString();
//        System.out.println("JSON: " + jsonString);
//
//        return new JSONObject(jsonString);
    }

    public static void displaybackarrow(AppCompatActivity context) {
        if (context.getSupportActionBar() != null) {
            context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }
}
