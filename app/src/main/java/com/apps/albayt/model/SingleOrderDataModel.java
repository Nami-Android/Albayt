package com.apps.albayt.model;

import java.io.Serializable;

public class SingleOrderDataModel extends StatusResponse implements Serializable {
    private OrderModel data;

    public OrderModel getData() {
        return data;
    }
}
