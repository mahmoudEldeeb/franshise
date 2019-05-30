package com.franshise.franshise.activites;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.fragments.MarkerOwnerRegisterPageFive;
import com.franshise.franshise.fragments.MarkerOwnerRegisterPageFour;
import com.franshise.franshise.fragments.MarkerOwnerRegisterPageOne;
import com.franshise.franshise.fragments.MarkerOwnerRegisterPageSix;
import com.franshise.franshise.fragments.MarkerOwnerRegisterPagetHREE;
import com.franshise.franshise.interfaces.AddFranchiseData;
import com.franshise.franshise.interfaces.FragmentTransformer;
import com.franshise.franshise.models.dataModels.CreatFranchiseModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.CreateFranchiseViewModel;

import java.util.List;

public class AddFranchise extends AppCompatActivity implements FragmentTransformer, AddFranchiseData {
    FragmentManager fragMan;
    FragmentTransaction fragTransaction;
    CreatFranchiseModel creatFranchiseModel;
    CreateFranchiseViewModel createFranchiseViewModel;
    Observer<Integer>createObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_franchise);

        //FirebaseApp.initializeApp(this);
        createObserver=new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                CustomProgressDialog.clodseProgress();
                if(integer==1){

                    Toast.makeText(AddFranchise.this,"done",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddFranchise.this,AddMarker.class));
                    finish();
                }
                else
                    Toast.makeText(AddFranchise.this,"error try later",Toast.LENGTH_SHORT).show();
            }
        };
        createFranchiseViewModel= ViewModelProviders.of(this).get(CreateFranchiseViewModel.class);
        Fragment selectedFragment=new MarkerOwnerRegisterPagetHREE();
        fragMan = getSupportFragmentManager();
        fragTransaction = fragMan.beginTransaction();
        fragTransaction.add(R.id.fragment2, selectedFragment );
        fragTransaction.commit();
        creatFranchiseModel=new CreatFranchiseModel();

    }

    @Override
    public void goToNextFragment(int i) {
        Fragment selected=new MarkerOwnerRegisterPageOne();
        switch (i){
            case 4:selected=new MarkerOwnerRegisterPageFour();break;
            case 5:selected=new MarkerOwnerRegisterPageFive();break;
            case 6:Bundle b=new Bundle();
                selected=new MarkerOwnerRegisterPageSix();
                b.putInt("modelNumber",creatFranchiseModel.getSpaceModels().size());
                selected.setArguments(b);break;
            case 7:startActivity(new Intent(AddFranchise.this,AddMarker.class));break;
        }
        fragMan = getSupportFragmentManager();
        fragTransaction = fragMan.beginTransaction();
        fragTransaction.replace(R.id.fragment2, selected );
        fragTransaction.addToBackStack(null);
        fragTransaction.commit();

    }


    @Override
    public void addfromFragmentthree(String details, String name, int cat_id) {
        creatFranchiseModel.setDetails(details);
        creatFranchiseModel.setName(name);
        creatFranchiseModel.setCat_id(cat_id);
    }

    @Override
    public void addfromFragmentfour(Integer origin, String date, int local_exist, int local_under, int out_exist, int out_under, List<String> spaceModels) {
        creatFranchiseModel.setOrigin(origin);
        creatFranchiseModel.setDate(date);
        creatFranchiseModel.setLoca_exist(local_exist);
        creatFranchiseModel.setLocal_under(local_under);
        creatFranchiseModel.setOut_exist(out_exist);
        creatFranchiseModel.setOut_under(out_under);
        creatFranchiseModel.setSpaceModels(spaceModels);
        Log.v("ggg",origin+"");
    }
    @Override
    public void addFromFragmentFive(List<Integer> franchiseTypeid, List<Integer> franchiseTypeValue, int propertyCommsion, int marketCommsion, String otherCommsionName, int otherCommsion) {
        creatFranchiseModel.setFranchiseTypeIds(franchiseTypeid);
        creatFranchiseModel.setFranchiseTypeValues(franchiseTypeValue);
        creatFranchiseModel.setPropertyCommsion(propertyCommsion);
        creatFranchiseModel.setMarketCommsion(marketCommsion);
        creatFranchiseModel.setOtherCommsionName(otherCommsionName);
        creatFranchiseModel.setOtherCommsion(otherCommsion);

    }

    @Override
    public void addFromFragmentSix(int period, List<String> investModels, List<Integer> countries, Uri image, List<Uri> imageOfProduct) {
        creatFranchiseModel.setPeriodId(period);
        creatFranchiseModel.setInvestModels(investModels);
        creatFranchiseModel.setFranchiseImage(image);
        Log.v("ppppp",image+"");
        creatFranchiseModel.setImageOfProduct(imageOfProduct);
        creatFranchiseModel.setMarketCounties(countries);
        CustomProgressDialog.showProgress(this);
        Log.v("tttt","3");
        createFranchiseViewModel.create(creatFranchiseModel,this).observe(this,createObserver);

    }

    @Override
    public CreatFranchiseModel getData() {
        return creatFranchiseModel;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

}
