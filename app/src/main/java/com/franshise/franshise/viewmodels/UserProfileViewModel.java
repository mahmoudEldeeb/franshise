package com.franshise.franshise.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.franshise.franshise.models.ResultNetworkModels.UserProfileResult;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.UserModel;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.models.dataModels.UserProfileModel;
import com.franshise.franshise.models.repositry.MarkerRegisterRepositry;
import com.franshise.franshise.models.repositry.UserProfileRepositry;
import com.franshise.franshise.utils.CustomProgressDialog;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class UserProfileViewModel extends ViewModel {
    public MutableLiveData<StatusModel> checkCompleteProfile(int id, String apiToken) {
        MutableLiveData<StatusModel>check=new MutableLiveData<>();
        MarkerRegisterRepositry.checkCompleteProfile(id,apiToken).subscribeWith(new SingleObserver<StatusModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(StatusModel statusModel) {
                check.setValue(statusModel);
            }

            @Override
            public void onError(Throwable e) {
                StatusModel s=new StatusModel();
                s.setStatus(0);
                check.setValue(s);
            }
        });
        return check;
    }
    public MutableLiveData<UserProfileModel> user_profile(int id, String apiToken) {

        MutableLiveData<UserProfileModel>check=new MutableLiveData<>();
        UserProfileRepositry.user_profile(id,apiToken).subscribeWith(new SingleObserver<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(ResponseBody statusModel) {
                //Log.v("ppppp","ffffffff"+ statusModel());
                UserProfileModel userProfileModel=new UserProfileModel();
                try {
                    JSONObject jsonObject=new JSONObject(statusModel.string()).getJSONObject("data");
                    userProfileModel.setCompany_name(jsonObject.getString("company_name"));
                    userProfileModel.setCompany_type(jsonObject.getString("company_type"));
                    userProfileModel.setCompany_phone(jsonObject.getString("company_phone"));
                    userProfileModel.setFax(jsonObject.getString("fax"));
                    userProfileModel.setAdmin_phone(jsonObject.getString("admin_phone"));
                    userProfileModel.setAdmin_conversion(jsonObject.getString("admin_conversion"));
                  //  Log.v("pppp",jsonObject.getString("company_name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
               // Log.v("ppppppp", statusModel.getCompany_name());
                check.setValue(userProfileModel);
            }

            @Override
            public void onError(Throwable e) {
                StatusModel s=new StatusModel();
                Log.v("pppppp",e.getMessage());
               CustomProgressDialog.clodseProgress();
                //s.setStatus(0);
               // check.setValue(s);
            }
        });
        return check;
    }

    public MutableLiveData<Integer> updateAllData(Context c,int id, String api_token,
                                                  String name, String email,
                                                  String phone, String country,
                                                  String city,
                                                  String company_name,
                                                  String company_type, String company_phone,
                                                  String fax,
                                                  String admin_phone,
                                                  String admin_conversion) {

        MutableLiveData<Integer> reesult=new MutableLiveData<>();
        UserProfileRepositry.updateAllData(id,api_token,name,email,phone,country,city
                ,company_name,company_type
                ,company_phone,fax,admin_phone,admin_conversion
        ).subscribeWith(new SingleObserver<StatusModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(StatusModel statusModel) {
                /*try {
                    Log.v("ppppp",statusModel.string());
String values=statusModel.string();
                    JSONObject jsonObject=new JSONObject(values);//("{\"status\":1,\"message\":\"Operation success\",\"data\":{\"id\":16,\"name\":\"deebddd\",\"username\":\"deeb\",\"email\":\"mahmoud.eldeeb.fci@gmail.com\",\"country\":\"assuit3\",\"city\":\"dfy\",\"phone\":\"85882\",\"company_name\":\"deebqwe\",\"company_type\":\"Company\",\"company_phone\":\"585444\",\"fax\":\"585\",\"cat_id\":4,\"admin_phone\":\"dffg\",\"admin_conversion\":\"55\",\"code\":26080,\"activecode\":1,\"subscribe\":0,\"end_date\":null,\"created_at\":\"2019-04-03 09:59:42\",\"updated_at\":\"2019-04-16 16:43:12\"}}");
                    if(jsonObject.getInt("status")==1){
                        Log.v("qqqq","1");
                        UserModel userModel=new UserModel();
                        JSONObject userObject=jsonObject.getJSONObject("data");
                        userModel.setId(userObject.getInt("id"));
                        userModel.setName(userObject.getString("name"));
                        userModel.setUsername(userObject.getString("username"));
                        userModel.setEmail(userObject.getString("email"));
                        userModel.setCountry(userObject.getString("country"));
                        userModel.setCity(userObject.getString("city"));
                        userModel.setPhone(userObject.getString("phone"));
                       // userModel.setPassword(password);
                        new SharedPrefrenceModel(c).storeUserlessApi(userModel);

                        reesult.setValue(jsonObject.getInt("status"));
                    }
                    else {

                        reesult.setValue(jsonObject.getInt("status"));
                    }

                } catch (IOException e) {
Log.v("eeeeeeee",e.getMessage());
                    reesult.setValue(-1);
                    e.printStackTrace();
                } catch (JSONException e) {

                    Log.v("eeeeeee111e",e.toString());
                    reesult.setValue(-1);
                    e.printStackTrace();
                }*/
                reesult.setValue(statusModel.getStatus());

            }

            @Override
            public void onError(Throwable e) {
                StatusModel s=new StatusModel();
                Log.v("pppppp",e.getMessage());
                CustomProgressDialog.clodseProgress();
                reesult.setValue(-1);
                //s.setStatus(0);
                // check.setValue(s);
            }
        });
        return reesult;
    }

    public MutableLiveData<Integer> updateAllData(Context c,int id, String api_token,
                                                  String name, String email,
                                                  String phone, String country,
                                                  String city) {

        MutableLiveData<Integer> reesult=new MutableLiveData<>();
        UserProfileRepositry.updateAllData(id,api_token,name,email,phone,country,city
        ).subscribeWith(new SingleObserver<StatusModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(StatusModel statusModel) {
                reesult.setValue(statusModel.getStatus());

            }

            @Override
            public void onError(Throwable e) {
                StatusModel s=new StatusModel();
                Log.v("pppppp",e.getMessage());
                CustomProgressDialog.clodseProgress();
                reesult.setValue(-1);
                //s.setStatus(0);
                // check.setValue(s);
            }
        });
        return reesult;
    }

}
