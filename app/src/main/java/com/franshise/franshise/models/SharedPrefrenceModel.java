package com.franshise.franshise.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

public class SharedPrefrenceModel {
    Context context;
    static SharedPreferences preferences;
    private String language;

    public SharedPrefrenceModel(Context c)
    {
        context=c;
        preferences =context.getSharedPreferences("franshise",MODE_PRIVATE);
    }

    public boolean isLogined()
    {

        Log.v("yyyy","fghjkl;");
        return preferences.getBoolean("logined",false);
    }


    public void setLogined(boolean logined) {

        Log.v("yyyy","ffffff");
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean("logined",logined).apply();

    }
    public boolean isNotificationOn()
    {

        Log.v("yyyy","fghjkl;");
        return preferences.getBoolean("notification",false);
    }

    public void setNotification(boolean noty) {

        Log.v("yyyy","ffffff");
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean("notification",noty).apply();

    }

    public void setToken(String  token) {

        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("token",token).commit();

    }
    public String getTToken() {
        return preferences.getString("token","0");
    }

    public void storeUser(UserModel userModel){

    SharedPreferences.Editor editor=preferences.edit();
    editor.putString("api_token",userModel.getApi_token());
    editor.putInt("id",userModel.getId());
    editor.putString("name",userModel.getName());
    editor.putString("username",userModel.getUsername());
    editor.putString("email",userModel.getEmail());
    editor.putString("country",userModel.getCountry());
    editor.putString("city",userModel.getCity());
    editor.putString("phone",userModel.getPhone());
    editor.putString("password",userModel.getPassword());
    editor.putString("date",userModel.getDate());
    editor.putInt("subscribe",userModel.getSubscribe());
    editor.commit();
}
public int getSubscribe(){
        return preferences.getInt("subscribe",0);
}

    public void setSubscribe(int i){
        SharedPreferences.Editor editor=preferences.edit();
        editor.putInt("subscribe",1);
        editor.commit();
    }
public String getDate(){
        return preferences.getString("date","2019-04-01");
}
public UserModel getUserModel(){
        UserModel userModel=new UserModel();
        userModel.setName(preferences.getString("name",""));

    userModel.setPhone(preferences.getString("phone",""));
    userModel.setCity(preferences.getString("city",""));
    userModel.setCountry(preferences.getString("country",""));
    userModel.setEmail(preferences.getString("email",""));
    userModel.setUsername(preferences.getString("username",""));

    userModel.setPassword(preferences.getString("password",""));
    return userModel;
}

    public void setLanguage(String language) {
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("language",language).commit();
       // this.language = language;
    }

    public String getLanguage() {
        language=preferences.getString("language","en");
        return language;
    }

    public void changeLanguage() {
        if(this.getLanguage().equals("ar")){
            setLanguage("en");
        }
        else setLanguage("ar");
    }

    public void confirmRegisteriation(Boolean confirm){
    SharedPreferences.Editor editor=preferences.edit();
    editor.putBoolean("confirmRegisteriation",confirm).commit();
}
    public  boolean getConfirmationStaus(){
        return preferences.getBoolean("confirmRegisteriation",true);
}
    public String getEmail(){
        return preferences.getString("email","");
    }
    public String getName(){
        return preferences.getString("name","");
    }

    public int getId(){
        return preferences.getInt("id",0);
    }
public String getApiToken(){
    return preferences.getString("api_token",".");
}
    public void setCompleteRegister(boolean b){

        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean("completeRegister",b).commit();
    }
    public boolean getCompleteregister(){
        return preferences.getBoolean("completeRegister",false);
    }

    public boolean isFirstTime() {
       return preferences.getBoolean("firstTime",false);
    }
    public void setFirstTime(boolean firstTime) {
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean("firstTime",firstTime).commit();
        // this.language = language;
    }
    public void putUsernameAndPassword(String toString, String toString1) {
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("username",toString);
        editor.putString("password",toString1);
        editor.commit();
    }

    public void storeUserlessApi(UserModel userModel) {
        SharedPreferences.Editor editor=preferences.edit();
        editor.putInt("id",userModel.getId());
        editor.putString("name",userModel.getName());
        editor.putString("username",userModel.getUsername());
        editor.putString("email",userModel.getEmail());
        editor.putString("country",userModel.getCountry());
        editor.putString("city",userModel.getCity());
        editor.putString("phone",userModel.getPhone());
        editor.commit();
    }
    public void setUserType(boolean type){
        preferences.edit().putBoolean("user_type",type).commit();

    }
    public boolean isOwner(){
        return preferences.getBoolean("user_type",false);
}


}
