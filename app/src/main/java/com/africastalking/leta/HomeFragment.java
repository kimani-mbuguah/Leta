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

    private DocumentSnapshot lastVisible;
    private Boolean isFirstPageFirstLoad = true;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home,container, false);
        foodsList = new ArrayList<>();

        //foodsList.add(new ModelHomeContent(R.drawable.bg,"Beans Chapo","Kibandaski", "KSH: 250"));

        homeRV = view.findViewById(R.id.homeRV);
        firebaseAuth = FirebaseAuth.getInstance();

        homeContentAdapter = new homeContentAdapter(foodsList);
        homeRV.setLayoutManager(new LinearLayoutManager(container.getContext()));
        homeRV.setAdapter(homeContentAdapter);
        homeRV.setHasFixedSize(true);

        if(firebaseAuth.getCurrentUser() != null) {
            firebaseFirestore = FirebaseFirestore.getInstance();
            homeRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    Boolean reachedBottom = !recyclerView.canScrollVertically(1);
                    
                    if (reachedBottom){
                        loadMoreItems();
                    }
                }
            });

            Query firstQuery = firebaseFirestore.collection("Menu").orderBy("timestamp", Query.Direction.DESCENDING).limit(3);
            firstQuery.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if(!queryDocumentSnapshots.isEmpty()){
                        if (isFirstPageFirstLoad){
                            lastVisible = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
                            foodsList.clear();
                        }

                        for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){
                            if (doc.getType() == DocumentChange.Type.ADDED){
                                String foodItemId = doc.getDocument().getId();
                                ModelHomeContent modelHomeContent = doc.getDocument().toObject(ModelHomeContent.class);

                                if (isFirstPageFirstLoad){
                                    foodsList.add(modelHomeContent);
                                }else {
                                    foodsList.add(0, modelHomeContent);
                                }
                            }

                            isFirstPageFirstLoad = false;
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

    private void loadMoreItems() {
        Query nextQuery = firebaseFirestore.collection("Menu").orderBy("timestamp",Query.Direction.DESCENDING).startAfter(lastVisible).limit(3);
        nextQuery.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(!queryDocumentSnapshots.isEmpty()){
                    lastVisible = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);
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

}
