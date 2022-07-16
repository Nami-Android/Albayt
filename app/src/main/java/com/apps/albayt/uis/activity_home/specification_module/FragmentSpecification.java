package com.apps.albayt.uis.activity_home.specification_module;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.albayt.R;
import com.apps.albayt.databinding.FragmentSpecificationBinding;
import com.apps.albayt.model.ProductModel;
import com.apps.albayt.uis.activity_base.BaseFragment;
import com.apps.albayt.uis.activity_home.HomeActivity;

public class FragmentSpecification extends BaseFragment {
    private FragmentSpecificationBinding binding;
    private ProductModel productModel;
    private HomeActivity activity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;

    }
    public static FragmentSpecification newInstance(ProductModel productModel) {
        FragmentSpecification fragment = new FragmentSpecification();
        Bundle args = new Bundle();
        args.putSerializable("data",productModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productModel = (ProductModel) getArguments().getSerializable("data");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_specification,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    private void initView() {
        binding.setModel(productModel);
    }


}