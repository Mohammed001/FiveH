package com.example.asus.fiveh.my_ad_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.fiveh.R;

/**
 * Created by ASUS on 11/12/2018.
 */

public class MyAdViewHolder extends RecyclerView.ViewHolder {

    public TextView mTextView;
    public ImageView mImageView;

    public MyAdViewHolder(View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.my_ad_info);
        mImageView = itemView.findViewById(R.id.my_ad_image);
    }

}
