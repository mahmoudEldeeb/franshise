package com.franshise.franshise.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.activites.AddFranchise;
import com.franshise.franshise.interfaces.AddFranchiseData;
import com.franshise.franshise.interfaces.FragmentTransformer;
import com.franshise.franshise.models.ResultNetworkModels.CategorysResult;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.CreatFranchiseModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MarkerOwnerRegisterPagetHREE extends Fragment {
Button next;
EditText about_franchise,name;
AddFranchiseData addFranchiseData;
Spinner spinner_cat;
HomeViewModel homeViewModel;
Observer<CategorysResult>categoryObserver;
List<Integer>categoryList;
static int counter=0;
    public MarkerOwnerRegisterPagetHREE() {
        // Required empty public constructor
    }
    static int count=0;
static int selected=0;
static CategorysResult categorysResult=new CategorysResult();
    FragmentTransformer fragmentTransformer;
    Button complete_register;
    ArrayAdapter<String> adp1;
    List<String> list=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_marker_owner_register_paget_hree, container, false);
        next=view.findViewById(R.id.next);
        about_franchise=view.findViewById(R.id.about_franchise);
        name=view.findViewById(R.id.name);
        spinner_cat=view.findViewById(R.id.spinner_cat);

        categoryList=new ArrayList<>();
        adp1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cat.setAdapter(adp1);

        homeViewModel= ViewModelProviders.of(MarkerOwnerRegisterPagetHREE.this).get(HomeViewModel.class);
        categoryObserver = new Observer<CategorysResult>() {
            @Override
            public void onChanged(@Nullable CategorysResult result) {
                if(result.getStatus()==1) {
                    categorysResult=result;
                    putCategory();
                    count=1;
                    adp1.notifyDataSetChanged();
                }
                else Toast.makeText(getActivity(),"connection error",Toast.LENGTH_LONG).show();
                CustomProgressDialog.clodseProgress();
            }
        };

        addFranchiseData= (AddFranchiseData) getActivity();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!about_franchise.getText().toString().isEmpty()||!name.getText().toString().isEmpty()||
                        categoryList.get(spinner_cat.getSelectedItemPosition())==null){
                    if(getcount(about_franchise.getText().toString())<=450){
                fragmentTransformer= (FragmentTransformer) getActivity();
                addFranchiseData.addfromFragmentthree(about_franchise.getText().toString(),name.getText().toString(),
                        categoryList.get(spinner_cat.getSelectedItemPosition()));
                selected=spinner_cat.getSelectedItemPosition();
                fragmentTransformer.goToNextFragment(4);}
                else {

             Toast.makeText(getActivity(),R.string.nomore,Toast.LENGTH_LONG).show();
                    }
            }
            else Toast.makeText(getActivity(),R.string.fill_data,Toast.LENGTH_LONG).show();
            }
        });
        if(count==0){

            CustomProgressDialog.showProgress(getActivity());
            homeViewModel.getCategorys().observe(MarkerOwnerRegisterPagetHREE.this,categoryObserver);

        }
        else {
            putCategory();
           PutData(addFranchiseData.getData());
        }
        return view;

    }

    private void PutData(CreatFranchiseModel data) {
        name.setText(data.getName());
        about_franchise.setText(data.getDetails());
        for(int i=0;i<categoryList.size();i++){
            if(data.getCat_id()==categoryList.get(i))
                spinner_cat.setSelection(i);
        }
    }


    public int getcount(String str){
    String words = str.trim();
    if (words.isEmpty())
        return 0;
    return words.split("\\s+").length;
}
    public void putCategory(){

        for (int i = 0; i < categorysResult.getData().size(); i++) {
            categoryList.add(categorysResult.getData().get(i).getId());
            if (new SharedPrefrenceModel(getActivity()).getLanguage().equals("en")) {
                list.add(categorysResult.getData().get(i).getEn_name());
            } else {
                list.add(categorysResult.getData().get(i).getEn_name());
            }
        }
        adp1.notifyDataSetChanged();
        spinner_cat.setSelection(selected);
    }
}

