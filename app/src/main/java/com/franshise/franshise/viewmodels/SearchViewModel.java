package com.franshise.franshise.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.franshise.franshise.models.ResultNetworkModels.DataResult;
import com.franshise.franshise.models.ResultNetworkModels.FranchisesResult;
import com.franshise.franshise.models.repositry.CreateFranchiseRepositry;
import com.franshise.franshise.models.repositry.SearchRepositry;
import com.franshise.franshise.utils.CustomProgressDialog;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class SearchViewModel extends ViewModel {
    public MutableLiveData<DataResult> getMoney(){
        MutableLiveData<DataResult>list=new MutableLiveData<>();
        SearchRepositry.getMoney().subscribeWith(new SingleObserver<DataResult>() {
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
    public MutableLiveData<FranchisesResult> search(String name, int min, int max, int cat_id){
        MutableLiveData<FranchisesResult>list=new MutableLiveData<>();
        SearchRepositry.search(name,min,max,cat_id).subscribeWith(new SingleObserver<FranchisesResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(FranchisesResult franchisesResult) {
                list.setValue(franchisesResult);
            }

            @Override
            public void onError(Throwable e) {
                CustomProgressDialog.clodseProgress();
            }
        });
        return list;
    }
}
