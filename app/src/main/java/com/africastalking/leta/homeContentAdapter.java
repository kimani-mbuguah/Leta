package com.africastalking.leta;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class homeContentAdapter extends RecyclerView.Adapter<homeContentAdapter.ViewHolder> {

    Dialog mDialog;
    public List<ModelHomeContent> homeItemsList;
    public Context context;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;




    public homeContentAdapter(List<ModelHomeContent> homeItemsList){
        this.homeItemsList = homeItemsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_rv_items,viewGroup,false);
        context = viewGroup.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        ModelHomeContent modelHomeContent = homeItemsList.get(position);
        viewHolder.setIsRecyclable(false);

        final String itemName = modelHomeContent.getItem_name();
        viewHolder.item_name.setText(itemName);

        final String thumbImageUrl = modelHomeContent.getThumb_image();
        final String imageUrl = modelHomeContent.getImage_url();

        viewHolder.setItemImage(imageUrl,thumbImageUrl);

        final String itemDescription = modelHomeContent.getItem_desc();
        viewHolder.item_desc.setText(itemDescription);



        viewHolder.viewItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog = new Dialog(v.getContext());
                ImageView singleItemImage;
                TextView txtclose,singleItemName,singleItemDesc;
                Button btnAddToCart;
                mDialog.setContentView(R.layout.item_popup);
                txtclose =(TextView) mDialog.findViewById(R.id.txtclose);
                txtclose.setText("X");

                singleItemImage = mDialog.findViewById(R.id.single_item_image);
                singleItemName = mDialog.findViewById(R.id.single_item_name);
                singleItemDesc = mDialog.findViewById(R.id.single_item_desc);

                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.drawable.asdf);

                Glide.with(context).applyDefaultRequestOptions(requestOptions).load(imageUrl).thumbnail(
                        Glide.with(context).load(thumbImageUrl)
                ).into(singleItemImage);

                singleItemName.setText(itemName);
                singleItemDesc.setText(itemDescription);
                final ElegantNumberButton addQuantityButton = mDialog.findViewById(R.id.addQuantityBtn);

                addQuantityButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String num = addQuantityButton.getNumber();
                    }
                });
                btnAddToCart  = (Button) mDialog.findViewById(R.id.btnAddToCart);
                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return homeItemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView item_image;
        public TextView item_name,item_price,restaurant_name,item_desc;
        public Button viewItemBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_image = itemView.findViewById(R.id.item_image);
            item_name = itemView.findViewById(R.id.item_name);
            item_price = itemView.findViewById(R.id.item_price);
            //restaurant_name = itemView.findViewById(R.id.restaurant_name);
            item_desc = itemView.findViewById(R.id.item_desc);
            viewItemBtn = itemView.findViewById(R.id.moreBtn);

        }

        public void setItemImage(String imageUrl, String thumbImageUrl) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.asdf);

            Glide.with(context).applyDefaultRequestOptions(requestOptions).load(imageUrl).thumbnail(
                    Glide.with(context).load(thumbImageUrl)
            ).into(item_image);

        }

    }
}
