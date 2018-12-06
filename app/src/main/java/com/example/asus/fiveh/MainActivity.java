package com.example.asus.fiveh;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.asus.fiveh.main_ad_adapter.MainAdAdapter;
import com.example.asus.fiveh.models.Ad;
import com.example.asus.fiveh.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.asus.fiveh.utils.Utils.ADVERTISER;
import static com.example.asus.fiveh.utils.Utils.LOGINUSERNAME_KEY;
import static com.example.asus.fiveh.utils.Utils.NOTLOGGEDIN;
import static com.example.asus.fiveh.utils.Utils.NOT_MEMBER;
import static com.example.asus.fiveh.utils.Utils.USER_TYPE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    FloatingActionButton fab;
    RecyclerView main_rv;
    MainAdAdapter adAdapter;
    List<Ad> data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.navigation);
        Utils.USER_TYPE = ADVERTISER;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);


        RetrofitAPI service = new RetrofitClient().getClient().create(RetrofitAPI.class);
        Call<List<Ad>> call = service.listAds();
        call.enqueue(new Callback<List<Ad>>() {
            @Override
            public void onResponse(Call<List<Ad>> call, Response<List<Ad>> response) {
                data = response.body();
                if (data != null) {
                    Log.i(TAG, "onResponse: Number of Ads received: " + data.size());
                }
            }

            @Override
            public void onFailure(Call<List<Ad>> call, Throwable t) {
                Log.i(TAG, "onFailure" + t.getMessage());
            }
        });

        main_rv = findViewById(R.id.main_rv);
        adAdapter = new MainAdAdapter(this);

        GridLayoutManager layoutManager;
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(this, 1);
        } else {
            layoutManager = new GridLayoutManager(this, 2);
        }
        main_rv.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(main_rv.getContext(), layoutManager.getOrientation());
        main_rv.addItemDecoration(itemDecoration);
        main_rv.setAdapter(adAdapter);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);      // Disable the button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false); // Remove the left caret
            getSupportActionBar().setDisplayShowHomeEnabled(false); // Remove the icon
        }
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = null;

        if (id == R.id.nav_my_profile) {
            intent = new Intent(this, MyProfile.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        } else if (id == R.id.nav_my_accounts) {
            // Handle the camera action
            if (USER_TYPE == ADVERTISER) {
                intent = new Intent(this, MyAds.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            } else if (USER_TYPE == Utils.GREED) {
                intent = new Intent(this, MyMoney.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        } else if (id == R.id.nav_my_points) {
            Snackbar.make(findViewById(R.id.main_root), "Not Implemented yet", Snackbar
                    .LENGTH_SHORT).show();
        } else if (id == R.id.nav_settings) {
            Snackbar.make(findViewById(R.id.main_root), "Not Implemented yet", Snackbar
                    .LENGTH_SHORT).show();
        } else if (id == R.id.nav_about) {
            build_about();
        } else if (id == R.id.logout) {
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void build_about() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("حول")
                .setMessage("تطبيق 5H للإعلان")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    protected void logout_actions() {
        USER_TYPE = NOT_MEMBER;
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(LOGINUSERNAME_KEY, NOTLOGGEDIN);
        editor.apply();
        Intent intent;
        intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (USER_TYPE == ADVERTISER) {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), CreateNewAd.class);
                    startActivity(intent);
                }
            });
        }
    }
}

