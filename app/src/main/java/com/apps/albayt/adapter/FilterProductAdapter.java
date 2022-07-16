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
import com.apps.albayt.databinding.FilterProductRowBinding;
import com.apps.albayt.model.ProductModel;
import com.apps.albayt.uis.activity_home.home_module.FragmentMain;
import com.apps.albayt.uis.activity_home.products_module.FragmentProducts;

import java.util.List;

public class FilterProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ProductModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;
    private String lang;


    public FilterProductAdapter(Context context, Fragment fragment,String lang) {
        this.context = context;
        this.fragment = fragment;
        inflater = LayoutInflater.from(context);
        this.lang = lang;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FilterProductRowBinding rowBinding = DataBindingUtil.inflate(inflater, R.layout.filter_product_row, parent, false);
        return new MyHolder(rowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setLang(lang);
        ProductModel productModel = list.get(position);

        myHolder.binding.setModel(productModel);



        myHolder.itemView.setOnClickListener(view -> {
            if (fragment instanceof FragmentMain) {
                FragmentMain fragmentMain = (FragmentMain) fragment;
                fragmentMain.showProductDetails(list.get(holder.getAdapterPosition()));
            } else if (fragment instanceof FragmentProducts) {
                FragmentProducts fragmentProducts = (FragmentProducts) fragment;
                fragmentProducts.showProductDetails(list.get(holder.getAdapterPosition()));
            }
        });

    }


    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        public FilterProductRowBinding binding;

        public MyHolder(FilterProductRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void updateList(List<ProductModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
