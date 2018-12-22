package com.example.asus.fiveh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class Temp extends AppCompatActivity {
    TextSwitcher testTextSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp);

        testTextSwitcher = findViewById(R.id.testTextSwitcher);
        testTextSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                // Create a new TextView
                TextView t = new TextView(Temp.this);
                t.setGravity(Gravity.CENTER);
//                t.setTextAppearance(Temp.this, android.R.style.TextAppearance_Large);
                return t;
            }
        });

        Animation in = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in);
        in.setDuration(100);

        Animation out = AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out);
        out.setDuration(100);

        testTextSwitcher.setInAnimation(in);
        testTextSwitcher.setOutAnimation(out);
        testTextSwitcher.setCurrentText("short text");
        testTextSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (testTextSwitcher.getTag().equals("0")) {
                    testTextSwitcher.setText(getString(R.string.very_long_text));
                    testTextSwitcher.setTag("1");
                } else if (testTextSwitcher.getTag().equals("1")) {
                    testTextSwitcher.setText("short text");
                    testTextSwitcher.setTag("0");
                }
            }
        });
    }
}
