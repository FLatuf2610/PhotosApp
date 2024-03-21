package com.example.intentsapp.ui.add_screen;

import android.graphics.Bitmap;
import android.net.Uri;

public class AddViewModelState {

    public AddViewModelState(){}
    private Uri _imageUri;
    private Bitmap _bitmap;

    public Uri getImageUri() {
        return _imageUri;
    }

    public Bitmap getIbitmap() {
        return _bitmap;
    }

    public void setImageUri(Uri uri){
        this._imageUri = uri;
        this._bitmap = null;
    }
    public void setBitmap(Bitmap bitmap) {
        this._bitmap = bitmap;
        this._imageUri = null;
    }
}
