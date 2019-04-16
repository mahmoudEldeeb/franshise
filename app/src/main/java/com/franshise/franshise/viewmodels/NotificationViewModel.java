package com.franshise.franshise.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.franshise.franshise.models.ResultNetworkModels.CategorysResult;
import com.franshise.franshise.models.ResultNetworkModels.FranchisesResult;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.models.repositry.FranchiseRepositry;
import com.franshise.franshise.models.repositry.MarkerOfCategoryRepositry;
import com.franshise.franshise.utils.CustomProgressDialog;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class NotificationViewModel extends ViewModel {
    MutableLiveData<FranchisesResult> categorysResults;
    public LiveData<FranchisesResult> getLastFranchise() {
        categorysResults=new MutableLiveData<>();
        MarkerOfCategoryRepositry.getLastFranchise().subscribeWith(new SingleObserver<FranchisesResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onSuccess(FranchisesResult categorysResult) {

                categorysResults.setValue(categorysResult);
            }

            @Override
            public void onError(Throwable e) {
                Log.v("qqqq",e.getMessage());
                return;
            }
        });
        return categorysResults;
    }

    public LiveData<FranchisesResult> getMyFranchise(int id, Context context) {
        categorysResults=new MutableLiveData<>();
        CustomProgressDialog.showProgress(context);
        MarkerOfCategoryRepositry.getMyFranchises(id).subscribeWith(new SingleObserver<FranchisesResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onSuccess(FranchisesResult categorysResult) {

                Log.v("qqqq","fghj");
                categorysResults.setValue(categorysResult);
            }

            @Override
            public void onError(Throwable e) {
                CustomProgressDialog.clodseProgress();
//                Log.v("qqqq",e.getMessage());
                return;
            }
        });
        return categorysResults;
    }
    public LiveData<StatusModel> deleteFranchise(int id) {
       MutableLiveData<StatusModel> results=new MutableLiveData<>();
        FranchiseRepositry.deleteFranchises(id).subscribeWith(new SingleObserver<StatusModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onSuccess(StatusModel result) {

                Log.v("qqqq","fghj");
                results.setValue(result);
            }

            @Override
            public void onError(Throwable e) {
                Log.v("qqqq",e.getMessage());
                StatusModel s=new StatusModel();s.setStatus(0);
                results.setValue(s);
                return;
            }
        });
        return results;
    }

}
