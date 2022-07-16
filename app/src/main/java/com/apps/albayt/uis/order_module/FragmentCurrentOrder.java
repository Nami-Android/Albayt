package com.apps.albayt.uis.order_module;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apps.albayt.R;
import com.apps.albayt.adapter.OrderAdapter;
import com.apps.albayt.databinding.FragmentCurrentOrderBinding;
import com.apps.albayt.model.OrderModel;
import com.apps.albayt.mvvm.FragmentCurrentOrderMvvm;
import com.apps.albayt.mvvm.GeneralMvvm;
import com.apps.albayt.uis.activity_base.BaseFragment;
import com.apps.albayt.uis.activity_current_order_details.CurrentOrderDetailsActivity;
import com.apps.albayt.uis.activity_home.HomeActivity;
import com.apps.albayt.uis.activity_previous_order_details.PreviousOrderDetailsActivity;

public class FragmentCurrentOrder extends BaseFragment {
    private GeneralMvvm generalMvvm;
    private FragmentCurrentOrderMvvm mvvm;
    private OrdersActivity activity;
    private FragmentCurrentOrderBinding binding;
    private OrderAdapter adapter;
    private ActivityResultLauncher<Intent> launcher;
    private int req;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (OrdersActivity) context;
    }

    public static FragmentCurrentOrder newInstance() {
        return new FragmentCurrentOrder();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_current_order, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    private void initView() {
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                mvvm.getOrders(getUserModel(), "current");

                if (result.getData() != null) {
                    if (result.getData().hasExtra("data")) {
                        if (result.getData().getStringExtra("data") != null) {
                            Intent intent = new Intent(activity, PreviousOrderDetailsActivity.class);
                            intent.putExtra("data", result.getData().getStringExtra("data"));
                            launcher.launch(intent);
                        }
                    } else if (result.getData().hasExtra("order_status")) {

                        if (result.getData().getStringExtra("order_status").equals("delivered")) {
                            generalMvvm.getOnPreviousOrderRefreshed().setValue(true);
                            generalMvvm.onOrderNavigate().setValue(1);
                        }
                    }

                }


            }
        });
        generalMvvm = ViewModelProviders.of(activity).get(GeneralMvvm.class);
        mvvm = ViewModelProviders.of(this).get(FragmentCurrentOrderMvvm.class);
        adapter = new OrderAdapter(activity, this, getLang());
        binding.recViewLayout.recView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recViewLayout.recView.setAdapter(adapter);
        binding.recViewLayout.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        binding.recViewLayout.tvNoData.setText(R.string.no_orders);

        generalMvvm.getOnCurrentOrderRefreshed().observe(activity, isRefreshed -> {
            if (isRefreshed) {
                mvvm.getOrders(getUserModel(), "current");
            }
        });

        generalMvvm.getOnLoggedOutSuccess().observe(activity, loggedOut -> {
            if (loggedOut) {
                mvvm.getOrders(getUserModel(), "current");

            }
        });

        generalMvvm.getOnUserLoggedIn().observe(activity, loggedIn -> {
            if (loggedIn) {
                mvvm.getOrders(getUserModel(), "current");

            }
        });

        mvvm.getIsLoading().observe(activity, isLoading -> {
            binding.recViewLayout.swipeRefresh.setRefreshing(isLoading);
        });
        mvvm.getOnDataSuccess().observe(activity, list -> {
            if (list.size() > 0) {
                binding.recViewLayout.tvNoData.setVisibility(View.GONE);
            } else {
                binding.recViewLayout.tvNoData.setVisibility(View.VISIBLE);

            }
            if (adapter != null) {
                adapter.updateList(list);
            }
        });

        binding.recViewLayout.swipeRefresh.setOnRefreshListener(() -> mvvm.getOrders(getUserModel(), "current"));
        mvvm.getOrders(getUserModel(), "current");

    }


    public void navigateToDetails(OrderModel orderModel) {
        req = 1;
        Intent intent;
        if (orderModel.getStatus().equals("current") || orderModel.getStatus().equals("new")) {
            intent = new Intent(activity, CurrentOrderDetailsActivity.class);
        } else {
            intent = new Intent(activity, PreviousOrderDetailsActivity.class);
        }
        intent.putExtra("data", orderModel.getId());
        launcher.launch(intent);
    }
}