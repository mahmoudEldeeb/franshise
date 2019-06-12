package com.franshise.franshise.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.models.ResultNetworkModels.DataResult;
import com.franshise.franshise.models.ResultNetworkModels.EventsModelResults;
import com.franshise.franshise.models.ResultNetworkModels.JobsResults;
import com.franshise.franshise.models.repositry.EventRepositry;
import com.franshise.franshise.models.repositry.LoginRepositry;
import com.franshise.franshise.utils.CustomProgressDialog;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class EventsViewModel extends ViewModel {
    public MutableLiveData<EventsModelResults> conferances(String lang){
        MutableLiveData<EventsModelResults>events=new MutableLiveData<>();
        EventRepositry.conferances(lang)
                .subscribeWith(new SingleObserver<EventsModelResults>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(EventsModelResults dataResult) {

                        events.setValue(dataResult);

                    }

                    @Override
                    public void onError(Throwable e) {
                        CustomProgressDialog.clodseProgress();

                    }
                });
        return events;
    }
    public MutableLiveData<EventsModelResults> courses(String lang,int country_id,Context context){
        MutableLiveData<EventsModelResults>events=new MutableLiveData<>();
        EventRepositry.courses(lang,country_id)
                .subscribeWith(new SingleObserver<EventsModelResults>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(EventsModelResults dataResult) {

                        events.setValue(dataResult);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                        CustomProgressDialog.clodseProgress();

                    }
                });
        return events;
    }
    public MutableLiveData<EventsModelResults> services(String lang){
        MutableLiveData<EventsModelResults>events=new MutableLiveData<>();
        EventRepositry.services(lang)
                .subscribeWith(new SingleObserver<EventsModelResults>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(EventsModelResults dataResult) {
                        Log.v("aaaa","ggggggg");
                        Log.v("aaaa",dataResult.getData().size()+"");
                        events.setValue(dataResult);

                    }

                    @Override
                    public void onError(Throwable e) {
                        CustomProgressDialog.clodseProgress();
                        Log.v("aaaa",e.getMessage());

                    }
                });
        return events;
    }
    public MutableLiveData<JobsResults> get_job(String lang){
        MutableLiveData<JobsResults>events=new MutableLiveData<>();
        EventRepositry.get_job(lang)
                .subscribeWith(new SingleObserver<JobsResults>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(JobsResults dataResult) {

                        events.setValue(dataResult);

                    }

                    @Override
                    public void onError(Throwable e) {
                        CustomProgressDialog.clodseProgress();

                    }
                });
        return events;
    }
    public MutableLiveData<DataResult> getCountries(){
        MutableLiveData<DataResult>countries=new MutableLiveData<>();
        LoginRepositry.getCountries()
                .subscribeWith(new SingleObserver<DataResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(DataResult dataResult) {

                        countries.setValue(dataResult);
                        Log.v("eeeee",dataResult.getData().get(0).getAr_name());
                /*for(int i=0;i<dataResult.getData().size();i++) {
                    if (new SharedPrefrenceModel(context).getLanguage().equals("en")) {

                        countries.getValue().set(i,dataResult.getData().get(i).getEn_name());
                    } else {

                        countries.getValue().set(i,dataResult.getData().get(i).getAr_name());
                    }
                }*/
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
        return countries;
    }

}
