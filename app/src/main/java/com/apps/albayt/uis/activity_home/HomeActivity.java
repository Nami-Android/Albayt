package com.apps.albayt.uis.activity_home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;


import com.apps.albayt.adapter.MyPagerAdapter;
import com.apps.albayt.model.ProductAmount;
import com.apps.albayt.mvvm.GeneralMvvm;
import com.apps.albayt.uis.activity_base.BaseActivity;

import com.apps.albayt.R;

import com.apps.albayt.databinding.ActivityHomeBinding;
import com.apps.albayt.language.Language;
import com.apps.albayt.uis.activity_home.add_address_module.FragmentAddAddress;
import com.apps.albayt.uis.activity_home.address_module.FragmentMyAddresses;
import com.apps.albayt.uis.activity_home.home_module.FragmentHome;
import com.apps.albayt.uis.activity_home.my_list_module.FragmentMyList;
import com.apps.albayt.uis.activity_home.notification_module.FragmentNotification;
import com.apps.albayt.uis.activity_home.product_detials_module.FragmentProductDetials;
import com.apps.albayt.uis.activity_home.products_module.FragmentProducts;
import com.apps.albayt.uis.activity_home.search_module.FragmentSearch;
import com.apps.albayt.uis.activity_home.setting_module.FragmentSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import io.paperdb.Paper;

public class HomeActivity extends BaseActivity {
    private GeneralMvvm generalMvvm;
    private ActivityHomeBinding binding;
    private MyPagerAdapter adapter;
    private List<Fragment> fragments;
    private Stack<Integer> stack;
    private String product_id =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        getDataFromIntent();
        initView();


    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        product_id = intent.getStringExtra("product_id");

    }


    private void initView() {
        generalMvvm = ViewModelProviders.of(this).get(GeneralMvvm.class);
        setUpPager();
        generalMvvm.onHomeNavigate().observe(this, this::updateStack);
        generalMvvm.onHomeBackNavigate().observe(this, value -> {
            onBackPressed();
        });

       /* homeActivityMvvm = ViewModelProviders.of(this).get(HomeActivityMvvm.class);


        homeActivityMvvm.firebase.observe(this, token -> {
            if (getUserModel() != null) {
                UserModel userModel = getUserModel();
                userModel.getData().setFirebase_token(token);
                setUserModel(userModel);
            }
        });


        if (getUserModel() != null) {
            homeActivityMvvm.updateFirebase(this, getUserModel());
        }*/
    }

    private void setUpPager() {
        stack = new Stack<>();
        fragments = new ArrayList<>();

        fragments.add(FragmentHome.newInstance());//0
        fragments.add(FragmentNotification.newInstance());//1
        fragments.add(FragmentSearch.newInstance());//2
        fragments.add(FragmentProductDetials.newInstance());//3
        fragments.add(FragmentProducts.newInstance());//4
        fragments.add(FragmentAddAddress.newInstance());//5
        fragments.add(FragmentMyAddresses.newInstance());//6
        fragments.add(FragmentSetting.newInstance());//7


        adapter = new MyPagerAdapter(getSupportFragmentManager(), fragments, null);
        binding.pager.setAdapter(adapter);
        binding.pager.setOffscreenPageLimit(fragments.size());

        stack.push(0);

        if (product_id!=null&&!product_id.isEmpty()){
            updateStack(6);
            ProductAmount productAmount = new ProductAmount(product_id, 0);
            generalMvvm.getProductAmount().setValue(productAmount);
        }
    }

    private void updateStack(int pagePos) {
        stack.push(pagePos);
        binding.pager.setCurrentItem(pagePos);

    }


    public void refreshActivity(String lang) {
        Paper.book().write("lang", lang);
        Language.setNewLocale(this, lang);
        new Handler()
                .postDelayed(() -> {

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }, 500);


    }


    public void updateFirebase() {
        if (getUserModel() != null) {
            // homeActivityMvvm.updateFirebase(this, getUserModel());
        }
    }


    @Override
    public void onBackPressed() {
        if (stack.size() > 1) {
            stack.pop();
            binding.pager.setCurrentItem(stack.peek());
        } else {
            FragmentHome fragmentHome = (FragmentHome) adapter.getItem(0);
            if (!fragmentHome.onBackPress()) {
                super.onBackPressed();
            }

        }


    }

}
