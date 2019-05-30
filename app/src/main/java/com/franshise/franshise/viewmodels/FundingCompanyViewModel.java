package com.franshise.franshise.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.franshise.franshise.models.ResultNetworkModels.CategorysResult;
import com.franshise.franshise.models.ResultNetworkModels.FundCompanyModelResult;
import com.franshise.franshise.models.dataModels.FundingCompanyMode;
import com.franshise.franshise.models.repositry.RepositryData;
import com.franshise.franshise.utils.CustomProgressDialog;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class FundingCompanyViewModel extends ViewModel {
    MutableLiveData<List<FundingCompanyMode>> fundingCompanyViewModelMutableLiveData;
public MutableLiveData<List<FundingCompanyMode>> getCompanyies(Context context){
    fundingCompanyViewModelMutableLiveData=new MutableLiveData<>();
    CustomProgressDialog.showProgress(context);
    RepositryData.getCompanies().subscribeWith(new SingleObserver<FundCompanyModelResult>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onSuccess(FundCompanyModelResult fundCompanyModelResult) {
            fundingCompanyViewModelMutableLiveData.setValue(fundCompanyModelResult.getData());
            CustomProgressDialog.clodseProgress();

            Log.v("eeeeeee","rtyuio");
        }

        @Override
        public void onError(Throwable e) {
            Log.v("eeeeeee",e.getMessage());
            CustomProgressDialog.clodseProgress();
        }
    });
    return fundingCompanyViewModelMutableLiveData;
}


}
