package com.franshise.franshise.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.models.ResultNetworkModels.DataResult;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.UserModel;
import com.franshise.franshise.models.dataModels.DataModel;
import com.franshise.franshise.models.repositry.LoginRepositry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.observable.ObservableAllSingle;
import okhttp3.ResponseBody;

public class RegisterViewModel extends ViewModel {

    MutableLiveData<Integer> registerResult;
    MutableLiveData<DataResult>countries;
    public RegisterViewModel() {
        registerResult=new MutableLiveData<>();
    }
    public MutableLiveData<DataResult> getCountries(Context context){
        countries=new MutableLiveData<>();
        LoginRepositry.getCountries()
                .subscribeWith(new SingleObserver<DataResult>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(DataResult dataResult) {

                countries.setValue(dataResult);
                Log.v("eeeee",dataResult.getData().get(0).getAr_name());
                /*for(int i=0;i<dataResult.getData().size();i++) {
                    if (new SharedPrefrenceModel(context).getLanguage().equals("en")) {

                        countries.getValue().set(i,dataResult.getData().get(i).getEn_name());
                    } else {

                        countries.getValue().set(i,dataResult.getData().get(i).getAr_name());
                    }
                }*/
            }

            @Override
            public void onError(Throwable e) {

            }
        });
        return countries;
    }
    public LiveData<Integer> register(final Context c,String name, String username,
                                      String email, String password,
                                      String phone, String country, String city) {
        LoginRepositry.register(name, username,email,password,phone,country,city)
                .subscribeWith(new SingleObserver<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {
            }
            @Override
            public void onSuccess(ResponseBody responseBody) {
                try {
                    String result=responseBody.string();
                    JSONObject jsonObject=new JSONObject(result);
                   if(jsonObject.getInt("status")==1){
                       UserModel userModel=new UserModel();
                        JSONObject dataObject=jsonObject.getJSONObject("data");
                        userModel.setApi_token(dataObject.getString("api_token"));
                        Log.v("eeeeeee",dataObject.getString("api_token"));
                        JSONObject userObject=dataObject.getJSONObject("user");
                        userModel.setId(userObject.getInt("id"));
                        userModel.setName(userObject.getString("name"));
                        userModel.setUsername(userObject.getString("username"));
                        userModel.setEmail(userObject.getString("email"));
                        userModel.setCountry(userObject.getString("country"));
                        userModel.setCity(userObject.getString("city"));
                        userModel.setPhone(userObject.getString("phone"));
                        userModel.setSubscribe(0);

                       DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                       Date date = new Date();
                       Date d = dateFormat.parse(dateFormat.format(date));
                        userModel.setDate("2019-04-01");
                       SharedPrefrenceModel sharedPrefrenceModel=  new SharedPrefrenceModel(c);
                        sharedPrefrenceModel.storeUser(userModel);
                        sharedPrefrenceModel.confirmRegisteriation(false);
                        registerResult.setValue(jsonObject.getInt("status"));
                    }
                    else {
                        String error="";
                        JSONObject dataObject=jsonObject.getJSONObject("data");
                            if(dataObject.has("email")){
                            JSONArray emailArray=dataObject.getJSONArray("email");
                                error+= (String) emailArray.get(0);
                            }
                       if(dataObject.has("phone")){
                           JSONArray phoneArray=dataObject.getJSONArray("phone");
                           error+= (String) phoneArray.get(0);
                       }
                       if(dataObject.has("username")){
                           JSONArray userArray=dataObject.getJSONArray("username");
                           error+= (String) userArray.get(0);
                       }
                       Toast.makeText(c, error,Toast.LENGTH_LONG).show();

                       registerResult.setValue(jsonObject.getInt("status"));
                    }
                } catch (IOException e) {

                    Toast.makeText(c, "there is error try later",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } catch (JSONException e) {
                    Log.v("ddddddd",e.toString());
                    Toast.makeText(c, "there is error try later",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        });
        return registerResult;
    }
}
