package com.apps.albayt.model;

import java.io.Serializable;
import java.util.List;

public class CategoryModel implements Serializable {
    private String id;
    private String title;
    private String image;
    private boolean selected;
    private List<CategoryModel> sub_categories;

    public CategoryModel(String id, String title) {
        this.id = id;
        this.title= title;

    }

    public String getId() {
        return id;
    }


    public String getImage() {
        return image;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getTitle() {
        return title;
    }

    public List<CategoryModel> getSub_categories() {
        return sub_categories;
    }
}
