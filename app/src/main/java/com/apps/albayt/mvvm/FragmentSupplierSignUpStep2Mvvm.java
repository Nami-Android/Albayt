package com.apps.albayt.mvvm;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apps.albayt.R;
import com.apps.albayt.model.CategoryDataModel;
import com.apps.albayt.model.CategoryModel;
import com.apps.albayt.model.CityDataModel;
import com.apps.albayt.model.CityModel;
import com.apps.albayt.model.NewsDataModel;
import com.apps.albayt.model.NewsModel;
import com.apps.albayt.model.SignUpModel;
import com.apps.albayt.model.SupplierSignUpModel;
import com.apps.albayt.model.UserModel;
import com.apps.albayt.remote.Api;
import com.apps.albayt.share.Common;
import com.apps.albayt.tags.Tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class FragmentSupplierSignUpStep2Mvvm extends AndroidViewModel {
    private MutableLiveData<List<CategoryModel>> onDataSuccess;
    private MutableLiveData<List<CityModel>> onCityDataSuccess;
    public MutableLiveData<UserModel> onUserDataSuccess;

    private CompositeDisposable disposable = new CompositeDisposable();


    public FragmentSupplierSignUpStep2Mvvm(@NonNull Application application) {
        super(application);

    }


    public MutableLiveData<UserModel> getUserData() {
        if (onUserDataSuccess == null) {
            onUserDataSuccess = new MutableLiveData<>();
        }
        return onUserDataSuccess;
    }

    public MutableLiveData<List<CategoryModel>> getOnDataSuccess() {
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
                                response.body().getData().add(0,new CityModel("0",getApplication().getApplicationContext().getString(R.string.ch_city)));
                                getOnCityDataSuccess().setValue(response.body().getData());

                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("error", e.toString());

                    }
                });

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


    public void signUp(SupplierSignUpModel model, Context context) {

        ProgressDialog dialog = Common.createProgressDialog(context, context.getResources().getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.show();
        RequestBody name_part = Common.getRequestBodyText(model.getName());
        RequestBody email_part = Common.getRequestBodyText(model.getEmail());

        RequestBody phone_part = Common.getRequestBodyText(model.getPhone());
        RequestBody phone_code_part = Common.getRequestBodyText(model.getPhone_code());

        RequestBody contact_phone = Common.getRequestBodyText(model.getPhone_code()+model.getPhone_contact());
        RequestBody whatsNumber_part = Common.getRequestBodyText(model.getPhone_code()+model.getPhone_whatsapp());
        RequestBody vat_number = Common.getRequestBodyText(model.getVat_number());
        RequestBody comerical_number = Common.getRequestBodyText(model.getCommercial_number());
        RequestBody city_id = Common.getRequestBodyText(model.getCity_id());
        RequestBody lat_part = Common.getRequestBodyText(model.getLat()+"");
        RequestBody lng_part = Common.getRequestBodyText(model.getLng()+"");
        List<RequestBody> categories = new ArrayList<>();
        for (CategoryModel categoryModel:model.getCategories()){
            RequestBody cat_part = Common.getRequestBodyText(categoryModel.getId());
            categories.add(cat_part);
        }

        MultipartBody.Part image = null;
        if (model.getPhoto_path() != null && !model.getPhoto_path().isEmpty()) {
            image = Common.getMultiPartPathImage(model.getPhoto_path(),"logo");
        }


        Api.getService(Tags.base_url).signUpSupplier(name_part,phone_code_part,phone_part,email_part,contact_phone,whatsNumber_part,city_id,vat_number,comerical_number,lat_part,lng_part,categories,image)
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




}
