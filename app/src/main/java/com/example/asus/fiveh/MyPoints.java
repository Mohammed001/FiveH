package com.example.asus.fiveh;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.widget.SeekBar;

public class MyPoints extends AppCompatActivity {

    AppCompatSeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_points);
        seekBar = findViewById(R.id.my_points_seekbar);
        int i = get_seekbar_progress_from_user_type();
        seekBar.setProgress(i);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    seekBar.setProgress(50, true);
                } else {
                    seekBar.setProgress(50);
                }
            }
        });
    }

    private int get_seekbar_progress_from_user_type() {
        return seekBar.getMax() / 3;
    }
}
