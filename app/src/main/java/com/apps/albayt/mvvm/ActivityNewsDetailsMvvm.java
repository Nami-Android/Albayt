package com.apps.albayt.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.albayt.model.NewsDataModel;
import com.apps.albayt.model.NewsModel;
import com.apps.albayt.remote.Api;
import com.apps.albayt.tags.Tags;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ActivityNewsDetailsMvvm extends AndroidViewModel {
    private MutableLiveData<Boolean> isDataLoading;
    private MutableLiveData<NewsModel> onDataSuccess;
    private CompositeDisposable disposable = new CompositeDisposable();

    public MutableLiveData<Boolean> getIsDataLoading() {
        if (isDataLoading==null){
            isDataLoading=new MutableLiveData<>();
        }
        return isDataLoading;
    }

    public MutableLiveData<NewsModel> getOnDataSuccess() {
        if (onDataSuccess==null){
            onDataSuccess=new MutableLiveData<>();
        }
        return onDataSuccess;
    }

    public ActivityNewsDetailsMvvm(@NonNull Application application) {
        super(application);
    }

    public void getSingleBlog(String id,String lang){
        getIsDataLoading().setValue(true);
    /*    Api.getService(Tags.base_url).getBlogDetails(id,lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<NewsDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<NewsDataModel> response) {
                        getIsDataLoading().postValue(false);

                        if (response.isSuccessful() && response.body() !=null) {
                            if (response.body().getStatus()==200){
                                onDataSuccess.setValue(response.body().getData());
                            }

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        getIsDataLoading().setValue(false);
                    }
                });*/
    }
}
