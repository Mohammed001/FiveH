package com.example.asus.fiveh.tests;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.fiveh.R;
import com.example.asus.fiveh.utils.ReadMoreTextView;
import com.example.asus.fiveh.utils.Utils;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity {

    public static final String PHOTOS_BASE_URL =
            "http://services.hanselandpetal.com/photos/";
    public static final String ENDPOINT =
            "http://services.hanselandpetal.com";
    ProgressBar pb;

    List<Flower> flowerList;
    Button btn;
    ReadMoreTextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        output = findViewById(R.id.newtext);
//        output.setText(getString(R.string.very_long_text));
//        output.setText("");

        pb = findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);

        btn = findViewById(R.id.testbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnline()) {
                    requestData("http://services.hanselandpetal.com/feeds/flowers.json");
                } else {
                    Toast.makeText(TestActivity.this, "Network isn't available", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void requestData(String uri) {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build();

        FlowersAPI api = adapter.create(FlowersAPI.class);

        api.getFeed(new Callback<List<Flower>>() {

            @Override
            public void success(List<Flower> arg0, Response arg1) {
                flowerList = arg0;
                updateDisplay();
            }

            @Override
            public void failure(RetrofitError arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    protected void updateDisplay() {
        //Use FlowerAdapter to display data
        FlowerAdapter adapter = new FlowerAdapter(this, R.layout.item_flower, flowerList);
        setListAdapter(adapter);
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
}
