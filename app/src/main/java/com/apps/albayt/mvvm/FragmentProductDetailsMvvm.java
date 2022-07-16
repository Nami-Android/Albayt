package com.apps.albayt.mvvm;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.albayt.model.ProductModel;
import com.apps.albayt.model.SingleProductModel;
import com.apps.albayt.model.StatusResponse;
import com.apps.albayt.model.UserModel;
import com.apps.albayt.remote.Api;
import com.apps.albayt.tags.Tags;


import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FragmentProductDetailsMvvm extends AndroidViewModel {

    private MutableLiveData<Boolean> isDataLoading;
    private MutableLiveData<ProductModel> onDataSuccess;
    private MutableLiveData<ProductModel> onFavUnFavSuccess;

    private CompositeDisposable disposable = new CompositeDisposable();

    public MutableLiveData<Boolean> getIsDataLoading() {
        if (isDataLoading == null) {
            isDataLoading = new MutableLiveData<>();
        }
        return isDataLoading;
    }

    public MutableLiveData<ProductModel> getOnDataSuccess() {
        if (onDataSuccess == null) {
            onDataSuccess = new MutableLiveData<>();
        }
        return onDataSuccess;
    }

    public FragmentProductDetailsMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<ProductModel> getOnFavUnFavSuccess() {
        if (onFavUnFavSuccess == null) {
            onFavUnFavSuccess = new MutableLiveData<>();
        }
        return onFavUnFavSuccess;
    }

    public void getSingleProduct(String id,String lang) {

        getIsDataLoading().setValue(true);
        Api.getService(Tags.base_url).getSingleProduct(id,lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SingleProductModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<SingleProductModel> response) {
                        getIsDataLoading().postValue(false);
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getStatus() == 200) {
                                onDataSuccess.setValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getIsDataLoading().setValue(false);
                        Log.e("err", e.toString());

                    }
                });
    }



}
