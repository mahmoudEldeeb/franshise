package com.franshise.franshise.models.repositry;

import com.franshise.franshise.models.ResultNetworkModels.CategorysResult;
import com.franshise.franshise.models.ResultNetworkModels.FranchiseResultModel;
import com.franshise.franshise.models.ResultNetworkModels.FranchiseResultsView;
import com.franshise.franshise.models.ResultNetworkModels.FranchisesResult;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.models.networks.RetrofitConnections;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class FranchiseRepositry {
    public static Single<FranchiseResultsView> getFranchises(int id){
        return RetrofitConnections.getNetworkConnection().getFranchises(id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    public static Single<StatusModel> deleteFranchises(int id){
        return RetrofitConnections.getNetworkConnection().deleteFranchise(id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<FranchisesResult> specialFranchise(){
        return RetrofitConnections.getNetworkConnection().specialFranchise()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    public static Single<ResponseBody> get_userdata_by_franchise(int id){
        return RetrofitConnections.getNetworkConnection().get_userdata_by_franchise(id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }



}
