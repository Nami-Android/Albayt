package com.apps.albayt.mvvm;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.albayt.model.CategoryDataModel;
import com.apps.albayt.model.CategoryModel;
import com.apps.albayt.model.MainHomeDataModel;
import com.apps.albayt.model.ProductModel;
import com.apps.albayt.model.RecentProductDataModel;
import com.apps.albayt.model.SliderDataModel;
import com.apps.albayt.model.StatusResponse;
import com.apps.albayt.model.UserModel;
import com.apps.albayt.model.cart_models.ManageCartModel;
import com.apps.albayt.remote.Api;
import com.apps.albayt.tags.Tags;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FragmentHomeMvvm extends AndroidViewModel {
    private MutableLiveData<MainHomeDataModel.MainHomeData> onDataSuccess;


    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<Boolean> isLoading;


    public FragmentHomeMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<MainHomeDataModel.MainHomeData> getOnDataSuccess() {
        if (onDataSuccess == null) {
            onDataSuccess = new MutableLiveData<>();
        }
        return onDataSuccess;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoading== null) {
            isLoading = new MutableLiveData<>();
        }
        return isLoading;
    }



    public void getData(String lang) {
        getIsLoading().setValue(true);
        Api.getService(Tags.base_url).getMainHomeData(lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<MainHomeDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<MainHomeDataModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getStatus() == 200) {
                                getIsLoading().setValue(false);
                                getOnDataSuccess().setValue(response.body().getData());
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.toString());
                    }
                });

    }


}
