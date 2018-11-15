package com.africastalking.leta;

public class ModelHomeContent {
    public String item_name;
    public String thumb_image;
    public String image_url;

    public ModelHomeContent(String item_name, String thumb_image, String image_url) {
        this.item_name = item_name;
        this.thumb_image = thumb_image;
        this.image_url = image_url;
    }


    public ModelHomeContent() {
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
