package com.apps.albayt.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProductModel implements Serializable {
    private String id;
    private String title;
    private String desc;
    private String specification;
    private String color;
    private String price;
    private String image;
    private ParentCategory parent_category;
    private ChildCategory child_category;
    private SubChildCategory sub_child_category;
    private List<ProductGalary> product_applications;
    private List<ProductGalary> product_certifictions;
    private List<ProductGalary> product_galaries;
    private int amount = 0;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getSpecification() {
        return specification;
    }

    public String getColor() {
        return color;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public ParentCategory getParent_category() {
        return parent_category;
    }

    public ChildCategory getChild_category() {
        return child_category;
    }

    public SubChildCategory getSub_child_category() {
        return sub_child_category;
    }

    public List<ProductGalary> getProduct_applications() {
        return product_applications;
    }

    public List<ProductGalary> getProduct_certifictions() {
        return product_certifictions;
    }

    public List<ProductGalary> getProduct_galaries() {
        return product_galaries;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public static class ParentCategory implements Serializable{
        private String product_id;
        private String category_id;
        private CategoryModel category;

        public String getProduct_id() {
            return product_id;
        }

        public String getCategory_id() {
            return category_id;
        }

        public CategoryModel getCategory() {
            return category;
        }
    }
    public static class ChildCategory implements Serializable{
        private String product_id;
        private String category_id;
        private CategoryModel category;

        public String getProduct_id() {
            return product_id;
        }

        public String getCategory_id() {
            return category_id;
        }

        public CategoryModel getCategory() {
            return category;
        }
    }
    public static class SubChildCategory implements Serializable{
        private String product_id;
        private String category_id;
        private CategoryModel category;

        public String getProduct_id() {
            return product_id;
        }

        public String getCategory_id() {
            return category_id;
        }

        public CategoryModel getCategory() {
            return category;
        }
    }


    public static class ProductGalary implements Serializable{
        private int id;
        private int product_id;
        private String image;
        private String image_name;

        public int getId() {
            return id;
        }

        public int getProduct_id() {
            return product_id;
        }

        public String getImage() {
            return image;
        }

        public String getImage_name() {
            return image_name;
        }
    }
}
