package com.apps.albayt.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.albayt.R;
import com.apps.albayt.model.AddAddressModel;
import com.apps.albayt.model.AddressModel;
import com.apps.albayt.model.SingleAddressData;
import com.apps.albayt.model.TimeDataModel;
import com.apps.albayt.model.TimeModel;
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

public class FragmentAddAddressMvvm extends AndroidViewModel {

    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<List<TimeModel>> onDataSuccess;
    private MutableLiveData<AddressModel> onAddressAdded;
    private MutableLiveData<AddressModel> onAddressUpdated;

    public FragmentAddAddressMvvm(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoading == null) {
            isLoading = new MutableLiveData<>();
        }
        return isLoading;
    }


    public MutableLiveData<List<TimeModel>> getOnDataSuccess() {
        if (onDataSuccess == null) {
            onDataSuccess = new MutableLiveData<>();
        }
        return onDataSuccess;
    }

    public MutableLiveData<AddressModel> getOnAddressAdded() {
        if (onAddressAdded == null) {
            onAddressAdded = new MutableLiveData<>();
        }
        return onAddressAdded;
    }

    public MutableLiveData<AddressModel> getOnAddressUpdated() {
        if (onAddressUpdated == null) {
            onAddressUpdated = new MutableLiveData<>();
        }
        return onAddressUpdated;
    }



    public void addAddress(UserModel userModel, AddAddressModel model,Context context){
        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url).addAddress(userModel.getData().getId(), model.getTitle(),model.getAdmin_name(), model.getPhone(),model.getAddress(), model.getLat(), model.getLng())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SingleAddressData>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<SingleAddressData> response) {
                        dialog.dismiss();

                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatus() == 200) {
                                    getOnAddressAdded().setValue(response.body().getData());
                                }
                            }

                        } else {
                            try {
                                Log.e("error", response.errorBody().string() + "__" + response.code());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();

                    }
                });
    }

    public void updateAddress(UserModel userModel, AddAddressModel model,Context context){
        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url).updateAddress("Bearer "+userModel.getData().getToken(), model.getId(), userModel.getData().getId(), model.getTitle(),model.getAdmin_name(), model.getPhone(),model.getAddress(), model.getLat(), model.getLng())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<SingleAddressData>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<SingleAddressData> response) {
                        dialog.dismiss();

                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatus() == 200) {
                                    getOnAddressUpdated().setValue(response.body().getData());
                                }
                            }

                        } else {
                            try {
                                Log.e("error", response.errorBody().string() + "__" + response.code());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dialog.dismiss();

                    }
                });
    }

}
