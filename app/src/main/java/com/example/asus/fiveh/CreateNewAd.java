package com.example.asus.fiveh;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.fiveh.utils.ImagePicker;
import com.example.asus.fiveh.utils.Utils;

import java.util.Objects;

public class CreateNewAd extends AppCompatActivity {
    private static final int PICK_IMAGE_ID = 234; // the number doesn't matter
    private static final String PIC = "pic";
    ImageView imageView;
    static Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_ad);
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

    public void launch_dialog(View view) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(getString(R.string.choose_advertisment_type));
        String[] types = getResources().getStringArray(R.array.options_when_create_ad);
        b.setItems(types, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch (which) {
                    case 0: // FACEBOOK
                        Toast.makeText(CreateNewAd.this, "Facebook", Toast.LENGTH_SHORT).show();
                        break;
                    case 1: // Twitter
                        Toast.makeText(CreateNewAd.this, "Twitter", Toast.LENGTH_SHORT).show();
                        break;
                    case 2: // Instagram
                        Toast.makeText(CreateNewAd.this, "Instagram", Toast.LENGTH_SHORT).show();
                        break;
                    case 3: // LinkedIn
                        Toast.makeText(CreateNewAd.this, "LinkedIn", Toast.LENGTH_SHORT).show();
                        break;
                    case 4: // Our website
                        Toast.makeText(CreateNewAd.this, "Our website", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        AlertDialog dialog = b.create();
        ListView listView = dialog.getListView();
//        listView.setDivider(new ColorDrawable(Color.GRAY));
        listView.setDivider(getResources().getDrawable(R.drawable.create_ad_dialog_line));

        listView.setDividerHeight(3);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.drawable.create_ad_dialog_bg);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Finally, display the alert dialog
        dialog.show();
//        dialog.getWindow().setLayout(1000, 1000); //Controlling width and height.



    }

    public void vv(View v){
        View view = getLayoutInflater().inflate(R.layout.temp_sheet_dialog, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        TextView camera_sel = view.findViewById(R.id.camera);
        TextView gallery_sel = view.findViewById(R.id.gallery);
        camera_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CreateNewAd.this, "Camera", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        gallery_sel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CreateNewAd.this, "Gallery", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}