package com.franshise.franshise.viewmodels;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.franshise.franshise.models.ResultNetworkModels.CategorysResult;
import com.franshise.franshise.models.ResultNetworkModels.FranchiseDataMODEL;
import com.franshise.franshise.models.ResultNetworkModels.FranchiseResultModel;
import com.franshise.franshise.models.ResultNetworkModels.FranchiseResultsView;
import com.franshise.franshise.models.ResultNetworkModels.FranchisesResult;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.UserModel;
import com.franshise.franshise.models.dataModels.FranchiseData;
import com.franshise.franshise.models.database.FavModel;
import com.franshise.franshise.models.repositry.FavRepositry;
import com.franshise.franshise.models.repositry.FranchiseRepositry;
import com.franshise.franshise.models.repositry.HomeRepositry;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ContentHandler;
import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class MarkerViewViewModel extends AndroidViewModel {
List<String>franchiseList;
    FavRepositry favRepositry;
    MutableLiveData<FranchiseDataMODEL> franchisesResults;
   public MarkerViewViewModel (@NonNull  Application application){
       super(application);
        favRepositry=new FavRepositry(application);

    }
    public LiveData<FranchiseDataMODEL> getFranchises(Context context, int id) {
        franchiseList=new ArrayList<>();
        List<String>franchiseListTitle=new ArrayList<>();
        franchisesResults=new MutableLiveData<>();
        CustomProgressDialog.showProgress(context);
        FranchiseRepositry.getFranchises(id).subscribeWith(new SingleObserver<FranchiseResultsView>() {
            @Override
            public void onSubscribe(Disposable d) {
            }
            @Override
            public void onSuccess(FranchiseResultsView franchiseResultModel) {
                CustomProgressDialog.clodseProgress();
                FranchiseData franchiseData=franchiseResultModel.getData();
                FranchiseDataMODEL franchiseDataMODEL=new FranchiseDataMODEL();
               /* JSONArray jsonElements=franchiseData.getFranchise_gift();
                for (int i=0;i<jsonElements.length();i++){
                    try {
                        JSONObject jsonObject=jsonElements.getJSONObject(i);
                        Log.v("ppppp",jsonObject.getInt("value")+"");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
*/
               Log.v("pppppp",franchiseData.getFranchise_gift().size()+"");

                Log.v("pppppp",franchiseData.getFranchise_type().size()+"");
                franchiseList.add(franchiseData.getFranchise().getCountries().getEn_name()+"/"+franchiseData.getFranchise().getCountries().getAr_name());
                franchiseList.add(franchiseData.getFranchise().getEstablish_date()+"");
                franchiseList.add(franchiseData.getFranchise().getExisting_local_branch()+"");
                franchiseList.add(franchiseData.getFranchise().getExisting_outside_branch()+"");
                String space="";
                String investment="";
                for(int i=0;i<franchiseData.getFranchise().getPorts().size();i++){
                    space+="Moldel"+i+":  "+franchiseData.getFranchise().getPorts().get(i).getSpace()+"\n";
                    investment+="Moldel"+i+":  "+franchiseData.getFranchise().getPorts().get(i).getTotal_Investment()+"\n";
                }

                for(int i=0;i<franchiseData.getFranchise_gift().size();i++){
                    franchiseList.add(franchiseData.getFranchise_gift().get(i).getValue()+"");
                    franchiseListTitle.add(franchiseData.getFranchise_type().get(i).getEn_name()+"/"+franchiseData.getFranchise_type().get(i).getAr_name()) ;
                }
                franchiseDataMODEL.setPortsNumber(franchiseData.getFranchise_gift().size());
                franchiseList.add(space+"");
                franchiseList.add(investment+"");
                franchiseList.add(franchiseData.getFranchise().getPeriods().getName()+"");
                //franchiseList.add(".");
                //franchiseList.add(".");

                franchiseList.add(franchiseData.getFranchise().getOwner_ship_commission()+" %");
                franchiseList.add(franchiseData.getFranchise().getMarketing_commission()+" %");
                String country="";
                for(int i=0;i<franchiseData.getFranchise().getFranchise_market().size();i++){
                    country+=franchiseData.getFranchise().getFranchise_market().get(i).getEn_name()+"\n";
                }
                franchiseList.add(country+"");

                franchiseDataMODEL.setAbout(franchiseData.getFranchise().getDetails());
                franchiseDataMODEL.setData(franchiseList);
                franchiseDataMODEL.setFranchiseType(franchiseListTitle);
                franchiseDataMODEL.setImages(franchiseData.getFranchise().getImages());
                franchiseDataMODEL.setMainImage(franchiseData.getFranchise().getImage());
                franchiseDataMODEL.setUser_id(franchiseData.getFranchise().getUser_id());

                franchisesResults.setValue(franchiseDataMODEL);

            }

            @Override
            public void onError(Throwable e) {
                CustomProgressDialog.clodseProgress();
                Log.v("rrrr",e.toString());
            }
        });

        return franchisesResults;
    }
    @SuppressLint("CheckResult")
    public LiveData<UserModel> getOwnerData(int id) {
       MutableLiveData<UserModel> userData=new MutableLiveData<>();
        FranchiseRepositry.get_userdata_by_franchise(id).subscribeWith(new SingleObserver<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {
            }
            @Override
            public void onSuccess(ResponseBody user) {
                UserModel userModel=new UserModel();
                try {
                    JSONObject jsonObject=new JSONObject(user.string());
                    JSONObject data=jsonObject.getJSONObject("data");
                    userModel.setPhone(data.getString("company_phone"));
                    userModel.setName(data.getString("name"));
                    userModel.setEmail(data.getString("email"));
                    Log.v("ooooooo",data.getString("name"));
                    userData.setValue(userModel);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                CustomProgressDialog.clodseProgress();
                Log.v("rrrr",e.toString());
            }
        });

        return userData;
    }


    public  void saveFranchise(FavModel favModel, Context context) {
        if (favRepositry.insert(favModel)) {
            Toast.makeText(context, "Done", Toast.LENGTH_LONG).show();
        }
    }

}
