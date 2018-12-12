package com.example.asus.fiveh.my_ad_adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.fiveh.LoginActivity;
import com.example.asus.fiveh.MainActivity;
import com.example.asus.fiveh.R;

/**
 * Created by ASUS on 11/12/2018.
 */

public class MyAdAdapter extends RecyclerView.Adapter<MyAdViewHolder> {

    private Context context;
    private static final int NUMBER_OF_ADS = 10;

    public MyAdAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public MyAdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_ads_row, parent, false);
        return new MyAdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdViewHolder holder, int position) {
//        String text = mData.get(position);
        holder.mImageView.setImageResource(R.drawable.image_ad);
    }

    @Override
    public int getItemCount() {
        return NUMBER_OF_ADS;
    }
}
