package com.africastalking.leta;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private RecyclerView homeRV;
    private List<ModelHomeContent> foodsList;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private homeContentAdapter homeContentAdapter;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home,container, false);
        foodsList = new ArrayList<>();


        homeRV = view.findViewById(R.id.homeRV);
        firebaseAuth = FirebaseAuth.getInstance();

        homeContentAdapter = new homeContentAdapter(foodsList);
        homeRV.setLayoutManager(new LinearLayoutManager(container.getContext()));
        homeRV.setAdapter(homeContentAdapter);
        homeRV.setHasFixedSize(true);

        if(firebaseAuth.getCurrentUser() != null) {
            firebaseFirestore = FirebaseFirestore.getInstance();

            Query firstQuery = firebaseFirestore.collection("Menu").orderBy("timestamp", Query.Direction.DESCENDING);
            firstQuery.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if(!queryDocumentSnapshots.isEmpty()){


                        for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                            if (doc.getType() == DocumentChange.Type.ADDED){
                                String foodItemId = doc.getDocument().getId();
                                ModelHomeContent modelHomeContent = doc.getDocument().toObject(ModelHomeContent.class);

                                foodsList.add(modelHomeContent);
                                homeContentAdapter.notifyDataSetChanged();

                            }
                        }
                    }
                }
            });
        }


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.my_cart_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myCartIntent = new Intent(getActivity().getApplicationContext(),MyCartActivity.class);
                startActivity(myCartIntent);
            }
        });

        return view;
    }
}
