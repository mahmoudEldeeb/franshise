package com.franshise.franshise.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.SendMessageViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComplaintsFragment extends Fragment {

EditText message,email,from,country;
Button  send;
RadioGroup typegroup;
RadioButton radioButton,radioButton2,radioButton3;
Observer<StatusModel> messageObserver;
SendMessageViewModel sendMessageViewModel;
    public ComplaintsFragment() {
        // Required empty public constructor
    }
String type="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_complaints, container, false);
        from=view.findViewById(R.id.from);
        email=view.findViewById(R.id.email);
        message=view.findViewById(R.id.message);
        country=view.findViewById(R.id.password);
        send=view.findViewById(R.id.send);
        typegroup=view.findViewById(R.id.typegroup);

        radioButton=view.findViewById(R.id.radioButton);
        radioButton2=view.findViewById(R.id.radioButton2);
        radioButton3=view.findViewById(R.id.radioButton3);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValuse())sendMessage();
                else Toast.makeText(getActivity(),R.string.fill_data,Toast.LENGTH_SHORT).show();
            }
        });
        typegroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
             switch (checkedId){
                 case R.id.radioButton :type=radioButton.getText().toString();break;
                 case R.id.radioButton2 :type=radioButton2.getText().toString();break;
                 case R.id.radioButton3 :type=radioButton3.getText().toString();break;
             }
            }
        });
        sendMessageViewModel= ViewModelProviders.of(this).get(SendMessageViewModel.class);

        messageObserver=new Observer<StatusModel>() {
            @Override
            public void onChanged(@Nullable StatusModel statusModel) {
                CustomProgressDialog.clodseProgress();
                Toast.makeText(getActivity(),statusModel.getMessage(),Toast.LENGTH_SHORT).show();
            }
        };
        return view;
    }

    private void sendMessage() {
    sendMessageViewModel.sendSugetion(type,from.getText().toString(),email.getText().toString(),country.getText().toString(),
            message.getText().toString()).observe(this,messageObserver);
        CustomProgressDialog.showProgress(getActivity());

    }

    private boolean checkValuse() {
        if(from.getText().toString().isEmpty()||email.getText().toString().isEmpty()
        ||country.getText().toString().isEmpty()||message.getText().toString().isEmpty()||type=="")
            return false;
        else return true;
    }

}
