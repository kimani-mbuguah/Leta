package com.africastalking.leta;

public class MyMeals {
    public String item_id;
    public String item_name;
    public String item_desc;
    public Long item_price;

    public MyMeals(String item_id, String item_name, String item_desc, Long item_price) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_desc = item_desc;
        this.item_price = item_price;
    }

    public MyMeals() {
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
    }

    public Long getItem_price() {
        return item_price;
    }

    public void setItem_price(Long item_price) {
        this.item_price = item_price;
    }

}
