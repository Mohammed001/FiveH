package com.example.asus.fiveh;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
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
import com.example.asus.fiveh.my_database.TaskContract;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.asus.fiveh.ApplicationData.ADVERTISER;
import static com.example.asus.fiveh.ApplicationData.GREED;
import static com.example.asus.fiveh.MainActivityUtils.getFlowersLoader;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor>, AdsAdapter.intface, FlowersAdapter.intface {

    private static final String TAG = MainActivity.class.getSimpleName();
    FloatingActionButton fab;
    RecyclerView main_rv;
    FlowersAdapter flowersAdapter;
    AdsAdapter adsAdapter;
    List<Flower> flower_data = null;
    List<Ad> ad_data = null;
    SwipeRefreshLayout swiperefreshlayout;
    NavigationView navigationView;
    public static final int ADS_LOADER_ID = 0;
    public static final int FLOWERS_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation);
//        ApplicationData.USER_TYPE_INT = ADVERTISER_INT;
        initViewsWithListeners();

        // todo: 1
//        createAdsRecyclerview();
        createFlowersRecyclerview();

        // todo: 1
        // getSupportLoaderManager().initLoader(ADS_LOADER_ID, null, this);
        if (!is_empty_databases()) {
            getSupportLoaderManager().initLoader(FLOWERS_LOADER_ID, null, this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // re-queries for all tasks
        // todo: 1
//        getSupportLoaderManager().restartLoader(ADS_LOADER_ID, null, this);
        getSupportLoaderManager().restartLoader(FLOWERS_LOADER_ID, null, this);
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
//                createAdsRecyclerview();
                createFlowersRecyclerview();
            }
        }
    }

    private void ad_connection_failed(Throwable t) {
        Log.i(TAG, "onFailure" + t.getMessage());
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
                flowersAdapter.swapList(flower_data);
                refresh_flower_database();
            }
        }
    }

    private void refresh_flower_database() {
        getContentResolver().delete(TaskContract.FlowerTable.CONTENT_URI, null, null);
        Log.i(TAG, "onResponse: Number of Flowers received: " + flower_data.size());
        ContentValues[] flavorValuesArr = new ContentValues[flower_data.size()];
        // Loop through static array of Flavors, add each to an instance of ContentValues
        // in the array of ContentValues
        for (int i = 0; i < flower_data.size(); i++) {
            flavorValuesArr[i] = new ContentValues();
            if (flower_data.get(i).getProductId() > 0
                    && flower_data.get(i).getInstructions() != null
                    && flower_data.get(i).getPhoto() != null) {
                flavorValuesArr[i].put(TaskContract.FlowerTable.COLUMN_PRODUCT_ID, flower_data.get(i).getProductId());
                flavorValuesArr[i].put(TaskContract.FlowerTable.COLUMN_TEXT, flower_data.get(i).getInstructions());
                flavorValuesArr[i].put(TaskContract.FlowerTable.COLUMN_PHOTO_PATH, flower_data.get(i).getPhoto());
            }
        }
        // bulkInsert our ContentValues array
        if (flavorValuesArr.length > 0) {
            getContentResolver().bulkInsert(TaskContract.FlowerTable.CONTENT_URI,
                    flavorValuesArr);
        }
    }

    private void flower_connection_failed(Throwable t) {
        Log.i(TAG, "onFailure" + t.getMessage());
        // todo
        createFlowersRecyclerview();
    }


    ///// ______________________________ loader issue ______________________________ /////

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        // todo: 1
//        return getAdsLoader(this);
        return getFlowersLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        // Update the data that the adapter uses to create ViewHolders
        // todo: 1
//        adsAdapter.swapCursor(data);
        flowersAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        // todo: 1
//        adsAdapter.swapCursor(null);
        flowersAdapter.swapCursor(null);
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
                    // todo 1
//                    doHTTP_ads();
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

        onCreateDrawer(toolbar);

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

    private boolean is_empty_databases() {
        Cursor csr = getContentResolver().query(TaskContract.FlowerTable.CONTENT_URI, null, null,
                null, null);
        if (csr != null && csr.moveToFirst()) {
            csr.close();
            return false;
        } else {
            return true;
        }
    }

    private void createFlowersRecyclerview() {
        main_rv = findViewById(R.id.main_rv);
        flowersAdapter = new FlowersAdapter(this);

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

    @Override
    public void doit_ads() {
        getSupportLoaderManager().restartLoader(ADS_LOADER_ID, null, MainActivity.this);
    }

    @Override
    public void doit_flowers() {
        getSupportLoaderManager().restartLoader(FLOWERS_LOADER_ID, null, MainActivity.this);
    }
}

