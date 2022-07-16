package com.apps.albayt.mvvm;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.albayt.model.CityDataModel;
import com.apps.albayt.model.CityModel;
import com.apps.albayt.model.SupplierDataModel;
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

public class FragmentContractorsMvvm extends AndroidViewModel {
    private MutableLiveData<List<UserModel.Data>> onDataSuccess;
    private MutableLiveData<List<CityModel>> onCityDataSuccess;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<Boolean> isLoading;


    public FragmentContractorsMvvm(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoading == null) {
            isLoading = new MutableLiveData<>();
        }
        return isLoading;
    }


    public MutableLiveData<List<UserModel.Data>> getOnDataSuccess() {
        if (onDataSuccess == null) {
            onDataSuccess = new MutableLiveData<>();
        }
        return onDataSuccess;
    }

    public MutableLiveData<List<CityModel>> getOnCityDataSuccess() {
        if (onCityDataSuccess == null) {
            onCityDataSuccess = new MutableLiveData<>();
        }
        return onCityDataSuccess;
    }


    public void getCities(String lang){
        Api.getService(Tags.base_url)
                .getCities(lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<CityDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<CityDataModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getData() != null && response.body().getStatus() == 200) {
                                getOnCityDataSuccess().setValue(response.body().getData());
                                if (response.body().getData().size()>0){
                                    String city_id = response.body().getData().get(0).getId();
                                    search(city_id,null,lang);
                                }else {
                                    getIsLoading().setValue(false);
                                }
                            }else {
                                getIsLoading().setValue(false);

                            }
                        }else {
                            getIsLoading().setValue(false);

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.toString());
                        getIsLoading().setValue(false);

                    }
                });

    }

    public void search(String city_id,String query,String lang) {

        getIsLoading().setValue(true);
        Api.getService(Tags.base_url)
                .searchForContractors(city_id,query,lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SupplierDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<SupplierDataModel> response) {
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
