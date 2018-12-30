package com.example.asus.fiveh.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.asus.fiveh.R;
import com.example.asus.fiveh.Repository;
import com.example.asus.fiveh.RetrofitClient;
import com.example.asus.fiveh.models.Flower;
import com.example.asus.fiveh.utils.ReadMoreOption;

import java.util.List;

/**
 * Created by ASUS on 11/12/2018.
 */

public class FlowersAdapter extends RecyclerView.Adapter<FlowersAdapter.MainAdViewHolder> {

    private Context context;
    private List<Flower> mFlowers;
    private ReadMoreOption readMoreOption;

    public FlowersAdapter(Context context) {
        this.context = context;
        readMoreOption = new ReadMoreOption.Builder(context)
//                .textLength(3, ReadMoreOption.TYPE_LINE) // OR
//                .textLength(300, ReadMoreOption.TYPE_CHARACTER)
                .moreLabel(context.getString(R.string.read_more_label))
                .lessLabel(context.getString(R.string.read_less_label))
                .moreLabelColor(Color.GRAY)
                .lessLabelColor(Color.GRAY)
//                .labelUnderLine(true)
//                .expandAnimation(true)
                .build();
    }

    @NonNull
    @Override
    public MainAdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_row_user_ad, parent, false);
        return new MainAdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainAdViewHolder holder, int position) {
        final Flower flower = mFlowers.get(position);

        final String imageUrl = RetrofitClient.BASE_FLOWERS_PHOTOS_URL + flower.getPhoto();
        Glide.with(context).load(imageUrl)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.image_ad))
                .into(holder.mImageView);

        readMoreOption.addReadMoreTo(holder.mTextView, flower.getInstructions());

        holder.mImageView.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(imageUrl));
            context.startActivity(i);
            if (Repository.getInstance() != null) {
                Repository.getInstance().deleteFlower(flower);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mFlowers != null ? mFlowers.size() : 0;
    }

    class MainAdViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        TextView mTextView;

        MainAdViewHolder(View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.ad_image_in_rv_row_user_ad);

            mTextView = itemView.findViewById(R.id.testReadMoreText_in_rv_row_user_ad);

            Animation in = AnimationUtils.loadAnimation(context,
                    android.R.anim.fade_in);
            in.setDuration(100);

            Animation out = AnimationUtils.loadAnimation(context,
                    android.R.anim.fade_out);
            out.setDuration(100);
        }

    }

    // _____________________ (( helper functions )) _____________________

    public void setFlowers(List<Flower> mFlowers) {
        this.mFlowers = mFlowers;
        notifyDataSetChanged();
    }

}
