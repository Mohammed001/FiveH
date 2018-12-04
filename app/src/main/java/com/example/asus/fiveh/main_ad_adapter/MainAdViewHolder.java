package com.example.asus.fiveh.main_ad_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.fiveh.R;

/**
 * Created by ASUS on 11/12/2018.
 */

class MainAdViewHolder extends RecyclerView.ViewHolder {

    TextView mText;
    ImageView mImageView;

    MainAdViewHolder(View itemView) {
        super(itemView);
        mText = itemView.findViewById(R.id.ad_title);
        mImageView = itemView.findViewById(R.id.ad_image);
    }

}
