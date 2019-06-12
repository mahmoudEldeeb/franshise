package com.franshise.franshise.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import com.franshise.franshise.models.ResultNetworkModels.DataResult;
import com.franshise.franshise.models.ResultNetworkModels.PeriodResult;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.CreatFranchiseModel;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.models.repositry.CreateFranchiseRepositry;
import com.franshise.franshise.models.repositry.LoginRepositry;
import com.franshise.franshise.utils.CustomProgressDialog;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;

public class CreateFranchiseViewModel extends ViewModel {
Context context;
    public MutableLiveData<Integer> create(CreatFranchiseModel model,Context c){
Log.v("rrrrrr",model.getMarketCounties().get(0)+"");
//        Log.v("rrrrrr",model.().get(0)+"");

        context=c;
        MutableLiveData<Integer>resuls=new MutableLiveData<>();
        HashMap<String,List<MultipartBody.Part>>map=new HashMap<>();
        List<MultipartBody.Part>imagespart=new ArrayList<>();
        Log.v("ppppp2",model.getFranchiseImage()+"");
        MultipartBody.Part imagepart=prepareFilePart("image",model.getFranchiseImage());
        for (int i=0;i<model.getImageOfProduct().size();i++){
            //imagespart.set
            imagespart.add(prepareFilePart("banners["+i+"]",model.getImageOfProduct().get(i)));
            // imagespart.add(prepareFilePart(""+i,images.get(i)));
        }
        CreateFranchiseRepositry.create(imagespart,imagepart,model,new SharedPrefrenceModel(context).getId()).subscribeWith(new SingleObserver<StatusModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(StatusModel responseBody) {

                    Log.v("wwwwwww",responseBody.getMessage()+"  "+responseBody.getStatus());
                    resuls.setValue(responseBody.getStatus());
            }

            @Override
            public void onError(Throwable e) {
                resuls.setValue(0);
                Log.v("wwww",e.toString());
            }
        });
    return resuls;}
public MutableLiveData<PeriodResult> getPeriod(){
MutableLiveData<PeriodResult>list=new MutableLiveData<>();
CreateFranchiseRepositry.getPeriod().subscribeWith(new SingleObserver<PeriodResult>() {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onSuccess(PeriodResult periodResult) {
            list.setValue(periodResult);
    }

    @Override
    public void onError(Throwable e) {
    }
});

return list;
}
    public MutableLiveData<DataResult> getfranchisetype(){
        MutableLiveData<DataResult>list=new MutableLiveData<>();
        CreateFranchiseRepositry.getfranchisetype().subscribeWith(new SingleObserver<DataResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(DataResult dataResult) {
                list.setValue(dataResult);
            }

            @Override
            public void onError(Throwable e) {
                Log.v("wwww",e.toString());
                CustomProgressDialog.clodseProgress();
            }
        });

        return list;
    }
    public MutableLiveData<DataResult> getCountry(int i){
        MutableLiveData<DataResult>list=new MutableLiveData<>();
       if(i==0) {
           LoginRepositry.getMarkets().subscribeWith(new SingleObserver<DataResult>() {
               @Override
               public void onSubscribe(Disposable d) {

               }

               @Override
               public void onSuccess(DataResult dataResult) {
                   list.setValue(dataResult);
               }

               @Override
               public void onError(Throwable e) {
               }
           });
       }
       else {
               LoginRepositry.getCountries().subscribeWith(new SingleObserver<DataResult>() {
                   @Override
                   public void onSubscribe(Disposable d) {

                   }

                   @Override
                   public void onSuccess(DataResult dataResult) {
                       list.setValue(dataResult);
                   }

                   @Override
                   public void onError(Throwable e) {
                   }
               });

       }
        return list;
    }
    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
//        Log.v("wwww",getRealPathFromURI(fileUri)+"   ");
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
        String path = String.valueOf(contentUri);
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
//        Log.v("ppppppppp4",path);
        return path;
    }

}

