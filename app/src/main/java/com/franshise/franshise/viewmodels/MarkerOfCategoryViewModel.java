package com.franshise.franshise.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.franshise.franshise.models.ResultNetworkModels.CategorysResult;
import com.franshise.franshise.models.ResultNetworkModels.DataResult;
import com.franshise.franshise.models.repositry.CreateFranchiseRepositry;
import com.franshise.franshise.models.repositry.HomeRepositry;
import com.franshise.franshise.models.repositry.MarkerOfCategoryRepositry;
import com.franshise.franshise.utils.CustomProgressDialog;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class MarkerOfCategoryViewModel extends ViewModel {
    MutableLiveData<CategorysResult> categorysResults;
    public LiveData<CategorysResult> getCategorysBanner(int id) {
        categorysResults=new MutableLiveData<>();
        MarkerOfCategoryRepositry.getCategorysBanner(id).subscribeWith(new SingleObserver<CategorysResult>() {
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
    public MutableLiveData<DataResult> getfranchisetype(){
        MutableLiveData<DataResult>list=new MutableLiveData<>();
        CreateFranchiseRepositry.getfranchisetype().subscribeWith(new SingleObserver<DataResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(DataResult dataResult) {
                list.setValue(dataResult);
            }

            @Override
            public void onError(Throwable e) {
                CustomProgressDialog.clodseProgress();
            }
        });

        return list;
    }

}
