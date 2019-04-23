package com.franshise.franshise.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.franshise.franshise.models.ResultNetworkModels.DataResult;
import com.franshise.franshise.models.ResultNetworkModels.EventsModelResults;
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
    public MutableLiveData<EventsModelResults> courses(String lang){
        MutableLiveData<EventsModelResults>events=new MutableLiveData<>();
        EventRepositry.courses(lang)
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
    public MutableLiveData<EventsModelResults> services(String lang){
        MutableLiveData<EventsModelResults>events=new MutableLiveData<>();
        EventRepositry.services(lang)
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

                        Log.v("aaaa",e.getMessage());

                    }
                });
        return events;
    }
    public MutableLiveData<EventsModelResults> jobs(String lang){
        MutableLiveData<EventsModelResults>events=new MutableLiveData<>();
        EventRepositry.jobs(lang)
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

}
