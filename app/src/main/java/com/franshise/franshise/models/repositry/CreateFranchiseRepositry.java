package com.franshise.franshise.models.repositry;

import android.support.annotation.NonNull;
import android.util.Log;

import com.franshise.franshise.models.ResultNetworkModels.DataResult;
import com.franshise.franshise.models.ResultNetworkModels.PeriodResult;
import com.franshise.franshise.models.dataModels.CreatFranchiseModel;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.models.networks.RetrofitConnections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class CreateFranchiseRepositry {


    public static Single<StatusModel> create(List<MultipartBody.Part> imagespart, MultipartBody.Part imagepart, CreatFranchiseModel model, int id){

        return RetrofitConnections.getNetworkConnection(0).createfranchise(
                createPartFromString(model.getName()),
                createPartFromString(id+""),
                createPartFromString(model.getDetails())
                ,createPartFromString(""+model.getCat_id()),
                createPartFromString(model.getOrigin()+""),
                createPartFromString(model.getDate()),
                createPartFromString(model.getPeriodId()+""),
                createPartFromString(model.getLoca_exist()+"")
                ,createPartFromString(model.getLocal_under()+""),
                createPartFromString(model.getOut_exist()+""),
                createPartFromString(model.getOut_under()+""),
                createPartFromString(model.getOtherCommsionName()+""),
                createPartFromString(model.getOtherCommsion()+""),
                imagespart,imagepart,
                model.getFranchiseTypeIds(),
                model.getFranchiseTypeValues(),
                createPartFromString(model.getPropertyCommsion()+""),
                createPartFromString(model.getMarketCommsion()+""),
                model.getMarketCounties(),
                model.getSpaceModels()
                ,model.getInvestModels())

                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }
    public static Single<StatusModel> updateFranchise(int franchiseId,List<MultipartBody.Part> imagespart, MultipartBody.Part imagepart,List<Integer>ids, CreatFranchiseModel model, int id){

        return RetrofitConnections.getNetworkConnection(0).updatefranchise(
                createPartFromString(franchiseId+""),
                createPartFromString(model.getName()),
                createPartFromString(id+""),
                createPartFromString(model.getDetails())
                ,createPartFromString(""+model.getCat_id()),
                createPartFromString(model.getOrigin()+""),
                createPartFromString(model.getDate()),
                createPartFromString(model.getPeriodId()+""),
                createPartFromString(model.getLoca_exist()+"")
                ,createPartFromString(model.getLocal_under()+""),
                createPartFromString(model.getOut_exist()+""),
                createPartFromString(model.getOut_under()+""),
                createPartFromString(model.getOtherCommsionName()+""),
                createPartFromString(model.getOtherCommsion()+""),
                imagespart,imagepart,ids,
                model.getFranchiseTypeIds(),
                model.getFranchiseTypeValues(),
                createPartFromString(model.getPropertyCommsion()+""),
                createPartFromString(model.getMarketCommsion()+""),
                model.getMarketCounties(),
                model.getSpaceModels()
                ,model.getInvestModels())

                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }

    public static Single<StatusModel> updateFranchise(int franchiseId,List<MultipartBody.Part> imagespart,List<Integer>ids, CreatFranchiseModel model, int id){

        return RetrofitConnections.getNetworkConnection(0).updatefranchise(
                createPartFromString(franchiseId+""),
                createPartFromString(model.getName()),
                createPartFromString(id+""),
                createPartFromString(model.getDetails())
                ,createPartFromString(""+model.getCat_id()),
                createPartFromString(model.getOrigin()+""),
                createPartFromString(model.getDate()),
                createPartFromString(model.getPeriodId()+""),
                createPartFromString(model.getLoca_exist()+"")
                ,createPartFromString(model.getLocal_under()+""),
                createPartFromString(model.getOut_exist()+""),
                createPartFromString(model.getOut_under()+""),
                createPartFromString(model.getOtherCommsionName()+""),
                createPartFromString(model.getOtherCommsion()+""),
                imagespart,ids,
                model.getFranchiseTypeIds(),
                model.getFranchiseTypeValues(),
                createPartFromString(model.getPropertyCommsion()+""),
                createPartFromString(model.getMarketCommsion()+""),
                model.getMarketCounties(),
                model.getSpaceModels()
                ,model.getInvestModels())

                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }
    public static Single<StatusModel> updateFranchise(int franchiseId, MultipartBody.Part imagepart,List<Integer>ids, CreatFranchiseModel model, int id){

        return RetrofitConnections.getNetworkConnection(0).updatefranchise(
                createPartFromString(franchiseId+""),
                createPartFromString(model.getName()),
                createPartFromString(id+""),
                createPartFromString(model.getDetails())
                ,createPartFromString(""+model.getCat_id()),
                createPartFromString(model.getOrigin()+""),
                createPartFromString(model.getDate()),
                createPartFromString(model.getPeriodId()+""),
                createPartFromString(model.getLoca_exist()+"")
                ,createPartFromString(model.getLocal_under()+""),
                createPartFromString(model.getOut_exist()+""),
                createPartFromString(model.getOut_under()+""),
                createPartFromString(model.getOtherCommsionName()+""),
                createPartFromString(model.getOtherCommsion()+""),
                imagepart,ids,
                model.getFranchiseTypeIds(),
                model.getFranchiseTypeValues(),
                createPartFromString(model.getPropertyCommsion()+""),
                createPartFromString(model.getMarketCommsion()+""),
                model.getMarketCounties(),
                model.getSpaceModels()
                ,model.getInvestModels())

                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }
    public static Single<StatusModel> updateFranchise(int franchiseId,List<Integer>ids, CreatFranchiseModel model, int id){

        return RetrofitConnections.getNetworkConnection(0).updatefranchise(
                createPartFromString(franchiseId+""),
                createPartFromString(model.getName()),
                createPartFromString(id+""),
                createPartFromString(model.getDetails())
                ,createPartFromString(""+model.getCat_id()),
                createPartFromString(model.getOrigin()+""),
                createPartFromString(model.getDate()),
                createPartFromString(model.getPeriodId()+""),
                createPartFromString(model.getLoca_exist()+"")
                ,createPartFromString(model.getLocal_under()+""),
                createPartFromString(model.getOut_exist()+""),
                createPartFromString(model.getOut_under()+""),
                createPartFromString(model.getOtherCommsionName()+""),
                createPartFromString(model.getOtherCommsion()+""),
                ids,
                model.getFranchiseTypeIds(),
                model.getFranchiseTypeValues(),
                createPartFromString(model.getPropertyCommsion()+""),
                createPartFromString(model.getMarketCommsion()+""),
                model.getMarketCounties(),
                model.getSpaceModels()
                ,model.getInvestModels())

                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }



    @NonNull
    private static RequestBody createPartFromString(String descriptionString) {
      //   RequestBody.create(
        //        MediaType.parse("multipart/form-data"), descriptionString);
        return RequestBody.create(MediaType.parse("text/plain"), descriptionString);

    }



    public static Single<PeriodResult> getPeriod(){
        return RetrofitConnections.getNetworkConnection().getPeriod()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }
    public static Single<DataResult> getfranchisetype(){
        return RetrofitConnections.getNetworkConnection().getfranchisetype()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }

}
