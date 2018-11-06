package com.africastalking.leta;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private RecyclerView homeRV;
    ArrayList<ModelHomeContent> foodsList;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home,container, false);
        foodsList = new ArrayList<>();
        foodsList.add(new ModelHomeContent(R.drawable.bg1,"Managu Mingi","Kibandaski", "KSH: 200"));
        foodsList.add(new ModelHomeContent(R.drawable.bg,"Beans Chapo","Kibandaski", "KSH: 250"));
        foodsList.add(new ModelHomeContent(R.drawable.bg,"Beans Mchele","Kibandaski", "KSH: 150"));
        foodsList.add(new ModelHomeContent(R.drawable.bg,"Beans Chapo","Kibandaski", "KSH: 250"));

        homeRV = view.findViewById(R.id.homeRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager((container.getContext()));
        RecyclerView.LayoutManager homeLinearLayoutManager = layoutManager;
        homeRV.setLayoutManager(homeLinearLayoutManager);

        homeContentAdapter homeContentAdapter = new homeContentAdapter(container.getContext(), foodsList);
        homeRV.setAdapter(homeContentAdapter);

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
