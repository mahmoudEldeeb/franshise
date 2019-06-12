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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.activites.ShowEvents;
import com.franshise.franshise.adapters.CourseAdapter;
import com.franshise.franshise.adapters.CoursesAdapter;
import com.franshise.franshise.adapters.EventsAdapter;
import com.franshise.franshise.models.ResultNetworkModels.DataResult;
import com.franshise.franshise.models.ResultNetworkModels.EventsModelResults;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.DataModel;
import com.franshise.franshise.models.dataModels.EventsModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.EventsViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrainingFragment extends Fragment  implements EventsAdapter.Click {
    RecyclerView events_recycle;
    CoursesAdapter coursesAdapter;
Spinner country_spinner;
LayoutInflater layoutInflater;
LinearLayout spaceParent;
    public TrainingFragment() {
        // Required empty public constructor
    }

    EventsViewModel eventsViewModel;
    List<Integer> countryIds=new ArrayList<>();
    List<DataModel>countryDat=new ArrayList<>();
    //
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_training, container, false);
        events_recycle = view.findViewById(R.id.events_recycle);
        layoutInflater=inflater;
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
        events_recycle.setLayoutManager(mLayoutManager1);
        country_spinner=view.findViewById(R.id.country_spinner);
        spaceParent=view.findViewById(R.id.spaceParent);
        List<String> list=new ArrayList<>();
        list.add(getResources().getString(R.string.all));
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country_spinner.setAdapter(adp1);

        eventsViewModel = ViewModelProviders.of(this).get(EventsViewModel.class);
        Bundle bundle = getArguments();
        eventsViewModel.getCountries().observe(this, new Observer<DataResult>() {
    @Override
    public void onChanged(@Nullable DataResult result) {
        countryDat=result.getData();
        for(int i=0;i<result.getData().size();i++) {

            countryIds.add(result.getData().get(i).getId());
            if (new SharedPrefrenceModel(getActivity()).getLanguage().equals("en")) {
                list.add(result.getData().get(i).getEn_name()) ;
            } else {
                list.add(result.getData().get(i).getEn_name()) ;
            }
        }
        adp1.notifyDataSetChanged();

    }
});
country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position!=0)
        getcourses(countryIds.get(position-1));
        else getcourses(0);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});
//getcourses(0);

        return view;
    }
int month=-1;
    private void getcourses(int i) {
month=-1;
        spaceParent.removeAllViews();
       CustomProgressDialog.showProgress(getActivity());
       if(coursesAdapter!=null)
           coursesAdapter.clear();
        eventsViewModel.courses(new SharedPrefrenceModel(getActivity()).getLanguage(),i,getActivity()).observe(this, new Observer<EventsModelResults>() {
            @Override
            public void onChanged(@Nullable EventsModelResults eventsModelResults) {
                Log.v("eeeeeeee",eventsModelResults.getData().size()+"");
                CustomProgressDialog.clodseProgress();
                if(eventsModelResults.getData().size()>0){

                TreeSet<Integer> hashSetDate=new TreeSet<>();
              /*  for (int i=0;i<eventsModelResults.getData().size();i++){
                    String month1=eventsModelResults.getData().get(i).getDate().substring(5,7);


                    Log.v("aaaaaaa",month1+"   "+month);
                    hashSetDate.add(Integer.parseInt(month1));

                }*/
                  /*  Iterator iterator = hashSetDate.iterator();

                    for(int i=0;i<hashSetDate.size();i++){
                    while (iterator.hasNext()){
                        addElement((Integer) iterator.next());
                        for(int j=0;j<eventsModelResults.getData().size();j++){
                            String month1=eventsModelResults.getData().get(i).getDate().substring(5,7);

                        }
                    }
                    //addElement(0);

                }*/
                coursesAdapter = new CoursesAdapter(getActivity(), TrainingFragment.this::onclick,
                        eventsModelResults.getData(),countryDat);
                events_recycle.setHasFixedSize(true);
                events_recycle.setAdapter(coursesAdapter);
            }
            }
        });

    }


    @Override
    public void onclick(EventsModel eventsViewModel) {

        Intent intent = new Intent(getActivity(), ShowEvents.class);
        intent.putExtra("eventModel", eventsViewModel);
        intent.putExtra("type",2);

        startActivity(intent);
    }
}
