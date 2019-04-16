package com.franshise.franshise.models.repositry;

import com.franshise.franshise.models.ResultNetworkModels.BannersResult;
import com.franshise.franshise.models.ResultNetworkModels.CategorysResult;
import com.franshise.franshise.models.networks.RetrofitConnections;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeRepositry {
public static Single<BannersResult> getHomeBannaers(String lang){
        return RetrofitConnections.getNetworkConnection().getHomeBanners(lang)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
}
public static Single<CategorysResult> getCategorys(){
                return RetrofitConnections.getNetworkConnection().getAllCategorys()
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }

}
