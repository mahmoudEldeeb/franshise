package com.franshise.franshise.models.repositry;

import com.franshise.franshise.models.ResultNetworkModels.CategorysResult;
import com.franshise.franshise.models.ResultNetworkModels.DataResult;
import com.franshise.franshise.models.ResultNetworkModels.FranchisesResult;
import com.franshise.franshise.models.networks.RetrofitConnections;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchRepositry {

    public static Single<DataResult> getMoney(){
        return RetrofitConnections.getNetworkConnection().getMoney()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<FranchisesResult> search(String name,int min,int max,int cat_id){
        return RetrofitConnections.getNetworkConnection().search(name,min,max,cat_id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }




}
