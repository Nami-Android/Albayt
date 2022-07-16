package com.apps.albayt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.albayt.R;
import com.apps.albayt.databinding.ContractorRowBinding;
import com.apps.albayt.model.UserModel;
import com.apps.albayt.uis.activity_home.contractors_module.FragmentContractors;

import java.util.List;

public class ContractorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<UserModel.Data> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;
    private String lang;

    public ContractorAdapter(Context context, Fragment fragment, String lang) {
        this.context = context;
        this.fragment = fragment;
        inflater = LayoutInflater.from(context);
        this.lang = lang;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContractorRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.contractor_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));

        myHolder.itemView.setOnClickListener(view -> {
            if (fragment instanceof FragmentContractors) {
                FragmentContractors fragmentContractors = (FragmentContractors) fragment;
                fragmentContractors.navigateToContractorProfile(list.get(myHolder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }


    public static class MyHolder extends RecyclerView.ViewHolder {
        public ContractorRowBinding binding;

        public MyHolder(ContractorRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public void updateList(List<UserModel.Data> list) {
        this.list = list;
        notifyDataSetChanged();
    }

}
