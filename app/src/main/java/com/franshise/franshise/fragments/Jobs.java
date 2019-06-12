package com.franshise.franshise.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.franshise.franshise.R;
import com.franshise.franshise.activites.JobsInfo;
import com.franshise.franshise.activites.ShowEvents;
import com.franshise.franshise.adapters.EventsAdapter;
import com.franshise.franshise.adapters.JobAdapter;
import com.franshise.franshise.adapters.ServicesAdapter;
import com.franshise.franshise.models.ResultNetworkModels.EventsModelResults;
import com.franshise.franshise.models.ResultNetworkModels.JobsResults;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.EventsModel;
import com.franshise.franshise.models.dataModels.JobModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.EventsViewModel;
public class Jobs extends Fragment implements JobAdapter.Click {
    RecyclerView events_recycle;
    JobAdapter eventsAdapter;
    public Jobs() {
        // Required empty public constructor
    }
    EventsViewModel eventsViewModel;
    //
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_jobs, container, false);
        events_recycle=view.findViewById(R.id.events_recycle);

        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
        events_recycle.setLayoutManager(mLayoutManager1);
        eventsViewModel = ViewModelProviders.of(this).get(EventsViewModel.class);
        Bundle bundle=getArguments();
        CustomProgressDialog.showProgress(getActivity());
        eventsViewModel.get_job(new SharedPrefrenceModel(getActivity()).getLanguage()).observe(this, new Observer<JobsResults>() {
            @Override
            public void onChanged(@Nullable JobsResults eventsModelResults) {
                CustomProgressDialog.clodseProgress();
                eventsAdapter=new JobAdapter(getActivity(),Jobs.this::onclick,eventsModelResults.data);
                events_recycle.setAdapter(eventsAdapter);
            }
        });
        return view;
    }


    @Override
    public void onclick(JobModel jobModel) {
        Intent intent=new Intent(getActivity(), JobsInfo.class);
        intent.putExtra("job",jobModel);
        intent.putExtra("type",3);
        startActivity(intent);
    }
}