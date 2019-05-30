package com.franshise.franshise.activites;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.franshise.franshise.R;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class LanguageActivity extends AppCompatActivity {
Button english,arabic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        final Context context=this;
        english=findViewById(R.id.english);
        arabic=findViewById(R.id.arabic);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( LanguageActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String token = instanceIdResult.getToken();
                Log.v("aaaaaaa",token);
            }
        });

        if(new SharedPrefrenceModel(this).isLogined()){
            startActivity(new Intent(LanguageActivity.this,Main.class));
            finish();
        }
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SharedPrefrenceModel(context).setLanguage("en");
                new SharedPrefrenceModel(LanguageActivity.this).setFirstTime(true);
                new SharedPrefrenceModel(context).setNotification(true);
                startActivity(new Intent(LanguageActivity.this,Main.class));
                finish();
            }
        });
        arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SharedPrefrenceModel(context).setLanguage("ar");
                new SharedPrefrenceModel(LanguageActivity.this).setFirstTime(true);
                new SharedPrefrenceModel(context).setNotification(true);
                startActivity(new Intent(LanguageActivity.this,Main.class));
                finish();
            }
        });

    }
}
