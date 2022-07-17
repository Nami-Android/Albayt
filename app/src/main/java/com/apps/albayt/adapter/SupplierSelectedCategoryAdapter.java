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
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.albayt.R;
import com.apps.albayt.databinding.CategoryRowBinding;
import com.apps.albayt.databinding.SupplierSelectedCategoryBinding;
import com.apps.albayt.model.CategoryModel;
import com.apps.albayt.uis.activity_home.home_module.FragmentMain;
import com.apps.albayt.uis.activity_sign_up_supplier.fragments.FragmentSignUpSupplierStep2;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

public class SupplierSelectedCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CategoryModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;
    private String lang;

    public SupplierSelectedCategoryAdapter(Context context, Fragment fragment, String lang) {
        this.context = context;
        this.fragment = fragment;
        inflater = LayoutInflater.from(context);
        this.lang = lang;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SupplierSelectedCategoryBinding binding = DataBindingUtil.inflate(inflater, R.layout.supplier_selected_category, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));

        myHolder.binding.delete.setOnClickListener(view -> {
            if (fragment instanceof FragmentSignUpSupplierStep2) {
                FragmentSignUpSupplierStep2 fragmentSignUpSupplierStep2 = (FragmentSignUpSupplierStep2) fragment;
                fragmentSignUpSupplierStep2.deleteSelectedCategory(myHolder.getAdapterPosition());
            }
        });
    }



    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    public static class MyHolder extends RecyclerView.ViewHolder {
        public SupplierSelectedCategoryBinding binding;

        public MyHolder(SupplierSelectedCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void updateList(List<CategoryModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addItem(CategoryModel categoryModel){

        this.list.add(0,categoryModel);
        notifyItemInserted(0);
    }

    public void removeItem(int adapterPos){
        this.list.remove(adapterPos);
        notifyItemRemoved(adapterPos);
    }

}
