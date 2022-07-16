package com.apps.albayt.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.albayt.R;

import com.apps.albayt.databinding.RecentRowBinding;
import com.apps.albayt.model.ProductModel;
import com.apps.albayt.uis.activity_home.home_module.FragmentMain;

import java.util.List;

public class RecentProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ProductModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;
    private String lang;


    public RecentProductAdapter(Context context, Fragment fragment, String lang) {
        this.context = context;
        this.fragment = fragment;
        inflater = LayoutInflater.from(context);
        this.lang = lang;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecentRowBinding rowBinding = DataBindingUtil.inflate(inflater, R.layout.recent_row, parent, false);
        return new MyHolder(rowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        ProductModel productModel = list.get(position);
        myHolder.binding.setLang(lang);
        myHolder.binding.setModel(productModel);


        myHolder.itemView.setOnClickListener(view -> {
            if (fragment instanceof FragmentMain) {
                FragmentMain fragmentMain = (FragmentMain) fragment;
                fragmentMain.showProductDetails(list.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    public static class MyHolder extends RecyclerView.ViewHolder {
        public RecentRowBinding binding;

        public MyHolder(RecentRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void updateList(List<ProductModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }


}
