package com.franshise.franshise.activites;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.models.ResultNetworkModels.DataResult;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.services.RegisterToken;
import com.franshise.franshise.viewmodels.RegisterViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
ImageView star1;
EditText name,user_name,email,password,city,phone,phone_key;
Button register;
    ProgressDialog proDialog;
    Observer<Integer> registerObserver;

    Observer<DataResult> countriesObserver;
RegisterViewModel registerViewModel;
String county_name="";
Spinner country;
String token;
CheckBox rules;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        star1=findViewById(R.id.star1);
        register=findViewById(R.id.register);
        name=findViewById(R.id.name);
        user_name=findViewById(R.id.user_name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        city=findViewById(R.id.city);
        phone=findViewById(R.id.phone);
        country=findViewById(R.id.password);
        phone_key=findViewById(R.id.phone_key);
        rules=findViewById(R.id.rules);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( RegisterActivity.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult.getToken();
            }
        });
        if(!new SharedPrefrenceModel(this).getConfirmationStaus()){
            startActivity(new Intent(RegisterActivity.this,ConformRegister.class));
            finish();
        }


        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        registerObserver = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer Result) {
                if(Result==1){
                    Intent i=new Intent(RegisterActivity.this, RegisterToken.class);
                    new SharedPrefrenceModel(RegisterActivity.this).setToken(token);
                    i.putExtra("token",token);
                    startService(i);
                    startActivity(new Intent(RegisterActivity.this,ConformRegister.class));
                }
                proDialog.dismiss();
            }
        };
        List<String>list=new ArrayList<>();
        list.add(getResources().getString(R.string.country));
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(adp1);

countriesObserver=new Observer<DataResult>() {
    @Override
    public void onChanged(@Nullable DataResult result) {
        for(int i=0;i<result.getData().size();i++) {
            if (new SharedPrefrenceModel(RegisterActivity.this).getLanguage().equals("en")) {
            list.add(result.getData().get(i).getEn_name()) ;
            } else {
                list.add(result.getData().get(i).getEn_name()) ;
            }
        }
        adp1.notifyDataSetChanged();
    }
};
        registerViewModel.getCountries(this).observe(this,countriesObserver);

        register.setOnClickListener(new View.OnClickListener() {
                 @Override
              public void onClick(View v) {

                     proDialog = ProgressDialog.show(RegisterActivity.this, "", "Looding.....");
                     checkValues();

                 }
                });
country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        county_name=list.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});

rules.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            startActivity(new Intent(RegisterActivity.this,Policey.class));
        }

    }
});
    }

    private void checkValues() {
Log.v("qqqq",
        name.getText().toString()+user_name.getText().toString()+email.getText().toString()+
                password.getText().toString()+city.getText()
                +phone_key.getText().toString());

        if(name.getText().toString().isEmpty()||user_name.getText().toString().isEmpty()||email.getText().toString().isEmpty()||
                password.getText().toString().isEmpty()||city.getText().toString().isEmpty()
                ||phone_key.getText().toString().isEmpty()||county_name.equals(getResources().getString(R.string.country))
                ||!rules.isChecked()
        ){
            proDialog.dismiss();
            Toast.makeText(getBaseContext(),getResources().getString(R.string.fill_data),Toast.LENGTH_LONG).show();
        }
        else if(checkMail()){
            registerViewModel.register(RegisterActivity.this,name.getText().toString(),user_name.getText().toString(),
                    email.getText().toString()
                    ,password.getText().toString(),phone_key.getText().toString()+phone.getText().toString(),county_name,city.getText().toString())
                    .observe(RegisterActivity.this, registerObserver);
        }
        else
        {
            proDialog.dismiss();
            Toast.makeText(getBaseContext(),getResources().getString(R.string.fill_data),Toast.LENGTH_LONG).show();}
    }
    public boolean checkMail(){

            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";

            Pattern pat = Pattern.compile(emailRegex);
            if (email == null)
                return false;
            return pat.matcher(email.getText().toString()).matches();

    }
}
