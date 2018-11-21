package com.africastalking.leta;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<CartItem> mList;
    CartItemsAdapter(Context context, ArrayList<CartItem> list){
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public CartItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.single_cart_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return  viewHolder;
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
        CartItem cartItem = mList.get(position);
        viewHolder.itemName.setText(cartItem.getItemName());
        viewHolder.itemPrice.setText("KSH 200");
        //viewHolder.itemQuatity.setText("dummy");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
