package com.example.intentsapp.utilities;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utilities {

    private static String createImageName(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + ".jpg";
        return imageFileName;
    }

    public static Uri saveBitmapToInternalStorage(Bitmap bitmap, Context context) {

        //Crea o accede el directorio para guardar las imagenes
        File storageDir = new File(context.getFilesDir(), "images");
        if (!storageDir.exists()) {
            boolean success = storageDir.mkdirs();
            if (!success) {
                Toast.makeText(context, "Could not create needed directories", Toast.LENGTH_SHORT).show();
            }
        }

        //Crea archivo en el directorio
        String imageFileName = createImageName();
        File imageFile = new File(storageDir, imageFileName);

        try {
            //Pinta los bitmap en el archivo y devuelve la uri
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(imageFile);

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static Uri saveImageAndGetUri(Uri imageUri, Context context) {

        try{
            //Intenta leer los datos de la imagen
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            if (inputStream == null) {
                Log.d("ACA MONO", "InputSream NULL");
                return null;
            }

            //Crea o accede al directorio
            File storageDir = new File(context.getFilesDir(), "images");
            if (!storageDir.exists()) {
                boolean success = storageDir.mkdirs();
                if (!success) {
                    Toast.makeText(context, "Could not create needed directories",
                            Toast.LENGTH_SHORT).show();
                }
            }

            //Crea el archivo en el directorio
            String imageName = createImageName();
            File imageFile = new File(storageDir, imageName);

            //Pinta los datos de la imagen accedida a traves de la Uri
            OutputStream outputStream = new FileOutputStream(imageFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.close();
            inputStream.close();

            //Devuelve la uri del nuevo archivo
            return FileProvider.getUriForFile(context,
                    context.getApplicationContext().getPackageName() + ".provider", imageFile);


        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void deleteFromInternStorage(String uriString, Context context) throws IOException {
        Uri uri = Uri.parse(uriString);
        File file = new File(uri.getPath());
        file.delete();
        if(file.exists()){
            Log.i("DELETE", "TODAVIA NO");
            file.getCanonicalFile().delete();
            if(file.exists()){
                Log.i("DELETE", "TODAVIA NO");
                context.deleteFile(file.getName());
                if(!file.exists()) {
                    Log.i("DELETE", "SI O SI");
                }
            } else {
                Log.i("DELETE", "file.getCanonicalFile().delete()");
            }
        } else {
            Log.i("DELETE", "file.delete()");

        }
    }


}
