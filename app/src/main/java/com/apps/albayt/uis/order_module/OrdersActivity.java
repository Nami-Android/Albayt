package com.apps.albayt.uis.order_module;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.albayt.R;
import com.apps.albayt.adapter.MyPagerAdapter;
import com.apps.albayt.adapter.NewsAdapter;
import com.apps.albayt.databinding.ActivityNewsBinding;
import com.apps.albayt.databinding.ActivityOrdersBinding;
import com.apps.albayt.mvvm.FragmentNewsMvvm;
import com.apps.albayt.mvvm.GeneralMvvm;
import com.apps.albayt.uis.activity_base.BaseActivity;
import com.apps.albayt.uis.activity_news_details.NewsDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends BaseActivity {
    private GeneralMvvm generalMvvm;
    private ActivityOrdersBinding binding;
    private MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_orders);
        initView();


    }


    private void initView() {
        generalMvvm = ViewModelProviders.of(OrdersActivity.this).get(GeneralMvvm.class);
        generalMvvm.onOrderNavigate().observe(OrdersActivity.this, pos -> {
            binding.pager.setCurrentItem(pos);
        });
        setUpPager();
    }


    private void setUpPager() {
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        fragments.add(FragmentCurrentOrder.newInstance());
        fragments.add(FragmentPreviousOrder.newInstance());
        titles.add(getString(R.string.current));
        titles.add(getString(R.string.prev));
        adapter = new MyPagerAdapter(getSupportFragmentManager(), fragments, titles);
        binding.pager.setAdapter(adapter);
        binding.pager.setOffscreenPageLimit(fragments.size());
        binding.tab.setupWithViewPager(binding.pager);


    }

}