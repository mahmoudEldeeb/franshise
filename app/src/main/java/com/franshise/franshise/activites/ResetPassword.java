package com.franshise.franshise.activites;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.LoginViewModel;

public class ResetPassword extends AppCompatActivity {
    EditText email;
    Button change;
    LoginViewModel loginViewModel;
    TextView forget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        email=findViewById(R.id.email);
        change=findViewById(R.id.change);
        forget=findViewById(R.id.forget);

        loginViewModel= ViewModelProviders.of(this).get(LoginViewModel.class);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().isEmpty()){
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
        loginViewModel.reset(email.getText().toString())
                .observe(this, new Observer<StatusModel>() {
                    @Override
                    public void onChanged(@Nullable StatusModel statusModel) {
                        if(statusModel.getStatus()==1){
                            Toast.makeText(getBaseContext(),statusModel.getMessage(),Toast.LENGTH_SHORT).show();
                          forget.setVisibility(View.VISIBLE);
                        }
                        else
                            Toast.makeText(getBaseContext(),statusModel.getMessage(),Toast.LENGTH_SHORT).show();
                        CustomProgressDialog.clodseProgress();
                    }

                });
    }
}
