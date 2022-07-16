package com.apps.albayt.model;

import java.io.Serializable;

public class UserModel extends StatusResponse {
    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data implements Serializable {
        private String id;
        private String user_type;
        private String phone_code;
        private String phone;
        private String first_name;
        private String last_name;
        private String full_name;
        private String email;
        private String contact_phone;
        private String contact_whats_up;
        private String vat_number;
        private String comerical_number;
        private String latitude;
        private String longitude;
        private String logo;
        private String created_at;
        private String token;
        private City city;
        private static String firebase_token;

        public String getFirebase_token() {
            return firebase_token;
        }

        public void setFirebase_token(String firebase_token) {
            this.firebase_token = firebase_token;
        }

        public String getId() {
            return id;
        }

        public String getUser_type() {
            return user_type;
        }

        public String getPhone_code() {
            return phone_code;
        }

        public String getPhone() {
            return phone;
        }

        public String getFirst_name() {
            return first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public String getFull_name() {
            return full_name;
        }

        public String getEmail() {
            return email;
        }

        public String getContact_phone() {
            return contact_phone;
        }

        public String getContact_whats_up() {
            return contact_whats_up;
        }

        public String getVat_number() {
            return vat_number;
        }

        public String getComerical_number() {
            return comerical_number;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getLogo() {
            return logo;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getToken() {
            return token;
        }

        public City getCity() {
            return city;
        }


    }

    public static class City implements Serializable{
        private String user_id;
        private String city_id;
        private CityModel city;

        public String getUser_id() {
            return user_id;
        }

        public String getCity_id() {
            return city_id;
        }

        public CityModel getCity() {
            return city;
        }
    }

}
