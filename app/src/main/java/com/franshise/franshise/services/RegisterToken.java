package com.franshise.franshise.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.franshise.franshise.activites.AddFranchise;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.repositry.LoginRepositry;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.IOException;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class RegisterToken extends Service {
    public RegisterToken() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    String token;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
Log.v("oooo",intent.getStringExtra("token"));
        LoginRepositry.registerToken(intent.getStringExtra("token"),new SharedPrefrenceModel(this).getApiToken(),new SharedPrefrenceModel(RegisterToken.this).getId())
                .subscribeWith(new SingleObserver<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onSuccess(ResponseBody responseBody) {
                        try {
                            Log.v("dd",responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
Log.v("ddd",e.getMessage());
                    }
                });
        return super.onStartCommand(intent, flags, startId);
    }
}
