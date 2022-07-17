package com.apps.albayt.mvvm;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.albayt.R;
import com.apps.albayt.model.CategoryDataModel;
import com.apps.albayt.model.CategoryModel;
import com.apps.albayt.model.NewsDataModel;
import com.apps.albayt.model.NewsModel;
import com.apps.albayt.remote.Api;
import com.apps.albayt.tags.Tags;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class FragmentSupplierSignUpStep2Mvvm extends AndroidViewModel {
    private MutableLiveData<List<CategoryModel>> onDataSuccess;

    private CompositeDisposable disposable = new CompositeDisposable();


    public FragmentSupplierSignUpStep2Mvvm(@NonNull Application application) {
        super(application);

    }



    public MutableLiveData<List<CategoryModel>> getOnDataSuccess() {
        if (onDataSuccess == null) {
            onDataSuccess = new MutableLiveData<>();
        }
        return onDataSuccess;
    }



    public void getCategory(String lang) {

        Api.getService(Tags.base_url)
                .getCategories(lang)
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
                                CategoryModel categoryModel = new CategoryModel("0",getApplication().getApplicationContext().getString(R.string.ch_category));
                                response.body().getData().add(0,categoryModel);
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
