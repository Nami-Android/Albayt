package com.apps.albayt.uis.order_module;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apps.albayt.R;
import com.apps.albayt.adapter.OrderAdapter;
import com.apps.albayt.databinding.FragmentCurrentOrderBinding;
import com.apps.albayt.model.OrderModel;
import com.apps.albayt.model.cart_models.ManageCartModel;
import com.apps.albayt.mvvm.FragmentCurrentOrderMvvm;
import com.apps.albayt.mvvm.GeneralMvvm;
import com.apps.albayt.uis.activity_base.BaseFragment;
import com.apps.albayt.uis.activity_previous_order_details.PreviousOrderDetailsActivity;


public class FragmentPreviousOrder extends BaseFragment {
    private GeneralMvvm generalMvvm;
    private FragmentCurrentOrderMvvm mvvm;
    private OrdersActivity activity;
    private FragmentCurrentOrderBinding binding;
    private OrderAdapter adapter;
    private ManageCartModel manageCartModel;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (OrdersActivity) context;

    }

    public static FragmentPreviousOrder newInstance() {
        return new FragmentPreviousOrder();
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
        manageCartModel = ManageCartModel.newInstance();
        generalMvvm = ViewModelProviders.of(activity).get(GeneralMvvm.class);
        mvvm = ViewModelProviders.of(this).get(FragmentCurrentOrderMvvm.class);
        adapter = new OrderAdapter(activity, this, getLang());
        binding.recViewLayout.recView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recViewLayout.recView.setAdapter(adapter);
        binding.recViewLayout.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        binding.recViewLayout.tvNoData.setText(R.string.no_orders);

        generalMvvm.getOnPreviousOrderRefreshed().observe(activity, isRefreshed -> {
            if (isRefreshed) {
                mvvm.getOrders(getUserModel(), "previous");
            }
        });

        generalMvvm.getOnLoggedOutSuccess().observe(activity, loggedOut -> {
            if (loggedOut) {
                mvvm.getOrders(getUserModel(), "previous");

            }
        });

        generalMvvm.getOnUserLoggedIn().observe(activity, loggedIn -> {
            if (loggedIn) {
                mvvm.getOrders(getUserModel(), "previous");

            }
        });
        mvvm.getIsLoading().observe(activity, isLoading -> {
            binding.recViewLayout.swipeRefresh.setRefreshing(isLoading);
        });
        mvvm.getOnDataSuccess().observe(this, list -> {
            if (list.size() > 0) {
                binding.recViewLayout.tvNoData.setVisibility(View.GONE);
            } else {
                binding.recViewLayout.tvNoData.setVisibility(View.VISIBLE);

            }
            if (adapter != null) {
                adapter.updateList(list);
            }
        });


        binding.recViewLayout.swipeRefresh.setOnRefreshListener(() -> mvvm.getOrders(getUserModel(), "previous"));
        mvvm.getOrders(getUserModel(), "previous");
    }


    public void navigateToDetails(OrderModel orderModel, int adapterPosition) {
        Intent intent = new Intent(activity, PreviousOrderDetailsActivity.class);
        intent.putExtra("data", orderModel.getId());
        startActivity(intent);
    }




}