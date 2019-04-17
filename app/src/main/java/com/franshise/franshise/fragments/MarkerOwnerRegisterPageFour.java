package com.franshise.franshise.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.interfaces.AddFranchiseData;
import com.franshise.franshise.interfaces.FragmentTransformer;
import com.franshise.franshise.models.ResultNetworkModels.DataResult;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.CreatFranchiseModel;
import com.franshise.franshise.models.dataModels.FranchiseModel;
import com.franshise.franshise.viewmodels.CreateFranchiseViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class MarkerOwnerRegisterPageFour extends Fragment {


    public MarkerOwnerRegisterPageFour() {
        // Required empty public constructor
    }
LinearLayout spaceParent;
TextView re;
int i=1;
Button add,next;
    LayoutInflater layoutInflater;
    Button date;
EditText origin,local_exist,local_under,out_exist,out_under;
    FragmentTransformer fragmentTransformer;
    Button complete_register;
    String dates;
    List<String>spaceArray;
    Spinner spinner_origin;
    final Calendar myCalendar = Calendar.getInstance();
    List<Integer>countryIdList;
    static int count=0;
    ArrayAdapter<String> adp1;
    CreateFranchiseViewModel createFranchiseViewModel;
      DataResult dataResult1;
     int spacecount=0;
     FranchiseModel franchiseModel;
    List<String>countryList=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layoutInflater=inflater;
        View view=inflater.inflate(R.layout.fragment_marker_owner_register_page_four, container, false);
        spaceParent=view.findViewById(R.id.spaceParent);
        next=view.findViewById(R.id.next);
        add=view.findViewById(R.id.add);
        spinner_origin=view.findViewById(R.id.spinner_origin);
        date=view.findViewById(R.id.date);
        local_exist=view.findViewById(R.id.local_exist);
        local_under=view.findViewById(R.id.local_under);
        out_exist=view.findViewById(R.id.out_exist);
        out_under=view.findViewById(R.id.out_under);
        Bundle bundle=getArguments();

        if(bundle!=null) {
            Log.v("aaaaa",bundle.getInt("loc_exist")+"");
        local_exist.setText(bundle.getInt("loc_exist")+"");
        local_under.setText(bundle.getInt("loc_under")+"");
        out_under.setText(bundle.getInt("out_under")+"");
        out_exist.setText(bundle.getInt("out_exist")+"");
        ArrayList<String>spaces=bundle.getStringArrayList("spaces");
        date.setText(bundle.getString("date"));
        Log.v("aaaaaaaa",spaces.size()+"");
            for(int j=0;j<spaces.size();j++){
                addElement(spaces.get(j));
            }

        }else addElement("");
            spaceArray=new ArrayList<>();



countryIdList=new ArrayList<>();
        adp1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, countryList);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_origin.setAdapter(adp1);

        createFranchiseViewModel= ViewModelProviders.of(this).get(CreateFranchiseViewModel.class);

if(count==0) {
    createFranchiseViewModel.getCountry(1).observe(this, new Observer<DataResult>() {
        @Override
        public void onChanged(@Nullable DataResult dataResult) {
            dataResult1 = dataResult;
            putCountrys(dataResult1);
            count=1;
        }
    });
}
else {
    putCountrys(dataResult1);
    AddFranchiseData addFranchiseData= (AddFranchiseData) getActivity();
    CreatFranchiseModel data=addFranchiseData.getData();
    for(int j=0;j>data.getSpaceModels().size();j++){
        addElement(data.getSpaceModels().get(j));
    }
    if(data.getOut_exist()!=0){
        out_exist.setText(data.getOut_exist()+"");
    }
    if(data.getOut_under()!=0){
        out_under.setText(data.getOut_under()+"");
    }
    if(data.getLoca_exist()!=0){
        local_exist.setText(data.getLoca_exist()+"");
    }
    if(data.getLocal_under()!=0){
        local_under.setText(data.getLocal_under()+"");
    }
    for(int j=0;j>countryIdList.size();j++){
        if(countryIdList.get(j)==data.getOrigin())
            spinner_origin.setSelection(j);
    }
date.setText(data.getDate());

}
        fragmentTransformer= (FragmentTransformer) getActivity();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(countryIdList.get(spinner_origin.getSelectedItemPosition())==null||date.getText().toString().isEmpty()){
                        Toast.makeText(getActivity(),R.string.fill_data,Toast.LENGTH_SHORT).show();
                }
                else {

                    getSpaceModelsValues();

                }
               // fragmentTransformer.goToNextFragment(5);

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addElement("");
            }
        });

        DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(getActivity(), date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return view;

    }
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dates=sdf.format(myCalendar.getTime());
        date.setText(sdf.format(myCalendar.getTime()));
    }
    private void addElement(String val) {
        Log.v("aaaaa","fghjk");
        View view1=layoutInflater.inflate(R.layout.space_model, spaceParent, false);
        re=view1.findViewById(R.id.re);
        re.setText("Model "+i);
        EditText value=view1.findViewById(R.id.value);
        if(!val.isEmpty())value.setText(val);
        ImageButton delete1=view1.findViewById(R.id.delete);
        delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View v2=spaceParent.getChildAt( spaceParent.indexOfChild(view1));
                spaceParent.removeView(v2);
                changeCount();
            }
        });
         spaceParent.addView(view1,spaceParent.getChildCount());
         i++;
    }

    TextView title;
    private void changeCount() {
        for(int j=0;j<spaceParent.getChildCount();j++) {
            View v4 = spaceParent.getChildAt(j);
            title = v4.findViewById(R.id.re);
            int y=j+1;
            title.setText("Model "+y);
        }
        i=spaceParent.getChildCount();
        i++;
    }

    public void putCountrys(DataResult dataResult){
        for(int i=0;i<dataResult.getData().size();i++) {
            countryIdList.add(dataResult.getData().get(i).getId());
            if (new SharedPrefrenceModel(getActivity()).getLanguage().equals("en")) {
                countryList.add(dataResult.getData().get(i).getEn_name()) ;
            } else {
                countryList.add(dataResult.getData().get(i).getEn_name()) ;
            }
        }
        adp1.notifyDataSetChanged();
    }
    public void getSpaceModelsValues(){

for(int i=0;i<spaceParent.getChildCount();i++) {
    View v2 = spaceParent.getChildAt(i);
    EditText s = v2.findViewById(R.id.value);
    if(s.getText().toString().isEmpty()){
        spaceArray.clear();
        Toast.makeText(getActivity(),"fill data of model"+i,Toast.LENGTH_SHORT).show();
    }
    else {
        spaceArray.add(s.getText().toString());
    }

}
if(spaceArray.size()==spaceParent.getChildCount()){
    spacecount=spaceParent.getChildCount();
    AddFranchiseData addFranchiseData= (AddFranchiseData) getActivity();
    int loc_undern,loc_exitstn,out_existn,out_undern;
    if(out_exist.getText().toString().isEmpty()){
        out_existn=0;
    }else out_existn=Integer.valueOf(out_exist.getText().toString());
    if(out_under.getText().toString().isEmpty()){
        out_undern=0;
    }else out_undern=Integer.valueOf(out_under.getText().toString());
    if(local_exist.getText().toString().isEmpty()){
        loc_exitstn=0;
    }else loc_exitstn=Integer.valueOf(local_exist.getText().toString());
    if(local_under.getText().toString().isEmpty()){
        loc_undern=0;
    }else loc_undern=Integer.valueOf(local_under.getText().toString());
    addFranchiseData.addfromFragmentfour(countryIdList.get(spinner_origin.getSelectedItemPosition()),date.getText().toString(),
            loc_exitstn,loc_undern,out_existn,out_undern,spaceArray);
    fragmentTransformer.goToNextFragment(5);


}
else
    Log.v("ssss","3333333333333");
    }


    @Override
    public void onDestroy() {
        count=0;
        super.onDestroy();
    }
}

