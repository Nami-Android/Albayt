package com.apps.albayt.model;

import java.io.Serializable;
import java.util.List;

public class SupplierDataModel extends StatusResponse implements Serializable {
    private List<UserModel.Data> data;

    public List<UserModel.Data> getData() {
        return data;
    }
}
