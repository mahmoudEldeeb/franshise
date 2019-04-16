package com.franshise.franshise.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.UserModel;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.models.repositry.LoginRepositry;
import com.franshise.franshise.models.repositry.MarkerRegisterRepositry;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class MarkerOwnerRegisterViewModel extends ViewModel {
    MutableLiveData<StatusModel>check;
    MutableLiveData<StatusModel>completeRegister;

    public MutableLiveData<StatusModel> checkCompleteProfile(int id, String apiToken) {
        check=new MutableLiveData<>();
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
    public MutableLiveData<StatusModel>completeRegister(int id, String company_name,
                                                        String company_type,String company_phone,
                                                        String fax, int cat_id,
                                                        String admin_phone, String admin_conversion,String api_token){
        completeRegister=new MutableLiveData<>();
        MarkerRegisterRepositry.completeRegister(id,company_name,company_type,company_phone,fax,cat_id,admin_phone,admin_conversion,api_token)
                .subscribeWith(new SingleObserver<StatusModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }



                   @Override
                    public void onSuccess(StatusModel statusModel) {
                        completeRegister.setValue(statusModel);
                        Log.v("rrrr","2222222222222222222");
                    }

                    @Override
                    public void onError(Throwable e) {
                        StatusModel s=new StatusModel();
                        s.setStatus(0);
                        s.setMessage("there is error try later");
                        completeRegister.setValue(s);

                        Log.v("rrrr",e.getMessage());
                    }
                });
        return  completeRegister;
    }
}
