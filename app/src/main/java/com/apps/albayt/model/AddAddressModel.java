package com.apps.albayt.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.apps.albayt.BR;


public class AddAddressModel extends BaseObservable {
    private String id;
    private String title;
    private String admin_name;
    private String phone;
    private String address;
    private double lat;
    private double lng;
    private boolean valid;

    public AddAddressModel() {
        id ="";
        title = "";
        admin_name = "";
        phone = "";
        address = "";
        lat = 0;
        lng = 0;
    }

    public void isDataValid() {
        if (!title.trim().isEmpty() &&
                !admin_name.trim().isEmpty() &&
                !phone.trim().isEmpty() &&
                !address.trim().isEmpty()
        ) {
            setValid(true);
        } else {
            setValid(false);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Bindable
    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
        isDataValid();
    }

    @Bindable
    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
        notifyPropertyChanged(BR.admin_name);
        isDataValid();
    }



    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
        isDataValid();
    }



    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
        isDataValid();
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Bindable
    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
        notifyPropertyChanged(BR.valid);
    }
}