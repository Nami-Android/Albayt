package com.apps.albayt.model;

import java.io.Serializable;

public class ProductAmount implements Serializable {
    private String product_id;
    private int amount;

    public ProductAmount(String product_id, int amount) {
        this.product_id = product_id;
        this.amount = amount;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_d(String product_d) {
        this.product_id = product_d;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
