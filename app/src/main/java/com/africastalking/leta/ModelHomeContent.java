package com.africastalking.leta;

public class ModelHomeContent {
    private int image;
    private String name, restaurant, price;

    public ModelHomeContent(int image, String name, String restaurant, String price) {
        this.image = image;
        this.name = name;
        this.restaurant = restaurant;
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
