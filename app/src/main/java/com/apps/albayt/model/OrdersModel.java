package com.apps.albayt.model;

import java.io.Serializable;
import java.util.List;

public class OrdersModel extends StatusResponse implements Serializable {
    private List<OrderModel> data;

    public List<OrderModel> getData() {
        return data;
    }

    public static class Data implements Serializable {
        private List<OrderModel> orders;

        public List<OrderModel> getOrders() {
            return orders;
        }

    }
}
