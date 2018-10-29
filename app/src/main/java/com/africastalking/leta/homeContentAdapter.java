package com.africastalking.leta;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class homeContentAdapter extends RecyclerView.Adapter<homeContentAdapter.ViewHolder> {
    private Context mContext;
    private  ArrayList<ModelHomeContent>mList;
    homeContentAdapter(Context context, ArrayList<ModelHomeContent> list){
        mContext = context;
        mList = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.home_rv_items,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ModelHomeContent modelHomeContent = mList.get(position);
        ImageView image = viewHolder.item_image;
        TextView name,price,restaurant;
        name = viewHolder.item_name;
        price = viewHolder.item_price;
        restaurant = viewHolder.restaurant_name;
        image.setImageResource(modelHomeContent.getImage());
        name.setText(modelHomeContent.getName());
        price.setText(modelHomeContent.getPrice());
        restaurant.setText(modelHomeContent.getRestaurant());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView item_image;
        TextView item_name,item_price,restaurant_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_image = itemView.findViewById(R.id.item_image);
            item_name = itemView.findViewById(R.id.item_name);
            item_price = itemView.findViewById(R.id.item_price);
            restaurant_name = itemView.findViewById(R.id.restaurant_name);
        }
    }
}
