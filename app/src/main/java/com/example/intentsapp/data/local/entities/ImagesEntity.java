package com.example.intentsapp.data.local.entities;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ImagesEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;

    public String image;

    public ImagesEntity(int id, String title, String image){
        this.image = image;
        this.id = id;
        this.title = title;
    }

}
