package com.apps.albayt.model;

import java.io.Serializable;

public class AddressModel implements Serializable {
    private String id;
    private String user_id;
    private String title;
    private String admin_name;
    private String phone_number;
    private String address;
    private String latitude;
    private String longitude;


    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getTitle() {
        return title;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getAddress() {
        return address;
    }


    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

}
