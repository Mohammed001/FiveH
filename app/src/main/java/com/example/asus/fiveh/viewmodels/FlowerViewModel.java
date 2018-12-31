package com.example.asus.fiveh.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.asus.fiveh.Repository;
import com.example.asus.fiveh.models.Flower;

import java.util.List;

public class FlowerViewModel extends AndroidViewModel{

    private LiveData<List<Flower>> mAllFlowers;
    private Repository mRepository;

    public FlowerViewModel(Application application) {
        super(application);
        mRepository = new Repository(application);
        mAllFlowers = mRepository.getAllFlowers();
    }

    public LiveData<List<Flower>> getAllFlowers() {
        return mAllFlowers;
    }

    public void refreshAllFlowers() {
        mRepository.refresh();
    }

}
