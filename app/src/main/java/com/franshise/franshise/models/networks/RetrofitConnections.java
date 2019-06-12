package com.franshise.franshise.models.networks;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConnections {

    final static  String RootUrl="http://204.93.167.45/~helix/franchise/api/";
    //final static String RootUrl="http://192.168.1.7/franchise/api/";
    private static Retrofit getInstance(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        OkHttpClient okHttpClient = builder.build();

        return new Retrofit.Builder()
                .baseUrl(RootUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
    private static Retrofit getInstance(int i){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.MINUTES) // connect timeout

                .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                .readTimeout(5, TimeUnit.MINUTES); // read timeout

        OkHttpClient okHttpClient = builder.build();

        return new Retrofit.Builder()
                .baseUrl(RootUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
    public static  NetworkData getNetworkConnection(){
        return getInstance().create(NetworkData.class);
    }

    public static  NetworkData getNetworkConnection(int i){
        return getInstance(0).create(NetworkData.class);
    }
}
