package com.franshise.franshise.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.models.repositry.MessagesRepositry;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class SendMessageViewModel extends ViewModel{
    MutableLiveData<StatusModel>result;
    public MutableLiveData<StatusModel>sendSugetion(String type,String from,String email,String country,String message){
        result=new MutableLiveData<>();
        MessagesRepositry.sendSuggestion(type,from,email, country, message).subscribeWith(new SingleObserver<StatusModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(StatusModel statusModel) {
                result.setValue(statusModel);
            }

            @Override
            public void onError(Throwable e) {
                StatusModel s=new StatusModel();
                s.setStatus(0);
                s.setMessage(e.getMessage());
                result.setValue(s);
            }
        });

        return result;
    }
    public MutableLiveData<StatusModel>send_for_consultant(String from,String email,String country,String message){
        result=new MutableLiveData<>();
        MessagesRepositry.send_for_consultant(from,email, country, message).subscribeWith(new SingleObserver<StatusModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(StatusModel statusModel) {
                result.setValue(statusModel);
            }

            @Override
            public void onError(Throwable e) {
                StatusModel s=new StatusModel();
                s.setStatus(0);
                s.setMessage(e.getMessage());
                result.setValue(s);
            }
        });

        return result;
    }

    public MutableLiveData<StatusModel>send_for_owner(String from,String email,String country,String message,int id,int sender){
        result=new MutableLiveData<>();
        MessagesRepositry.send_for_owner(from,email, country, message,id,sender).subscribeWith(new SingleObserver<StatusModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(StatusModel statusModel) {
                result.setValue(statusModel);
            }

            @Override
            public void onError(Throwable e) {
                StatusModel s=new StatusModel();
                s.setStatus(0);
                s.setMessage(e.getMessage());
                result.setValue(s);
            }
        });

        return result;
    }

}
