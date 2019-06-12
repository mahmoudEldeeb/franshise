package com.franshise.franshise.models.repositry;

import android.database.Observable;

import com.franshise.franshise.models.ResultNetworkModels.MessageResults;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.models.networks.RetrofitConnections;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MessagesRepositry {
    public static Single<StatusModel> sendSuggestion(String type,String from,String email,String country,String message, int sender){
        return RetrofitConnections.getNetworkConnection().suggestions(type,from,email, country, message, sender)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<StatusModel> send_for_consultant(String from,String email,String country,String message){
        return RetrofitConnections.getNetworkConnection().send_for_consultant(from,email, country, message)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<StatusModel> send_for_owner(String from,String email,String country,String message,int id,int sender){
        return RetrofitConnections.getNetworkConnection().send_for_owner(from,email, country, message,id,sender)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<MessageResults> getMessages(int user_id){
        return RetrofitConnections.getNetworkConnection().getMessages(user_id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }



}
