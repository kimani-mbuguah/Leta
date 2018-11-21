package com.africastalking.leta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class MyCartActivity extends AppCompatActivity {
    private RecyclerView cartItemsRecyclerView;
    private List<CartItem> cartItems;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private CartItemsAdapter cartItemsAdapter;
    private Button btnContinueToCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        cartItems = new ArrayList<>();
        cartItemsRecyclerView = findViewById(R.id.cart_rv);
        cartItemsAdapter = new CartItemsAdapter(cartItems);
        cartItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartItemsRecyclerView.setAdapter(cartItemsAdapter);
        cartItemsRecyclerView.setHasFixedSize(true);

        if(firebaseAuth.getCurrentUser() != null) {
            Query firstQuery = firebaseFirestore.collection("Menu").orderBy("timestamp", Query.Direction.DESCENDING);
            firstQuery.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                    if (e != null) {
                       // Toast.makeText(this,"Listen Failed", Toast.LENGTH_LONG).show();
                        //Toast.makeText(this,"Listen failed",Toast.LENGTH_LONG).show();
                        //Log.w(TAG, "Listen failed.", e);
                        return;
                    }


                    if(!queryDocumentSnapshots.isEmpty()){


                        for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){

                            switch (doc.getType()) {
                                case ADDED:
                                    String foodItemId = doc.getDocument().getId();
                                    CartItem cartItem  = doc.getDocument().toObject(CartItem.class);
                                    cartItems.add(cartItem);
                                    cartItemsAdapter.notifyDataSetChanged();
                                    break;
                                case MODIFIED:
                                   // Toast.makeText(this,"Modified menu",Toast.LENGTH_LONG).show();
                                    break;
                                case REMOVED:
                                    //Toast.makeText(view.getContext(),"Removed from menu",Toast.LENGTH_LONG).show();
                                    break;
                            }

                        }
                    }
                }
            });
        }


    }
}
