package com.franshise.franshise.viewmodels;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.activites.ConformRegister;
import com.franshise.franshise.activites.LoginActivity;
import com.franshise.franshise.activites.Main;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.repositry.ConfirmRepositry;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class ConfirmViewModel extends ViewModel {

 public void verify(final Context context, String code){
    final ProgressDialog proDialog = ProgressDialog.show(context, "", "Looding.....");
     ConfirmRepositry.verfiy(new SharedPrefrenceModel(context).getEmail(),code).subscribeWith(new SingleObserver<ResponseBody>() {
         @Override
         public void onSubscribe(Disposable d) {

         }

         @Override
         public void onSuccess(ResponseBody responseBody) {
             try {
                 proDialog.dismiss();
                 String result=responseBody.string();
                 JSONObject jsonObject=new JSONObject(result);
                 int status=jsonObject.getInt("status");
                 if(status==1){
                     SharedPrefrenceModel sharedPrefrenceModel=new SharedPrefrenceModel(context);
                     sharedPrefrenceModel.setLogined(true);
                     sharedPrefrenceModel.confirmRegisteriation(true);
                     context.startActivity(new Intent(context,Main.class));

                 }
                 else {
                     Toast.makeText(context,R.string.code_wrong,Toast.LENGTH_LONG).show();
                }

             } catch (IOException e) {
                 Toast.makeText(context,"there is error connection try later",Toast.LENGTH_LONG).show();
                 e.printStackTrace();
             } catch (JSONException e) {
                 Toast.makeText(context,"there is error connection try later",Toast.LENGTH_LONG).show();
                 e.printStackTrace();
             }
         }

         @Override
         public void onError(Throwable e) {
             proDialog.dismiss();
             Toast.makeText(context,"there is error connection try later",Toast.LENGTH_LONG).show();
         }
     });
 }
}
