package com.example.asus.fiveh;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.asus.fiveh.models.Flower;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.asus.fiveh.ApplicationData.TAG;

class AppNetwork {
    private Application mApplication;
    private Repository mRepository;

    AppNetwork(Application application, Repository repository) {
        mApplication = application;
        mRepository = repository;
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) mApplication.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    void doHTTP_flowers() {
        if (!isOnline()) return;
        RetrofitAPI service = new RetrofitClient().getFlowersRetrofitClient().create(RetrofitAPI.class);
        Call<ArrayList<Flower>> call = service.listFlowers();
        call.enqueue(flowerConnectionCallback());
    }

    @NonNull
    private Callback<ArrayList<Flower>> flowerConnectionCallback() {
        return new Callback<ArrayList<Flower>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Flower>> call, @NonNull Response<ArrayList<Flower>> response) {
                flower_connection_succeed(response);
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Flower>> call, @NonNull Throwable t) {
                Log.i(TAG, "onFailure" + t.getMessage());
            }
        };
    }

    private void flower_connection_succeed(Response<ArrayList<Flower>> response) {
        if (response.isSuccessful()) {
            ArrayList<Flower> list = response.body();
            if (list != null) {
                mRepository.insertAll(list);
            }
        }
    }

}
