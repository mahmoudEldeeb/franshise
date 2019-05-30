package com.franshise.franshise.activites;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.services.RegisterToken;
import com.franshise.franshise.viewmodels.LoginViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
TextView newUser,forget;
Button login;
LoginViewModel loginViewModel;
EditText email,password;
    ProgressDialog proDialog;
    Observer<Integer> loginObserver;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String languageToLoad="en";
        if(new SharedPrefrenceModel(this).getLanguage().equals("ar")){
            languageToLoad = "ar"; // your language
        }
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.change);
        newUser=findViewById(R.id.newUser);
        email=findViewById(R.id.email);
        password=findViewById(R.id.country);
        forget=findViewById(R.id.forget);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ResetPassword.class));
            }
        });
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( LoginActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult.getToken();
                Log.v("aaaaaaa",token);
            }
        });
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer loginResult) {
                if(loginResult==1){

                    new SharedPrefrenceModel(LoginActivity.this).setLogined(true);
                    Intent i=new Intent(LoginActivity.this, RegisterToken.class);
                    i.putExtra("token",token);

                    new SharedPrefrenceModel(LoginActivity.this).setToken(token);

            loginViewModel.registerToken(token, new SharedPrefrenceModel(LoginActivity.this).getApiToken()
                    ,new SharedPrefrenceModel(LoginActivity.this).getId());

                   // startService(i);
                    //startActivity(new Intent(LoginActivity.this,Main.class));
                 //   onBackPressed();
                   finish();

                }
                else {
                    Toast.makeText(getBaseContext(),R.string.incorrect,Toast.LENGTH_LONG).show();
                }
                proDialog.dismiss();
            }
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().isEmpty()||password.getText().toString().isEmpty()){
                    Toast.makeText(getBaseContext(),R.string.fill_data,Toast.LENGTH_LONG).show();
                }
                else {

                     proDialog = ProgressDialog.show(LoginActivity.this, "", "Looding.....");
                    loginViewModel.login(LoginActivity.this,email.getText().toString(),password.getText().toString())
                        .observe(LoginActivity.this, loginObserver);
                }
                // startActivity(new Intent(LoginActivity.this,Main.class));

            }
        });
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));

            }
        });
    }

}
