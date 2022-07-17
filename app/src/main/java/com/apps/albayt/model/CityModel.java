package com.apps.albayt.model;

import java.io.Serializable;

public class CityModel implements Serializable {
    private String id;
    private String title;

    public CityModel(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
