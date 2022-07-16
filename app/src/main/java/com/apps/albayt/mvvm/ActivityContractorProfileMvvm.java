package com.apps.albayt.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.albayt.R;
import com.apps.albayt.model.AddressModel;
import com.apps.albayt.model.AddressesDataModel;
import com.apps.albayt.model.ContractorDataModel;
import com.apps.albayt.model.ContractorModel;
import com.apps.albayt.model.StatusResponse;
import com.apps.albayt.model.UserModel;
import com.apps.albayt.remote.Api;
import com.apps.albayt.share.Common;
import com.apps.albayt.tags.Tags;

import java.io.IOException;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityContractorProfileMvvm extends AndroidViewModel {

    private MutableLiveData<Boolean> isLoading;

    private MutableLiveData<ContractorModel> onDataSuccess;


    private CompositeDisposable disposable = new CompositeDisposable();


    public ActivityContractorProfileMvvm(@NonNull Application application) {
        super(application);

    }


    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoading == null) {
            isLoading = new MutableLiveData<>();
        }
        return isLoading;
    }

    public MutableLiveData<ContractorModel> getOnDataSuccess() {
        if (onDataSuccess == null) {
            onDataSuccess = new MutableLiveData<>();
        }
        return onDataSuccess;
    }


    public void getProfile(String lang,String user_id)
    {
        getIsLoading().setValue(true);

        Api.getService(Tags.base_url).getContractorProfile(lang,user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<ContractorDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<ContractorDataModel> response) {
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
