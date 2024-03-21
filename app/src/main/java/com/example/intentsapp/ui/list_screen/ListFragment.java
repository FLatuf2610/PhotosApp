package com.example.intentsapp.ui.list_screen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.intentsapp.data.local.entities.ImagesEntity;
import com.example.intentsapp.databinding.FragmentListBinding;
import com.example.intentsapp.ui.list_screen.detail_bottom_sheet.DetailBottomSheetFragment;

import java.util.ArrayList;


public class ListFragment extends Fragment implements OnClickItem {


    private ListViewModel viewModel;
    private FragmentListBinding _binding;
    public FragmentListBinding binding() {
        return _binding;
    }

    private ImagesAdapter mAdapter = new ImagesAdapter(new ArrayList<>(), this);

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false);
        return binding().getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ListViewModel(requireContext());
        viewModel.getAllImages();
        initUI();
        initObserver();
    }

    private void initUI() {
        binding().rvImages.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding().rvImages.setAdapter(mAdapter);
    }

    private void initObserver() {
        viewModel.getAllImages().observe(getViewLifecycleOwner(), (list -> {
            mAdapter.updateData(list);
        }));
    }

    @Override
    public void onClickItem(ImagesEntity img) {
        DetailBottomSheetFragment bottomSheet = DetailBottomSheetFragment.newInstance(img.id);
        bottomSheet.show(getParentFragmentManager(), bottomSheet.getTag());
    }
}