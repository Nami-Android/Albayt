package com.apps.albayt.mvvm;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.albayt.model.NewsDataModel;
import com.apps.albayt.model.NewsModel;
import com.apps.albayt.model.ProductModel;
import com.apps.albayt.model.RecentProductDataModel;
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

public class FragmentNewsMvvm extends AndroidViewModel {
    private MutableLiveData<List<NewsModel>> onDataSuccess;

    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<Boolean> isLoading;


    public FragmentNewsMvvm(@NonNull Application application) {
        super(application);

    }


    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoading == null) {
            isLoading = new MutableLiveData<>();
        }
        return isLoading;
    }


    public MutableLiveData<List<NewsModel>> getOnDataSuccess() {
        if (onDataSuccess == null) {
            onDataSuccess = new MutableLiveData<>();
        }
        return onDataSuccess;
    }



    public void getNews(String lang) {

        getIsLoading().setValue(true);
        Api.getService(Tags.base_url)
                .getNews(lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<NewsDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<NewsDataModel> response) {
                        getIsLoading().setValue(false);
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getData() != null && response.body().getStatus() == 200) {

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
