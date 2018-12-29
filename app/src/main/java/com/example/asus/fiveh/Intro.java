package com.example.asus.fiveh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.example.asus.fiveh.models.User;
import com.google.gson.Gson;

import static com.example.asus.fiveh.ApplicationData.APP_PREFERENCES_FILE;
import static com.example.asus.fiveh.ApplicationData.USER_DATA_KEY;

public class Intro extends AppCompatActivity {

    static final String BEHAVE_KEY = "BAHAVE_KEY";
    static final String BEHAVE_SIGNUP = "BAHAVE_SIGNUP";
    static final String BEHAVE_LOGIN = "BEHAVE_LOGIN";
    AppCompatButton btn_go_to_creat_account;
    AppCompatButton btn_go_to_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        decide();
    }

    private void decide() {

        if (!logedIn()) {
//        if (true) {
            final Intent intent = new Intent(Intro.this, SignUpActivity.class);
            btn_go_to_creat_account = findViewById(R.id.btn_go_to_creat_account);
            btn_go_to_creat_account.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.putExtra(BEHAVE_KEY, BEHAVE_SIGNUP);
                    startActivity(intent);
                }
            });

            btn_go_to_login = findViewById(R.id.btn_go_to_login);
            btn_go_to_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent.putExtra(BEHAVE_KEY, BEHAVE_LOGIN);
                    startActivity(intent);
                }
            });
        }
    }

    private boolean logedIn() {

        SharedPreferences sharedPref = getSharedPreferences(APP_PREFERENCES_FILE, Context.MODE_PRIVATE);
        String user_as_json_string = sharedPref.getString(USER_DATA_KEY, null);

        if (user_as_json_string != null) {

            Gson gson = new Gson();
            // this will long to the whole app lifetime..
            ApplicationData.current_user = gson.fromJson(user_as_json_string, User.class);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            finish();
            return true;
        }
        return false;
    }
}
