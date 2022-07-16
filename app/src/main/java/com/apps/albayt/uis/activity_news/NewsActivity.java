package com.apps.albayt.uis.activity_news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.apps.albayt.R;
import com.apps.albayt.adapter.NewsAdapter;
import com.apps.albayt.adapter.RecentProductAdapter;
import com.apps.albayt.databinding.ActivityNewsBinding;
import com.apps.albayt.mvvm.FragmentMyListMvvm;
import com.apps.albayt.mvvm.FragmentNewsMvvm;
import com.apps.albayt.uis.activity_base.BaseActivity;
import com.apps.albayt.uis.activity_news_details.NewsDetailsActivity;

public class NewsActivity extends BaseActivity {
    private ActivityNewsBinding binding;
    private FragmentNewsMvvm mvvm;
    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news);
        initView();


    }

    private void initView() {
        mvvm = ViewModelProviders.of(this).get(FragmentNewsMvvm.class);

        setUpToolbar(binding.toolbar, getString(R.string.news), R.color.white, R.color.black, R.drawable.small_rounded_grey4, true);
        adapter = new NewsAdapter(this, null, getLang());
        binding.recViewLayout.recView.setHasFixedSize(true);
        binding.recViewLayout.recView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        binding.recViewLayout.recView.setItemViewCacheSize(20);
        binding.recViewLayout.recView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        adapter.setHasStableIds(true);
        binding.recViewLayout.recView.setAdapter(adapter);
        mvvm.getIsLoading().observe(this, isLoading -> {
            binding.recViewLayout.tvNoData.setVisibility(View.GONE);
            binding.recViewLayout.swipeRefresh.setRefreshing(isLoading);
        });

        mvvm.getOnDataSuccess().observe(this, list -> {
            if (list.size() > 0) {
                binding.recViewLayout.tvNoData.setVisibility(View.GONE);

            } else {
                binding.recViewLayout.tvNoData.setVisibility(View.VISIBLE);

            }
            adapter.updateList(list);
        });

        binding.recViewLayout.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        binding.recViewLayout.swipeRefresh.setOnRefreshListener(()->{
            mvvm.getNews(getLang());
        });

        mvvm.getNews(getLang());
    }

    public void navigateToDetails(String id) {
        Intent intent=new Intent(NewsActivity.this, NewsDetailsActivity.class);
        intent.putExtra("data",id);
        startActivity(intent);
    }
}