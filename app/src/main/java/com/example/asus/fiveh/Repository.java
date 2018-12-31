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
    private AppNetwork weatherNetworkDataSource;
    private LiveData<List<Flower>> mAllFlowers;
    private static Repository mInstance;

    public Repository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        weatherNetworkDataSource = new AppNetwork(application, this);
        mFlowerDao = db.flowerDao();
        mAllFlowers = mFlowerDao.getAllFlowers();
        mInstance = this;
    }

    public static Repository getInstance() {
        if (mInstance != null)
            return mInstance;
        else return null;
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

    @SuppressLint("StaticFieldLeak")
    public void deleteFlower(final Flower flower) {
        new AsyncTask<Flower, Void, Void>() {
            @Override
            protected Void doInBackground(Flower... flowers) {
                mFlowerDao.deleteFlower(flowers[0]);
                return null;
            }
        }.execute(flower);
    }
}
