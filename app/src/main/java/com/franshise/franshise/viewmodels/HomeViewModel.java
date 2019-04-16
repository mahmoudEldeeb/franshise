package com.franshise.franshise.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.franshise.franshise.models.ResultNetworkModels.BannersResult;
import com.franshise.franshise.models.ResultNetworkModels.CategorysResult;
import com.franshise.franshise.models.ResultNetworkModels.FranchisesResult;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.UserModel;
import com.franshise.franshise.models.repositry.FranchiseRepositry;
import com.franshise.franshise.models.repositry.HomeRepositry;
import com.franshise.franshise.models.repositry.LoginRepositry;
import com.franshise.franshise.models.repositry.MarkerOfCategoryRepositry;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class HomeViewModel extends ViewModel {
    MutableLiveData<BannersResult> bannersResultList;
    MutableLiveData<CategorysResult> categorysResults;

    public HomeViewModel() {
        bannersResultList=new MutableLiveData<>();

    }
    public LiveData<CategorysResult> getCategorys() {
        categorysResults=new MutableLiveData<>();
        HomeRepositry.getCategorys().subscribeWith(new SingleObserver<CategorysResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(CategorysResult categorysResult) {
                categorysResults.setValue(categorysResult);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
        return categorysResults;
    }

    public LiveData<BannersResult> getbannesr(String lang) {
        HomeRepositry.getHomeBannaers(lang).subscribeWith(new SingleObserver<BannersResult>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onSuccess(BannersResult bannersResult) {
                bannersResultList.setValue(bannersResult);
                // Log.v("ddddd",bannersResult.getData().get(0).getImagePath());
            }

            @Override
            public void onError(Throwable e) {

                Log.v("dddddd",e.toString());
            }
        });
        return bannersResultList;
    }


    public LiveData<FranchisesResult> specialFranchise() {

        MutableLiveData<FranchisesResult> franchiseResults=new MutableLiveData<>();
        FranchiseRepositry.specialFranchise().subscribeWith(new SingleObserver<FranchisesResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onSuccess(FranchisesResult categorysResult) {
                franchiseResults.setValue(categorysResult);
            }

            @Override
            public void onError(Throwable e) {
//                Log.v("qqqq",e.getMessage());
                return;
            }
        });
        return franchiseResults;
    }

}

