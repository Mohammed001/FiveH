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

import com.example.asus.fiveh.adapters.MainAdAdapter;
import com.example.asus.fiveh.models.Ad;

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
    MainAdAdapter adAdapter;
    List<Ad> data = null;
    SwipeRefreshLayout swiperefreshlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation);
//        ApplicationData.USER_TYPE_INT = ADVERTISER_INT;
        initViewsWithListeners();

//        https://kodejava.org/how-to-convert-json-string-to-java-object/
//        https://stackoverflow.com/questions/10308452/how-to-convert-the-following-json-string-to-java-object

        if (isOnline()) {
            doHTTP();
        }
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

    NavigationView navigationView;

    private void onCreateDrawer(Toolbar toolbar) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @NonNull
    private SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swiperefreshlayout.setRefreshing(false);
                if (isOnline()) {
                    doHTTP();
                }
            }
        };
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void createRecyclerview() {
        main_rv = findViewById(R.id.main_rv);
        adAdapter = new MainAdAdapter(this, data);

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
    }

    private void doHTTP() {
        RetrofitAPI service = new RetrofitClient().getMainClient().create(RetrofitAPI.class);
        Call<List<Ad>> call = service.listAds();
        call.enqueue(getMainConnectionCallback());
    }

    @NonNull
    private Callback<List<Ad>> getMainConnectionCallback() {
        return new Callback<List<Ad>>() {
            @Override
            public void onResponse(Call<List<Ad>> call, Response<List<Ad>> response) {
                connection_succeed(response);
            }

            @Override
            public void onFailure(Call<List<Ad>> call, Throwable t) {
                connection_failed(t);
            }
        };
    }

    private void connection_failed(Throwable t) {
        Log.i(TAG, "onFailure" + t.getMessage());
        createRecyclerview();
    }

    private void connection_succeed(Response<List<Ad>> response) {
        if (response.isSuccessful()) {
            data = response.body();
            if (data != null) {
                Log.i(TAG, "onResponse: Number of Ads received: " + data.size());
                createRecyclerview();
            }
        }
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
            service.call_5H_logout().enqueue(new Callback<com.example.asus.fiveh.models.Response>() {
                @Override
                public void onResponse(Call<com.example.asus.fiveh.models.Response> call, Response<com.example.asus.fiveh.models.Response> response) {
                    if (response.body() != null) {
                        Log.i(TAG, "onResponse: " + (response.body().getResult()));
                        Log.i(TAG, "onResponse: " + response.body().getMsg());
                    } else {
                        Log.i(TAG, "onResponse: no JSON!");
                    }
                }

                @Override
                public void onFailure(Call<com.example.asus.fiveh.models.Response> call, Throwable t) {
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


}

