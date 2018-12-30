package com.example.asus.fiveh;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.asus.fiveh.models.User;
import com.google.gson.Gson;

/**
 * Created by ASUS on 11/19/2018.
 */

class ApplicationData {

    private static final String APP_PREFERENCES_FILE = "app_preferences";
    private static final String ALL_USER_INFO_KEY = "user_data";

    static String signup_activity_behaviour;
    static final String SIGNUP_ACTIVITY_BEHAVE_SIGNUP = "BAHAVE_SIGNUP";
    static final String SIGNUP_ACTIVITY_BEHAVE_LOGIN = "BEHAVE_LOGIN";

    static User current_user;
    static final String ADVERTISER_WORD = "advertiser";
    static final String USER_WORD = "user";

    static final String TAG = ApplicationData.class.getSimpleName();

    static void writeUserIntoSharedPreferences(Activity activity, User user) {
        current_user = user;
        SharedPreferences sharedPref = activity.getSharedPreferences(APP_PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPref.edit();
        Gson gson = new Gson();
        String user_data = gson.toJson(current_user);
        prefsEditor.putString(ALL_USER_INFO_KEY, user_data);
        prefsEditor.apply();
    }

    static boolean readUserFromSharedPreferences(Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences(APP_PREFERENCES_FILE, Context.MODE_PRIVATE);
        String user_as_json_string = sharedPref.getString(ALL_USER_INFO_KEY, null);
        if (user_as_json_string != null) {
            Gson gson = new Gson();
            current_user = gson.fromJson(user_as_json_string, User.class);
            return true;
        }
        return false;
    }

    static void hideKeyBoardWhenTouchOutSideEditText(Activity activity, MotionEvent event, int... ids) {
        // dont forget this in on create:
        //         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = activity.getCurrentFocus();
            if (v instanceof EditText) {

                for (int i : ids) {
                    if (v.getId() == i) {
                        EditText editText = activity.findViewById(i);
                        editText.setCursorVisible(false);
                        editText.setBackgroundResource(android.R.color.transparent);
                    }
                }
                // disappearing the keyboard
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
    }

    static void displaybackarrow(AppCompatActivity context) {
        if (context.getSupportActionBar() != null) {
            context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

}
