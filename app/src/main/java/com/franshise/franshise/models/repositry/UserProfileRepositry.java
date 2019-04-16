package com.franshise.franshise.models.repositry;

import com.franshise.franshise.models.ResultNetworkModels.FranchisesResult;
import com.franshise.franshise.models.ResultNetworkModels.UserProfileResult;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.models.dataModels.UserProfileModel;
import com.franshise.franshise.models.networks.RetrofitConnections;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.http.Field;

public class UserProfileRepositry
{

    public static Single<ResponseBody> user_profile(int id, String api_token){
        return RetrofitConnections.getNetworkConnection().user_profile(id,api_token)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<StatusModel> updateAllData( int id, String api_token,
                                                      String name, String email,
                                                      String phone, String country,
                                                      String city,
                                                      String company_name,
                                                      String company_type,  String company_phone,
                                                      String fax,
                                                      String admin_phone,
                                                      String admin_conversion){
        return RetrofitConnections.getNetworkConnection().update_profile(id,api_token,name,email,phone,country,city
                ,company_name,company_type
        ,company_phone,fax,admin_phone,admin_conversion)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<StatusModel> updateAllData(int id, String api_token,
                                                    String name, String email,
                                                    String phone, String country,
                                                    String city){
        return RetrofitConnections.getNetworkConnection().update_profile(id,api_token,name,email,phone,country,city
                )
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
