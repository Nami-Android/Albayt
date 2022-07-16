package com.apps.albayt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.albayt.R;
import com.apps.albayt.databinding.AddressRowBinding;
import com.apps.albayt.model.AddressModel;
import com.apps.albayt.uis.activity_home.address_module.FragmentMyAddresses;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AddressModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;

    public AddressAdapter( Context context,Fragment fragment) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AddressRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.address_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));

        myHolder.itemView.setOnClickListener(v -> {
            if (fragment instanceof FragmentMyAddresses) {
                FragmentMyAddresses fragmentMyAddresses = (FragmentMyAddresses) fragment;
                fragmentMyAddresses.setItemAddress(list.get(myHolder.getAdapterPosition()),myHolder.getAdapterPosition());
            }
        });

        myHolder.binding.imgDelete.setOnClickListener(v -> {
            if (fragment instanceof FragmentMyAddresses) {
                FragmentMyAddresses fragmentMyAddresses = (FragmentMyAddresses) fragment;
                fragmentMyAddresses.delete(list.get(myHolder.getAdapterPosition()),myHolder.getAdapterPosition());
            }
        });
        myHolder.binding.imgEdit.setOnClickListener(v -> {
            if (fragment instanceof FragmentMyAddresses) {
                FragmentMyAddresses fragmentMyAddresses = (FragmentMyAddresses) fragment;
                fragmentMyAddresses.edit(list.get(myHolder.getAdapterPosition()),myHolder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public AddressRowBinding binding;

        public MyHolder(AddressRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void updateList(List<AddressModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
