package com.apps.albayt.uis.activity_home.home_module;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.albayt.R;

import com.apps.albayt.adapter.HomeCategoryAdapter;
import com.apps.albayt.adapter.NewsAdapter;
import com.apps.albayt.adapter.RecentProductAdapter;
import com.apps.albayt.adapter.SliderAdapter;
import com.apps.albayt.databinding.FragmentMainBinding;
import com.apps.albayt.model.CategoryModel;
import com.apps.albayt.model.MainHomeDataModel;
import com.apps.albayt.model.ProductAmount;
import com.apps.albayt.model.ProductModel;
import com.apps.albayt.mvvm.FragmentHomeMvvm;
import com.apps.albayt.mvvm.GeneralMvvm;
import com.apps.albayt.uis.activity_base.BaseFragment;
import com.apps.albayt.uis.activity_home.HomeActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class FragmentMain extends BaseFragment {
    private GeneralMvvm generalMvvm;
    private HomeActivity activity;
    private FragmentMainBinding binding;
    private FragmentHomeMvvm mvvm;
    private SliderAdapter sliderAdapter;
    private Timer timer;
    private MyTask myTask;
    private HomeCategoryAdapter categoryAdapter;
    private RecentProductAdapter recentProductAdapter;
    private NewsAdapter newsAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    public static FragmentMain newInstance() {
        return new FragmentMain();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }


    private void initView() {
        binding.setLang(getLang());
        generalMvvm = ViewModelProviders.of(activity).get(GeneralMvvm.class);
        binding.setNotificationCount("0");
        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary);

        mvvm = ViewModelProviders.of(this).get(FragmentHomeMvvm.class);
        mvvm.getIsLoading().observe(activity, isLoading -> {

            if (!isLoading) {
                binding.loader.stopShimmer();
                binding.loader.setVisibility(View.GONE);
            }else {
                binding.scrollView.setVisibility(View.GONE);
                binding.loader.setVisibility(View.VISIBLE);
                binding.loader.startShimmer();
            }
        });


        mvvm.getOnDataSuccess().observe(activity, data -> {
            binding.swipeRefresh.setRefreshing(false);
            updateData(data);

        });


        setUpSliderData();

        categoryAdapter = new HomeCategoryAdapter(activity, this, getLang(), binding.recViewCategory);
        binding.recViewCategory.setHasFixedSize(true);
        binding.recViewCategory.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        binding.recViewCategory.setItemViewCacheSize(20);
        binding.recViewCategory.setLayoutManager(new LinearLayoutManager(activity));
        categoryAdapter.setHasStableIds(true);
        binding.recViewCategory.setAdapter(categoryAdapter);


        recentProductAdapter = new RecentProductAdapter(activity, this, getLang());
        binding.recViewLatestProducts.setHasFixedSize(true);
        binding.recViewLatestProducts.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        binding.recViewLatestProducts.setItemViewCacheSize(20);
        binding.recViewLatestProducts.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        recentProductAdapter.setHasStableIds(true);
        binding.recViewLatestProducts.setAdapter(recentProductAdapter);

        newsAdapter = new NewsAdapter(activity, this, getLang());
        binding.recViewNews.setHasFixedSize(true);
        binding.recViewNews.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        binding.recViewNews.setItemViewCacheSize(20);
        binding.recViewNews.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        newsAdapter.setHasStableIds(true);
        binding.recViewNews.setAdapter(newsAdapter);


        binding.swipeRefresh.setOnRefreshListener(this::getData);

        getData();
    }

    private void getData() {
        mvvm.getData(getLang());


    }

    private void updateData(MainHomeDataModel.MainHomeData data) {
        sliderAdapter.updateList(data.getSlider());
        if (data.getSlider().size() > 1) {
            timer = new Timer();
            myTask = new MyTask();
            timer.scheduleAtFixedRate(myTask, 6000, 6000);
        }
        categoryAdapter.updateList(data.getCategories());
        if (data.getCategories().size() > 0) {
            binding.tvNoCategories.setVisibility(View.GONE);
        } else {
            binding.tvNoCategories.setVisibility(View.VISIBLE);

        }

        recentProductAdapter.updateList(data.getLast_products());
        if (data.getLast_products().size() > 0) {
            binding.tvNoLatestProduct.setVisibility(View.GONE);
        } else {
            binding.tvNoLatestProduct.setVisibility(View.VISIBLE);

        }

        newsAdapter.updateList(data.getNews());
        if (data.getNews().size() > 0) {
            binding.tvNoNews.setVisibility(View.GONE);
        } else {
            binding.tvNoNews.setVisibility(View.VISIBLE);

        }
        binding.scrollView.setVisibility(View.VISIBLE);


    }

    private void setUpSliderData() {
        sliderAdapter = new SliderAdapter(activity);
        binding.pager.setAdapter(sliderAdapter);
        binding.pager.setClipToPadding(false);
        binding.pager.setPadding(20, 0, 20, 0);
    }

    public void showProductDetails(ProductModel productModel) {
        ProductAmount productAmount = new ProductAmount(productModel.getId(), productModel.getAmount());
        generalMvvm.getProductAmount().setValue(productAmount);
        generalMvvm.onHomeNavigate().setValue(3);

    }

    public void showCategoryDetails(CategoryModel categoryModel, int pos,int mainPos) {
        List<CategoryModel> categories = new ArrayList<>();
        if (mvvm.getOnDataSuccess().getValue()!=null&&mvvm.getOnDataSuccess().getValue().getCategories().size()>0){
            categories.addAll(mvvm.getOnDataSuccess().getValue().getCategories().get(mainPos).getSub_categories());
        }
        generalMvvm.getCategoryList().setValue(categories);
        generalMvvm.getCategory_pos().setValue(pos);
        generalMvvm.onHomeNavigate().setValue(4);
    }



    public class MyTask extends TimerTask {
        @Override
        public void run() {
            activity.runOnUiThread(() -> {
                int current_page = binding.pager.getCurrentItem();
                if (current_page < sliderAdapter.getCount() - 1) {
                    binding.pager.setCurrentItem(binding.pager.getCurrentItem() + 1);
                } else {
                    binding.pager.setCurrentItem(0);

                }
            });

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timer != null && myTask != null) {
            timer.purge();
            timer.cancel();
            myTask.cancel();

        }

    }
}