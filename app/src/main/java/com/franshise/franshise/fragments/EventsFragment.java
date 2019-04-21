package com.franshise.franshise.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import com.franshise.franshise.activites.ShowEvents;
import com.franshise.franshise.adapters.EventsAdapter;
import com.franshise.franshise.adapters.MarkerAdapter;
import com.franshise.franshise.models.ResultNetworkModels.EventsModelResults;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.EventsModel;
import com.franshise.franshise.viewmodels.EventsViewModel;
import com.franshise.franshise.viewmodels.LoginViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment implements EventsAdapter.Click {
   RecyclerView events_recycle;
EventsAdapter eventsAdapter;
    public EventsFragment() {
        // Required empty public constructor
    }
EventsViewModel eventsViewModel;
//
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_events, container, false);
        events_recycle=view.findViewById(R.id.events_recycle);

        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
        events_recycle.setLayoutManager(mLayoutManager1);
        eventsViewModel = ViewModelProviders.of(this).get(EventsViewModel.class);
            Bundle bundle=getArguments();
                eventsViewModel.conferances(new SharedPrefrenceModel(getActivity()).getLanguage()).observe(this, new Observer<EventsModelResults>() {
                    @Override
                    public void onChanged(@Nullable EventsModelResults eventsModelResults) {
                        eventsAdapter=new EventsAdapter(getActivity(),EventsFragment.this::onclick,eventsModelResults.getData());
                        events_recycle.setAdapter(eventsAdapter);
                    }
                });

        return view;
    }

    @Override
    public void onclick(EventsModel eventsViewModel) {

        Intent intent=new Intent(getActivity(), ShowEvents.class);
        intent.putExtra("eventModel",eventsViewModel);
        startActivity(intent);
    }
}
