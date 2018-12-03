package com.example.asus.fiveh;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.asus.fiveh.utils.ImagePicker;
import com.example.asus.fiveh.utils.Utils;

public class CreateNewAd extends AppCompatActivity {
    private static final int PICK_IMAGE_ID = 234; // the number doesn't matter
    private static final String PIC = "pic";
    ImageView imageView;
    static Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_ad);
        Utils.displaybackarrow(this);
        imageView = findViewById(R.id.ad_image);
//        imageView.setImageResource(R.drawable.image_ad);
        /* todo: my solution to save the bitmap after rotating the screen
        is to  make the {bitmap} variable static, but the community suggest to pass it like {byteArray} data
        in bundle
        */
        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean(PIC)) {
                imageView.setImageBitmap(bitmap);
                findViewById(R.id.myImageViewText).setVisibility(View.GONE);
                findViewById(R.id.newmyImageViewText).setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (bitmap != null) {
            outState.putBoolean(PIC, true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICK_IMAGE_ID:
                    bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                    // use the bitmap
                    imageView.setImageBitmap(bitmap);
                    findViewById(R.id.myImageViewText).setVisibility(View.GONE);
                    findViewById(R.id.newmyImageViewText).setVisibility(View.VISIBLE);
                    break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        }
    }

    public void uploadNewImage(View view) {
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
    }

    public void createAd(View view) {
        finish();
    }
}