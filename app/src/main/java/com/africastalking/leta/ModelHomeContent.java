package com.africastalking.leta;

public class ModelHomeContent {
    public String item_name;
    public String thumb_image;
    public String image_url;
    public String item_desc;
    public String item_id;
    public Long item_price;
    public Long total_items;

    public ModelHomeContent(String item_name, String thumb_image, String image_url, String item_desc, String item_id, Long item_price, Long total_items) {
        this.item_name = item_name;
        this.thumb_image = thumb_image;
        this.image_url = image_url;
        this.item_desc = item_desc;
        this.item_id = item_id;
        this.item_price = item_price;
        this.total_items = total_items;
    }

    public ModelHomeContent() {
    }

    public Long getItem_price() {
        return item_price;
    }

    public void setItem_price(Long item_price) {
        this.item_price = item_price;
    }

    public Long getTotal_items() {
        return total_items;
    }

    public void setTotal_items(Long total_items) {
        this.total_items = total_items;
    }

    public String getItem_id(){
        return  item_id;
    }

    public void setItem_id(String item_id){
        this.item_id = item_id;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }


    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }
}
