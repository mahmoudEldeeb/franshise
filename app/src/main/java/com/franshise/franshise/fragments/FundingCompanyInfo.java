package com.franshise.franshise.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.franshise.franshise.R;
import com.franshise.franshise.adapters.FacouriteAdapter;
import com.franshise.franshise.adapters.FundingCompanyAdapter;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.FundingCompanyMode;
import com.franshise.franshise.models.database.FavModel;
import com.franshise.franshise.viewmodels.FavViewModel;
import com.franshise.franshise.viewmodels.FundingCompanyViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FundingCompanyInfo extends Fragment {


    public FundingCompanyInfo() {
        // Required empty public constructor
    }
FundingCompanyViewModel fundingCompanyViewModel;
RecyclerView res_fund;
FundingCompanyAdapter fundingCompanyAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_funding_company_info, container, false);
        res_fund=view.findViewById(R.id.res_fund);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        res_fund.setLayoutManager(mLayoutManager);
        fundingCompanyViewModel= ViewModelProviders.of(this).get(FundingCompanyViewModel.class);

        fundingCompanyViewModel.getCompanyies(getActivity()).observe(this, new Observer<List<FundingCompanyMode>>() {
            @Override
            public void onChanged(@Nullable List<FundingCompanyMode> fundingCompanyModes) {
                fundingCompanyAdapter=new FundingCompanyAdapter(getActivity(),fundingCompanyModes);
                res_fund.setAdapter(fundingCompanyAdapter);
                Log.v("eeeeeee",fundingCompanyModes.get(0).getAr_details());
            }
        });
        return view;
    }

}
