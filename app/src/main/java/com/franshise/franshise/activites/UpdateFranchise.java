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
import com.franshise.franshise.adapters.GiftStyleAdapter;
import com.franshise.franshise.fragments.MarkerOwnerRegisterPageFive;
import com.franshise.franshise.fragments.MarkerOwnerRegisterPageFour;
import com.franshise.franshise.fragments.MarkerOwnerRegisterPageOne;
import com.franshise.franshise.fragments.MarkerOwnerRegisterPageSix;
import com.franshise.franshise.fragments.MarkerOwnerRegisterPagetHREE;
import com.franshise.franshise.fragments.UpdateFranchiseFive;
import com.franshise.franshise.fragments.UpdateFranchiseSix;
import com.franshise.franshise.interfaces.AddFranchiseData;
import com.franshise.franshise.interfaces.FragmentTransformer;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.CreatFranchiseModel;
import com.franshise.franshise.models.dataModels.FranchiseData;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.UpdateFranchiseViewModel;

import java.util.ArrayList;
import java.util.List;

public class UpdateFranchise extends AppCompatActivity implements AddFranchiseData, FragmentTransformer, GiftStyleAdapter.DeleteListener {
    UpdateFranchiseViewModel updateFranchise;
    FragmentManager fragMan;
    FranchiseData franchise;
    FragmentTransaction fragTransaction;
    CreatFranchiseModel creatFranchiseModel;
Observer<Integer>updateObserver;
    List<Integer>imagesIds=new ArrayList<>();
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_franchise);
        id=getIntent().getIntExtra("id",0);
        updateFranchise= ViewModelProviders.of(this).get(UpdateFranchiseViewModel.class);
creatFranchiseModel=new CreatFranchiseModel();
        updateFranchise.getFranchises(this,id).observe(this, new Observer<FranchiseData>() {
            @Override
            public void onChanged(@Nullable FranchiseData franchiseData) {
                franchise=franchiseData;
                Fragment selectedFragment=new MarkerOwnerRegisterPagetHREE();
                fragMan = getSupportFragmentManager();
                Bundle b=new Bundle();
                b.putInt("cat_id",franchiseData.getFranchise().getCategory_id());
                b.putString("name",franchiseData.getFranchise().getName());
                b.putString("about",franchiseData.getFranchise().getDetails());
                fragTransaction = fragMan.beginTransaction();
                selectedFragment.setArguments(b);
                fragTransaction.add(R.id.fragment2, selectedFragment );
                fragTransaction.commit();
            }
        });
        updateObserver=new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                CustomProgressDialog.clodseProgress();
                if(integer==1){
                    Toast.makeText(UpdateFranchise.this,"done",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpdateFranchise.this,AddMarker.class));
                    finish();
                }
                else {
                    Log.v("ttttttttt", "frttfffffffffffffffffffff");
                    Toast.makeText(UpdateFranchise.this, "error try later", Toast.LENGTH_SHORT).show();
                }
            }
        };

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

        creatFranchiseModel.setMarketCounties(countries);
        CustomProgressDialog.showProgress(this);
        if(image==null){
         if(imageOfProduct.size()==0){
             updateFranchise.updateFranchise(franchise.getFranchise().getId(),imagesIds,creatFranchiseModel,this)
                     .observe(this,updateObserver);

         }
         else {

             Log.v("tttttt","2");
             updateFranchise.updateFranchise(franchise.getFranchise().getId(),imagesIds,imageOfProduct,creatFranchiseModel,this)
                     .observe(this,updateObserver);
         }
        }
        else {
            if(imageOfProduct.size()==0){
                updateFranchise.updateFranchise(franchise.getFranchise().getId(),imagesIds,image,creatFranchiseModel,this)
                        .observe(this,updateObserver);
                Log.v("tttttt","3");
            }
            else {
                updateFranchise.updateFranchise(franchise.getFranchise().getId(),imagesIds,image
                        ,imageOfProduct,creatFranchiseModel,this).observe(this,updateObserver);
                Log.v("tttttt","4");
            }
          }
    }

    @Override
    public CreatFranchiseModel getData() {
        return creatFranchiseModel;
    }

    @Override
    public void goToNextFragment(int i) {
        Fragment selected=new MarkerOwnerRegisterPageOne();
        switch (i){
            case 4:fragmentFour();break;
            case 5:fragmentFive();break;
            case 6:fragmentSix();
            break;
            case 7:startActivity(new Intent(UpdateFranchise.this,AddMarker.class));break;
        }

    }
public void fragmentFour(){
    Bundle b=new Bundle();
    b.putString("date",franchise.getFranchise().getEstablish_date());
    b.putInt("out_under",franchise.getFranchise().getUndercost_outside_branch());
    b.putInt("out_exist",franchise.getFranchise().getExisting_outside_branch());
    b.putInt("loc_exist",franchise.getFranchise().getExisting_local_branch());
    b.putInt("loc_under",franchise.getFranchise().getUndercost_local_branch());
    //b.putSerializable("model",franchise.getFranchise());
   /// b.putString("about",franchiseData.getFranchise().getDetails());
    Fragment selected=new MarkerOwnerRegisterPageFour();

    ArrayList<String>spaceList=new ArrayList<>();
    for(int i=0;i<franchise.getFranchise().getPorts().size();i++){
        spaceList.add(franchise.getFranchise().getPorts().get(i).getSpace().replace("\"",""));
    }
    b.putStringArrayList("spaces",spaceList);
    selected.setArguments(b);
    fragMan = getSupportFragmentManager();
    fragTransaction = fragMan.beginTransaction();
    fragTransaction.replace(R.id.fragment2, selected ).addToBackStack(null);
    fragTransaction.addToBackStack(null);
    fragTransaction.commit();
}

    public void fragmentFive(){
        Bundle b=new Bundle();
        b.putInt("Owner_ship",franchise.getFranchise().getOwner_ship_commission());
        b.putInt("market",franchise.getFranchise().getMarketing_commission());
        b.putString("other_name",franchise.getFranchise().getOther_commission());
        b.putInt("other_value",franchise.getFranchise().getOther_commission_value());

       // b.putInt("loc_under",franchise.getFranchise().getUndercost_local_branch());
        //b.putSerializable("model",franchise.getFranchise());
        /// b.putString("about",franchiseData.getFranchise().getDetails());
        Fragment selected=new UpdateFranchiseFive();

        ArrayList<String>typeList=new ArrayList<>();
        ArrayList<Integer>typeListValue=new ArrayList<>();

        for(int i=0;i<franchise.getFranchise_type().size();i++){
            if(new SharedPrefrenceModel(this).getLanguage().equals("en"))
            typeList.add(franchise.getFranchise_type().get(i).getEn_name());
            else
                typeList.add(franchise.getFranchise_type().get(i).getAr_name());
            typeListValue.add(franchise.getFranchise_gift().get(i).getValue());
        }
        b.putStringArrayList("typeList",typeList);
        b.putIntegerArrayList("typeListValue",typeListValue);

        selected.setArguments(b);
        fragMan = getSupportFragmentManager();
        fragTransaction = fragMan.beginTransaction();
        fragTransaction.replace(R.id.fragment2, selected ).addToBackStack(null);
        fragTransaction.addToBackStack(null);
        fragTransaction.commit();
    }
    public void fragmentSix(){
        Bundle b=new Bundle();
        Fragment selected=new UpdateFranchiseSix();

        ArrayList<Integer>marketList=new ArrayList<>();
        ArrayList<String>invesList=new ArrayList<>();

        ArrayList<String>imagesList=new ArrayList<>();
        for(int i=0;i<franchise.getFranchise().getPorts().size();i++){
            invesList.add(franchise.getFranchise().getPorts().get(i).getTotal_Investment().replace("\"",""));
        }
        for(int i=0;i<franchise.getFranchise().getFranchise_market().size();i++){
            marketList.add(franchise.getFranchise().getFranchise_market().get(i).getId());
        }

        for(int i=0;i<franchise.getFranchise().getImages().size();i++){
            imagesList.add(franchise.getFranchise().getImages().get(i).getImagePath());
        }
        b.putIntegerArrayList("marketList",marketList);
        b.putStringArrayList("invesList",invesList);

        b.putStringArrayList("imagesList",imagesList);
        b.putString("mainImage",franchise.getFranchise().getImage());
        b.putInt("modelNumber",creatFranchiseModel.getSpaceModels().size());
        Log.v("qwww",franchise.getFranchise().getPeriods().getId()+"");
        b.putInt("period",franchise.getFranchise().getPeriods().getId());
        selected.setArguments(b);
        fragMan = getSupportFragmentManager();
        fragTransaction = fragMan.beginTransaction();
        fragTransaction.replace(R.id.fragment2, selected ).addToBackStack(null);
        fragTransaction.addToBackStack(null);
        fragTransaction.commit();
    }
    @Override
    public void delete(int type, int position) {
        Log.v("tttttt",position+"   "+franchise.getFranchise().getImages().get(position).getId());
        imagesIds.add(franchise.getFranchise().getImages().get(position).getId());
    }
}
