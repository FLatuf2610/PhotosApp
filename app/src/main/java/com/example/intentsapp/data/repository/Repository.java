package com.example.intentsapp.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.intentsapp.data.local.database.AppDatabase;
import com.example.intentsapp.data.local.entities.ImagesEntity;
import com.example.intentsapp.utilities.Utilities;

import java.io.IOException;
import java.util.List;

public class Repository {

    private final AppDatabase db;

    private static Repository repository;

    private Context context;

    private Repository(Context context) {
        this.context = context;
        this.db = Room.databaseBuilder(
                        context,
                        AppDatabase.class,
                        "dbImages"
                ).allowMainThreadQueries()
                .build();
    }

    public static Repository getRepositoryInstance(Context context){
        if (repository == null) {
            repository = new Repository(context);
        }
        return repository;
    }

    public LiveData<List<ImagesEntity>> getAllImages() {
       return db.getDao().getAllImages();
    }

    public void insertImage(ImagesEntity img) {
        db.getDao().insertImage(img);
    }

    public ImagesEntity getImageById(int id) {
        return db.getDao().getImageById(id);
    }

    public void deleteImage(ImagesEntity imageEntity) throws IOException {
        db.getDao().deleteImage(imageEntity);
        Utilities.deleteFromInternStorage(imageEntity.image, this.context);
    }

}
