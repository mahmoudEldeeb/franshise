package com.franshise.franshise.models.networks;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConnections {
    final static  String RootUrl="http://204.93.167.45/~helix/franchise/api/";
    private static Retrofit getInstance(){
        return new Retrofit.Builder()
                .baseUrl(RootUrl).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();

    }
    public static  NetworkData getNetworkConnection(){
        return getInstance().create(NetworkData.class);
    }
}
