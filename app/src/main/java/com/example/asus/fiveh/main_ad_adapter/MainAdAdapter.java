package com.example.asus.fiveh.main_ad_adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.asus.fiveh.R;
import com.example.asus.fiveh.RetrofitClient;
import com.example.asus.fiveh.models.Ad;

import java.util.List;

/**
 * Created by ASUS on 11/12/2018.
 */

public class MainAdAdapter extends RecyclerView.Adapter<MainAdAdapter.MainAdViewHolder> {

    static class MainAdViewHolder extends RecyclerView.ViewHolder {

        TextView mText;
        ImageView mImageView;

        MainAdViewHolder(View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.ad_title);
            mImageView = itemView.findViewById(R.id.ad_image);
        }

    }

    private Context context;
    private List<Ad> data;

    public MainAdAdapter(Context context, List<Ad> data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MainAdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_row_user_ad, parent, false);
        return new MainAdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdViewHolder holder, int position) {
//        String text = mData.get(position);
        if (data != null) {
            Ad ad = data.get(position);
//        holder.mText.setText(R.string.AD_TITLE_FIXED);
            holder.mText.setText(ad.getName());
//        holder.mImageView.setImageResource(R.drawable.image_ad);
            final String imageUrl = RetrofitClient.BASE_ADS_PHOTOS_URL + ad.getPhoto();
            Glide.with(context).load(imageUrl)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.image_ad))
                    .into(holder.mImageView);

            holder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(imageUrl));
                    context.startActivity(i);
                }
            });
        } else {
            holder.mImageView.setImageResource(R.drawable.image_ad);
            holder.mText.setText(R.string.ad_tesxt_placeholder);
        }
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 10;
    }
}
