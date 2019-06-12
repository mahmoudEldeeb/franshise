package com.franshise.franshise.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.franshise.franshise.models.ResultNetworkModels.DataResult;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.models.repositry.LoginRepositry;
import com.franshise.franshise.models.repositry.RepositryData;

import java.io.IOException;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class AddJobsViewModel extends ViewModel {
    public MutableLiveData<DataResult> getCountries(Context context){
        MutableLiveData<DataResult>  countries=new MutableLiveData<>();
        LoginRepositry.getCountries()
                .subscribeWith(new SingleObserver<DataResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(DataResult dataResult) {

                        countries.setValue(dataResult);
                        Log.v("eeeee",dataResult.getData().get(0).getAr_name());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
        return countries;
    }

    public MutableLiveData<DataResult> getQualification(Context context){
        MutableLiveData<DataResult>  getQualification=new MutableLiveData<>();
        RepositryData.getQualification()
                .subscribeWith(new SingleObserver<DataResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(DataResult dataResult) {

                        getQualification.setValue(dataResult);
                        Log.v("eeeee",dataResult.getData().get(0).getAr_name());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
        return getQualification;
    }

    public MutableLiveData<DataResult> city_with_country(int  country_id){
        MutableLiveData<DataResult>  getQualification=new MutableLiveData<>();
        RepositryData.city_with_country(country_id)
                .subscribeWith(new SingleObserver<DataResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(DataResult dataResult) {

                        getQualification.setValue(dataResult);
                        Log.v("eeeee",dataResult.getData().get(0).getAr_name());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
        return getQualification;
    }

    public  MutableLiveData<StatusModel>  createJob(String name,int number_require,
                                   int qualification_id, String details,
                                   int country_id, List<Integer> city_id, int start, int end,
                                   int gender, String currency, int number){
        MutableLiveData<StatusModel>  getQualification=new MutableLiveData<>();
        RepositryData.createJob(name,number_require,qualification_id,details,country_id,
                city_id,start,end,gender,currency,number)
                .subscribeWith(new SingleObserver<StatusModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(StatusModel responseBody) {
                        getQualification.setValue(responseBody);
                    }

                    @Override
                    public void onError(Throwable e) {
                    StatusModel statusModel=new StatusModel();
                    statusModel.setStatus(0);
                    statusModel.setMessage(e.getMessage());
                    getQualification.setValue(statusModel);
                    }
                });
        return getQualification;
    }

    public  MutableLiveData<StatusModel>  updateJob(int id,String name,int number_require,
                                                    int qualification_id, String details,
                                                    int country_id, List<Integer> city_id, int start, int end,
                                                    int gender, String currency, int number){
        MutableLiveData<StatusModel>  getQualification=new MutableLiveData<>();
        RepositryData.updateJob(id,name,number_require,qualification_id,details,country_id,
                city_id,start,end,gender,currency,number)
                .subscribeWith(new SingleObserver<StatusModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(StatusModel responseBody) {
                        getQualification.setValue(responseBody);
                    }

                    @Override
                    public void onError(Throwable e) {
                        StatusModel statusModel=new StatusModel();
                        statusModel.setStatus(0);
                        statusModel.setMessage(e.getMessage());
                        getQualification.setValue(statusModel);
                    }
                });
        return getQualification;
    }
    public  MutableLiveData<StatusModel>  deleteJob(int id){
        MutableLiveData<StatusModel>  getQualification=new MutableLiveData<>();
        RepositryData.deleteJob(id)
                .subscribeWith(new SingleObserver<StatusModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(StatusModel responseBody) {
                        getQualification.setValue(responseBody);
                    }

                    @Override
                    public void onError(Throwable e) {
                        StatusModel statusModel=new StatusModel();
                        statusModel.setStatus(0);
                        statusModel.setMessage(e.getMessage());
                        getQualification.setValue(statusModel);
                    }
                });
        return getQualification;
    }

}
