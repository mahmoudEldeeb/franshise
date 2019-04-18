package com.franshise.franshise.activites;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.UserModel;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.LoginViewModel;

public class ChangePassowrs extends AppCompatActivity {
EditText email,password;
Button change;
LoginViewModel loginViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_passowrs);
        email=findViewById(R.id.email);
        password=findViewById(R.id.country);
        change=findViewById(R.id.change);
        loginViewModel= ViewModelProviders.of(this).get(LoginViewModel.class);
        UserModel userModel=new SharedPrefrenceModel(this).getUserModel();
        email.setText(userModel.getUsername());
        Log.v("ppppp",new SharedPrefrenceModel(this).getApiToken());
        password.setText(userModel.getPassword());
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().isEmpty()||password.getText().toString().isEmpty()){
                    Toast.makeText(getBaseContext(),R.string.fill_data,Toast.LENGTH_SHORT).show();

                }
                else {
                    update();
                }
            }
        });

    }

    private void update() {
        CustomProgressDialog.showProgress(this);
        loginViewModel.update_password(email.getText().toString(),password.getText().toString()
                ,new SharedPrefrenceModel(this).getApiToken(),new SharedPrefrenceModel(this).getId())
                .observe(this, new Observer<StatusModel>() {
                    @Override
                    public void onChanged(@Nullable StatusModel statusModel) {
                        if(statusModel.getStatus()==1){
                            Toast.makeText(getBaseContext(),statusModel.getMessage(),Toast.LENGTH_SHORT).show();
                            new SharedPrefrenceModel(ChangePassowrs.this).putUsernameAndPassword(email.getText().toString()
                                    ,password.getText().toString());
                            finish();
                        }
                        else
                            Toast.makeText(getBaseContext(),statusModel.getMessage(),Toast.LENGTH_SHORT).show();

                        CustomProgressDialog.clodseProgress();
                    }

                });
    }
}
