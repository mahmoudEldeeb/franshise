package com.franshise.franshise.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import dmax.dialog.SpotsDialog;

public class CustomProgressDialog {
public static ProgressDialog dialog;

    public static void showProgress(Context context){
    /* proDialog = new SpotsDialog.Builder()
            .setContext(context)
                .setMessage("Loading......")
                .setCancelable(false)
                 .build();
   try{
       proDialog.show();
   }
   catch (Exception e){}*/
    try {


        dialog = new ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        dialog.setMessage("loading.....");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }catch (Exception e){}

    }
public static void clodseProgress(){
        try {
            dialog.dismiss();
        }catch (Exception e){}
}
}
