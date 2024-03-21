package com.example.intentsapp.ui.list_screen.detail_bottom_sheet;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.intentsapp.R;
import com.example.intentsapp.data.local.entities.ImagesEntity;
import com.example.intentsapp.databinding.FragmentDetailBottomSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class DetailBottomSheetFragment extends BottomSheetDialogFragment {
    private int mParam1;

    private FragmentDetailBottomSheetBinding _binding;
    private FragmentDetailBottomSheetBinding get_binding() {
        return _binding;
    }

    private DetailViewModel viewModel;

    private ImagesEntity imgState;

    public DetailBottomSheetFragment() {
    }



    public static DetailBottomSheetFragment newInstance(int param1) {
        DetailBottomSheetFragment fragment = new DetailBottomSheetFragment();
        Bundle args = new Bundle();
        args.putInt("id", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt("id");
        }
        viewModel = new DetailViewModel(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBottomSheetBinding.inflate(inflater, container, false);
        return get_binding().getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getImageById(mParam1);
        viewModel.image.observe(getViewLifecycleOwner(), (img) -> {
            imgState = img;
            get_binding().ivImage.setImageURI(Uri.parse(img.image));
            get_binding().tvTitle.setText(img.title);
        });
        get_binding().btnDelete.setOnClickListener( (v) -> {
            viewModel.deleteItem(imgState.id);
            this.dismiss();
        }  );
        get_binding().btnEdit.setOnClickListener( (v) -> {
            NavController navController = Navigation.findNavController(requireView());
            Bundle args = new Bundle();
            args.putBoolean("editMode", true);
            args.putInt("imgId" ,imgState.id);
            navController.navigate(R.id.action_listFragment_to_addFragment,args);
        } );
    }
}