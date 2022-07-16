package com.apps.albayt.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.albayt.R;
import com.apps.albayt.databinding.CategoryRowBinding;
import com.apps.albayt.databinding.NewsRowBinding;
import com.apps.albayt.model.CategoryModel;
import com.apps.albayt.model.NewsModel;
import com.apps.albayt.uis.activity_home.home_module.FragmentMain;
import com.apps.albayt.uis.activity_news.NewsActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<NewsModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;
    private String lang;
    private AppCompatActivity appCompatActivity;

    public NewsAdapter(Context context, Fragment fragment, String lang) {
        this.context = context;
        this.fragment = fragment;
        inflater = LayoutInflater.from(context);
        this.lang = lang;
        appCompatActivity = (AppCompatActivity) context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewsRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.news_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));

        myHolder.itemView.setOnClickListener(view -> {
            if (fragment !=null){
                if (fragment instanceof FragmentMain) {
                    FragmentMain fragmentMain = (FragmentMain) fragment;
                }
            }else {
                if (appCompatActivity instanceof NewsActivity){
                    }
            }

        });

    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    public static class MyHolder extends RecyclerView.ViewHolder {
        public NewsRowBinding binding;

        public MyHolder(NewsRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void updateList(List<NewsModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

}
