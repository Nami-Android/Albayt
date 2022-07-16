package com.apps.albayt.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.albayt.R;
import com.apps.albayt.databinding.CategoryRowBinding;
import com.apps.albayt.databinding.HomeCategoryRowBinding;
import com.apps.albayt.model.CategoryModel;
import com.apps.albayt.uis.activity_home.home_module.FragmentMain;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

public class HomeCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CategoryModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;
    private String lang;
    private RecyclerView.RecycledViewPool recycledViewPool;

    public HomeCategoryAdapter(Context context, Fragment fragment, String lang,RecyclerView recyclerView) {
        this.context = context;
        this.fragment = fragment;
        inflater = LayoutInflater.from(context);
        this.lang = lang;
        recycledViewPool = recyclerView.getRecycledViewPool();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HomeCategoryRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.home_category_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        HomeSubCategoryAdapter adapter = new HomeSubCategoryAdapter(context,fragment,lang,position);
        adapter.updateList(list.get(position).getSub_categories());
        adapter.setHasStableIds(true);
        myHolder.binding.recView.setRecycledViewPool(recycledViewPool);
        myHolder.binding.recView.setHasFixedSize(true);
        myHolder.binding.recView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        myHolder.binding.recView.setItemViewCacheSize(20);
        myHolder.binding.recView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        myHolder.binding.recView.setAdapter(adapter);

        /*if (lang.equals("ar")) {
            myHolder.binding.img.setTranslationX(-10);
        } else {
            myHolder.binding.img.setTranslationX(10);

        }*/
        myHolder.itemView.setOnClickListener(view -> {
            if (fragment instanceof FragmentMain) {
                FragmentMain fragmentMain = (FragmentMain) fragment;
                fragmentMain.showCategoryDetails(list.get(holder.getAdapterPosition()),0 ,myHolder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    public static class MyHolder extends RecyclerView.ViewHolder {
        public HomeCategoryRowBinding binding;

        public MyHolder(HomeCategoryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void updateList(List<CategoryModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

}
