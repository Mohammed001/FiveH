package com.example.asus.fiveh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;

import com.example.asus.fiveh.my_ad_adapter.MyAdAdapter;
import com.example.asus.fiveh.utils.Utils;

public class MyActiveAds extends AppCompatActivity {

    MyAdAdapter adAdapter;
    RecyclerView recyclerView;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ads);
        recyclerView = findViewById(R.id.my_ads_rv);
        Utils.displaybackarrow(this);
        adAdapter = new MyAdAdapter(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adAdapter);

        Utils.displaybackarrow(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }
}
