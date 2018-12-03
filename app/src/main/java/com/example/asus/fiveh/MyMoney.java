package com.example.asus.fiveh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.asus.fiveh.utils.Utils;

public class MyMoney extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_money);
        Utils.displaybackarrow(this);
    }
}
