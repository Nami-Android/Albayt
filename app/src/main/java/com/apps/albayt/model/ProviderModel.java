package com.apps.albayt.model;

import java.io.Serializable;

public class ProviderModel implements Serializable {
    private String id;
    private String name;
    private String fake_name;
    private String email;
    private String phone_code;
    private String phone;
    private String password;
    private String vat_number;
    private String image;
    private String created_at;
    private String updated_at;
    private String rate;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFake_name() {
        return fake_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_code() {
        return phone_code;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getVat_number() {
        return vat_number;
    }

    public String getImage() {
        return image;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getRate() {
        return rate;
    }
}
