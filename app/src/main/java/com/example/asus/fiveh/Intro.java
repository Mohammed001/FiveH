package com.example.asus.fiveh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import static com.example.asus.fiveh.ApplicationData.SIGNUP_ACTIVITY_BEHAVE_LOGIN;
import static com.example.asus.fiveh.ApplicationData.SIGNUP_ACTIVITY_BEHAVE_SIGNUP;
import static com.example.asus.fiveh.ApplicationData.readUserFromSharedPreferences;
import static com.example.asus.fiveh.ApplicationData.signup_activity_behaviour;

public class Intro extends AppCompatActivity {

    AppCompatButton btn_go_to_creat_account;
    AppCompatButton btn_go_to_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);

        // if this method returns {true} then static {current_user} is valid.
        if (readUserFromSharedPreferences(this)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            stickInSignup();
        }
    }

    private void stickInSignup() {
        final Intent intent = new Intent(Intro.this, SignUpActivity.class);
        btn_go_to_creat_account = findViewById(R.id.btn_go_to_creat_account);
        btn_go_to_creat_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup_activity_behaviour = SIGNUP_ACTIVITY_BEHAVE_SIGNUP;
                startActivity(intent);
            }
        });

        btn_go_to_login = findViewById(R.id.btn_go_to_login);
        btn_go_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup_activity_behaviour = SIGNUP_ACTIVITY_BEHAVE_LOGIN;
                startActivity(intent);
            }
        });
    }

}
