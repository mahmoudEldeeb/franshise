package com.franshise.franshise.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.interfaces.AddFranchiseData;
import com.franshise.franshise.interfaces.FragmentTransformer;
import com.franshise.franshise.models.ResultNetworkModels.CategorysResult;
import com.franshise.franshise.models.ResultNetworkModels.DataResult;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.DataModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.CreateFranchiseViewModel;
import com.franshise.franshise.viewmodels.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateFranchiseFive extends Fragment {

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
    Bundle bundle;
    ArrayList <Integer>typeListValue=new ArrayList<>();
    ArrayList <String>typeList=new ArrayList<>();
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

        bundle=getArguments();
        //int v1=
        if(bundle!=null) {
            market_spinner.setSelection(bundle.getInt("market"));
            propirty_spinner.setSelection(bundle.getInt("Owner_ship"));
            other_spinner.setSelection(bundle.getInt("other_value"));
            other_commsion.setText(bundle.getString("other_name")+"");
            typeList=bundle.getStringArrayList("typeList");
            typeListValue=bundle.getIntegerArrayList("typeListValue");

        }
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
            CustomProgressDialog.showProgress(getActivity());
            createFranchiseViewModel.getfranchisetype().observe(this, new Observer<DataResult>() {
                @Override
                public void onChanged(@Nullable DataResult dataResult) {
                    CustomProgressDialog.clodseProgress();
                    dataResult1=dataResult;
                    if(dataResult.getStatus()==1){
                        listOfScoler=dataResult.getData();
                        for (int i=0;i<listOfScoler.size();i++) {
                            addElementScolerType(i,"nothing");
                        }
                        putData();
                    }
                }
            });

        fragmentTransformer= (FragmentTransformer) getActivity();
        propirty_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                propertyCommsion=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        market_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                marketcommsion=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        other_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                otherCommsion=position;
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
        scholrshipedit.setText(val);

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

    public void putData() {
        for (int i = 0; i < spaceParent.getChildCount(); i++) {
            View v2 = spaceParent.getChildAt(i);
            EditText s = v2.findViewById(R.id.scholrshipedit);
            CheckBox scholrshipBox = v2.findViewById(R.id.scholrshipBox);
for(int j=0;j<typeList.size();j++) {
    if (scholrshipBox.getText().toString().equals(typeList.get(j))) {
        s.setText(typeListValue.get(j)+"");
        scholrshipBox.setChecked(true);
    }
}
}

    }

}