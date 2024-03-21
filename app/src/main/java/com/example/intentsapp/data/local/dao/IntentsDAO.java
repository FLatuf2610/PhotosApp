package com.example.intentsapp.data.local.dao;

import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.intentsapp.data.local.entities.ImagesEntity;

import java.util.List;

@Dao
public interface IntentsDAO {

    @Insert
    public void insertImage(ImagesEntity img);

    @Query("SELECT * FROM imagesentity")
    public LiveData<List<ImagesEntity>> getAllImages();

    @Query("SELECT * FROM imagesentity WHERE id == :id")
    public ImagesEntity getImageById(int id);

    @Query("DELETE FROM imagesentity WHERE id == :id")
    public void deleteImage(int id);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void editImage(ImagesEntity img);
}
