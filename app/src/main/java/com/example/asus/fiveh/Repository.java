package com.example.asus.fiveh;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.asus.fiveh.models.Flower;
import com.example.asus.fiveh.my_database.AppDatabase;
import com.example.asus.fiveh.my_database.FlowerDao;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    private FlowerDao mFlowerDao;
    private FlowerNetwork weatherNetworkDataSource;
    private LiveData<List<Flower>> mAllFlowers;

    public Repository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        weatherNetworkDataSource = new FlowerNetwork(application, this);
        mFlowerDao = db.flowerDao();
        mAllFlowers = mFlowerDao.getAllFlowers();
    }

    public LiveData<List<Flower>> getAllFlowers() {
        return mAllFlowers;
    }

    public void refresh() {
        weatherNetworkDataSource.doHTTP_flowers();
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteAll() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mFlowerDao.deleteAll();
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    void insertAll(ArrayList<Flower> list) {
        new AsyncTask<ArrayList<Flower>, Void, Void>() {
            @SafeVarargs
            @Override
            protected final Void doInBackground(ArrayList<Flower>... flowers) {
                ArrayList<Flower> passed = flowers[0];
                mFlowerDao.insertAll(passed);
                return null;
            }
        }.execute(list);
    }
}
