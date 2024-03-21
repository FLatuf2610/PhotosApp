package com.example.intentsapp.ui.list_screen;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.intentsapp.data.local.entities.ImagesEntity;
import com.example.intentsapp.data.repository.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListViewModel extends ViewModel {

    private Context context;
    private Repository repository;
    public ListViewModel(Context context) {
        this.context = context;
        this.repository = Repository.getRepositoryInstance(context);
    }

    public LiveData<List<ImagesEntity>> getAllImages() {
        return repository.getAllImages();
    }



}
