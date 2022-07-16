package com.apps.albayt.mvvm;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.albayt.model.ProductModel;
import com.apps.albayt.model.RecentProductDataModel;
import com.apps.albayt.model.SearchHomeDataModel;
import com.apps.albayt.model.UserModel;
import com.apps.albayt.remote.Api;
import com.apps.albayt.tags.Tags;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FragmentSearchMvvm extends AndroidViewModel {

    private CompositeDisposable disposable = new CompositeDisposable();

    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Boolean> isLoadingProducts;

    private MutableLiveData<SearchHomeDataModel.Data> onDataSuccess;


    private MutableLiveData<List<ProductModel>> onProductsDataSuccess;

    private MutableLiveData<String> subCategoryId;

    private MutableLiveData<String> query;

    public FragmentSearchMvvm(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoading == null) {
            isLoading = new MutableLiveData<>();
        }
        return isLoading;
    }

    public MutableLiveData<Boolean> getIsLoadingProducts() {
        if (isLoadingProducts == null) {
            isLoadingProducts = new MutableLiveData<>();
        }
        return isLoadingProducts;
    }


    public MutableLiveData<SearchHomeDataModel.Data> getOnDataSuccess() {
        if (onDataSuccess == null) {
            onDataSuccess = new MutableLiveData<>();
        }
        return onDataSuccess;
    }

    public MutableLiveData<List<ProductModel>> getOnProductsDataSuccess() {
        if (onProductsDataSuccess == null) {
            onProductsDataSuccess = new MutableLiveData<>();
        }
        return onProductsDataSuccess;
    }


    public MutableLiveData<String> getSubCategoryId() {
        if (subCategoryId == null) {
            subCategoryId = new MutableLiveData<>();
        }
        return subCategoryId;
    }

    public MutableLiveData<String> getQuery() {
        if (query == null) {
            query = new MutableLiveData<>();
        }
        return query;
    }

    public void search(String query, UserModel userModel) {
        String user_id = null;
        if (userModel!=null){
            user_id = userModel.getData().getId();
        }
        getQuery().setValue(query);
        getIsLoading().setValue(true);
        Api.getService(Tags.base_url).searchProduct(query,user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SearchHomeDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<SearchHomeDataModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getData() != null && response.body().getStatus() == 200) {
                                getIsLoading().setValue(false);
                                onDataSuccess.setValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.toString());
                    }
                });
    }


    public void getProductBySubCategory(UserModel userModel)  {
        /*getIsLoadingProducts().setValue(true);

        Api.getService(Tags.base_url).searchByCatProduct(user_id,null, getSubCategoryId().getValue(), null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<RecentProductDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<RecentProductDataModel> response) {
                        getIsLoadingProducts().setValue(false);
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getData() != null && response.body().getStatus() == 200) {

                                getOnProductsDataSuccess().setValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getIsLoadingProducts().setValue(false);
                        Log.e("error", e.toString());
                    }
                });
*/    }

}
