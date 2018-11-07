package com.africastalking.leta;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyMealsFragment extends Fragment {
    private Button testBtn;


    public MyMealsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_meals,container, false);
        testBtn = view.findViewById(R.id.test_button);
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testAddItemActivity = new Intent(getActivity().getApplicationContext(),NewItemActivity.class);
                startActivity(testAddItemActivity);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

}
