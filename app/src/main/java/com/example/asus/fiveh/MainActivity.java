package com.example.asus.fiveh;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.asus.fiveh.adapters.AdsAdapter;
import com.example.asus.fiveh.adapters.FlowersAdapter;
import com.example.asus.fiveh.models.Ad;
import com.example.asus.fiveh.models.FiveHResponse;
import com.example.asus.fiveh.models.Flower;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.asus.fiveh.ApplicationData.ADVERTISER;
import static com.example.asus.fiveh.ApplicationData.GREED;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    FloatingActionButton fab;
    RecyclerView main_rv;
    FlowersAdapter flowersAdapter;
    AdsAdapter adsAdapter;
    List<Flower> flower_data = null;
    List<Ad> ad_data = null;
    SwipeRefreshLayout swiperefreshlayout;
    NavigationView navigationView;
    private static final int TASK_LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation);
//        ApplicationData.USER_TYPE_INT = ADVERTISER_INT;
        initViewsWithListeners();

//        https://kodejava.org/how-to-convert-json-string-to-java-object/
//        https://stackoverflow.com/questions/10308452/how-to-convert-the-following-json-string-to-java-object

        if (isOnline()) {
//            doHTTP_flowers();
            doHTTP_ads();
        } else {
            displayWhatInDataBase();
        }
    }

    private void displayWhatInDataBase() {

    }

    private void doHTTP_ads() {
        RetrofitAPI service = new RetrofitClient().getAdsRetrofitClient().create(RetrofitAPI.class);
        // todo page id!!!!
        Call<List<Ad>> call = service.listAds(0);
        ///////////////////////////
        call.enqueue(adConnectionCallback());
    }
    @NonNull
    private Callback<List<Ad>> adConnectionCallback() {
        return new Callback<List<Ad>>() {
            @Override
            public void onResponse(@NonNull Call<List<Ad>> call, @NonNull Response<List<Ad>> response) {
                ad_connection_succeed(response);
            }

            @Override
            public void onFailure(@NonNull Call<List<Ad>> call, @NonNull Throwable t) {
                ad_connection_failed(t);
            }
        };
    }
    private void ad_connection_succeed(Response<List<Ad>> response) {
        if (response.isSuccessful()) {
            ad_data = response.body();
            if (ad_data != null) {
                Log.i(TAG, "onResponse: Number of Ads received: " + ad_data.size());
                createAdsRecyclerview();
            }
        }
    }
    private void ad_connection_failed(Throwable t) {
        Log.i(TAG, "onFailure" + t.getMessage());
        displayWhatInDataBase();
    }

    private void doHTTP_flowers() {
        RetrofitAPI service = new RetrofitClient().getFlowersRetrofitClient().create(RetrofitAPI.class);
        Call<List<Flower>> call = service.listFlowers();
        call.enqueue(flowerConnectionCallback());
    }
    @NonNull
    private Callback<List<Flower>> flowerConnectionCallback() {
        return new Callback<List<Flower>>() {
            @Override
            public void onResponse(@NonNull Call<List<Flower>> call, @NonNull Response<List<Flower>> response) {
                flower_connection_succeed(response);
            }

            @Override
            public void onFailure(@NonNull Call<List<Flower>> call, @NonNull Throwable t) {
                flower_connection_failed(t);
            }
        };
    }
    private void flower_connection_succeed(Response<List<Flower>> response) {
        if (response.isSuccessful()) {
            flower_data = response.body();
            if (flower_data != null) {
                Log.i(TAG, "onResponse: Number of Flowers received: " + flower_data.size());
                createFlowersRecyclerview();
            }
        }
    }
    private void flower_connection_failed(Throwable t) {
        Log.i(TAG, "onFailure" + t.getMessage());
        // todo
        createFlowersRecyclerview();
    }



    ///// ______________________________ view issue ______________________________ /////


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent = null;

        if (id == R.id.nav_my_profile) {
            intent = new Intent(this, MyProfile.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        } else if (id == R.id.nav_my_accounts) {
            goToAccountsOrArchive();
        } else if (id == R.id.nav_my_points) {
            intent = new Intent(this, MyPoints.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            Snackbar.make(findViewById(R.id.main_root), "Not Implemented yet", Snackbar
                    .LENGTH_SHORT).show();
        } else if (id == R.id.nav_about) {
            build_about();
        } else if (id == R.id.logout) {
            // clear the preferences file when connection sucess with server
            // with the one line:
            //////// getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit().clear().apply();
            RetrofitAPI service = new RetrofitClient().getAuthClient().create(RetrofitAPI.class);
            service.call_5H_logout().enqueue(new Callback<FiveHResponse>() {
                @Override
                public void onResponse(@NonNull Call<FiveHResponse> call, @NonNull Response<FiveHResponse> response) {
                    if (response.body() != null) {
                        Log.i(TAG, "onResponse: " + (response.body().getResult()));
                        Log.i(TAG, "onResponse: " + response.body().getMsg());
                    } else {
                        Log.i(TAG, "onResponse: no JSON!");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<FiveHResponse> call, @NonNull Throwable t) {
                    Toast.makeText(MainActivity.this, "onFailure: " + TAG, Toast.LENGTH_SHORT).show();
                }
            });
            intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void goToAccountsOrArchive() {
        Intent intent;
        if (ApplicationData.current_user.getUser_type().equals(GREED)) {
            intent = new Intent(this, MyAccounts.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        } else if (ApplicationData.current_user.getUser_type().equals(ADVERTISER)) {
            intent = new Intent(this, MyArchive.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
    }

    private void build_about() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("حول")
                .setMessage(R.string.about_msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @NonNull
    private SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swiperefreshlayout.setRefreshing(false);
                if (isOnline()) {
                    doHTTP_flowers();
                }
            }
        };
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

        if (ApplicationData.current_user.getUser_type().equals(ADVERTISER)) {
            menu.findItem(R.id.nav_my_profile).setVisible(false);
            menu.findItem(R.id.nav_my_accounts).setTitle(R.string.my_archive);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);      // Disable the button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false); // Remove the left caret
            getSupportActionBar().setDisplayShowHomeEnabled(false); // Remove the icon
        }
        return true;
    }

    private void initViewsWithListeners() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        swiperefreshlayout = findViewById(R.id.swiperefreshlayout);
        swiperefreshlayout.setOnRefreshListener(getOnRefreshListener());
        fab = findViewById(R.id.fab);

        LinearLayout linearLayout = navigationView.getHeaderView(0).findViewById(R.id.image_user_bundle);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        if (ApplicationData.current_user.getUser_type().equals(ADVERTISER)) {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), CreateNewAd.class);
                    startActivity(intent);
                }
            });
        }

        onCreateDrawer(toolbar);
    }

    private void onCreateDrawer(Toolbar toolbar) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void createFlowersRecyclerview() {
        main_rv = findViewById(R.id.main_rv);
        flowersAdapter = new FlowersAdapter(this, flower_data);

        GridLayoutManager layoutManager;
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(this, 1);
        } else {
            layoutManager = new GridLayoutManager(this, 2);
        }
        main_rv.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(main_rv.getContext(), layoutManager.getOrientation());
        main_rv.addItemDecoration(itemDecoration);
        main_rv.setAdapter(flowersAdapter);
    }

    private void createAdsRecyclerview() {
        main_rv = findViewById(R.id.main_rv);
        adsAdapter = new AdsAdapter(this, ad_data);

        GridLayoutManager layoutManager;
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(this, 1);
        } else {
            layoutManager = new GridLayoutManager(this, 2);
        }
        main_rv.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(main_rv.getContext(), layoutManager.getOrientation());
        main_rv.addItemDecoration(itemDecoration);
        main_rv.setAdapter(adsAdapter);
    }

}

