package com.franshise.franshise.activites;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.Observable;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.fragments.MakerOwnerRegisterPageTwo;
import com.franshise.franshise.fragments.MarkerOwnerRegisterPageFive;
import com.franshise.franshise.fragments.MarkerOwnerRegisterPageFour;
import com.franshise.franshise.fragments.MarkerOwnerRegisterPageOne;
import com.franshise.franshise.fragments.MarkerOwnerRegisterPageSix;
import com.franshise.franshise.fragments.MarkerOwnerRegisterPagetHREE;
import com.franshise.franshise.interfaces.FragmentTransformer;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.MarkerOwnerRegisterViewModel;

import dmax.dialog.SpotsDialog;

public class MarkerOwnerRegister extends AppCompatActivity implements FragmentTransformer {
    FragmentManager fragMan;
    FragmentTransaction fragTransaction;
    MarkerOwnerRegisterViewModel markerOwnerRegisterViewModel;
    Observer<StatusModel>markerOwnerObserver;
    AlertDialog proDialog;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_owner_register);
        ConstraintLayout layout = findViewById(R.id.layout);
        CustomProgressDialog.showProgress(this);
           ///  proDialog.show();

markerOwnerRegisterViewModel= ViewModelProviders.of(this).get(MarkerOwnerRegisterViewModel.class);
        markerOwnerObserver=new Observer<StatusModel>() {
            @Override
            public void onChanged(@Nullable StatusModel statusModel) {
             if(statusModel.getStatus()==1) {
                 //proDialog.dismiss();
                 CustomProgressDialog.clodseProgress();
                 new SharedPrefrenceModel(MarkerOwnerRegister.this).setCompleteRegister(true);
                 startActivity(new Intent(MarkerOwnerRegister.this, AddMarker.class));
                    finish();
             }
             else {
                 CustomProgressDialog.clodseProgress();
                 Fragment selectedFragment=new MarkerOwnerRegisterPageOne();
                 fragMan = getSupportFragmentManager();
                 fragTransaction = fragMan.beginTransaction();
                 fragTransaction.add(R.id.fragment2, selectedFragment );
                 fragTransaction.commit();

             }
            }


        };
if(new SharedPrefrenceModel(MarkerOwnerRegister.this).isLogined()){
        if(!new SharedPrefrenceModel(this).getCompleteregister()) {
           markerOwnerRegisterViewModel.checkCompleteProfile(new SharedPrefrenceModel(this).getId(),new SharedPrefrenceModel(this)
                   .getApiToken()).observe(this, markerOwnerObserver);
        }
        else {
            startActivity(new Intent(MarkerOwnerRegister.this, AddMarker.class));
            finish();
        }
    }
                else {
        Toast.makeText(getBaseContext(),"you must login first",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MarkerOwnerRegister.this,LoginActivity.class));
        finish();
    }
    }

    @Override
    public void goToNextFragment(int i) {
        Fragment selected=new MarkerOwnerRegisterPageOne();
        switch (i){
            case 2:selected=new MakerOwnerRegisterPageTwo();break;
           /* case 3:startActivity(new Intent(MarkerOwnerRegister.this,AddFranchise.class));
            finish();Log.v("rrrr","444444444444444");
            break;*/
        }
        fragMan = getSupportFragmentManager();
        fragTransaction = fragMan.beginTransaction();
        fragTransaction.add(R.id.fragment2, selected );
        fragTransaction.commit();
    }

}
