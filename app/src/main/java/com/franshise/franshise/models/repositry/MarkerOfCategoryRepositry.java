package com.franshise.franshise.models.repositry;

import com.franshise.franshise.models.ResultNetworkModels.CategorysResult;
import com.franshise.franshise.models.ResultNetworkModels.FranchisesResult;
import com.franshise.franshise.models.networks.RetrofitConnections;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MarkerOfCategoryRepositry {
//0223932919
    public static Single<CategorysResult> getCategorysBanner(int id){
        return RetrofitConnections.getNetworkConnection().getCategorysBanner(id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    public static Single<FranchisesResult> getLastFranchise(){
        return RetrofitConnections.getNetworkConnection().getLastFranchise()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    public static Single<FranchisesResult> getMyFranchises(int id){
        return RetrofitConnections.getNetworkConnection().getMyFranchise(id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
