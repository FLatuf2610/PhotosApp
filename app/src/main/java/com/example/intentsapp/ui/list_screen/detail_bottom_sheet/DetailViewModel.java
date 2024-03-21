package com.example.intentsapp.ui.list_screen.detail_bottom_sheet;


import android.content.Context;
import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.intentsapp.data.local.entities.ImagesEntity;
import com.example.intentsapp.data.repository.Repository;

import java.io.IOException;

public class DetailViewModel extends ViewModel {

    private final Repository repository;

    private final MutableLiveData<ImagesEntity> _image = new MutableLiveData<>();
    public final LiveData<ImagesEntity> image = _image;

    private Context context;
    public DetailViewModel(Context context){
        this.repository = Repository.getRepositoryInstance(context);
        this.context = context;
    }

    public void getImageById(int id) {
        _image.postValue(repository.getImageById(id));
    }

    public void deleteItem(ImagesEntity img) throws IOException {
        repository.deleteImage(img);
    }

}
