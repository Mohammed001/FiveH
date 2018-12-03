package com.example.asus.fiveh;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.fiveh.utils.Utils;


public class MyProfile extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MyProfile";
    public static final int NORMAL = 0;
    public static final int NAME = 1;
    public static final int EMAIL = 2;
    public static int FLAG = NORMAL;

    private TextView profile_name_tv, profile_email_tv, t3del1, t3del2;
    private EditText profile_name_et, profile_email_et;

    private String new_name = null;
    private String current_name = null;
    private String new_email = null;
    private String current_email = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        Utils.displaybackarrow(this);
        t3del1 = findViewById(R.id.t3del1);
        t3del1.setOnClickListener(this);

        t3del2 = findViewById(R.id.t3del2);
        t3del2.setOnClickListener(this);

        profile_name_tv = findViewById(R.id.profile_name_tv);
        profile_name_et = findViewById(R.id.profile_name_et);

        profile_email_tv = findViewById(R.id.profile_email_tv);
        profile_email_et = findViewById(R.id.profile_email_et);
        handle_if_rotation_occures(savedInstanceState);
    }

    private void handle_if_rotation_occures(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            String temp;
            if (FLAG == NAME) {
                t3del1.setText("حفظ");
                profile_name_tv.setVisibility(View.GONE);
                profile_name_et.setVisibility(View.VISIBLE);
                temp = savedInstanceState.getString("profile_name_et");
                profile_name_et.setText(temp);
            } else if (FLAG == EMAIL) {
                t3del2.setText("حفظ");
                profile_email_tv.setVisibility(View.GONE);
                profile_email_et.setVisibility(View.VISIBLE);
                temp = savedInstanceState.getString("profile_email_et");
                profile_email_et.setText(temp);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (FLAG == NAME) {
            outState.putString("profile_name_et", profile_name_et.getText().toString());
        } else if (FLAG == EMAIL) {
            outState.putString("profile_email_et", profile_name_et.getText().toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.t3del1:
                handle_name_change(t3del1, profile_name_tv, profile_name_et);
                break;
            case R.id.t3del2:
                handle_email_change(t3del2, profile_email_tv, profile_email_et);
                break;
        }
    }

    private void handle_name_change(TextView t3del, TextView plain, EditText edit) {
        // 0. if t3del is pressed for the first time
        if (FLAG == NORMAL) {
            FLAG = NAME;
            // 1. copy text from tv to et
            current_name = plain.getText().toString();
            edit.setText(current_name);
            // 2. hide tv and show ed
            plain.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);
            // 3. change tv from {t3del} to {save}
            t3del.setText("حفظ");
            // 4. if {save} is pressed
        } else if (FLAG == NAME) {
            FLAG = NORMAL;
            // 5. pick the new name
            new_name = edit.getText().toString();
            // 6. compare with the old name
            if (!current_name.equals(new_name)) {
                plain.setText(new_name);
            }
            t3del.setText("تعديل");
            plain.setVisibility(View.VISIBLE);
            edit.setVisibility(View.GONE);
        }
    }

    private void handle_email_change(TextView t3del, TextView plain, EditText edit) {
        // 0. if t3del is pressed for the first time
        if (FLAG == NORMAL) {
            FLAG = EMAIL;
            // 1. copy text from tv to et
            current_email = plain.getText().toString();
            edit.setText(current_email);
            // 2. hide tv and show ed
            plain.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);
            // 3. change tv from {t3del} to {save}
            t3del1.setText("حفظ");
            // 4. if {save} is pressed
        } else if (FLAG == EMAIL) {
            FLAG = NORMAL;
            // 5. pick the new name
            new_email = edit.getText().toString();
            // 6. compare with the old name
            if (!current_email.equals(new_email)) {
                // todo: validate new email
                if (isValid(new_email)) {
                    plain.setText(new_email);
                } else {
                    plain.setText(current_email);
                    Toast.makeText(this, "enter a valid email", Toast.LENGTH_SHORT).show();
                }
            }
            t3del.setText("تعديل");
            plain.setVisibility(View.VISIBLE);
            edit.setVisibility(View.GONE);
        }
    }

    private boolean isValid(String email) {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return false;
        return true;
    }

    public void dododo(View view) {
        // todo : send new data to server

        final ProgressDialog progressDialog = new ProgressDialog(MyProfile.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("saving...");
        progressDialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onSavingData();
                        progressDialog.dismiss();
                    }
                }, 3000);
        finish();
    }

    private void onSavingData() {
        handle_name_change(t3del1, profile_name_tv, profile_name_et);
        handle_name_change(t3del2, profile_email_tv, profile_email_et);
    }


}
