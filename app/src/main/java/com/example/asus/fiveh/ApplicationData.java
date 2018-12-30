package com.example.asus.fiveh;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.asus.fiveh.models.User;

/**
 * Created by ASUS on 11/19/2018.
 */

class ApplicationData {

    static User current_user;

    static final String APP_PREFERENCES_FILE = "app_preferences";
    static final String USER_DATA_KEY = "user_data";
    static final String USER_TYPE_KEY = "user_type";

    static final String ADVERTISER_WORD = "advertiser";
    static final String USER_WORD = "user";

    static final String TAG = ApplicationData.class.getSimpleName();

    static void displaybackarrow(AppCompatActivity context) {
        if (context.getSupportActionBar() != null) {
            context.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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


}
