package com.apps.albayt.model;

import android.util.Patterns;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.apps.albayt.BR;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SupplierSignUpModel extends BaseObservable implements Serializable {
    private String photo_path;
    private String name;
    private String email;
    private String phone_code;
    private String phone;
    private String phone_contact;
    private String phone_whatsapp;
    private String vat_number;
    private String commercial_number;
    private List<CategoryModel> categories;
    private boolean isStep1Valid;
    private boolean isStep2Valid;

    public SupplierSignUpModel() {
        photo_path = "";
        phone_code = "+966";
        name = "";
        email = "";
        phone ="";
        phone_contact = "";
        phone_whatsapp = "";
        vat_number = "";
        commercial_number ="";
        categories = new ArrayList<>();
        isStep1Valid = false;
        isStep2Valid = false;

    }

    private void isStep1ValidData() {
        if (!name.isEmpty() &&
                !email.isEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                !phone_contact.isEmpty() &&
                !phone_whatsapp.isEmpty()
        ) {
            setStep1Valid(true);
        } else {
            setStep1Valid(false);
        }
    }

    private void isStep2ValidData() {
        if (!vat_number.isEmpty() &&
                !commercial_number.isEmpty()&&
                categories.size()>0
        ) {
            setStep2Valid(true);
        } else {
            setStep2Valid(false);
        }
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
        isStep1ValidData();
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
        isStep1ValidData();

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(String phone_code) {
        this.phone_code = phone_code;
    }

    @Bindable
    public String getPhone_contact() {
        return phone_contact;
    }

    public void setPhone_contact(String phone_contact) {
        this.phone_contact = phone_contact;
        notifyPropertyChanged(BR.phone_contact);
        isStep1ValidData();

    }

    @Bindable
    public String getPhone_whatsapp() {
        return phone_whatsapp;
    }

    public void setPhone_whatsapp(String phone_whatsapp) {
        this.phone_whatsapp = phone_whatsapp;
        notifyPropertyChanged(BR.phone_whatsapp);
        isStep1ValidData();

    }

    @Bindable
    public boolean isStep1Valid() {
        return isStep1Valid;
    }

    public void setStep1Valid(boolean step1Valid) {
        isStep1Valid = step1Valid;
        notifyPropertyChanged(BR.step1Valid);
    }

    @Bindable
    public boolean isStep2Valid() {
        return isStep2Valid;
    }

    public void setStep2Valid(boolean step2Valid) {
        isStep2Valid = step2Valid;
        notifyPropertyChanged(BR.step2Valid);
    }

    @Bindable
    public String getVat_number() {
        return vat_number;
    }

    public void setVat_number(String vat_number) {
        this.vat_number = vat_number;
        notifyPropertyChanged(BR.vat_number);
        isStep2ValidData();
    }

    @Bindable
    public String getCommercial_number() {
        return commercial_number;
    }

    public void setCommercial_number(String commercial_number) {
        this.commercial_number = commercial_number;
        notifyPropertyChanged(BR.commercial_number);
        isStep2ValidData();
    }

    public List<CategoryModel> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryModel> categories) {
        this.categories = categories;
        isStep2ValidData();
    }
}
