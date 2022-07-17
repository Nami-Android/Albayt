package com.apps.albayt.uis.activity_sign_up_supplier.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apps.albayt.R;
import com.apps.albayt.adapter.SpinnerCategoryAdapter;
import com.apps.albayt.adapter.SpinnerCityAdapter;
import com.apps.albayt.adapter.SupplierSelectedCategoryAdapter;
import com.apps.albayt.databinding.FragmentSignUpSupplierStep2Binding;
import com.apps.albayt.model.CategoryModel;
import com.apps.albayt.model.CityModel;
import com.apps.albayt.model.SelectedLocation;
import com.apps.albayt.model.SupplierSignUpModel;
import com.apps.albayt.model.UserModel;
import com.apps.albayt.mvvm.FragmentSupplierSignUpStep2Mvvm;
import com.apps.albayt.uis.activity_base.BaseFragment;
import com.apps.albayt.uis.activity_map.MapActivity;
import com.apps.albayt.uis.activity_sign_up_supplier.SignUpSupplierActivity;

import java.util.ArrayList;
import java.util.List;

public class FragmentSignUpSupplierStep2 extends BaseFragment {
    private SignUpSupplierActivity activity;
    private FragmentSupplierSignUpStep2Mvvm mvvm;
    private FragmentSignUpSupplierStep2Binding binding;
    private SupplierSignUpModel model;
    private SpinnerCategoryAdapter spinnerCategoryAdapter;
    private List<CategoryModel> selectedCategories = new ArrayList<>();
    private SupplierSelectedCategoryAdapter adapter;
    private SpinnerCityAdapter spinnerCityAdapter;
    private ActivityResultLauncher<Intent> launcher;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (SignUpSupplierActivity) context;
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),result -> {
            if (result.getResultCode()== Activity.RESULT_OK&&result.getData()!=null){
                SelectedLocation selectedLocation = (SelectedLocation) result.getData().getSerializableExtra("location");
                model.setAddress(selectedLocation.getAddress());
                model.setLat(selectedLocation.getLat());
                model.setLng(selectedLocation.getLng());
            }
        });

    }

    public static FragmentSignUpSupplierStep2 newInstance() {
        FragmentSignUpSupplierStep2 fragment = new FragmentSignUpSupplierStep2();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up_supplier_step2, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {
        mvvm = ViewModelProviders.of(this).get(FragmentSupplierSignUpStep2Mvvm.class);
        mvvm.getOnDataSuccess().observe(activity, list -> {
            if (spinnerCategoryAdapter != null) {
                spinnerCategoryAdapter.updateList(list);
            }
        });

        mvvm.getOnCityDataSuccess().observe(activity,list->{
            if (spinnerCityAdapter!=null){
                spinnerCityAdapter.updateList(list);
            }
        });
        spinnerCategoryAdapter = new SpinnerCategoryAdapter(activity);
        binding.spinner.setAdapter(spinnerCategoryAdapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CategoryModel categoryModel = (CategoryModel) adapterView.getSelectedItem();
                if (!categoryModel.getId().equals("0")) {
                    addCategory(categoryModel);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerCityAdapter = new SpinnerCityAdapter(activity);
        binding.spinnerCity.setAdapter(spinnerCityAdapter);

        binding.spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CityModel cityModel = (CityModel) adapterView.getSelectedItem();
                if (!cityModel.getId().equals("0")) {
                    model.setCity_id(cityModel.getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        binding.recView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        binding.recView.setHasFixedSize(true);
        adapter = new SupplierSelectedCategoryAdapter(activity, this, getLang());
        binding.recView.setAdapter(adapter);
        adapter.updateList(new ArrayList<>());
        mvvm.getUserData().observe(activity,userModel->{
            Log.e("data",userModel.getData().getUser_type());
            setUserModel(userModel);
            activity.setResult(Activity.RESULT_OK);
            activity.finish();
        });
        binding.back.setOnClickListener(view -> {
            activity.navigateToPreviousFragment();
        });

        binding.flAddress.setOnClickListener(view -> {
            Intent intent = new Intent(activity, MapActivity.class);
            launcher.launch(intent);
        });
        binding.start.setOnClickListener(view -> {
            mvvm.signUp(model,activity);
        });
        mvvm.getCategory(getLang());
        mvvm.getCities(getLang());
    }

    private void addCategory(CategoryModel categoryModel) {
        int pos = isCategoryFound(categoryModel);
        if (pos == -1) {
            selectedCategories.add(categoryModel);
            model.setCategories(selectedCategories);
            adapter.addItem(categoryModel);
            binding.flCategory.setVisibility(View.VISIBLE);

        }
    }

    private int isCategoryFound(CategoryModel categoryModel) {
        for (int index = 0; index < selectedCategories.size(); index++) {
            if (categoryModel.getId().equals(selectedCategories.get(index).getId())) {
                return index;
            }
        }
        return -1;
    }

    public void updateModel(SupplierSignUpModel model) {
        if (model == null) {
            this.model = new SupplierSignUpModel();
        } else {
            this.model = model;
        }
        binding.setModel(model);
    }

    public void deleteSelectedCategory(int adapterPosition) {
        selectedCategories.remove(adapterPosition);
        model.setCategories(selectedCategories);
        adapter.removeItem(adapterPosition);

        binding.spinner.setSelection(0);

        if (selectedCategories.size() == 0) {
            binding.flCategory.setVisibility(View.GONE);

        }
    }
}