package com.apps.albayt.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.apps.albayt.R;
import com.apps.albayt.model.SignUpModel;
import com.apps.albayt.model.UserModel;
import com.apps.albayt.remote.Api;
import com.apps.albayt.share.Common;
import com.apps.albayt.tags.Tags;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class ActivitySignupMvvm extends AndroidViewModel {
    public MutableLiveData<UserModel> onUserDataSuccess;

    private CompositeDisposable disposable = new CompositeDisposable();

    public ActivitySignupMvvm(@NonNull Application application) {
        super(application);


    }

    public MutableLiveData<UserModel> getUserData() {
        if (onUserDataSuccess == null) {
            onUserDataSuccess = new MutableLiveData<>();
        }
        return onUserDataSuccess;
    }

    public void signUp(SignUpModel model, Context context) {

        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        RequestBody first_name_part = Common.getRequestBodyText(model.getFirst_name());
        RequestBody last_name_part = Common.getRequestBodyText(model.getLast_name());
        RequestBody email_part = Common.getRequestBodyText(model.getEmail());

        RequestBody phone_part = Common.getRequestBodyText(model.getPhone());
        RequestBody phone_code_part = Common.getRequestBodyText(model.getPhone_code());


        MultipartBody.Part image = null;
        if (model.getImage_path() != null && !model.getImage_path().isEmpty()) {
            image = Common.getMultiPartPathImage(model.getImage_path(),"logo");
        }


        Api.getService(Tags.base_url).signUp(first_name_part,last_name_part, phone_code_part, phone_part, email_part, image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<UserModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Response<UserModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {

                            if (response.body() != null) {
                                if (response.body().getStatus() == 200) {

                                    getUserData().setValue(response.body());
                                } else if (response.body().getStatus() == 406) {
                                    Toast.makeText(context, R.string.ph_found, Toast.LENGTH_LONG).show();
                                } else if (response.body().getStatus() == 407) {
                                    Toast.makeText(context, R.string.em_found, Toast.LENGTH_LONG).show();
                                }else {
                                    Log.e("code",response.body().getStatus()+"");
                                }
                            }

                        }else {
                            try {
                                Log.e("eerrorCode",response.code()+"__"+response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        Log.e("errorSignUp",throwable.getMessage());
                        dialog.dismiss();
                    }

                    @Override
                    public void onComplete() {
                        dialog.dismiss();
                    }
                });
    }

    public void update(SignUpModel model, String user_id, Context context) {
        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        RequestBody id_part = Common.getRequestBodyText(user_id);
        RequestBody first_name_part = Common.getRequestBodyText(model.getFirst_name());
        RequestBody last_name_part = Common.getRequestBodyText(model.getLast_name());
        RequestBody email_part = Common.getRequestBodyText(model.getEmail());
        RequestBody phone_part = Common.getRequestBodyText(model.getPhone());
        RequestBody phone_code_part = Common.getRequestBodyText(model.getPhone_code());


        MultipartBody.Part image = null;
        if (model.getImage_uri() != null && !model.getImage_uri().isEmpty()) {
            image = Common.getMultiPart(context, Uri.parse(model.getImage_uri()), "logo");
        }


        Api.getService(Tags.base_url).updateProfile(id_part,first_name_part,last_name_part,phone_code_part,phone_part,email_part,image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<UserModel>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(@NonNull Response<UserModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {

                            if (response.body() != null) {
                                if (response.body().getStatus() == 200) {

                                    getUserData().setValue(response.body());
                                } else if (response.body().getStatus() == 406) {
                                    Toast.makeText(context, R.string.ph_found, Toast.LENGTH_LONG).show();
                                } else if (response.body().getStatus() == 407) {
                                    Toast.makeText(context, R.string.em_found, Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onComplete() {
                        dialog.dismiss();
                    }
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
