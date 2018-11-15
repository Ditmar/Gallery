package com.developer.ditmar.gallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.module.GlideModule;

import java.util.ArrayList;

public class CustomRecycler extends RecyclerView.Adapter<CustomRecycler.MyViewHolder> {
    private ArrayList<GalleryData> LIST;
    public CustomRecycler (ArrayList<GalleryData> list) {
        //parent = contex;
        LIST = list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.itemrecycler, viewGroup, false);
        MyViewHolder ins = new MyViewHolder(view);
        return ins;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.username.setText(LIST.get(i).getUsername());
        myViewHolder.bio.setText(LIST.get(i).getBio());
        //GlideApp.with()
        GlideApp.with(myViewHolder.viewGlide).
                load(LIST.get(i).
                        getUrl()).centerCrop()
                .
                into(myViewHolder.img);
        
    }

    @Override
    public int getItemCount() {
        return LIST.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public TextView bio;
        public ImageView img;
        public View viewGlide;
        public MyViewHolder (View view) {
            super(view);
            viewGlide = view;
            username = view.findViewById(R.id.username);
            img = view.findViewById(R.id.img);
            bio = view.findViewById(R.id.bio);
        }
    }
}
