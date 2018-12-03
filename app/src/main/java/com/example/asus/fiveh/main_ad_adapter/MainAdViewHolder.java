package com.example.asus.fiveh.main_ad_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.asus.fiveh.R;

/**
 * Created by ASUS on 11/12/2018.
 */

public class MainAdViewHolder extends RecyclerView.ViewHolder {

    public EditText mEditText;
    public ImageView mImageView;

    public MainAdViewHolder(View itemView) {
        super(itemView);
        mEditText = itemView.findViewById(R.id.ad_title);
        mImageView = itemView.findViewById(R.id.ad_image);
    }

}
