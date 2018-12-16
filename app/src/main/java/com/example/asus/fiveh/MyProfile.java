package com.example.asus.fiveh;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.asus.fiveh.utils.Utils;


public class MyProfile extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public static final String TAG = MyProfile.class.getSimpleName();
    private static final String EMAILS_NO = "EMAILS_NO";
    private static final String MY_PROFILE_SHAREDPREF_NAME = "my_profile";
    EditText profile_name_value;
    Drawable profile_name_value_drawable;
    Drawable profile_address_value_drawable;
    EditText profile_age_value;
    EditText profile_address_value;
    Spinner spinner;
    // vertical linearLayout
    LinearLayout email_generator;
    LayoutInflater layoutInflater;
    ImageView plus_icon_add_new_email;
    ImageView minus_icon_remove_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
        Utils.displaybackarrow(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        layoutInflater = getLayoutInflater();

        profile_name_value = findViewById(R.id.profile_name_value);
        profile_name_value.setOnClickListener(this);
        profile_name_value.setCursorVisible(false);
        profile_name_value.setBackgroundResource(android.R.color.transparent);

        profile_name_value_drawable = profile_name_value.getBackground();

        profile_age_value = findViewById(R.id.profile_age_value);
        profile_age_value.setOnClickListener(this);
        profile_age_value.setCursorVisible(false);
        profile_age_value.setBackgroundResource(android.R.color.transparent);

        profile_address_value = findViewById(R.id.profile_address_value);
        profile_address_value.setOnClickListener(this);
        profile_address_value.setCursorVisible(false);
        profile_address_value.setBackgroundResource(android.R.color.transparent);
        profile_address_value_drawable = profile_address_value.getBackground();

//        spinnerIssues();

//        email_generator = findViewById(R.id.email_generator);

        plus_icon_add_new_email = findViewById(R.id.plus_icon_add_new_email);
        plus_icon_add_new_email.setOnClickListener(this);

        email_generator = findViewById(R.id.email_generator);
        SharedPreferences prefs = getSharedPreferences(MY_PROFILE_SHAREDPREF_NAME, MODE_PRIVATE);
        Editor editor = prefs.edit();
        int count = prefs.getInt(EMAILS_NO, -1);
        for (int i = 0; i < count; i++) {
            // this param is not required bcz {email_generator} is class member variable.. but it make the code clearer  to me!.
            addNewEmail(email_generator);
        }
        // after creating the views we should populate them with saved emails.
        editor.apply();
    }

    private void spinnerIssues() {
//        spinner = findViewById(R.id.gender_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                switch (v.getId()) {
                    case R.id.profile_name_value:
                        profile_name_value.setCursorVisible(false);
                        profile_name_value.setBackgroundResource(android.R.color.transparent);
                        break;
                    case R.id.profile_age_value:
                        profile_age_value.setCursorVisible(false);
                        profile_age_value.setBackgroundResource(android.R.color.transparent);
                        break;
                    case R.id.profile_address_value:
                        profile_address_value.setCursorVisible(false);
                        profile_address_value.setBackgroundResource(android.R.color.transparent);
                        break;
                }


                // disappearing the keyboard
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_name_value:
                profile_name_value.setCursorVisible(true);
                profile_name_value.setBackground(profile_name_value_drawable);
                break;

            case R.id.profile_age_value:
                profile_age_value.setCursorVisible(true);
                profile_age_value.setBackground(profile_name_value_drawable);
                break;

            case R.id.profile_address_value:
                profile_address_value.setCursorVisible(true);
                profile_address_value.setBackground(profile_address_value_drawable);
                break;

            case R.id.plus_icon_add_new_email:
                addNewEmail(email_generator);
                SharedPreferences prefs = getSharedPreferences(MY_PROFILE_SHAREDPREF_NAME, MODE_PRIVATE);
                Editor editor = prefs.edit();
                editor.putInt(EMAILS_NO, email_generator.getChildCount());
                editor.apply();
                break;
        }
    }

    private void addNewEmail(final LinearLayout email_generator) {
        // Add the text layout to the parent layout
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String[] arr = getResources().getStringArray(R.array.gender_array);
        String s = arr[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "Nothing Selected", Toast.LENGTH_SHORT).show();
    }
}
