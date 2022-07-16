package com.apps.albayt.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MainHomeDataModel extends StatusResponse implements Serializable {
    private MainHomeData data;

    public MainHomeData getData() {
        return data;
    }

    public static class MainHomeData implements Serializable{
        private List<SliderDataModel.SliderModel> slider;
        private List<CategoryModel> categories;
        private List<ProductModel> last_products;
        @SerializedName(value = "new",alternate = {"news"})
        private List<NewsModel> news;

        public List<SliderDataModel.SliderModel> getSlider() {
            return slider;
        }

        public List<CategoryModel> getCategories() {
            return categories;
        }

        public List<ProductModel> getLast_products() {
            return last_products;
        }

        public List<NewsModel> getNews() {
            return news;
        }
    }
}
