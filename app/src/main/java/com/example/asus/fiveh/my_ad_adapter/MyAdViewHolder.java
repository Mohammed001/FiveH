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

class MyAdViewHolder extends RecyclerView.ViewHolder {

    TextView mTextView;
    ImageView mImageView;

    MyAdViewHolder(View itemView) {
        super(itemView);
        mTextView = itemView.findViewById(R.id.make_it_clearer);
        mImageView = itemView.findViewById(R.id.my_ad_image);
    }

}
