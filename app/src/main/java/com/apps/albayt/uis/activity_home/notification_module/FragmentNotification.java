package com.apps.albayt.uis.activity_home.notification_module;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.apps.albayt.R;
import com.apps.albayt.databinding.FragmentNotificationBinding;
import com.apps.albayt.mvvm.GeneralMvvm;
import com.apps.albayt.uis.activity_base.BaseFragment;
import com.apps.albayt.uis.activity_home.HomeActivity;


public class FragmentNotification extends BaseFragment {
    private HomeActivity activity;
    private FragmentNotificationBinding binding;
    private GeneralMvvm generalMvvm;


    public static FragmentNotification newInstance() {
        return new FragmentNotification();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {
        generalMvvm = ViewModelProviders.of(activity).get(GeneralMvvm.class);
        View view = activity.setUpToolbar(binding.toolbar, getString(R.string.notifications), R.color.white, R.color.black, R.drawable.small_rounded_grey4, false);
        view.setOnClickListener(v -> {
            generalMvvm.onHomeBackNavigate().setValue(true);

        });
    }


}