package com.apps.albayt.uis.activity_sign_up_supplier.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.albayt.R;
import com.apps.albayt.databinding.FragmentSignUpSupplierStep1Binding;
import com.apps.albayt.uis.activity_base.BaseFragment;

public class FragmentSignUpSupplierStep1 extends BaseFragment {
    private FragmentSignUpSupplierStep1Binding binding;
    public static FragmentSignUpSupplierStep1 newInstance() {
        FragmentSignUpSupplierStep1 fragment = new FragmentSignUpSupplierStep1();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up_supplier_step1, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {

    }
}