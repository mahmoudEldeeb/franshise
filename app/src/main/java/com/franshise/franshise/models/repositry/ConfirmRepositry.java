package com.franshise.franshise.models.repositry;

import com.franshise.franshise.models.networks.RetrofitConnections;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
public class ConfirmRepositry {
    public static Single<ResponseBody> verfiy(String email, String code) {
        return RetrofitConnections.getNetworkConnection().verfiy(email, code)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
