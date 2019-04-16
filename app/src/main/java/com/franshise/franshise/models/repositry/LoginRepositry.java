package com.franshise.franshise.models.repositry;

import com.franshise.franshise.models.ResultNetworkModels.DataResult;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.models.networks.RetrofitConnections;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class LoginRepositry {

    public static Single<ResponseBody> login(String email,String password){
      return RetrofitConnections.getNetworkConnection().login(email, password)
              .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    public static Single<StatusModel> update_password(String username,String password,String api_token,int id){
        return RetrofitConnections.getNetworkConnection().update_password(username, password,api_token,id)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    public static Single<StatusModel> reset(String email){
        return RetrofitConnections.getNetworkConnection().reset(email)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<StatusModel> removetoken(String token, String api_token){
        return RetrofitConnections.getNetworkConnection().removeToken(token ,api_token)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<ResponseBody> registerToken(String token,String api_token){
        return RetrofitConnections.getNetworkConnection().registerToken(token,"android" ,api_token)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<ResponseBody> register(String name, String username,
                                                 String email, String password,
                                                String phone, String country, String city){
        return RetrofitConnections.getNetworkConnection().register(name, username,email,password,phone,country,city)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }



    public static Single<DataResult> getCountries(){
        return RetrofitConnections.getNetworkConnection().getCountries()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }
    public static Single<DataResult> getMarkets(){
        return RetrofitConnections.getNetworkConnection().getmarkets()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }

}
