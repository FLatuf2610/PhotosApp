package com.example.intentsapp.data.local.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.intentsapp.data.local.dao.IntentsDAO;
import com.example.intentsapp.data.local.entities.ImagesEntity;

@Database(
        entities = {ImagesEntity.class},
        version = 1
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract IntentsDAO getDao();
}
