package com.franshise.franshise.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.util.Log;

import com.franshise.franshise.activites.LoginActivity;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.UserModel;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.models.networks.RetrofitConnections;
import com.franshise.franshise.models.repositry.LoginRepositry;
import com.franshise.franshise.utils.CustomProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

public class LoginViewModel extends ViewModel {
//public void
MutableLiveData<Integer> loginResult;
public LoginViewModel() {
   loginResult=new MutableLiveData<>();
}
public LiveData<Integer> login(final Context c,String email,String password) {
    LoginRepositry.login(email,password).subscribeWith(new SingleObserver<ResponseBody>() {
        @Override
        public void onSubscribe(Disposable d) {
        }
        @Override
        public void onSuccess(ResponseBody responseBody) {
            try {
                JSONObject jsonObject=new JSONObject(responseBody.string());
                if(jsonObject.getInt("status")==1){
                    UserModel userModel=new UserModel();
                    loginResult.setValue(jsonObject.getInt("status"));
                    JSONObject dataObject=jsonObject.getJSONObject("data");
                    userModel.setApi_token(dataObject.getString("api_token"));
                    Log.v("dddd",dataObject.getString("api_token"));
                    JSONObject userObject=dataObject.getJSONObject("user");
                    userModel.setId(userObject.getInt("id"));
                    userModel.setName(userObject.getString("name"));
                    userModel.setUsername(userObject.getString("username"));
                    userModel.setEmail(userObject.getString("email"));
                    userModel.setCountry(userObject.getString("country"));
                    userModel.setCity(userObject.getString("city"));
                    userModel.setPhone(userObject.getString("phone"));
                    userModel.setDate(userObject.getString("end_date"));
                    userModel.setSubscribe(userObject.getInt("subscribe"));
                    userModel.setPassword(password);
                    new SharedPrefrenceModel(c).storeUser(userModel);

                }
                else {

                    loginResult.setValue(jsonObject.getInt("status"));
                }

            } catch (IOException e) {

                Log.v("dddddd","ghjkl");
                loginResult.setValue(-1);
                e.printStackTrace();
            } catch (JSONException e) {
                loginResult.setValue(-1);
                Log.v("dddddd","dfghjkl;'");
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {

            Log.v("dddddd",e.toString());
        loginResult.setValue(-1);
        }
    });
return loginResult;
}

    public MutableLiveData<Integer> LogOut(String token,String api_token) {
        MutableLiveData<Integer> logoutResult=new MutableLiveData<>();
LoginRepositry.removetoken(token,api_token).subscribeWith(new SingleObserver<StatusModel>() {
    @Override
    public void onSubscribe(Disposable d) {


    }

    @Override
    public void onSuccess(StatusModel responseBody) {
        CustomProgressDialog.clodseProgress();
            Log.v("eeee",responseBody.getStatus()+"");
            logoutResult.setValue(responseBody.getStatus());
    }

    @Override
    public void onError(Throwable e) {
        CustomProgressDialog.clodseProgress();
        logoutResult.setValue(0);
        Log.v("eeee",e.toString());
    }
});

        return logoutResult;
    }
    public MutableLiveData<StatusModel> update_password(String username,String password,String api_token,int id) {
        MutableLiveData<StatusModel> logoutResult=new MutableLiveData<>();
        LoginRepositry.update_password(username,password,api_token,id).subscribeWith(new SingleObserver<StatusModel>() {
            @Override
            public void onSubscribe(Disposable d) {


            }

            @Override
            public void onSuccess(StatusModel responseBody) {
                CustomProgressDialog.clodseProgress();
                Log.v("eeee",responseBody.getStatus()+"");
                logoutResult.setValue(responseBody);
            }

            @Override
            public void onError(Throwable e) {
                CustomProgressDialog.clodseProgress();
                StatusModel s=new StatusModel();
                s.setStatus(-1);
                s.setMessage("error try later");
                logoutResult.setValue(s);
                Log.v("eeee",e.toString());
            }
        });

        return logoutResult;
    }
    public MutableLiveData<StatusModel> reset(String email) {
        MutableLiveData<StatusModel> logoutResult=new MutableLiveData<>();
        LoginRepositry.reset(email).subscribeWith(new SingleObserver<StatusModel>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onSuccess(StatusModel responseBody) {
                CustomProgressDialog.clodseProgress();
                Log.v("eeee",responseBody.getStatus()+"");
                logoutResult.setValue(responseBody);
            }

            @Override
            public void onError(Throwable e) {
                CustomProgressDialog.clodseProgress();
                StatusModel s=new StatusModel();
                s.setStatus(-1);
                s.setMessage("error try later");
                logoutResult.setValue(s);
                Log.v("eeee",e.toString());
            }
        });

        return logoutResult;
    }
    public void registerToken(String token,String api_token,int user_id){
    LoginRepositry.registerToken(token,api_token,user_id)
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

}
}




