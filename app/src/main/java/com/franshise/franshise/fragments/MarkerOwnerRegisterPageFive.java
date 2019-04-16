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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.adapters.GiftStyleAdapter;
import com.franshise.franshise.adapters.ScolerTypeAdapter;
import com.franshise.franshise.interfaces.AddFranchiseData;
import com.franshise.franshise.interfaces.FragmentTransformer;
import com.franshise.franshise.models.ResultNetworkModels.DataResult;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.DataModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.CreateFranchiseViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MarkerOwnerRegisterPageFive extends Fragment implements ScolerTypeAdapter.ScolerTypeListener {


    public MarkerOwnerRegisterPageFive() {
        // Required empty public constructor
    }
Button next;
    FragmentTransformer fragmentTransformer;

EditText other_commsion;
LinearLayout spaceParent;
static boolean check1=true,check2=true,check3=true;
Spinner propirty_spinner,market_spinner,other_spinner;
int marketcommsion,propertyCommsion,otherCommsion;

CreateFranchiseViewModel createFranchiseViewModel;
    LayoutInflater layoutInflater;
    List<DataModel> listOfScoler;
    ImageView imageCheck1,imageCheck2,imageCheck3;
    static  int count=0;
    static DataResult dataResult1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layoutInflater=inflater;
        View view = inflater.inflate(R.layout.fragment_marker_owner_register_page_five, container, false);
        next = view.findViewById(R.id.nextpage);
        spaceParent=view.findViewById(R.id.spaceParent);
        propirty_spinner=view.findViewById(R.id.propirty_spinner);
        other_spinner=view.findViewById(R.id.other_spinner);
        market_spinner=view.findViewById(R.id.market_spinner);
        other_commsion=view.findViewById(R.id.other_commsion);
        imageCheck1=view.findViewById(R.id.imageCheck1);
        imageCheck2=view.findViewById(R.id.imageCheck2);
        imageCheck3=view.findViewById(R.id.imageCheck3);
imageCheck1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(check1){
        imageCheck1.setImageResource(R.drawable.subcheck);
        check1=false;
        }
        else {
            imageCheck1.setImageResource(R.drawable.sub);
            check1=true;
            propirty_spinner.setSelection(0);
        }
    }
});imageCheck2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check2){
                imageCheck2.setImageResource(R.drawable.subcheck);
                check2=false;
            }
            else {
                imageCheck2.setImageResource(R.drawable.sub);
                check2=true;
                market_spinner.setSelection(0);
            }
            }
        });imageCheck3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check3){
                    imageCheck3.setImageResource(R.drawable.subcheck);
                    check3=false;
                }
                else {
                    imageCheck3.setImageResource(R.drawable.sub);
                    other_spinner.setSelection(0);
                    check3=true;
                }
            }
        });

        //      RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity());
//        scolertype_res.setLayoutManager(mLayoutManager1);

        createFranchiseViewModel= ViewModelProviders.of(this).get(CreateFranchiseViewModel.class);
       if(count==0){
        CustomProgressDialog.showProgress(getActivity());
        createFranchiseViewModel.getfranchisetype().observe(this, new Observer<DataResult>() {
            @Override
            public void onChanged(@Nullable DataResult dataResult) {
                CustomProgressDialog.clodseProgress();
                 dataResult1=dataResult;
                if(dataResult.getStatus()==1){
                    listOfScoler=dataResult.getData();
                    for (int i=0;i<listOfScoler.size();i++) {
                    addElementScolerType(i,"nothing");}
                    count=0;
                }
            }
        });}
        else {
               listOfScoler=dataResult1.getData();
           AddFranchiseData addFranchiseData= (AddFranchiseData) getActivity();
           List<Integer>list=new ArrayList<>();
           list=addFranchiseData.getData().getFranchiseTypeValues();
           List<Integer>listIds=addFranchiseData.getData().getFranchiseTypeIds();
               for (int t=0;t<listOfScoler.size();t++) {
                   int z=0;
                   for(int i=0;i<listIds.size();i++){
                       Log.v("qqqqqq",listIds.get(i)+"    "+listOfScoler.get(t).getId());
                       if(listOfScoler.get(t).getId()==listIds.get(i)){
                           Log.v("ooooo",list.get(i)+"");
                           addElementScolerType(t,list.get(i)+"");
                           z=1;
                       }
                   }
                   if(z!=1)
                   addElementScolerType(t,"nothing");

           }
       }
        fragmentTransformer= (FragmentTransformer) getActivity();
propirty_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        propertyCommsion=position+1;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});
        market_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                marketcommsion=position+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        other_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                otherCommsion=position+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataOfScolers();
                    }
        });
        return view;
    }
CheckBox scholrshipBox;
    EditText scholrshipedit;
    int listPosition=0;
    private void addElementScolerType(int i,String val) {
            View v3 = layoutInflater.inflate(R.layout.scolrship_type, spaceParent, false);
            scholrshipBox = v3.findViewById(R.id.scholrshipBox);

            if (new SharedPrefrenceModel(getActivity()).getLanguage().equals("en")) {
                scholrshipBox.setText(listOfScoler.get(i).getEn_name());
            } else
                scholrshipBox.setText(listOfScoler.get(i).getAr_name());
            scholrshipedit = v3.findViewById(R.id.scholrshipedit);
            scholrshipBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    View v2=spaceParent.getChildAt( spaceParent.indexOfChild(v3));
                    EditText scholrshipedit1=v2.findViewById(R.id.scholrshipedit);
                  //  v2.s.removeView(v2);
                    if (isChecked) {
                        scholrshipedit1.setVisibility(View.VISIBLE);
                    } else scholrshipedit1.setVisibility(View.GONE);
                }
            });

        EditText s = v3.findViewById(R.id.scholrshipedit);
            if(!val.equals("nothing")){
               // scholrshipBox.setChecked(true);
                s.setText(val);
                s.setVisibility(View.VISIBLE);

            }
            spaceParent.addView(v3, spaceParent.getChildCount());

    }
    List<Integer>scolerTypeIds=new ArrayList<>();

    List<Integer>scolerTypevalues=new ArrayList<>();
    public void getDataOfScolers() {
        for (int i = 0; i < spaceParent.getChildCount(); i++) {
            View v2 = spaceParent.getChildAt(i);
            EditText s = v2.findViewById(R.id.scholrshipedit);
            CheckBox scholrshipBox = v2.findViewById(R.id.scholrshipBox);
            if (scholrshipBox.isChecked()) {
                // investArray.clear();
                if(s.getText().toString().isEmpty()){
                    scolerTypeIds.clear();
                    scolerTypevalues.clear();
                    Toast.makeText(getActivity(),R.string.fill_data,Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.v("qqqq",listOfScoler.get(i).getId()+"         "+listOfScoler.get(i).getEn_name());
                    scolerTypeIds.add(listOfScoler.get(i).getId());
                    scolerTypevalues.add(Integer.parseInt(s.getText().toString()));

                }
            }
        }
        if(scolerTypeIds.size()==scolerTypevalues.size()){
            AddFranchiseData addFranchiseData= (AddFranchiseData) getActivity();
            addFranchiseData.addFromFragmentFive(scolerTypeIds,scolerTypevalues,propertyCommsion,marketcommsion,other_commsion.getText().toString(),otherCommsion);
           fragmentTransformer.goToNextFragment(6);
        }
    }
    @Override
    public void check(int id, int position) {

    }
    @Override
    public void onDestroy() {
        count=0;
        super.onDestroy();
    }
}
