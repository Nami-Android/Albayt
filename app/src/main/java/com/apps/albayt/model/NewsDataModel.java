package com.apps.albayt.model;

import java.util.List;

public class NewsDataModel extends StatusResponse{
    private List<NewsModel> data;

    public List<NewsModel> getData() {
        return data;
    }
}
