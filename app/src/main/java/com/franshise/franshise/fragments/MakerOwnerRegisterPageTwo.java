package com.franshise.franshise.fragments;


import android.app.AlertDialog;
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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.activites.AddFranchise;
import com.franshise.franshise.activites.MarkerOwnerRegister;
import com.franshise.franshise.activites.RegisterActivity;
import com.franshise.franshise.adapters.MainCategorysAdapter;
import com.franshise.franshise.interfaces.FragmentTransformer;
import com.franshise.franshise.models.ResultNetworkModels.BannersResult;
import com.franshise.franshise.models.ResultNetworkModels.CategorysResult;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.HomeViewModel;
import com.franshise.franshise.viewmodels.MarkerOwnerRegisterViewModel;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class MakerOwnerRegisterPageTwo extends Fragment {

MarkerOwnerRegisterViewModel markerOwnerRegisterViewModel;
Observer<StatusModel>completeRegisterObserver;
    FragmentTransformer fragmentTransformer;
    Button complete_register;
    EditText company_name,phone,fax,manager,conversion;
    Spinner cat;
String compay_type;
List<Integer> categoryList=new ArrayList<>();
AlertDialog dialog;
int category=-1;
RadioGroup type;
    HomeViewModel homeViewModel;
    Observer<CategorysResult>categoryObserver;

    Observer<CategorysResult>registerObserver;
    public MakerOwnerRegisterPageTwo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_maker_owner_register_page_two, container, false);
        complete_register=view.findViewById(R.id.complete_register);
        company_name=view.findViewById(R.id.company_name);
        phone=view.findViewById(R.id.phone);
        fax=view.findViewById(R.id.fax);
        manager=view.findViewById(R.id.manager);
        conversion=view.findViewById(R.id.conversion);
        cat=view.findViewById(R.id.cat);
        type=view.findViewById(R.id.type);

        fragmentTransformer= (FragmentTransformer) getActivity();
       // fragmentTransformer.goToNextFragment(6);
        type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               if(checkedId==R.id.instit)compay_type="Institution";
               else compay_type="Company";
            }


        });
        markerOwnerRegisterViewModel= ViewModelProviders.of(this).get(MarkerOwnerRegisterViewModel.class);

        completeRegisterObserver=new Observer<StatusModel>() {
            @Override
            public void onChanged(@Nullable StatusModel statusModel) {
                CustomProgressDialog.clodseProgress();
                if(statusModel.getStatus()==1) {
                    new SharedPrefrenceModel(getActivity()).setCompleteRegister(true);
                    startActivity(new Intent(getActivity(), AddFranchise.class));
                    getActivity().finish();

                }
                else{
                    Toast.makeText(getActivity(),statusModel.getMessage(),Toast.LENGTH_LONG).show(); }

            }
        };
        List<String> list=new ArrayList<>();

        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cat.setAdapter(adp1);
        cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
try {


               if(position!=0) category = categoryList.get(position);}
               catch (IndexOutOfBoundsException e){}
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        CustomProgressDialog.showProgress(getActivity());
        homeViewModel=ViewModelProviders.of(MakerOwnerRegisterPageTwo.this).get(HomeViewModel.class);
        categoryObserver = new Observer<CategorysResult>() {
            @Override
            public void onChanged(@Nullable CategorysResult result) {
                if(result.getStatus()==1) {
                    for(int i=0;i<result.getData().size();i++) {
                        categoryList.add(result.getData().get(i).getId());
                        if (new SharedPrefrenceModel(getActivity()).getLanguage().equals("en")) {
                            list.add(result.getData().get(i).getEn_name()) ;
                        } else {
                            list.add(result.getData().get(i).getEn_name()) ;
                        }
                    }
                    adp1.notifyDataSetChanged();
                    CustomProgressDialog.clodseProgress();
                }
                else Toast.makeText(getActivity(),"connection error",Toast.LENGTH_LONG).show();

            }
        };

        homeViewModel.getCategorys().observe(MakerOwnerRegisterPageTwo.this,categoryObserver);


        complete_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkvalues()){
                    CustomProgressDialog.showProgress(getActivity());
                    markerOwnerRegisterViewModel.completeRegister(new SharedPrefrenceModel(getActivity()).getId()
                            ,company_name.getText().toString(),compay_type
                            ,phone.getText().toString(),fax.getText().toString()
                            ,categoryList.get(cat.getSelectedItemPosition()),manager.getText().toString(),
                            conversion.getText().toString(),new SharedPrefrenceModel(getActivity()).getApiToken())
                            .observe(MakerOwnerRegisterPageTwo.this,completeRegisterObserver);
                }
            }
        });
        return view;
    }


    public boolean checkvalues(){
        if(company_name.getText().toString().isEmpty()||phone.getText().toString().isEmpty()||
        fax.getText().toString().isEmpty()||manager.getText().toString().isEmpty()||conversion.getText().toString().isEmpty()
        ||compay_type==null)
        {
            Toast.makeText(getActivity(),R.string.fill_data,Toast.LENGTH_LONG).show();
            return false;
        }
else return  true;
    }

}
