package com.franshise.franshise.models.repositry;

import android.support.annotation.NonNull;

import com.franshise.franshise.models.ResultNetworkModels.FranchisesResult;
import com.franshise.franshise.models.ResultNetworkModels.SubscribtioResult;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.models.networks.RetrofitConnections;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SubscribtioRepositry
{
    public static Single<SubscribtioResult> subscription_types(){
        return RetrofitConnections.getNetworkConnection().subscription_types()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static Single<StatusModel> new_subscription(int user_id, int sub_id, MultipartBody.Part imagepart){
        return RetrofitConnections.getNetworkConnection().new_subscription(createPartFromString(user_id+""),
                createPartFromString(sub_id+""),imagepart)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    @NonNull
    private static RequestBody createPartFromString(String descriptionString) {
        //   RequestBody.create(
        //        MediaType.parse("multipart/form-data"), descriptionString);
        return RequestBody.create(MediaType.parse("text/plain"), descriptionString);

    }
}
