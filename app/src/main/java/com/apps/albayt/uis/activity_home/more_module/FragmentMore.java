package com.apps.albayt.uis.activity_home.more_module;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.albayt.R;
import com.apps.albayt.databinding.FragmentMainBinding;
import com.apps.albayt.databinding.FragmentMoreBinding;
import com.apps.albayt.mvvm.GeneralMvvm;
import com.apps.albayt.uis.activity_base.BaseFragment;
import com.apps.albayt.uis.activity_home.HomeActivity;
import com.apps.albayt.uis.activity_login.LoginActivity;
import com.apps.albayt.uis.activity_news.NewsActivity;
import com.apps.albayt.uis.activity_sign_up.SignUpActivity;
import com.apps.albayt.uis.order_module.OrdersActivity;


public class FragmentMore extends BaseFragment {
    private GeneralMvvm generalMvvm;
    private HomeActivity activity;
    private FragmentMoreBinding binding;
    private ActivityResultLauncher<Intent> launcher;
    private int req = 1;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode()==Activity.RESULT_OK){
                binding.setModel(getUserModel());
                binding.layoutProfile.setModel(getUserModel());
                generalMvvm.getOnUserLoggedIn().setValue(true);

            }
        });

    }

    public static FragmentMore newInstance() {
        return new FragmentMore();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {
        generalMvvm = ViewModelProviders.of(activity).get(GeneralMvvm.class);
        binding.setModel(getUserModel());
        binding.layoutProfile.setLang(getLang());
        if (getUserModel()!=null){
            if (getUserModel().getData().getUser_type().equals("client")){
                binding.layoutProfile.setLang(getLang());
                binding.layoutProfile.setModel(getUserModel());
            }else if (getUserModel().getData().getUser_type().equals("supplier")){
                binding.layoutSupplierProfile.setLang(getLang());
                binding.layoutSupplierProfile.setModel(getUserModel());
            }

        }else {
            binding.layoutLogin.setLang(getLang());
        }


        binding.layoutLogin.btnJoinUs.setOnClickListener(view -> {
            Intent intent = new Intent(activity,LoginActivity.class);
            launcher.launch(intent);
        });
        binding.layoutLogin.carViewSetting.setOnClickListener(view -> {
            generalMvvm.onHomeNavigate().setValue(7);
        });

        binding.layoutLogin.cardNews.setOnClickListener(view -> {
            Intent intent = new Intent(activity, NewsActivity.class);
            startActivity(intent);
        });


        binding.layoutProfile.cardMyList.setOnClickListener(view -> {
            Intent intent = new Intent(activity, OrdersActivity.class);
            startActivity(intent);
        });
        binding.layoutProfile.carViewSetting.setOnClickListener(view -> {
            generalMvvm.onHomeNavigate().setValue(7);
        });

        binding.layoutProfile.cardMyAddresses.setOnClickListener(view -> {
            generalMvvm.onHomeNavigate().setValue(6);
        });

        binding.layoutSupplierProfile.cardNews.setOnClickListener(view -> {
            Intent intent = new Intent(activity, NewsActivity.class);
            startActivity(intent);
        });

        binding.layoutSupplierProfile.cardMyAddresses.setOnClickListener(view -> {
            generalMvvm.onHomeNavigate().setValue(6);

        });

        binding.layoutSupplierProfile.cardMyList.setOnClickListener(view -> {
            Intent intent = new Intent(activity, OrdersActivity.class);
            startActivity(intent);
        });
        binding.layoutSupplierProfile.carViewSetting.setOnClickListener(view -> {
            generalMvvm.onHomeNavigate().setValue(7);
        });


    }




}