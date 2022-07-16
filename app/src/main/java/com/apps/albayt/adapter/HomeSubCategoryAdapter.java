package com.apps.albayt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.albayt.R;
import com.apps.albayt.databinding.CategoryRowBinding;
import com.apps.albayt.databinding.HomeSubCategoryRowBinding;
import com.apps.albayt.model.CategoryModel;
import com.apps.albayt.uis.activity_home.home_module.FragmentMain;

import java.util.List;

public class HomeSubCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CategoryModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;
    private String lang;
    private int mainPos;

    public HomeSubCategoryAdapter(Context context, Fragment fragment, String lang, int position) {
        this.context = context;
        this.fragment = fragment;
        inflater = LayoutInflater.from(context);
        this.lang = lang;
        this.mainPos = position;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HomeSubCategoryRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.home_sub_category_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));

        /*if (lang.equals("ar")) {
            myHolder.binding.img.setTranslationX(-10);
        } else {
            myHolder.binding.img.setTranslationX(10);

        }*/
        myHolder.itemView.setOnClickListener(view -> {
            if (fragment instanceof FragmentMain) {
                FragmentMain fragmentMain = (FragmentMain) fragment;
                fragmentMain.showCategoryDetails(list.get(holder.getAdapterPosition()), myHolder.getAdapterPosition(),mainPos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    public static class MyHolder extends RecyclerView.ViewHolder {
        public HomeSubCategoryRowBinding binding;

        public MyHolder(HomeSubCategoryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void updateList(List<CategoryModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

}
