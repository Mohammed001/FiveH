package com.example.asus.fiveh;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.asus.fiveh.utils.ImagePicker;

public class MyProfile extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_IMAGE_ID = 234; // the number doesn't matter
    private static final String PIC = "pic";
    ImageView imageView;
    static Bitmap bitmap;

    EditText profile_name_value;
    Drawable et_drawable;
    EditText profile_age_value;
    EditText profile_address_value;

    // vertical linearLayout
    LinearLayout email_generator;
    LinearLayout phone_generator;
    ImageView plus_icon_add_new_email;
    ImageView plus_icon_add_new_phone;
    ImageView minus_icon_remove_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
        ApplicationData.displaybackarrow(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        profile_name_value = findViewById(R.id.profile_name_value);
        profile_name_value.setOnClickListener(this);
        profile_name_value.setCursorVisible(false);
        profile_name_value.setBackgroundResource(android.R.color.transparent);
        et_drawable = profile_name_value.getBackground();

        profile_age_value = findViewById(R.id.profile_age_value);
        profile_age_value.setOnClickListener(this);
        profile_age_value.setCursorVisible(false);
        profile_age_value.setBackgroundResource(android.R.color.transparent);

        profile_address_value = findViewById(R.id.profile_address_value);
        profile_address_value.setOnClickListener(this);
        profile_address_value.setCursorVisible(false);
        profile_address_value.setBackgroundResource(android.R.color.transparent);

        plus_icon_add_new_email = findViewById(R.id.plus_icon_add_new_email);
        plus_icon_add_new_email.setOnClickListener(this);
        email_generator = findViewById(R.id.email_generator);
//        addNewEmail(email_generator);

        plus_icon_add_new_phone = findViewById(R.id.plus_icon_add_new_phone);
        plus_icon_add_new_phone.setOnClickListener(this);
        phone_generator = findViewById(R.id.phone_generator);
//        addNewPhone(phone_generator);
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
    public boolean dispatchTouchEvent(MotionEvent event) {
        ApplicationData.hideKeyBoardWhenTouchOutSideEditText(this, event, R.id.profile_name_value, R.id.profile_age_value, R.id.profile_address_value);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_name_value:
                profile_name_value.setCursorVisible(true);
                profile_name_value.setBackground(et_drawable);
                break;

            case R.id.profile_age_value:
                profile_age_value.setCursorVisible(true);
                profile_age_value.setBackground(et_drawable);
                break;

            case R.id.profile_address_value:
                profile_address_value.setCursorVisible(true);
                profile_address_value.setBackground(et_drawable);
                break;

            case R.id.plus_icon_add_new_email:
                addNewEmail(email_generator);
                break;

            case R.id.plus_icon_add_new_phone:
                addNewPhone(phone_generator);
                break;
        }
    }

    private void addNewEmail(final LinearLayout email_generator) {
        // Add the text layout to the parent layout
        LayoutInflater layoutInflater = getLayoutInflater();
        final LinearLayout new_horizontal_email = (LinearLayout) layoutInflater.inflate(R.layout.to_inflate_new_email, email_generator, false);
        ImageView minus = new_horizontal_email.findViewById(R.id.minus_icon_remove_email);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email_generator.removeView(new_horizontal_email);
            }
        });
        // Add the text view to the parent layout
        email_generator.addView(new_horizontal_email);
    }

    private void addNewPhone(final LinearLayout phone_generator) {
        // Add the text layout to the parent layout
        LayoutInflater layoutInflater = getLayoutInflater();
        final LinearLayout new_horizontal_phone = (LinearLayout) layoutInflater.inflate(R.layout.to_inflate_new_phone, phone_generator, false);
        ImageView minus = new_horizontal_phone.findViewById(R.id.minus_icon_remove_phone);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone_generator.removeView(new_horizontal_phone);
            }
        });
        // Add the text view to the parent layout
        phone_generator.addView(new_horizontal_phone);
    }

    public void uploadNewImage(View view) {
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
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



}
