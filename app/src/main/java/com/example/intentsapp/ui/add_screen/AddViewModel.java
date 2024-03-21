package com.example.intentsapp.ui.add_screen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.example.intentsapp.data.local.entities.ImagesEntity;
import com.example.intentsapp.data.repository.Repository;
import com.example.intentsapp.utilities.Utilities;

public class AddViewModel extends ViewModel {

    private final Repository repository;
    private final Context context;
    public AddViewModel(Context context){
        this.context = context;
        this.repository = Repository.getRepositoryInstance(context);
    }

    private final AddViewModelState state = new AddViewModelState();

    public void setUri(Uri uri) {
        state.setImageUri(uri);
        Log.i("VIEWMODEL", "URI ADDED");
    }

    public void setBitmap(Bitmap bitmap) {
        state.setBitmap(bitmap);
        Log.i("VIEWMODEL", "BITMAP ADDED");
    }


    public void addImageToDatabase(String title) {

        if (state.getImageUri() != null) {
            Uri finalImageUri = Utilities.saveImageAndGetUri(state.getImageUri(), context);
            if (finalImageUri != null) {
                ImagesEntity imageEntity = new ImagesEntity(0, title, finalImageUri.toString());
                repository.insertImage(imageEntity);
            } else {
                Toast.makeText(context, "An error occurred saving the image", Toast.LENGTH_SHORT).show();
            }
        }
        if (state.getIbitmap() != null) {
            Uri finalImageUri = Utilities.saveBitmapToInternalStorage(state.getIbitmap(), context);
            if (finalImageUri != null) {
                ImagesEntity imageEntity = new ImagesEntity(0, title, finalImageUri.toString());
                repository.insertImage(imageEntity);
            } else {
                Toast.makeText(context, "An error occurred saving the image", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public ImagesEntity getImage(int id) {
        return repository.getImageById(id);
    }

}
