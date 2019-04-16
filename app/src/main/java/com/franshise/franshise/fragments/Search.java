package com.franshise.franshise.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.adapters.FranchiseListAdapter;
import com.franshise.franshise.models.ResultNetworkModels.CategorysResult;
import com.franshise.franshise.models.ResultNetworkModels.DataResult;
import com.franshise.franshise.models.ResultNetworkModels.FranchisesResult;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.DataModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.HomeViewModel;
import com.franshise.franshise.viewmodels.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Search extends Fragment {
static int count=0;
static  DataResult dataResult1=new DataResult();
static CategorysResult categorysResult1=new CategorysResult();
    public Search() {
        // Required empty public constructor
    }
List<String>listMoney;
SearchViewModel searchViewModel;
Spinner money_spinner,spinner_cat;
List<Integer>categoryList;
List<DataModel>dataModelList;
EditText search_word;
Button search;
LinearLayout search_page;
RecyclerView franchise_res;
FranchiseListAdapter franchiseListAdapter;
    ArrayAdapter<String> adpcat,adp1;
    List<String>list=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_search, container, false);
        money_spinner=view.findViewById(R.id.money_spinner);
        spinner_cat=view.findViewById(R.id.spinner_cat);
        search=view.findViewById(R.id.search);
        search_word=view.findViewById(R.id.search_word);
        franchise_res=view.findViewById(R.id.franchise_res);
        search_page=view.findViewById(R.id.search_page);
        listMoney=new ArrayList<>();
listMoney.add("Choose....");

        list.add("Choose....");
        adp1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, listMoney);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adpcat = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, list);
        adpcat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cat.setAdapter(adpcat);
        money_spinner.setAdapter(adp1);
        searchViewModel= ViewModelProviders.of(this).get(SearchViewModel.class);
       if(count==0) {
           CustomProgressDialog.showProgress(getActivity());
           searchViewModel.getMoney().observe(this, new Observer<DataResult>() {
               @Override
               public void onChanged(@Nullable DataResult dataResult) {
                   if (dataResult.getStatus() == 1) {
                       dataResult1 = dataResult;
                       putMoney();
                   }
                   CustomProgressDialog.clodseProgress();

               }
           });
           categoryList = new ArrayList<>();
           HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
           homeViewModel.getCategorys().observe(this, new Observer<CategorysResult>() {
               @Override
               public void onChanged(@Nullable CategorysResult categorysResult) {
                   if (categorysResult.getStatus() == 1) {
                       categorysResult1 = categorysResult;
                       count = 1;
                       putCategory();
                   } else
                       Toast.makeText(getActivity(), "connection error", Toast.LENGTH_LONG).show();
                   // CustomProgressDialog.clodseProgress();

               }
           });
       }

       else {
      putMoney();
           putCategory();

       }
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),3);
        franchise_res.setLayoutManager(mLayoutManager);

        return view;
    }
public void putCategory(){

    for (int i = 0; i < categorysResult1.getData().size(); i++) {
        categoryList.add(categorysResult1.getData().get(i).getId());
        if (new SharedPrefrenceModel(getActivity()).getLanguage().equals("en")) {
            list.add(categorysResult1.getData().get(i).getEn_name());
        } else {
            list.add(categorysResult1.getData().get(i).getEn_name());
        }
    }
    adpcat.notifyDataSetChanged();
}
private void putMoney(){
    dataModelList = dataResult1.getData();
    for (int i = 0; i < dataResult1.getData().size(); i++) {
        DataModel dataModel1 = dataResult1.getData().get(i);
        String value = dataModel1.getMin() + "-" + dataModel1.getMax();
        listMoney.add(value);
        adp1.notifyDataSetChanged();
    }


}
    private void search() {
        String word="-1";
        int min=-1;
        int max=-1;
        int catId=-1;
        if(spinner_cat.getSelectedItemPosition()==0&&money_spinner.getSelectedItemPosition()==0&&search_word.getText().toString().isEmpty()){
            Toast.makeText(getActivity(),"enter at least one type of search",Toast.LENGTH_SHORT).show();
        }
        else {
            if(!search_word.getText().toString().isEmpty())word=search_word.getText().toString();
            if(money_spinner.getSelectedItemPosition()!=0){
                min=dataModelList.get(money_spinner.getSelectedItemPosition()-1).getMin();
                max=dataModelList.get(money_spinner.getSelectedItemPosition()-1).getMax();
            }
            if(spinner_cat.getSelectedItemPosition()!=0){
                catId=categoryList.get(spinner_cat.getSelectedItemPosition()-1);}

                searchViewModel.search(word,min,max,catId).observe(this, new Observer<FranchisesResult>() {
                @Override
                public void onChanged(@Nullable FranchisesResult franchisesResult) {
                    if(franchisesResult.getStatus()==1&&franchisesResult.getData().size()>0){
                        franchiseListAdapter=new FranchiseListAdapter(getActivity(),franchisesResult.getData());
                        franchise_res.setAdapter(franchiseListAdapter);
                        search_page.setVisibility(View.GONE);
                        franchise_res.setVisibility(View.VISIBLE);
                    }

                }
            });
        }
    }

    public boolean showSearch() {
        franchise_res.setVisibility(View.GONE);
        search_page.setVisibility(View.VISIBLE);
        return true;
    }

    public boolean issearch() {
        if(search_page.getVisibility()==View.VISIBLE)
            return true;
        else return false;
    }
}
