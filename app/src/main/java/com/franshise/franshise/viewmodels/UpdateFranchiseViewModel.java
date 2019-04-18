package com.franshise.franshise.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import com.franshise.franshise.models.ResultNetworkModels.DataResult;
import com.franshise.franshise.models.ResultNetworkModels.FranchiseDataMODEL;
import com.franshise.franshise.models.ResultNetworkModels.FranchiseResultsView;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.CreatFranchiseModel;
import com.franshise.franshise.models.dataModels.FranchiseData;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.models.repositry.CreateFranchiseRepositry;
import com.franshise.franshise.models.repositry.FranchiseRepositry;
import com.franshise.franshise.models.repositry.LoginRepositry;
import com.franshise.franshise.utils.CustomProgressDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UpdateFranchiseViewModel extends ViewModel {
    Context context;

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

    public MutableLiveData<Integer> updateFranchise(int franchiseId,List<Integer>deletedImagesIds,Uri franchiseImage,List<Uri>imagesProducts,CreatFranchiseModel model, Context c){
        context=c;
        MutableLiveData<Integer>resuls=new MutableLiveData<>();
        HashMap<String,List<MultipartBody.Part>> map=new HashMap<>();
        List<MultipartBody.Part>imagespart=new ArrayList<>();
        MultipartBody.Part imagepart=prepareFilePart("image",franchiseImage);
        for (int i=0;i<imagesProducts.size();i++){
              imagespart.add(prepareFilePart("banner["+i+"]",imagesProducts.get(i)));
          }
        CreateFranchiseRepositry.updateFranchise(franchiseId,imagespart,imagepart,deletedImagesIds,model,new SharedPrefrenceModel(context).getId()).subscribeWith(new SingleObserver<StatusModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(StatusModel responseBody) {
                Log.v("wwww",responseBody.getMessage()+"");
                resuls.setValue(responseBody.getStatus());

            }

            @Override
            public void onError(Throwable e) {
                resuls.setValue(0);
                Log.v("wwww",e.toString());
            }
        });
        return resuls;}
    public MutableLiveData<Integer> updateFranchise(int franchiseId,List<Integer>deletedImagesIds,List<Uri>imagesProducts,CreatFranchiseModel model, Context c){
        context=c;
        MutableLiveData<Integer>resuls=new MutableLiveData<>();
        HashMap<String,List<MultipartBody.Part>> map=new HashMap<>();
        List<MultipartBody.Part>imagespart=new ArrayList<>();
       // MultipartBody.Part imagepart=prepareFilePart("image",franchiseImage);
        for (int i=0;i<imagesProducts.size();i++){
            imagespart.add(prepareFilePart("banner["+i+"]",imagesProducts.get(i)));
        }
        CreateFranchiseRepositry.updateFranchise(franchiseId,imagespart,deletedImagesIds,model,new SharedPrefrenceModel(context).getId()).subscribeWith(new SingleObserver<StatusModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(StatusModel responseBody) {
                Log.v("wwww",responseBody.getMessage()+"");
                resuls.setValue(responseBody.getStatus());

            }

            @Override
            public void onError(Throwable e) {
                resuls.setValue(0);
                Log.v("wwww",e.toString());
            }
        });
        return resuls;}
    public MutableLiveData<Integer> updateFranchise(int franchiseId,List<Integer>deletedImagesIds,Uri franchiseImage,CreatFranchiseModel model, Context c){
        context=c;
        MutableLiveData<Integer>resuls=new MutableLiveData<>();
        MultipartBody.Part imagepart=prepareFilePart("image",franchiseImage);

        CreateFranchiseRepositry.updateFranchise(franchiseId,imagepart,deletedImagesIds,model,new SharedPrefrenceModel(context).getId()).subscribeWith(new SingleObserver<StatusModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(StatusModel responseBody) {
                Log.v("wwww",responseBody.getMessage()+"");
                resuls.setValue(responseBody.getStatus());

            }

            @Override
            public void onError(Throwable e) {
                resuls.setValue(0);
                Log.v("wwww",e.toString());
            }
        });
        return resuls;}
    public MutableLiveData<Integer> updateFranchise(int franchiseId,List<Integer>deletedImagesIds,CreatFranchiseModel model, Context c){
        context=c;
        MutableLiveData<Integer>resuls=new MutableLiveData<>();

        CreateFranchiseRepositry.updateFranchise(franchiseId,deletedImagesIds,model,new SharedPrefrenceModel(context).getId()).subscribeWith(new SingleObserver<StatusModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(StatusModel responseBody) {
                Log.v("wwww",responseBody.getMessage()+"");
                resuls.setValue(responseBody.getStatus());

            }

            @Override
            public void onError(Throwable e) {
                resuls.setValue(0);
                Log.v("wwww",e.toString());
            }
        });
        return resuls;}


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
