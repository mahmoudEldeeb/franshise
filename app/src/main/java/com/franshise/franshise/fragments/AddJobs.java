package com.franshise.franshise.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.activites.CompleteProcess;
import com.franshise.franshise.activites.Pay;
import com.franshise.franshise.activites.RegisterActivity;
import com.franshise.franshise.interfaces.AddFranchiseData;
import com.franshise.franshise.models.ResultNetworkModels.DataResult;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.viewmodels.AddJobsViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddJobs extends Fragment {



    LinearLayout spaceParent,spaceParent2;
    TextView re,re2;
    public AddJobs() {
        // Required empty public constructor
    }
    AddJobsViewModel  addJobsViewModel;

Spinner job_coountry,job_qualification;
List<Integer>countryIds=new ArrayList<>();
    List<Integer>qualificationIds=new ArrayList<>();
    List<String>cityList=new ArrayList<>();
    List<Integer>cityIds=new ArrayList<>();
    List<Integer>selectedcityIds=new ArrayList<>();
    LayoutInflater layoutInflater;
    Observer<DataResult>cityObserver;
    EditText job_number,job_name,job_description,job_vacancy,sallary_from,sallary_to,sallary_currency;

    Button add_city,add_job;
    int type=-1;
    RadioGroup  user_type;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
View view=inflater.inflate(R.layout.fragment_add_jobs, container, false);
        addJobsViewModel= ViewModelProviders.of(this).get(AddJobsViewModel.class);
        job_coountry=view.findViewById(R.id.job_coountry);
        job_qualification=view.findViewById(R.id.job_qualification);
        spaceParent=view.findViewById(R.id.spaceParent);
        add_city=view.findViewById(R.id.add_city);
        user_type=view.findViewById(R.id.user_type);
        add_job=view.findViewById(R.id.add_job);
        job_number=view.findViewById(R.id.job_number);
        job_name=view.findViewById(R.id.job_name);
        job_description=view.findViewById(R.id.job_description);

        job_vacancy=view.findViewById(R.id.job_vacancy);
        sallary_from=view.findViewById(R.id.sallary_from);
        sallary_to=view.findViewById(R.id.sallary_to);
        sallary_currency=view.findViewById(R.id.sallary_currency);

        add_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addElement2();
            }
        });
        layoutInflater=inflater;

        List<String> list=new ArrayList<>();
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        job_coountry.setAdapter(adp1);

        List<String> qualificatiolist=new ArrayList<>();
        ArrayAdapter<String> adp2= new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, qualificatiolist);
        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        job_qualification.setAdapter(adp2);

        user_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.male:type=0;break;
                    case R.id.female:type=1;break;
                    case R.id.no_preference:type=2;
                }
            }
        });
        cityObserver=new Observer<DataResult>() {
            @Override
            public void onChanged(@Nullable DataResult result) {
                cityList.clear();
                cityIds.clear();
                spaceParent.removeAllViews();

                for(int i=0;i<result.getData().size();i++) {
                    cityIds.add(result.getData().get(i).getId());
                    if (new SharedPrefrenceModel(getActivity()).getLanguage().equals("en")) {
                        cityList.add(result.getData().get(i).getEn_name()) ;
                    } else {
                        cityList.add(result.getData().get(i).getEn_name()) ;

                    }
                }
                addElement2();
            }
        };
        addJobsViewModel.getCountries(getActivity()).observe(this, new Observer<DataResult>() {
            @Override
            public void onChanged(@Nullable DataResult result) {
               // addJobsViewModel
                addJobsViewModel.city_with_country(result.getData().get(0).getId()).observe(AddJobs.this,cityObserver);
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
        addJobsViewModel.getQualification(getActivity()).observe(this, new Observer<DataResult>() {
            @Override
            public void onChanged(@Nullable DataResult result) {
                for(int i=0;i<result.getData().size();i++) {
                    qualificationIds.add(result.getData().get(i).getId());
                    if (new SharedPrefrenceModel(getActivity()).getLanguage().equals("en")) {
                        qualificatiolist.add(result.getData().get(i).getEn_name()) ;
                    } else {
                        qualificatiolist.add(result.getData().get(i).getEn_name()) ;

                    }
                }
                adp2.notifyDataSetChanged();

            }
        });
job_coountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        addJobsViewModel.city_with_country(countryIds.get(position)).observe(AddJobs.this,cityObserver);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});
add_job.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        creatJob();
    }
});
        return view;
    }

    private void creatJob() {
        if(job_name.getText().toString().isEmpty()||job_number.getText().toString().isEmpty()||job_description.getText().toString().isEmpty()
        ||job_vacancy.getText().toString().isEmpty()||sallary_from.getText().toString().isEmpty()||sallary_to.getText().toString().isEmpty()
        ||type==-1||countryIds.size()==0||cityIds.size()==0||qualificationIds.size()==0){

            Toast.makeText(getActivity(),getResources().getString(R.string.fill_data),Toast.LENGTH_LONG).show();
        }
        else {
            getCitiesIds();
  }
    }

    private void getCitiesIds() {
        selectedcityIds.clear();
        for(int i=0;i<spaceParent.getChildCount();i++) {
            View v2 = spaceParent.getChildAt(i);
            Spinner s = v2.findViewById(R.id.value);
            selectedcityIds.add(cityIds.get(s.getSelectedItemPosition()));
        }
        if(selectedcityIds.size()==spaceParent.getChildCount())
        {
            addJobsViewModel.createJob(job_name.getText().toString(),Integer.parseInt(job_number.getText().toString()),qualificationIds.get(job_qualification.getSelectedItemPosition())
                    ,job_description.getText().toString(),countryIds.get(job_coountry.getSelectedItemPosition()),selectedcityIds,Integer.parseInt(sallary_from.getText().toString()),
                    Integer.parseInt(sallary_to.getText().toString()),type,sallary_currency.getText().toString(),Integer.parseInt(job_vacancy.getText().toString()))
                    .observe(this, new Observer<StatusModel>() {
                        @Override
                        public void onChanged(@Nullable StatusModel statusModel) {
                            if(statusModel.getStatus()==1){
                                Toast.makeText(getActivity(),statusModel.getMessage(),Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getActivity(), CompleteProcess.class));
                               getActivity().finish();
                            }
                            else Toast.makeText(getActivity(),statusModel.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }


    private void addElement2() {
        View v3=layoutInflater.inflate(R.layout.city_model, spaceParent, false);
        re2=v3.findViewById(R.id.re);
        ImageButton delete=v3.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View v2=spaceParent.getChildAt( spaceParent.indexOfChild(v3));
                spaceParent.removeView(v2);
            }
        });

        Spinner spinner=v3.findViewById(R.id.value);
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, cityList);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp1);
        //re2.setText("Country ");
        spaceParent.addView(v3,spaceParent.getChildCount());

    }
}
