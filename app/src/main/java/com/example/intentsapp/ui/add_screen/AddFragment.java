package com.example.intentsapp.ui.add_screen;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.intentsapp.R;
import com.example.intentsapp.data.local.entities.ImagesEntity;
import com.example.intentsapp.databinding.FragmentAddBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class AddFragment extends Fragment {

    private FragmentAddBinding _binding;
    private FragmentAddBinding get_binding() {
        return _binding;
    }
    private AddViewModel viewmodel;

    ActivityResultLauncher<PickVisualMediaRequest> pickerLauncher = registerForActivityResult(
            new ActivityResultContracts.PickVisualMedia(),
            (uri) -> {
                get_binding().ivImageAdd.setImageURI(uri);
                viewmodel.setUri(uri);
            });

    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                    get_binding().ivImageAdd.setImageBitmap(imageBitmap);
                    viewmodel.setBitmap(imageBitmap);
                }
            }
    );

    ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                    isGranted -> {
                        if (isGranted) {
                            // Permiso concedido, realiza la acción deseada
                            // Por ejemplo, abre la cámara
                            Log.d("HOLA", "EJECUTADO CAMARA");
                            cameraLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
                        } else {
                            // Permiso denegado, muestra un mensaje o toma alguna otra acción
                            MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(requireContext());
                            dialogBuilder
                                    .setTitle("Permission required")
                                    .setMessage("Please enable camera permissions for this feature tu work")
                                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                                    .setPositiveButton("Ok", (dialog, which) -> {
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", requireActivity().getPackageName(), null);
                                        intent.setData(uri);
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }).show();
                        }
                    });



    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewmodel= new AddViewModel(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false);
        return get_binding().getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUi();

    }

    private void initUi(){
        get_binding().toolbar.setTitle("Add Image");
        get_binding().toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        get_binding().toolbar.setNavigationOnClickListener( (v) -> {
            Navigation.findNavController(requireView()).popBackStack();
        });
        initListeners();
    }

    private void initListeners() {
        get_binding().btnPick.setOnClickListener( v -> openVisualMediaPicker() );
        get_binding().btnTake.setOnClickListener( v -> openCamera() );
        get_binding().btnAdd.setOnClickListener(v -> {
            if (get_binding().etTitle.getText().toString().trim().isEmpty()) {
                get_binding().etLayoutTitle.setError("Title is requered");
                get_binding().etLayoutTitle.setErrorEnabled(true);
            }
            else {
                Log.i("YA CASI MONO", "ACA ESTARIAS SUBIENDO LA IMAGEN MONO");
                get_binding().etLayoutTitle.setErrorEnabled(false);
                    viewmodel.addImageToDatabase(get_binding().etTitle.getText().toString().trim());

            }
        });
    }


    private void openCamera(){
        requestPermissionLauncher.launch(Manifest.permission.CAMERA);
    }

    private void openVisualMediaPicker(){
        pickerLauncher.launch(new PickVisualMediaRequest());
    }

}
