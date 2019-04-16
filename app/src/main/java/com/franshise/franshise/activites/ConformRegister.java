package com.franshise.franshise.activites;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import com.franshise.franshise.viewmodels.ConfirmViewModel;
import com.franshise.franshise.viewmodels.RegisterViewModel;

public class ConformRegister extends AppCompatActivity {
Button confirm;
TextView newuser;
EditText confirm_number;
ConfirmViewModel confirmViewModel;

    Observer<Integer> confirmObserver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conform_register);
        newuser=findViewById(R.id.newuser);
        confirm_number=findViewById(R.id.confirm_number);

        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SharedPrefrenceModel(ConformRegister.this).confirmRegisteriation(true);
                startActivity(new Intent(ConformRegister.this,RegisterActivity.class));
                finish();
            }
        });
        confirmViewModel = ViewModelProviders.of(this).get(ConfirmViewModel.class);


        confirm=findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!confirm_number.getText().toString().isEmpty())
                        confirmViewModel.verify(ConformRegister.this,confirm_number.getText().toString());//.observe(ConformRegister.this,confirmObserver);
                else Toast.makeText(getBaseContext(),ConformRegister.this.getResources().getString(R.string.fill_data),Toast.LENGTH_LONG).show();
              //  startActivity(new Intent(ConformRegister.this,Main.class));
            }
        });
    }
}
