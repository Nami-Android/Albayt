package com.apps.albayt.uis.activity_sign_up_supplier;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.apps.albayt.R;
import com.apps.albayt.adapter.MyPagerAdapter;
import com.apps.albayt.databinding.ActivitySignUpSupplierBinding;
import com.apps.albayt.model.SupplierSignUpModel;
import com.apps.albayt.uis.activity_base.BaseActivity;
import com.apps.albayt.uis.activity_sign_up_supplier.fragments.FragmentSignUpSupplierStep1;

import java.util.ArrayList;
import java.util.List;

public class SignUpSupplierActivity extends BaseActivity {
    private ActivitySignUpSupplierBinding binding;
    private String phone_code, phone;
    private MyPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up_supplier);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        phone_code = intent.getStringExtra("phone_code");
        phone = intent.getStringExtra("phone");
    }

    private void initView() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(FragmentSignUpSupplierStep1.newInstance(phone_code,phone));
        adapter = new MyPagerAdapter(getSupportFragmentManager(),fragments,null);
        binding.pager.setAdapter(adapter);
        binding.pager.setOffscreenPageLimit(fragments.size());
    }

    public void navigateToNextFragment(SupplierSignUpModel model){
        binding.pager.setCurrentItem(1);
    }
}