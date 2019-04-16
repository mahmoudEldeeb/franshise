package com.franshise.franshise.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.franshise.franshise.R;
import com.franshise.franshise.interfaces.FragmentTransformer;

/**
 * A simple {@link Fragment} subclass.
 */
public class MarkerOwnerRegisterPageOne extends Fragment {


    public MarkerOwnerRegisterPageOne() {
        // Required empty public constructor
    }

Button complete_register;
    FragmentTransformer fragmentTransformer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_marker_owner_register_page_one, container, false);
        complete_register=view.findViewById(R.id.complete_register);
        fragmentTransformer= (FragmentTransformer) getActivity();
        complete_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransformer.goToNextFragment(2);
            }
        });
        return view;
    }

}
