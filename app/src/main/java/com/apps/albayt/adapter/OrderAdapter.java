package com.apps.albayt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.apps.albayt.R;
import com.apps.albayt.databinding.OrderRowBinding;
import com.apps.albayt.model.OrderModel;
import com.apps.albayt.uis.order_module.FragmentCurrentOrder;
import com.apps.albayt.uis.order_module.FragmentPreviousOrder;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<OrderModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;
    private String lang;

    public OrderAdapter(Context context, Fragment fragment, String lang) {
        this.context = context;
        this.fragment = fragment;
        inflater = LayoutInflater.from(context);
        this.lang = lang;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.order_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setLang(lang);
        myHolder.binding.setModel(list.get(position));
        myHolder.binding.llDetails.setOnClickListener(view -> {
            if (fragment instanceof FragmentCurrentOrder) {
                FragmentCurrentOrder fragmentCurrentOrder = (FragmentCurrentOrder) fragment;
                fragmentCurrentOrder.navigateToDetails(list.get(myHolder.getAdapterPosition()));
            } else if (fragment instanceof FragmentPreviousOrder) {
                FragmentPreviousOrder fragmentPreviousOrder = (FragmentPreviousOrder) fragment;
                fragmentPreviousOrder.navigateToDetails(list.get(myHolder.getAdapterPosition()),myHolder.getAdapterPosition());
            }
        });




    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public OrderRowBinding binding;

        public MyHolder(OrderRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void updateList(List<OrderModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
