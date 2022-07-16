package com.apps.albayt.mvvm;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.albayt.model.NewsDataModel;
import com.apps.albayt.model.NewsModel;
import com.apps.albayt.model.OrderModel;
import com.apps.albayt.model.OrdersModel;
import com.apps.albayt.model.UserModel;
import com.apps.albayt.remote.Api;
import com.apps.albayt.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FragmentCurrentOrderMvvm extends AndroidViewModel {

    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<List<OrderModel>> onDataSuccess;

    public FragmentCurrentOrderMvvm(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<OrderModel>> getOnDataSuccess() {
        if (onDataSuccess == null) {
            onDataSuccess = new MutableLiveData<>();
        }
        return onDataSuccess;
    }


    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoading == null) {
            isLoading = new MutableLiveData<>();
        }
        return isLoading;
    }


    public void getOrders(UserModel userModel,String type) {
        if (userModel == null) {
            getIsLoading().setValue(false);
            getOnDataSuccess().setValue(new ArrayList<>());
            return;
        }
        getIsLoading().setValue(true);

        Log.e("type",type+"");
        Api.getService(Tags.base_url)
                .getOrders(userModel.getData().getId(),type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<OrdersModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<OrdersModel> response) {
                        getIsLoading().setValue(false);
                            if (response.isSuccessful() && response.body() != null) {
                                if (response.body().getData() != null && response.body().getStatus() == 200) {

                                    getOnDataSuccess().setValue(response.body().getData());
                                }
                            }

                         else {
                                try {
                                    Log.e("error", response.errorBody().string() + "__" + response.code());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.getMessage());
                        getIsLoading().setValue(false);
                    }
                });
    }


}
