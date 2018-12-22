package com.example.asus.fiveh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.asus.fiveh.adapters.ArchiveAdsAdapter;

public class MyArchive extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ArchiveAdsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_archive);
        mRecyclerView = findViewById(R.id.archive_rv);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ArchiveAdsAdapter(new String[10]);
        mRecyclerView.setAdapter(mAdapter);
    }
}
