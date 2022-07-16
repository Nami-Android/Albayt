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
import com.apps.albayt.model.AddressesDataModel;
import com.apps.albayt.model.SingleAddressData;
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

public class FragmentMyAddressesMvvm extends AndroidViewModel {

    private MutableLiveData<Boolean> isLoading;

    private MutableLiveData<List<AddressModel>> onDataSuccess;

    private MutableLiveData<Integer> onDeletedSuccess;

    private CompositeDisposable disposable = new CompositeDisposable();


    public FragmentMyAddressesMvvm(@NonNull Application application) {
        super(application);

    }


    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoading == null) {
            isLoading = new MutableLiveData<>();
        }
        return isLoading;
    }
    public MutableLiveData<Integer> getOnDeletedSuccess() {
        if (onDeletedSuccess == null) {
            onDeletedSuccess = new MutableLiveData<>();
        }
        return onDeletedSuccess;
    }

    public MutableLiveData<List<AddressModel>> getOnDataSuccess() {
        if (onDataSuccess == null) {
            onDataSuccess = new MutableLiveData<>();
        }
        return onDataSuccess;
    }


    public void getAddresses(String user_id)
    {
        getIsLoading().setValue(true);

        Api.getService(Tags.base_url).getMyAddresses(user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<AddressesDataModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<AddressesDataModel> response) {
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

    public void deleteAddress(UserModel userModel ,String address_id,Context context,int pos){
        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .deleteAddress("Bearer "+userModel.getData().getToken(),address_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<StatusResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull Response<StatusResponse> response) {
                        dialog.dismiss();

                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getStatus() == 200) {
                                    getOnDeletedSuccess().setValue(pos);
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
