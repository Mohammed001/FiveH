package com.example.asus.fiveh.models;

import android.graphics.Bitmap;

public class Ad {
    private int adv_id;
    private String adv_text;
    private String file_path; // photo path
    private String adv_type;
    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


    public int getAdv_id() {
        return adv_id;
    }

    public void setAdv_id(int adv_id) {
        this.adv_id = adv_id;
    }

    public String getAdv_text() {
        return adv_text;
    }

    public void setAdv_text(String adv_text) {
        this.adv_text = adv_text;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getAdv_type() {
        return adv_type;
    }

    public void setAdv_type(String adv_type) {
        this.adv_type = adv_type;
    }
}
