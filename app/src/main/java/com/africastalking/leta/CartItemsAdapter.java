package com.africastalking.leta;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.ViewHolder> {
    public List<CartItem> CartItemsList;
    public Context context;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;


    public CartItemsAdapter(List<CartItem> CartItemsList){
        this.CartItemsList = CartItemsList;
    }

    @NonNull
    @Override
    public CartItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_cart_item,viewGroup,false);
        context = viewGroup.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName,restautantName,itemPrice,itemQuatity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.cart_item_name);
            itemPrice = itemView.findViewById(R.id.cart_item_price);
            //itemQuatity = itemView.findViewById(R.id.cart_item_quantity);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemsAdapter.ViewHolder viewHolder, int position) {
        CartItem cartItem = CartItemsList.get(position);
        viewHolder.itemName.setText(cartItem.getItem_name());
       // viewHolder.itemPrice.setText("KSH 200");
        //viewHolder.itemQuatity.setText("dummy");
    }

    @Override
    public int getItemCount() {
        return CartItemsList.size();
    }
}
