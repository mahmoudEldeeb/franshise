package com.franshise.franshise.utils;

import android.app.AlertDialog;
import android.content.Context;

import dmax.dialog.SpotsDialog;

public class CustomProgressDialog {
public static AlertDialog proDialog;

    public static void showProgress(Context context){
     proDialog = new SpotsDialog.Builder()
            .setContext(context)
                .setMessage("Loading......")
                .setCancelable(false)
                 .build();
   try{  proDialog.show();}
   catch (Exception e){}
}
public static void clodseProgress(){
        proDialog.dismiss();
}
}
