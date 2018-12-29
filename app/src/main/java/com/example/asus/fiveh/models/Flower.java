package com.example.asus.fiveh.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

@Entity
public class Flower {

    @PrimaryKey(autoGenerate = true)
    public int id;
    private int page_id;
    private String instructions;
    private String photo;

    public Flower(int id, int page_id, String instructions, String photo) {
        this.id = id;
        this.page_id = page_id;
        this.instructions = instructions;
        this.photo = photo;
    }

    // ___________________ (( to ignore )) ___________________

    @Ignore
    public Flower(int page_id, String instructions, String photo) {
        this.page_id = page_id;
        this.instructions = instructions;
        this.photo = photo;
    }

    @Ignore
    private Bitmap bitmap;

    // _____________________________ (( setters and getters )) _____________________________

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getPage_id() {
        return page_id;
    }

    public void setPage_id(int page_id) {
        this.page_id = page_id;
    }

}
