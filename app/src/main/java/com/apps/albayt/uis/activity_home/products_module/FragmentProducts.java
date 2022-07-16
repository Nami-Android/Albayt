package com.apps.albayt.uis.activity_home.products_module;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.albayt.R;
import com.apps.albayt.adapter.FilterProductAdapter;
import com.apps.albayt.adapter.MainProductCategoryAdapter;
import com.apps.albayt.adapter.SubProductCategoryAdapter;
import com.apps.albayt.databinding.FragmentProductsBinding;
import com.apps.albayt.model.CategoryModel;
import com.apps.albayt.model.ProductAmount;
import com.apps.albayt.model.ProductModel;
import com.apps.albayt.model.cart_models.ManageCartModel;
import com.apps.albayt.mvvm.FragmentProductsMvvm;
import com.apps.albayt.mvvm.GeneralMvvm;
import com.apps.albayt.uis.activity_base.BaseFragment;
import com.apps.albayt.uis.activity_home.HomeActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;


public class FragmentProducts extends BaseFragment {
    private HomeActivity activity;
    private GeneralMvvm generalMvvm;
    private FragmentProductsMvvm mvvm;
    private FragmentProductsBinding binding;
    private MainProductCategoryAdapter mainProductCategoryAdapter;
    private SubProductCategoryAdapter subProductCategoryAdapter;
    private FilterProductAdapter productAdapter;
    private String query = "";
    private ManageCartModel manageCartModel;

    public static FragmentProducts newInstance() {
        return new FragmentProducts();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (HomeActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_products, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();

    }

    @SuppressLint("CheckResult")
    private void initView() {
        manageCartModel = ManageCartModel.newInstance();

        generalMvvm = ViewModelProviders.of(activity).get(GeneralMvvm.class);
        mvvm = ViewModelProviders.of(activity).get(FragmentProductsMvvm.class);
        View view = activity.setUpToolbar(binding.toolbar, getString(R.string.products), R.color.white, R.color.black, R.drawable.small_rounded_grey4, false);

        view.setOnClickListener(v -> {
            generalMvvm.onHomeBackNavigate().setValue(true);
        });

        generalMvvm.getCategoryList().observe(activity, list -> {
            mvvm.getOnCategoryDataSuccess().setValue(list);
            if (mainProductCategoryAdapter != null) {
                mainProductCategoryAdapter.updateList(list);
            }
        });

        generalMvvm.getCategory_pos().observe(activity, pos -> {

            if (mainProductCategoryAdapter != null && mvvm.getOnCategoryDataSuccess().getValue() != null) {
                List<CategoryModel> list = new ArrayList<>();
                for (CategoryModel model : mvvm.getOnCategoryDataSuccess().getValue()) {
                    model.setSelected(false);
                    list.add(model);
                }
                CategoryModel model = list.get(pos);
                model.setSelected(true);
                list.set(pos, model);
                String sub_category_id = null;
                if(model.getSub_categories()!=null&&model.getSub_categories().size()>0){
                    sub_category_id = model.getSub_categories().get(0).getId();
                }
                mvvm.getSubCategoryId().setValue(sub_category_id);
                mainProductCategoryAdapter = new MainProductCategoryAdapter(activity, this, getLang());
                binding.recViewMain.setAdapter(mainProductCategoryAdapter);
                mainProductCategoryAdapter.setSelectedPos(pos);
                mainProductCategoryAdapter.updateList(list);
                mvvm.setCategoryId(mvvm.getOnCategoryDataSuccess().getValue().get(pos).getId(), activity,getUserModel(),model.getSub_categories(),getLang());

            }

            mvvm.getCategoryPos().setValue(pos);
        });



        mvvm.getIsLoading().observe(activity, isLoading -> {
            binding.tvNoData.setVisibility(View.GONE);
            binding.swipeRefresh.setRefreshing(isLoading);
        });

        mvvm.getOnProductsDataSuccess().observe(activity, list -> {
            if (list.size() > 0) {
                binding.tvNoData.setVisibility(View.GONE);

            } else {
                binding.tvNoData.setVisibility(View.VISIBLE);

            }
            productAdapter.updateList(list);
        });

        mvvm.getOnSubCategoryDataSuccess().observe(activity, list -> {
            if (subProductCategoryAdapter != null) {
                List<CategoryModel> subCategoryList = new ArrayList<>();
                for (CategoryModel model : list) {
                    model.setSelected(false);
                    subCategoryList.add(model);
                }

                if (subCategoryList.size() > 0) {
                    CategoryModel model = subCategoryList.get(0);
                    model.setSelected(true);
                    subCategoryList.set(0, model);

                }

                subProductCategoryAdapter = new SubProductCategoryAdapter(activity, this, getLang());
                binding.recViewSub.setAdapter(subProductCategoryAdapter);
                subProductCategoryAdapter.setSelectedPos(0);
                subProductCategoryAdapter.updateList(subCategoryList);
            }
        });

        binding.swipeRefresh.setOnRefreshListener(() -> {
            mvvm.searchProduct(mvvm.getQuery().getValue(), activity,getUserModel(),getLang());
        });


        mainProductCategoryAdapter = new MainProductCategoryAdapter(activity, this, getLang());
        binding.recViewMain.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        binding.recViewMain.setAdapter(mainProductCategoryAdapter);

        subProductCategoryAdapter = new SubProductCategoryAdapter(activity, this, getLang());
        binding.recViewSub.setAdapter(subProductCategoryAdapter);
        binding.recViewSub.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));


        productAdapter = new FilterProductAdapter(activity, this,getLang());
        binding.recViewProducts.setLayoutManager(new GridLayoutManager(activity, 2));
        binding.recViewProducts.setAdapter(productAdapter);

        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            binding.edtSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    emitter.onNext(s.toString().trim());
                }
            });
        }).debounce(1, TimeUnit.SECONDS)
                .distinctUntilChanged()
                .subscribe(query -> {
                    mvvm.getQuery().postValue(query);
                    mvvm.searchProduct(query, activity,getUserModel(),getLang());
                });


    }


    public void getSubCat(CategoryModel categoryModel) {
        mvvm.setCategoryId(categoryModel.getId(), activity,getUserModel(),categoryModel.getSub_categories(),getLang());
    }

    public void showProducts(CategoryModel categoryModel) {
        mvvm.getSubCategoryId().setValue(categoryModel.getId());
        mvvm.searchProduct(mvvm.getQuery().getValue(), activity,getUserModel(),getLang());
    }

    public void showProductDetails(ProductModel productModel) {
        ProductAmount productAmount = new ProductAmount(productModel.getId(), productModel.getAmount());
        generalMvvm.getProductAmount().setValue(productAmount);
        generalMvvm.onHomeNavigate().setValue(3);
    }

    public void addProductToCart(ProductModel productModel) {
        generalMvvm.getOnCartItemUpdated().setValue(productModel);

        manageCartModel.add(productModel, activity);
        generalMvvm.getOnCartRefreshed().setValue(true);


    }

    public void removeProductFromCart(ProductModel productModel) {
        productModel.setAmount(0);
        generalMvvm.getOnCartItemUpdated().setValue(productModel);
        manageCartModel.delete(productModel, activity);
        generalMvvm.getOnCartRefreshed().setValue(true);

    }



    private int getProductPos(String product_id) {
        if (mvvm.getOnProductsDataSuccess().getValue() != null) {
            for (int index = 0; index < mvvm.getOnProductsDataSuccess().getValue().size(); index++) {
                if (mvvm.getOnProductsDataSuccess().getValue().get(index).getId().equals(product_id)) {
                    return index;
                }
            }
        }

        return -1;
    }
}