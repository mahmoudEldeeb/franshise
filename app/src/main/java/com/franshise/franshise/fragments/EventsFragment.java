package com.franshise.franshise.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.franshise.franshise.R;
import com.franshise.franshise.adapters.EventsAdapter;
import com.franshise.franshise.adapters.MarkerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {
   RecyclerView events_recycle;
EventsAdapter eventsAdapter;
    public EventsFragment() {
        // Required empty public constructor
    }

//
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_events, container, false);
        events_recycle=view.findViewById(R.id.events_recycle);
        eventsAdapter=new EventsAdapter(getActivity());
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
        events_recycle.setLayoutManager(mLayoutManager1);
        events_recycle.setAdapter(eventsAdapter);

        return view;
    }

}
