package com.apps.albayt.model;

import java.io.Serializable;
import java.util.List;

public class CityDataModel extends StatusResponse implements Serializable {
    private List<CityModel> data;

    public List<CityModel> getData() {
        return data;
    }
}
