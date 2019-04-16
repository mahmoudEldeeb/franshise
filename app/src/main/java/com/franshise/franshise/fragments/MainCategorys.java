package com.franshise.franshise.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.adapters.MainCategorysAdapter;
import com.franshise.franshise.models.ResultNetworkModels.BannersResult;
import com.franshise.franshise.models.ResultNetworkModels.CategorysResult;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.HomeViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainCategorys extends Fragment {
RecyclerView main_cat_recycler;
MainCategorysAdapter mainCategorysAdapter;
static  int count=0;
static CategorysResult categorysResult=new CategorysResult();
    public MainCategorys() {
        // Required empty public constructor
    }
    HomeViewModel homeViewModel;
    Observer<BannersResult> bannerObserver;
    Observer<CategorysResult>categoryObserver;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_main_categorys, container, false);

        main_cat_recycler=view.findViewById(R.id.main_cat_recycler);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),3);
        main_cat_recycler.setLayoutManager(mLayoutManager);

        homeViewModel= ViewModelProviders.of(this).get(HomeViewModel.class);

        categoryObserver = new Observer<CategorysResult>() {
            @Override
            public void onChanged(@Nullable CategorysResult result) {
                if(result.getStatus()==1) {
                    categorysResult=result;
                    mainCategorysAdapter = new MainCategorysAdapter(getActivity(),result.getData());
                    main_cat_recycler.setAdapter(mainCategorysAdapter);
                    count=1;
                }
                else Toast.makeText(getActivity(),"connection error",Toast.LENGTH_LONG).show();
CustomProgressDialog.clodseProgress();
            }
        };
        if(count==0){
        CustomProgressDialog.showProgress(getActivity());
        homeViewModel.getCategorys().observe(MainCategorys.this,categoryObserver);}
        else {
            mainCategorysAdapter = new MainCategorysAdapter(getActivity(),categorysResult.getData());
            main_cat_recycler.setAdapter(mainCategorysAdapter);
        }
        return view;

    }

}
