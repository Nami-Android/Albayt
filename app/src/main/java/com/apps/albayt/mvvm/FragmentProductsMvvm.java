package com.apps.albayt.mvvm;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.albayt.model.CategoryDataModel;
import com.apps.albayt.model.CategoryModel;
import com.apps.albayt.model.ProductModel;
import com.apps.albayt.model.RecentProductDataModel;
import com.apps.albayt.model.StatusResponse;
import com.apps.albayt.model.UserModel;
import com.apps.albayt.model.cart_models.ManageCartModel;
import com.apps.albayt.remote.Api;
import com.apps.albayt.tags.Tags;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FragmentProductsMvvm extends AndroidViewModel {
    private ManageCartModel manageCartModel;

    private MutableLiveData<Boolean> isLoading;

    private MutableLiveData<List<CategoryModel>> onCategoryDataSuccess;

    private MutableLiveData<List<CategoryModel>> onSubCategoryDataSuccess;

    private MutableLiveData<List<ProductModel>> onProductsDataSuccess;

    private MutableLiveData<String> categoryId;

    private MutableLiveData<String> subCategoryId;

    private MutableLiveData<String> query;

    private MutableLiveData<Integer> categoryPos;




    private CompositeDisposable disposable = new CompositeDisposable();


    public FragmentProductsMvvm(@NonNull Application application) {
        super(application);
        manageCartModel = ManageCartModel.newInstance();

    }


    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoading == null) {
            isLoading = new MutableLiveData<>();
        }
        return isLoading;
    }

    public MutableLiveData<List<CategoryModel>> getOnCategoryDataSuccess() {
        if (onCategoryDataSuccess == null) {
            onCategoryDataSuccess = new MutableLiveData<>();
        }
        return onCategoryDataSuccess;
    }


    public MutableLiveData<List<CategoryModel>> getOnSubCategoryDataSuccess() {
        if (onSubCategoryDataSuccess == null) {
            onSubCategoryDataSuccess = new MutableLiveData<>();
        }
        return onSubCategoryDataSuccess;
    }


    public MutableLiveData<List<ProductModel>> getOnProductsDataSuccess() {
        if (onProductsDataSuccess == null) {
            onProductsDataSuccess = new MutableLiveData<>();
        }
        return onProductsDataSuccess;
    }


    public MutableLiveData<Integer> getCategoryPos() {
        if (categoryPos == null) {
            categoryPos = new MutableLiveData<>();
        }
        return categoryPos;
    }

    public MutableLiveData<String> getCategoryId() {
        if (categoryId == null) {
            categoryId = new MutableLiveData<>();
        }
        return categoryId;
    }

    public MutableLiveData<String> getQuery() {
        if (query == null) {
            query = new MutableLiveData<>();
        }
        return query;
    }

    public MutableLiveData<String> getSubCategoryId() {
        if (subCategoryId == null) {
            subCategoryId = new MutableLiveData<>();
        }
        return subCategoryId;
    }

    public void setCategoryId(String categoryId,Context context,UserModel userModel,List<CategoryModel> categories,String lang) {
        getCategoryId().setValue(categoryId);
        if (categories.size()>0&&categories.get(0).getSub_categories().size()>0){
            getSubCategoryId().setValue(categories.get(0).getSub_categories().get(0).getId());

        }
        if (categories.size()>0){
            getOnSubCategoryDataSuccess().setValue(categories);
        }else {
            getOnSubCategoryDataSuccess().setValue(new ArrayList<>());
        }
        searchProduct(getQuery().getValue(),context,userModel,lang);

    }


/*
    public void getSubCategory(String cat_id,Context context,UserModel userModel) {

        getOnSubCategoryDataSuccess().setValue(new ArrayList<>());
        Api.getService(Tags.base_url).getSubCategory(cat_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<CategoryDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<CategoryDataModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getData() != null && response.body().getStatus() == 200) {
                                List<CategoryModel> list = response.body().getData();

                                if (list.size() > 0) {
                                    CategoryModel model = new CategoryModel(null, "الكل", "All",  true);
                                    list.add(0, model);
                                }
                                getCategoryId().setValue(cat_id);
                                searchProduct(getQuery().getValue(),context,userModel);
                                getOnSubCategoryDataSuccess().setValue(list);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                        Log.e("error", e.toString());
                    }
                });
    }
*/

    public void searchProduct(String query, Context context, UserModel userModel,String lang) {
        getIsLoading().postValue(true);
        List<String> ids = new ArrayList<>();
        ids.add(getCategoryId().getValue());
        ids.add(getSubCategoryId().getValue());

        Log.e("ids",getCategoryId().getValue()+"___"+getSubCategoryId().getValue()+"__"+query);
        Api.getService(Tags.base_url).searchByCatProduct(ids, query,lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<RecentProductDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<RecentProductDataModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getData() != null && response.body().getStatus() == 200) {

                                prepareProducts(response.body().getData(),context);
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getIsLoading().setValue(false);
                        Log.e("error", e.toString());
                    }
                });
    }

    private void prepareProducts(List<ProductModel> data, Context context) {
        for (int index = 0; index < data.size(); index++) {
            ProductModel productModel = data.get(index);
            productModel.setAmount(manageCartModel.getProductAmount(productModel.getId(),context));
            data.set(index, productModel);
        }
        getIsLoading().setValue(false);
        getOnProductsDataSuccess().setValue(data);
    }



}
