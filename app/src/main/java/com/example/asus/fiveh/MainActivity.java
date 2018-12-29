package com.example.asus.fiveh;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.example.asus.fiveh.adapters.FlowersAdapter;
import com.example.asus.fiveh.models.FiveHResponse;
import com.example.asus.fiveh.models.Flower;
import com.example.asus.fiveh.viewmodels.FlowerViewModel;

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
    SwipeRefreshLayout swiperefreshlayout;
    NavigationView navigationView;
    private FlowerViewModel mFlowerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation);
        initViewsWithListeners();

        mFlowerViewModel = ViewModelProviders.of(this).get(FlowerViewModel.class);

        mFlowerViewModel.getAllFlowers().observe(this, getObserver());

        createFlowersRecyclerview();
    }

    @NonNull
    private Observer<List<Flower>> getObserver() {
        return new Observer<List<Flower>>() {
            @Override
            public void onChanged(@Nullable final List<Flower> flowers) {
                // Update the cached copy of the words in the adapter.
                flowersAdapter.setFlowers(flowers);
            }
        };
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;

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
                    // todo
                    Toast.makeText(null, "onFailure: " + TAG, Toast.LENGTH_SHORT).show();
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
            startActivity(intent);
        } else if (ApplicationData.current_user.getUser_type().equals(ADVERTISER)) {
            intent = new Intent(this, MyArchive.class);
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
                mFlowerViewModel.refreshAllFlowers();
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

}

