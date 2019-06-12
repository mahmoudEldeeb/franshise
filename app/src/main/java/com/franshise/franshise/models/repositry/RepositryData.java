package com.franshise.franshise.models.repositry;


import com.franshise.franshise.models.ResultNetworkModels.CategorysResult;
import com.franshise.franshise.models.ResultNetworkModels.DataResult;
import com.franshise.franshise.models.ResultNetworkModels.FundCompanyModelResult;
import com.franshise.franshise.models.dataModels.PayModel;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.models.networks.RetrofitConnections;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.http.Field;

public class RepositryData {

    public static Single<FundCompanyModelResult> getCompanies(){
        return RetrofitConnections.getNetworkConnection().getCompanies()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<DataResult> getQualification(){
        return RetrofitConnections.getNetworkConnection().getQualification()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    public static Single<DataResult> city_with_country(int country_id){
        return RetrofitConnections.getNetworkConnection().city_with_country(country_id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    public static Single<StatusModel> createJob( String name, int number_require,
                                                int qualification_id, String details,
                                                int country_id, List<Integer> city_id, int start, int end,
                                                int gender, String currency, int number){
        return RetrofitConnections.getNetworkConnection(0).createJob(name,number_require,qualification_id,details,country_id,
                city_id,start,end,gender,currency,number)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    public static Single<StatusModel> updateJob( int id,String name, int number_require,
                                                 int qualification_id, String details,
                                                 int country_id, List<Integer> city_id, int start, int end,
                                                 int gender, String currency, int number){
        return RetrofitConnections.getNetworkConnection(0).updateJob(id,name,number_require,qualification_id,details,country_id,
                city_id,start,end,gender,currency,number)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    public static Single<StatusModel> deleteJob( int id){
        return RetrofitConnections.getNetworkConnection().delete(id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    public static Single<PayModel> pay_way(){
        return RetrofitConnections.getNetworkConnection().pay_way()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
