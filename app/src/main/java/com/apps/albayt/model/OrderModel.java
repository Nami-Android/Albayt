package com.apps.albayt.model;

import java.io.Serializable;
import java.util.List;

public class OrderModel implements Serializable {
    public int id;
    public String order_number;
    public String order_date;
    public String order_time;
    public int user_id;
    public int supplier_id;
    public int address_id;
    public String status;
    public int total_price;
    public String notes;
    public User user;
    public Supplier supplier;
    public Address address;

    public int getId() {
        return id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public String getOrder_date() {
        return order_date;
    }

    public String getOrder_time() {
        return order_time;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public int getAddress_id() {
        return address_id;
    }

    public String getStatus() {
        return status;
    }

    public int getTotal_price() {
        return total_price;
    }

    public String getNotes() {
        return notes;
    }

    public User getUser() {
        return user;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Address getAddress() {
        return address;
    }

    public class Address{
        public int id;
        public int user_id;
        public String title;
        public String admin_name;
        public String phone_number;
        public int longitude;
        public int latitude;
        public String address;

        public int getId() {
            return id;
        }

        public int getUser_id() {
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

        public int getLongitude() {
            return longitude;
        }

        public int getLatitude() {
            return latitude;
        }

        public String getAddress() {
            return address;
        }
    }

    public class Supplier{
        public int id;
        public String user_type;
        public String phone_code;
        public String phone;
        public String first_name;
        public String last_name;
        public String full_name;
        public String email;
        public String contact_phone;
        public String contact_whats_up;
        public String vat_number;
        public String comerical_number;
        public double latitude;
        public double longitude;
        public String logo;
        public String created_at;
        public Object token;
        public City city;

        public int getId() {
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

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public String getLogo() {
            return logo;
        }

        public String getCreated_at() {
            return created_at;
        }

        public Object getToken() {
            return token;
        }

        public City getCity() {
            return city;
        }

        public class City{
            public int user_id;
            public int city_id;
            public City city;
            public int id;
            public String title;

            public int getUser_id() {
                return user_id;
            }

            public int getCity_id() {
                return city_id;
            }

            public City getCity() {
                return city;
            }

            public int getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }
        }
    }

    public class User{
        public int id;
        public String user_type;
        public String phone_code;
        public String phone;
        public String first_name;
        public String last_name;
        public String full_name;
        public String email;
        public Object contact_phone;
        public Object contact_whats_up;
        public Object vat_number;
        public Object comerical_number;
        public int latitude;
        public int longitude;
        public String logo;
        public String created_at;
        public String token;
        public City city;

        public int getId() {
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

        public Object getContact_phone() {
            return contact_phone;
        }

        public Object getContact_whats_up() {
            return contact_whats_up;
        }

        public Object getVat_number() {
            return vat_number;
        }

        public Object getComerical_number() {
            return comerical_number;
        }

        public int getLatitude() {
            return latitude;
        }

        public int getLongitude() {
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

        public class City{
            public int user_id;
            public int city_id;
            public City city;
            public int id;
            public String title;

            public int getUser_id() {
                return user_id;
            }

            public int getCity_id() {
                return city_id;
            }

            public City getCity() {
                return city;
            }

            public int getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }
        }
    }


}
