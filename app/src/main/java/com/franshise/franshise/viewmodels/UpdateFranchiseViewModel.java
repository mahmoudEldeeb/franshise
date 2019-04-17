package com.franshise.franshise.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.franshise.franshise.models.ResultNetworkModels.FranchiseDataMODEL;
import com.franshise.franshise.models.ResultNetworkModels.FranchiseResultsView;
import com.franshise.franshise.models.dataModels.FranchiseData;
import com.franshise.franshise.models.repositry.FranchiseRepositry;
import com.franshise.franshise.utils.CustomProgressDialog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class UpdateFranchiseViewModel extends ViewModel {
    public LiveData<FranchiseData> getFranchises(Context context, int id) {

        MutableLiveData<FranchiseData>franchisesResults=new MutableLiveData<>();
        CustomProgressDialog.showProgress(context);
        FranchiseRepositry.getFranchises(id).subscribeWith(new SingleObserver<FranchiseResultsView>() {
            @Override
            public void onSubscribe(Disposable d) {
            }
            @Override
            public void onSuccess(FranchiseResultsView franchiseResultModel) {
                CustomProgressDialog.clodseProgress();
                franchisesResults.setValue(franchiseResultModel.getData());
            }

            @Override
            public void onError(Throwable e) {
                CustomProgressDialog.clodseProgress();
                Log.v("rrrr",e.toString());
            }
        });

        return franchisesResults;
    }
}
