package com.example.asus.fiveh.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

@Entity
public class Flower {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @SerializedName("instructions")
    private String instructions;

    @SerializedName("photo")
    private String photo;

    // ___________________ (( constructor )) ___________________

    public Flower(int id, String instructions, String photo) {
        this.id = id;
        this.instructions = instructions;
        this.photo = photo;
    }

    // ___________________ (( to ignore )) ___________________

    @Ignore
    public Flower(String instructions, String photo) {
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

}
