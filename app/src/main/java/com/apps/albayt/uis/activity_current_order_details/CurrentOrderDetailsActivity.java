package com.apps.albayt.uis.activity_current_order_details;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.apps.albayt.R;
import com.apps.albayt.databinding.ActivityCurrentOrderDetailsBinding;
import com.apps.albayt.model.OrderModel;
import com.apps.albayt.mvvm.ActivityOrderDetailsMvvm;
import com.apps.albayt.uis.activity_base.BaseActivity;

public class CurrentOrderDetailsActivity extends BaseActivity {
    private ActivityOrderDetailsMvvm mvvm;
    private ActivityCurrentOrderDetailsBinding binding;
    private OrderModel orderModel;
    private String order_id;
    private ActivityResultLauncher<Intent> launcher;
    private boolean update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_current_order_details);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        order_id = intent.getStringExtra("data");

    }

    private void initView() {
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                update = true;
                if (result.getData()!=null&&result.getData().getStringExtra("data") != null) {
                    Intent intent = getIntent();
                    intent.putExtra("data", order_id);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    mvvm.getOrderDetails(order_id);
                }
            }
        });
        binding.setLang(getLang());
        mvvm = ViewModelProviders.of(this).get(ActivityOrderDetailsMvvm.class);
        setUpToolbar(binding.toolbar, getString(R.string.order_details), R.color.white, R.color.black, R.drawable.small_rounded_grey4, true);
        mvvm.getIsLoading().observe(this, isLoading -> {
            binding.swipeRefresh.setRefreshing(isLoading);
            if (isLoading) {
                binding.scrollView.setVisibility(View.GONE);
            }
        });

        mvvm.getOnDataSuccess().observe(this, model -> {
            if (model != null) {
                binding.scrollView.setVisibility(View.VISIBLE);
                orderModel = model;
                binding.setModel(orderModel);

            }
        });

        mvvm.getOrderDetails(order_id);

        
        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);

        binding.swipeRefresh.setOnRefreshListener(() -> mvvm.getOrderDetails(order_id));

    }



    @Override
    public void onBackPressed() {
        if (update) {
            setResult(RESULT_OK);
        }
        finish();

    }
}