package com.africastalking.leta;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyMealsAdapter extends RecyclerView.Adapter<MyMealsAdapter.ViewHolder> {
    Dialog mDialog;
    public List<MyMeals> MyMealsList;
    public Context context;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    public MyMealsAdapter(List<MyMeals> MyMealsList){
        this.MyMealsList = MyMealsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_meals_layout,viewGroup,false);
        context = viewGroup.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName,itemDesc,itemPrice,removeItem,restaurantName;
        public Button moreButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.my_meals_item_name);
            itemDesc = itemView.findViewById(R.id.my_meals_item_desc);
            itemPrice = itemView.findViewById(R.id.my_meals_item_price);
            removeItem = itemView.findViewById(R.id.my_meals_txtclose);
            restaurantName = itemView.findViewById(R.id.my_meals_restaurant_name);
            moreButton = itemView.findViewById(R.id.my_meals_moreBtn);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        MyMeals myMeals = MyMealsList.get(position);
        final String itemName = myMeals.getItem_name();
        final String itemDesc = myMeals.getItem_desc();
        final Long itemPrice = myMeals.getItem_price();
        final String itemId = myMeals.getItem_id();

        //set values to layout
        viewHolder.itemName.setText(itemName);
        viewHolder.itemDesc.setText(itemDesc);
        viewHolder.itemPrice.setText(itemPrice.toString());

    }

    @Override
    public int getItemCount() {
        return MyMealsList.size();
    }
}
