package com.apps.albayt.model;

import java.io.Serializable;

public class NewsModel implements Serializable {
    private String id;
    private String title;
    private String desc;
    private String image;
    private String created_at;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getImage() {
        return image;
    }

    public String getCreated_at() {
        return created_at;
    }
}
