package com.apps.albayt.uis.activity_news_details;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.apps.albayt.R;
import com.apps.albayt.databinding.ActivityNewsDetailsBinding;
import com.apps.albayt.mvvm.ActivityNewsDetailsMvvm;
import com.apps.albayt.uis.activity_base.BaseActivity;


public class NewsDetailsActivity extends BaseActivity {
    private ActivityNewsDetailsBinding binding;
    private ActivityNewsDetailsMvvm mvvm;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_news_details);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        id =  intent.getStringExtra("data");

    }
    private void initView() {

        setUpToolbar(binding.toolbar, getString(R.string.blog_details), R.color.white, R.color.black, R.drawable.small_rounded_grey4, true);
        binding.setLang(getLang());


        mvvm = ViewModelProviders.of(this).get(ActivityNewsDetailsMvvm.class);

        mvvm.getIsDataLoading().observe(this, isLoading -> {
            if (isLoading) {
                binding.progBar.setVisibility(View.VISIBLE);
            } else {
                binding.progBar.setVisibility(View.GONE);
            }
        });

        mvvm.getOnDataSuccess().observe(this, blogModel -> {
            if (blogModel!=null){
                binding.setModel(blogModel);
            }
        });
        mvvm.getSingleBlog(id,getLang());
    }
}