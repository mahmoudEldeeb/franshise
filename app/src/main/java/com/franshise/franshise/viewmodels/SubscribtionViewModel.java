package com.franshise.franshise.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import com.franshise.franshise.models.ResultNetworkModels.SubscribtioResult;
import com.franshise.franshise.models.dataModels.PayModel;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.models.repositry.MessagesRepositry;
import com.franshise.franshise.models.repositry.RepositryData;
import com.franshise.franshise.models.repositry.SubscribtioRepositry;
import com.franshise.franshise.utils.CustomProgressDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SubscribtionViewModel extends ViewModel {
    MutableLiveData<SubscribtioResult> result;
    Context context;
    public MutableLiveData<SubscribtioResult>subscription_types(){
        result=new MutableLiveData<>();
        SubscribtioRepositry.subscription_types().subscribeWith(new SingleObserver<SubscribtioResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(SubscribtioResult subscribtioResult) {
            result.setValue(subscribtioResult);
            }

            @Override
            public void onError(Throwable e) {
                CustomProgressDialog.clodseProgress();
            }
        }
        );
        return result;
    }

    public MutableLiveData<PayModel>pay_way(){
       MutableLiveData<PayModel> result1=new MutableLiveData<>();
        RepositryData.pay_way().subscribeWith(new SingleObserver<PayModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(PayModel payModel) {
                result1.setValue(payModel);
            }

            @Override
            public void onError(Throwable e) {
CustomProgressDialog.clodseProgress();
            }
        });
    return result1;
    }

    public MutableLiveData<StatusModel>new_subscription(int user_id, int sub_id, Uri image,Context c){
context=c;
        MultipartBody.Part imagepart=prepareFilePart("image",image);
        MutableLiveData<StatusModel> sub_result=new MutableLiveData<>();
        SubscribtioRepositry.new_subscription(user_id,sub_id,imagepart).subscribeWith(new SingleObserver<StatusModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(StatusModel statusModel) {
sub_result.setValue(statusModel);
            }

            @Override
            public void onError(Throwable e) {
                Log.v("ddddd",e.getMessage());
                CustomProgressDialog.clodseProgress();
            }
        });
        return sub_result;
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        // File file = FileUtils.getFile(this, fileUri);
        // create RequestBody instance from file
        File file= new File(getRealPathFromURI(fileUri));
        Log.v("wwww",getRealPathFromURI(fileUri)+"   ");

        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(Objects.requireNonNull(context.getContentResolver().getType(fileUri))),
                        file
                );
        // RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/from-data"),file);
        // MultipartBody.Part is used to send also the actual file name
        try{
            return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
        }catch (IllegalArgumentException e) {
            String name=file.getName().substring(file.getName().indexOf("."),file.getName().length());
            Log.v("eeeeee",name+"     "+file.getName());
            Locale locale = new Locale("en");
            String  filename = "Franchise"
                    +new SimpleDateFormat("yyyyMMdd_HHmmss",locale).format(new Date())+
                    name;
            Log.v("eeeeeeee",filename);
            return MultipartBody.Part.createFormData(partName, filename, requestFile);
        }
    }

    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }


}
