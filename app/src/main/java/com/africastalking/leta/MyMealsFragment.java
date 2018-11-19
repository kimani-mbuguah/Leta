package com.africastalking.leta;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
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
public class MyMealsFragment extends Fragment {
    private RecyclerView myMealsRV;
    private List<MyMeals> myMealsList;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private MyMealsAdapter myMealsAdapter;


    public MyMealsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_my_meals,container, false);
        // Inflate the layout for this fragment

        myMealsList = new ArrayList<>();


        myMealsRV = view.findViewById(R.id.my_meals_recyclerview);
        firebaseAuth = FirebaseAuth.getInstance();

        final String currentUserId = firebaseAuth.getCurrentUser().getUid();

        myMealsAdapter = new MyMealsAdapter(myMealsList);
        myMealsRV.setLayoutManager(new LinearLayoutManager(container.getContext()));
        myMealsRV.setAdapter(myMealsAdapter);
        myMealsRV.setHasFixedSize(true);

        if (firebaseAuth.getCurrentUser() != null){
            firebaseFirestore = FirebaseFirestore.getInstance();

            Query firstQuery = firebaseFirestore.collection(currentUserId).orderBy("timestamp", Query.Direction.DESCENDING);
            firstQuery.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                    if (e != null) {
                        Toast.makeText(view.getContext(),"Listen failed",Toast.LENGTH_LONG).show();
                        //Log.w(TAG, "Listen failed.", e);
                        return;
                    }


                    if(!queryDocumentSnapshots.isEmpty()){


                        for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){

                            switch (doc.getType()) {
                                case ADDED:
                                    String foodItemId = doc.getDocument().getId();
                                    MyMeals myMeals = doc.getDocument().toObject(MyMeals.class);
                                    myMealsList.add(myMeals);
                                    myMealsAdapter.notifyDataSetChanged();
                                    break;
                                case MODIFIED:
                                    Toast.makeText(view.getContext(),"Modified menu",Toast.LENGTH_LONG).show();
                                    break;
                                case REMOVED:
                                    Toast.makeText(view.getContext(),"Removed from menu",Toast.LENGTH_LONG).show();
                                    break;
                            }

                        }
                    }
                }
            });
        }
        return view;
    }

}
