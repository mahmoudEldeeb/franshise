package com.franshise.franshise.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.database.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.franshise.franshise.R;
import com.franshise.franshise.adapters.FacouriteAdapter;
import com.franshise.franshise.adapters.MainCategorysAdapter;
import com.franshise.franshise.models.ResultNetworkModels.FranchiseDataMODEL;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.database.FavModel;
import com.franshise.franshise.viewmodels.FavViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {
    RecyclerView favourite_recycler;
    FacouriteAdapter facouriteAdapter;
FavViewModel favViewModel;

    Observer<List<FavModel>>favModelsList;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_favourite, container, false);
        favourite_recycler=view.findViewById(R.id.favourite_recycler);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        favourite_recycler.setLayoutManager(mLayoutManager);
        favViewModel= ViewModelProviders.of(this).get(FavViewModel.class);

        favModelsList=new Observer<List<FavModel>>() {
            @Override
            public void onChanged(@Nullable List<FavModel> favModels) {
                facouriteAdapter=new FacouriteAdapter(getActivity(),favModels);
                favourite_recycler.setAdapter(facouriteAdapter);
            }
        };
        favViewModel.getFav(new SharedPrefrenceModel(getActivity()).getId()).observe(this,favModelsList);
        return view;
    }

}
