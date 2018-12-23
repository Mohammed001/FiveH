package com.example.asus.fiveh.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.asus.fiveh.R;
import com.example.asus.fiveh.RetrofitClient;
import com.example.asus.fiveh.models.Flower;

import java.util.List;

/**
 * Created by ASUS on 11/12/2018.
 */

public class FlowersAdapter extends RecyclerView.Adapter<FlowersAdapter.MainAdViewHolder> {

    private Context context;
    private List<Flower> data;

    public FlowersAdapter(Context context, List<Flower> data) {
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
    public void onBindViewHolder(@NonNull final MainAdViewHolder holder, int position) {
//        String text = mData.get(position);
        if (data != null) {
            final Flower flower = data.get(position);
            final String imageUrl = RetrofitClient.BASE_FLOWERS_PHOTOS_URL + flower.getPhoto();
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
            holder.mTextSwitcher.setCurrentText(flower.getName());
            holder.mTextSwitcher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.mTextSwitcher.getTag().equals("0")) {
                        holder.mTextSwitcher.setText(context.getString(R.string.very_long_text));
                        holder.mTextSwitcher.setTag("1");
                    } else if (holder.mTextSwitcher.getTag().equals("1")) {
                        holder.mTextSwitcher.setText(flower.getName());
                        holder.mTextSwitcher.setTag("0");
                    }
                }
            });

        } else {
            holder.mImageView.setImageResource(R.drawable.image_ad);
            holder.mTextSwitcher.setText(context.getString(R.string.ad_tesxt_placeholder));
        }
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 10;
    }


    class MainAdViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        TextSwitcher mTextSwitcher;

        MainAdViewHolder(View itemView) {
            super(itemView);
            mTextSwitcher = itemView.findViewById(R.id.testTextSwitcher_in_temp);
            mImageView = itemView.findViewById(R.id.ad_image_in_rv_row_user_ad);
            mTextSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
                @Override
                public View makeView() {
                    // Create a new TextView
                    TextView t = new TextView(context);
                    t.setGravity(Gravity.CENTER);
                    //                    t.setMaxLines(1);
//                    t.setTag("0");
//                    t.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            if (view.getTag().equals("0")) {
//                                view.setTag("1");
//                                ((TextView) view).setMaxLines(100);
//                            } else if (view.getTag().equals("1")) {
//                                view.setTag("0");
//                                ((TextView) view).setMaxLines(1);
//                            }
//                        }
//                    });
//                t.setTextAppearance(Temp.this, android.R.style.TextAppearance_Large);
                    return t;
                }
            });

            Animation in = AnimationUtils.loadAnimation(context,
                    android.R.anim.fade_in);
            in.setDuration(100);

            Animation out = AnimationUtils.loadAnimation(context,
                    android.R.anim.fade_out);
            out.setDuration(100);

            mTextSwitcher.setInAnimation(in);
            mTextSwitcher.setOutAnimation(out);
        }

    }

}
