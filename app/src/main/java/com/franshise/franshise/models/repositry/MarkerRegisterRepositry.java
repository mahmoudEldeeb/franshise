package com.franshise.franshise.models.repositry;

import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.models.networks.RetrofitConnections;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.http.Field;

public class MarkerRegisterRepositry  {
    public static Single<StatusModel> checkCompleteProfile(int id, String apiToken){
        return RetrofitConnections.getNetworkConnection().checkCompleteProfile(id,apiToken)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
  public static Single<StatusModel>  completeRegister(int id, String company_name,
                                                       String company_type, String company_phone,
                                                       String fax, int cat_id,
                                                       String admin_phone, String admin_conversion, String api_token){

      return RetrofitConnections.getNetworkConnection().completeRegister(id,company_name,company_type,company_phone
      ,fax,cat_id,admin_phone,admin_conversion,api_token)
              .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

  }

}
