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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

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
        final String currentUserId = firebaseAuth.getCurrentUser().getUid();

        final String itemName = modelHomeContent.getItem_name();
        viewHolder.item_name.setText(itemName);

        final String thumbImageUrl = modelHomeContent.getThumb_image();
        final String imageUrl = modelHomeContent.getImage_url();

        viewHolder.setItemImage(imageUrl,thumbImageUrl);

        final String itemDescription = modelHomeContent.getItem_desc();
        viewHolder.item_desc.setText(itemDescription);

        final String itemId = modelHomeContent.getItem_id();



        viewHolder.viewItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mDialog = new Dialog(v.getContext());
                final ImageView singleItemImage,favImageView;
                TextView txtclose,singleItemName,singleItemDesc;
                Button btnAddToCart;
                mDialog.setContentView(R.layout.item_popup);
                txtclose =(TextView) mDialog.findViewById(R.id.txtclose);
                txtclose.setText("X");

                singleItemImage = mDialog.findViewById(R.id.single_item_image);
                singleItemName = mDialog.findViewById(R.id.single_item_name);
                singleItemDesc = mDialog.findViewById(R.id.single_item_desc);
                favImageView = mDialog.findViewById(R.id.fav_image_btn);

                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.drawable.asdf);

                Glide.with(context).applyDefaultRequestOptions(requestOptions).load(imageUrl).thumbnail(
                        Glide.with(context).load(thumbImageUrl)
                ).into(singleItemImage);

                singleItemName.setText(itemName);
                singleItemDesc.setText(itemDescription);

                //set favourite button color
                firebaseFirestore.collection(currentUserId).document(itemId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        if (documentSnapshot.exists()){
                            favImageView.setImageDrawable(v.getContext().getDrawable(R.mipmap.ic_fav));
                        }else {
                            favImageView.setImageDrawable(v.getContext().getDrawable(R.mipmap.ic_star));
                        }
                    }
                });

                favImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        firebaseFirestore.collection(currentUserId).document(itemId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                                if (!documentSnapshot.exists()){
                                    Map<String, Object>myMealsMap = new HashMap<>();
                                    myMealsMap.put("item_id", itemId);

                                    firebaseFirestore.collection(currentUserId).document(itemId).set(myMealsMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            favImageView.setImageDrawable(v.getContext().getDrawable(R.mipmap.ic_fav));
                                            Toast.makeText(v.getContext(), itemName+ " Added To My Meals",Toast.LENGTH_LONG).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(v.getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                                        }
                                    });

                                }else {
                                    favImageView.setImageDrawable(v.getContext().getDrawable(R.mipmap.ic_star));
                                    Toast.makeText(v.getContext(), itemName+ " Removed From My Meals",Toast.LENGTH_LONG).show();
//                                    firebaseFirestore.collection(currentUserId).document(itemId)
//                                            .delete()
//                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void aVoid) {
//                                                    favImageView.setImageDrawable(v.getContext().getDrawable(R.mipmap.ic_star));
//                                                    Toast.makeText(v.getContext(), itemName+ " Removed From My Meals",Toast.LENGTH_LONG).show();
//                                                }
//                                            })
//                                            .addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                Toast.makeText(v.getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
//                                            }
//                                    });
                                }
                            }
                        });
                    }
                });

                final ElegantNumberButton addQuantityButton = mDialog.findViewById(R.id.addQuantityBtn);

                addQuantityButton.setOnClickListener(new ElegantNumberButton.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String num = addQuantityButton.getNumber();
                    }
                });
                btnAddToCart  = (Button) mDialog.findViewById(R.id.btnAddToCart);

                btnAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        Map<String, Object> imagesMap = new HashMap<>();
                        imagesMap.put("item_name",itemName);
                        imagesMap.put("restaurant_name", "Kibanda");
                        imagesMap.put("quantity", 5);
                        imagesMap.put("total_price", 300);
                        imagesMap.put("item_id", itemId);
                        imagesMap.put("timestamp", FieldValue.serverTimestamp());

                        firebaseFirestore.collection(currentUserId+"_Cart").add(imagesMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {

                                if(task.isSuccessful()){
                                    mDialog.dismiss();
                                    Toast.makeText(v.getContext(), itemName + " Added To Cart", Toast.LENGTH_LONG).show();

                                } else {
                                    mDialog.dismiss();
                                    Toast.makeText(v.getContext(), task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }


                            }
                        });

                    }
                });

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
