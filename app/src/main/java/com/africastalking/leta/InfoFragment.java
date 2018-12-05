package com.africastalking.leta;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {
    private RatingBar mRatingBar;

    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_info, container, false);
        mRatingBar = view.findViewById(R.id.restaurantRatingBar);
        mRatingBar.setRating(Float.parseFloat("2.0"));
        return view;
    }

}
