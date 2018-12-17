package com.example.asus.fiveh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import static com.example.asus.fiveh.utils.AplicationData.LOGINUSERNAME_KEY;
import static com.example.asus.fiveh.utils.AplicationData.NOTLOGGEDIN;

public class Intro extends AppCompatActivity {

    AppCompatButton btn_go_to_creat_account;
    AppCompatButton btn_go_to_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);

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
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String loginusername = sharedPref.getString(LOGINUSERNAME_KEY, NOTLOGGEDIN);
        if (!loginusername.equals(NOTLOGGEDIN)) {
            // TODO: later we will pass parameters and if user loged out we will clear the preference.
            startActivity(new Intent(this, MainActivity.class));
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
