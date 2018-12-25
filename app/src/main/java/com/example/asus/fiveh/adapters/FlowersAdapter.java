package com.example.asus.fiveh.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import com.example.asus.fiveh.MainActivity;
import com.example.asus.fiveh.R;
import com.example.asus.fiveh.RetrofitClient;
import com.example.asus.fiveh.models.Flower;
import com.example.asus.fiveh.my_database.TaskContract.FlowerTable;

import java.util.List;

/**
 * Created by ASUS on 11/12/2018.
 */

public class FlowersAdapter extends RecyclerView.Adapter<FlowersAdapter.MainAdViewHolder> {

    private Context context;
    private List<Flower> data;
    private Cursor mCursor;
    private int flag;
    private static final int CURSOR = 0;
    private static final int LIST = 1;

    public FlowersAdapter(Context context) {
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
//        onBind_using_List(holder, position);
        if (flag == CURSOR) {
            onBind_using_cursor(holder, position);
        } else if (flag == LIST) {
            onBind_using_List(holder, position);
        }

    }

    @Override
    public int getItemCount() {
        if (mCursor != null)
            return mCursor.getCount();
        else if (data != null && data.size() > 0)
            return data.size();
        return 0;
    }


    class MainAdViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        TextSwitcher mTextSwitcher;

        MainAdViewHolder(View itemView) {
            super(itemView);
            mTextSwitcher = itemView.findViewById(R.id.testTextSwitcher_in_rv_row_user_ad);
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

    // ___________________________ ((  my methods )) ___________________________


    private void onBind_using_cursor(@NonNull MainAdViewHolder holder, int position) {
        // Indices for the _id, description, and priority columns
        int idIndex = mCursor.getColumnIndex(FlowerTable._ID);
        int flower_text_Index = mCursor.getColumnIndex(FlowerTable.COLUMN_TEXT);
        int photo_pathIndex = mCursor.getColumnIndex(FlowerTable.COLUMN_PHOTO_PATH);

        mCursor.moveToPosition(position); // get to the right location in the cursor

        // Determine the values of the wanted data
        final int id = mCursor.getInt(idIndex);
        String flower_text = mCursor.getString(flower_text_Index);
        String photo_path = mCursor.getString(photo_pathIndex);

        //Set values
        holder.itemView.setTag(id);
        holder.mTextSwitcher.setText(flower_text);

        final String imageUrl = RetrofitClient.BASE_FLOWERS_PHOTOS_URL + photo_path;

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
                removeFromDatabase(id);
            }
        });
    }

    private void onBind_using_List(@NonNull final MainAdViewHolder holder, int position) {
        final Flower flower = data.get(position);
        final String imageUrl = RetrofitClient.BASE_FLOWERS_PHOTOS_URL + flower.getPhoto();
        Glide.with(context).load(imageUrl)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.image_ad))
                .into(holder.mImageView);
        holder.mTextSwitcher.setCurrentText(flower.getName());
    }

    /**
     * When data changes and a re-query occurs, this function swaps the old Cursor
     * with a newly updated Cursor (Cursor c) that is passed in.
     */
    public Cursor swapCursor(Cursor c) {

        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
            flag = CURSOR;
        }
        return temp;
    }

    /**
     * When data changes and a re-query occurs, this function swaps the old Cursor
     * with a newly updated Cursor (Cursor c) that is passed in.
     */
    public List<Flower> swapList(List<Flower> c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (data == c) {
            return null; // bc nothing has changed
        }
        List<Flower> temp = data;
        this.data = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
            flag = LIST;
        }
        return temp;
    }

    private void removeFromDatabase(int id) {

        // Build appropriate uri with String row id appended
        String stringId = Integer.toString(id);
        Uri uri = FlowerTable.CONTENT_URI;
        uri = uri.buildUpon().appendPath(stringId).build();

        // Delete via a ContentResolver
        context.getContentResolver().delete(uri, null, null);

        // COMPLETED: Restart the loader to re-query for all tasks after a deletion
        ((MainActivity) context).doit_ads();
    }


    public interface intface {
        void doit_flowers();
    }

}
