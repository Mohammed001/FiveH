package com.example.asus.fiveh;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
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

import static com.example.asus.fiveh.ApplicationData.ADVERTISER_WORD;
import static com.example.asus.fiveh.ApplicationData.USER_WORD;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    FloatingActionButton fab;
    RecyclerView main_rv;
    SwipeRefreshLayout swiperefreshlayout;
    NavigationView navigationView;
    DrawerLayout drawer;
    Toolbar toolbar;

    FlowersAdapter flowersAdapter;
    private FlowerViewModel mFlowerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        findviews();
        justFab();
        justDrawer();
        justRecyclerView();
        swiperefreshlayout.setOnRefreshListener(getOnRefreshListener());
        mFlowerViewModel = ViewModelProviders.of(this).get(FlowerViewModel.class);
        mFlowerViewModel.getAllFlowers().observe(this, getObserver());
    }

    private void justRecyclerView() {
        flowersAdapter = new FlowersAdapter(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//
        main_rv.setLayoutManager(layoutManager);

        main_rv.setAdapter(flowersAdapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(main_rv.getContext(), layoutManager.getOrientation());
        main_rv.addItemDecoration(itemDecoration);
    }

    private void justFab() {
        if (ApplicationData.current_user.getUser_type().equals(ADVERTISER_WORD)) {
            // todo: new method or what?
            fab.show();
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), CreateNewAd.class);
                    startActivity(intent);
                }
            });
        }
    }

    private void justDrawer() {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        LinearLayout linearLayout = navigationView.getHeaderView(0).findViewById(R.id.image_user_bundle);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
    }

    private void findviews() {
        main_rv = findViewById(R.id.main_rv);
        fab = findViewById(R.id.fab);
        navigationView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);
        swiperefreshlayout = findViewById(R.id.swiperefreshlayout);
    }

    @NonNull
    private Observer<List<Flower>> getObserver() {
//        return new Observer<List<Flower>>() {
//            @Override
//            public void onChanged(@Nullable final List<Flower> flowers) {
//                // Update the cached copy of the words in the adapter.
//                flowersAdapter.setFlowers(flowers);
//            }
//        };
        return flowers -> {
            // Update the cached copy of the words in the adapter.
            flowersAdapter.setFlowers(flowers);
        };
    }

    private void goToAccountsOrArchive() {
        Intent intent;
        if (ApplicationData.current_user.getUser_type().equals(USER_WORD)) {
            intent = new Intent(this, MyAccounts.class);
            startActivity(intent);
        } else if (ApplicationData.current_user.getUser_type().equals(ADVERTISER_WORD)) {
            intent = new Intent(this, MyArchive.class);
            startActivity(intent);
        }
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

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (ApplicationData.current_user.getUser_type().equals(ADVERTISER_WORD)) {
            menu.findItem(R.id.nav_my_points).setVisible(false);
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
            intent = new Intent(this, About.class);
            startActivity(intent);
        } else if (id == R.id.logout) {
            RetrofitAPI service = new RetrofitClient().getAuthClient().create(RetrofitAPI.class);
            service.call_5H_logout().enqueue(new Callback<FiveHResponse>() {
                @Override
                public void onResponse(@NonNull Call<FiveHResponse> call, @NonNull Response<FiveHResponse> response) {
                    if (response.body() != null) {
                        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
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
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

