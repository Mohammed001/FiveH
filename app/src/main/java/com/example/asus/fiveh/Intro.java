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

import static com.example.asus.fiveh.ApplicationData.ADVERTISER;
import static com.example.asus.fiveh.ApplicationData.APP_PREFERENCES_FILE;
import static com.example.asus.fiveh.ApplicationData.GREED;
import static com.example.asus.fiveh.ApplicationData.USER_DATA;
import static com.example.asus.fiveh.ApplicationData.USER_TYPE;

public class Intro extends AppCompatActivity {

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
            btn_go_to_creat_account = findViewById(R.id.btn_go_to_creat_account);
            btn_go_to_creat_account.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intro.this, SignUpActivity.class));
                }
            });

            btn_go_to_login = findViewById(R.id.btn_go_to_login);
            btn_go_to_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intro.this, LoginActivity.class));
                }
            });
        }
    }

    private boolean logedIn() {
        SharedPreferences sharedPref = getSharedPreferences(APP_PREFERENCES_FILE, Context.MODE_PRIVATE);
        String user_as_json = sharedPref.getString(USER_DATA, null);

        if (user_as_json != null) {

            Gson gson = new Gson();
            ApplicationData.current_user = gson.fromJson(user_as_json, User.class);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            finish();
            return true;
        }
        return false;
    }

//    private void fadeSplashOut() {
//        // Set the content view to 0% opacity but visible, so that it is visible
//        // (but fully transparent) during the animation.
//        splash.setAlpha(0f);
//        // Animate the content view to 100% opacity, and clear any animation
//        // listener set on the view.
//        splash.animate()
//                .alpha(1f)
//                .setDuration(SPLASH_DISPLAY_LENGTH)
//                .setListener(null);
//
//        // Animate the loading view to 0% opacity. After the animation ends,
//        // set its visibility to GONE as an optimization step (it won't
//        // participate in layout passes, etc.)
//        splash.animate()
//                .alpha(0f)
//                .setDuration(SPLASH_DISPLAY_LENGTH)
//                .setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        splash.setVisibility(View.GONE);
//                    }
//                });
//    }
}
