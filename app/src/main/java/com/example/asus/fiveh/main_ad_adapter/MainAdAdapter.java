package com.example.asus.fiveh.main_ad_adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.fiveh.LoginActivity;
import com.example.asus.fiveh.R;
import com.example.asus.fiveh.utils.Utils;

/**
 * Created by ASUS on 11/12/2018.
 */

public class MainAdAdapter extends RecyclerView.Adapter<MainAdViewHolder> {

    private Context context;
    private static final int NUMBER_OF_ADS = 50;

    public MainAdAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public MainAdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_in_main_activity, parent, false);
        return new MainAdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdViewHolder holder, int position) {
//        String text = mData.get(position);
        holder.mText.setText(R.string.AD_TITLE_FIXED);
        holder.mImageView.setImageResource(R.drawable.image_ad);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                if (Utils.USER_TYPE != Utils.NOT_MEMBER) {
                    String url = "http://www.facebook.com";
                    i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
                } else {
                    i = new Intent(context, LoginActivity.class);
                    context.startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return NUMBER_OF_ADS;
    }
}
