package com.example.asus.fiveh.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Ad {

    @PrimaryKey(autoGenerate = true)
    @Expose
    private int id;  // transient from {gson}

    @SerializedName("adv_id")
    @Expose
    private int adId;

    @SerializedName("adv_text")
    @Expose
    private String adText;

    @SerializedName("file_path")
    @Expose
    private String photo_path;

    @SerializedName("adv_type")
    @Expose
    private String adType;

    // _________________ (()) _________________
    @Ignore // Ignore from {ROOM}
    private Bitmap bitmap; // transient from {gson}

    // _________________ ((constructors)) _________________

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


    public int getAdId() {
        return adId;
    }

    public void setAdId(int adId) {
        this.adId = adId;
    }

    public String getAdText() {
        return adText;
    }

    public void setAdText(String adText) {
        this.adText = adText;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public String getAdType() {
        return adType;
    }

    public void setAdType(String adType) {
        this.adType = adType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
