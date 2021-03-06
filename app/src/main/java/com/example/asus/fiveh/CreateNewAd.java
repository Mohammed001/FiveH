package com.example.asus.fiveh;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.asus.fiveh.utils.ImagePicker;

import java.util.Objects;

public class CreateNewAd extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_IMAGE_ID = 234; // the number doesn't matter
    private static final String PIC = "pic";
    ImageView imageView;
    static Bitmap bitmap;
    EditText advertisement_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_ad);
        ApplicationData.displaybackarrow(this);

        imageView = findViewById(R.id.ad_image_in_rv_row_user_ad);
        advertisement_text = findViewById(R.id.ad_txt);

        advertisement_text.setOnClickListener(this);
//        d = advertisement_text.getBackground();
        advertisement_text.setBackgroundResource(android.R.color.transparent);
        advertisement_text.setCursorVisible(false);

//        imageView.setImageResource(R.drawable.image_ad);
        /* todo: my solution to save the bitmap after rotating the screen
        is to  make the {bitmap} variable static, but the community suggest to
        pass it like {byteArray} flower_data in bundle
        */
        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean(PIC)) {
                imageView.setImageBitmap(bitmap);
//                findViewById(R.id.myImageViewText).setVisibility(View.GONE);
//                findViewById(R.id.newmyImageViewText).setVisibility(View.VISIBLE);
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
//                    findViewById(R.id.myImageViewText).setVisibility(View.GONE);
//                    findViewById(R.id.newmyImageViewText).setVisibility(View.VISIBLE);
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
        b.setItems(types, getDialogListener());

        AlertDialog dialog = b.create();

        ListView listView = dialog.getListView();
//        listView.setDivider(new ColorDrawable(Color.GRAY));
        listView.setDivider(getResources().getDrawable(R.drawable.create_ad_dialog_line));
        listView.setDividerHeight(1);
        listView.setFooterDividersEnabled(false);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.drawable.create_ad_dialog_bg);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Finally, display the alert dialog
        dialog.show();
//        dialog.getWindow().setLayout(1000, 1000); //Controlling width and height.


    }

    @NonNull
    private DialogInterface.OnClickListener getDialogListener() {
        return new DialogInterface.OnClickListener() {
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
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ad_txt:
                advertisement_text.setCursorVisible(true);
                break;
            case R.id.explain_more:
                break;

        }
    }
}