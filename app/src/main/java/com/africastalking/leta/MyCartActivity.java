package com.africastalking.leta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MyCartActivity extends AppCompatActivity {
    private RecyclerView cartItemsRecyclerView;
    ArrayList<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        cartItems = new ArrayList<>();
        cartItems.add(new CartItem("Ugali Matumbo", "Kibanda1", 300,3));
        cartItems.add(new CartItem("Skuma Skuma", "Kibanda2", 300,2));

        cartItemsRecyclerView = findViewById(R.id.cart_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager cartItemsLinearLayourManager = linearLayoutManager;
        cartItemsRecyclerView.setLayoutManager(cartItemsLinearLayourManager);

        CartItemsAdapter cartItemsAdapter = new CartItemsAdapter(this,cartItems);
        cartItemsRecyclerView.setAdapter(cartItemsAdapter);
    }
}
