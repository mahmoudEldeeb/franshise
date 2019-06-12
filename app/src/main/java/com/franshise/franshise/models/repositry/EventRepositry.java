package com.franshise.franshise.models.repositry;

import com.franshise.franshise.models.ResultNetworkModels.EventsModelResults;
import com.franshise.franshise.models.ResultNetworkModels.JobsResults;
import com.franshise.franshise.models.networks.RetrofitConnections;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class EventRepositry {




    public static Single<EventsModelResults> courses(String lang,int country_id){
        return RetrofitConnections.getNetworkConnection().courses(lang,country_id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    public static Single<EventsModelResults> services(String lang){
        return RetrofitConnections.getNetworkConnection().services(lang)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }public static Single<EventsModelResults> conferances(String lang){
        return RetrofitConnections.getNetworkConnection().conferances(lang)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    public static Single<JobsResults> get_job(String lang){
        return RetrofitConnections.getNetworkConnection().get_job()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
