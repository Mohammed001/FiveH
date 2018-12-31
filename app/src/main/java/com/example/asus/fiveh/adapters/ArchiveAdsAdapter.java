package com.example.asus.fiveh.adapters;


import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.asus.fiveh.R;

public class ArchiveAdsAdapter extends RecyclerView.Adapter<ArchiveAdsAdapter.MyViewHolder> {
    private String[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        LinearLayout mLinearLayout;

        MyViewHolder(LinearLayout v) {
            super(v);
            mLinearLayout = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ArchiveAdsAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ArchiveAdsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                             int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_row_archive_ad, parent, false);

        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        setLocked((ImageView) holder.mLinearLayout.findViewById(R.id.archive_imageview));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    private static void setLocked(ImageView v) {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);  //0 means grayscale
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        v.setColorFilter(cf);
    }

    private static void setUnlocked(ImageView v) {
        v.setColorFilter(null);
        v.setImageAlpha(255);
    }
}


