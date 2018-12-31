package com.example.asus.fiveh;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class MyPoints extends AppCompatActivity implements View.OnClickListener {

    TextView my_points_choose_participation_type_alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_points);
        initviews();
        ApplicationData.displaybackarrow(this);
    }

    private void initviews() {
        my_points_choose_participation_type_alert = findViewById(R.id.my_points_choose_participation_type_alert);
        my_points_choose_participation_type_alert.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_points_choose_participation_type_alert:
                dialog_popup();
                break;
        }
    }

    private void dialog_popup() {

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(getString(R.string.choose_participation_type));

        String[] types = getResources().getStringArray(R.array.options_when_choose_participation_type);
        b.setItems(types, getDialogListener());

        AlertDialog dialog = b.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.drawable.create_ad_dialog_bg);
        dialog.show();
    }

    @NonNull
    private DialogInterface.OnClickListener getDialogListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch (which) {
                    case 0: // Silver
                        Toast.makeText(MyPoints.this, "Silver", Toast.LENGTH_SHORT).show();
                        break;
                    case 1: // Golden
                        Toast.makeText(MyPoints.this, "Golden", Toast.LENGTH_SHORT).show();
                        break;
                    case 2: // Diamond
                        Toast.makeText(MyPoints.this, "Diamond", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
