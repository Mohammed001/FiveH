package com.example.asus.fiveh;

import android.content.Context;
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


public class MyProfile2 extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public static final String TAG = MyProfile2.class.getSimpleName();
    EditText profile_name_value;
    Drawable profile_name_value_drawable;
    Drawable profile_address_value_drawable;
    EditText profile_age_value;
    EditText profile_address_value;
    Spinner spinner;
    LinearLayout email_generator;
    LayoutInflater layoutInflater;
    ImageView doaddnewemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile2);
        Utils.displaybackarrow(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

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

        spinnerIssues();

        doaddnewemail = findViewById(R.id.doaddnewemail);
        // Parent layout
        email_generator = findViewById(R.id.email_generator);
        // Layout inflater
        layoutInflater = getLayoutInflater();
        doaddnewemail.setOnClickListener(this);

    }

    private void spinnerIssues() {
        spinner = findViewById(R.id.gender_spinner);
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
            case R.id.doaddnewemail:
                // Add the text layout to the parent layout
                view = layoutInflater.inflate(R.layout.to_inflate_new_email, email_generator, false);

                // In order to get the view we have to use the new view with text_layout in it
                LinearLayout horizontal_email = view.findViewById(R.id.horizontal_email);
//                horizontal_email.setTag();
                // Add the text view to the parent layout
                email_generator.addView(horizontal_email);

                break;
        }
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
