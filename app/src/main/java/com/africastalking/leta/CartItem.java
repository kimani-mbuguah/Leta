package com.africastalking.leta;

public class CartItem {
    private String itemName, restaurantName;
    private int itemPrice, itemQuantity;

    public CartItem(String itemName, String restaurantName, int itemPrice, int itemQuantity) {
        this.itemName = itemName;
        this.restaurantName = restaurantName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
    }

    public CartItem() {
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }


}
