package com.apps.albayt.uis.activity_home.application_module;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apps.albayt.R;
import com.apps.albayt.adapter.ApplicationAdapter;
import com.apps.albayt.adapter.HomeCategoryAdapter;
import com.apps.albayt.databinding.FragmentApplicationBinding;
import com.apps.albayt.model.ProductModel;
import com.apps.albayt.uis.activity_base.BaseFragment;
import com.apps.albayt.uis.activity_home.HomeActivity;
import com.apps.albayt.uis.activity_home.notification_module.FragmentNotification;

public class FragmentApplications extends BaseFragment {
    private HomeActivity activity;
    private FragmentApplicationBinding binding;
    private ApplicationAdapter adapter;
    private ProductModel productModel;

    public static FragmentApplications newInstance(ProductModel productModel) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",productModel);
        FragmentApplications fragmentApplications = new FragmentApplications();
        fragmentApplications.setArguments(bundle);
        return fragmentApplications;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            productModel = (ProductModel) getArguments().getSerializable("data");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_application,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    private void initView() {
        adapter = new ApplicationAdapter(activity, this, getLang());
        binding.recViewLayout.recView.setHasFixedSize(true);
        binding.recViewLayout.recView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        binding.recViewLayout.recView.setItemViewCacheSize(20);
        binding.recViewLayout.recView.setLayoutManager(new GridLayoutManager(activity,2));
        adapter.setHasStableIds(true);
        binding.recViewLayout.recView.setAdapter(adapter);
        adapter.updateList(productModel.getProduct_applications());

    }
}
