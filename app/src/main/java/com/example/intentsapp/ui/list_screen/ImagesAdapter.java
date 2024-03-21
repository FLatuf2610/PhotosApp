package com.example.intentsapp.ui.list_screen;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intentsapp.R;
import com.example.intentsapp.data.local.entities.ImagesEntity;
import com.example.intentsapp.databinding.ItemImagesBinding;

import java.util.ArrayList;
import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder> {

    private OnClickItem onClick;

    public ImagesAdapter(List<ImagesEntity> list, OnClickItem onClick) {
        this.list = list;
        this.onClick = onClick;
    }

    private List<ImagesEntity> list;

    @NonNull
    @Override
    public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ImagesViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_images,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesViewHolder holder, int position) {
        holder.render(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateData(List<ImagesEntity> data) {
        list = data;
        notifyDataSetChanged();
    }

    class ImagesViewHolder extends RecyclerView.ViewHolder {
        public ImagesViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        ItemImagesBinding binding = ItemImagesBinding.bind(itemView);
        void render(ImagesEntity img) {
            binding.tvTitle.setText(img.title);
            binding.ivImage.setImageURI(Uri.parse(img.image));
            itemView.setOnClickListener( (v) -> onClick.onClickItem(img) );
        }
    }
}
