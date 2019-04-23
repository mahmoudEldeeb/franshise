package com.franshise.franshise.activites;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.models.ResultNetworkModels.DataResult;
import com.franshise.franshise.models.ResultNetworkModels.UserProfileResult;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.UserModel;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.models.dataModels.UserProfileModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.LoginViewModel;
import com.franshise.franshise.viewmodels.RegisterViewModel;
import com.franshise.franshise.viewmodels.UserProfileViewModel;

import java.util.ArrayList;
import java.util.List;

public class MyData extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LoginViewModel loginViewModel;
    Observer<Integer>outObserver;
Spinner country;
UserProfileViewModel userProfileViewModel;
RegisterViewModel registerViewModel;
RadioButton instit,com;
    RadioGroup type;
    int compelted=0;
    EditText company_name,phoneCompany,fax,manager,conversion;
    int countryId=0;
    List<String> list;
    EditText name,user_name,email,password,city,phone,phone_key;
    Button register;
    LinearLayout completed;
    MenuItem nav_camara;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R
                .layout.activity_my_data);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // getSupportActionBar().setLogo(R.drawable.logo2);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

         name=findViewById(R.id.name);
        user_name=findViewById(R.id.user_name);
        email=findViewById(R.id.email);
        city=findViewById(R.id.city);
        phone=findViewById(R.id.phone);
        phone_key=findViewById(R.id.phone_key);
        country=findViewById(R.id.country);
        company_name=findViewById(R.id.company_name);
        phoneCompany=findViewById(R.id.phonecompany);
        completed=findViewById(R.id.completed);
        fax=findViewById(R.id.fax);
        manager=findViewById(R.id.manager);
        conversion=findViewById(R.id.conversion);
        instit=findViewById(R.id.instit);
        com=findViewById(R.id.com);
        register=findViewById(R.id.register);
type=findViewById(R.id.type);

        UserModel userModel=new SharedPrefrenceModel(this).getUserModel();
        email.setText(userModel.getEmail());
        phone.setText(userModel.getPhone());
        city.setText(userModel.getCity());
        name.setText(userModel.getName());
       DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();

         nav_camara = menu.findItem(R.id.nav_logout);
        if(!new SharedPrefrenceModel(this).isLogined())
            nav_camara.setTitle(R.string.login);

        navigationView.setNavigationItemSelectedListener(this);
        loginViewModel= ViewModelProviders.of(this).get(LoginViewModel.class);
        outObserver=new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                Log.v("eee",integer+"");
                SharedPrefrenceModel shared=new SharedPrefrenceModel(MyData.this);
                if(integer==1){
                    if(shared.isLogined())
                        nav_camara.setTitle(R.string.logout);
                    shared.setLogined(false);
                    shared.setCompleteRegister(false);
                    startActivity(new Intent(MyData.this,LoginActivity.class));
                    finish();
                }
            }
        };
        list=new ArrayList<>();
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(adp1);

        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        registerViewModel.getCountries(this).observe(this, new Observer<DataResult>() {
            @Override
            public void onChanged(@Nullable DataResult result) {
                for(int i=0;i<result.getData().size();i++) {
                    if(userModel.getCountry().equals(result.getData().get(i).getAr_name())
                    ||userModel.getCountry().equals(result.getData().get(i).getEn_name()))
                        countryId=i;
                    if (new SharedPrefrenceModel(MyData.this).getLanguage().equals("en")) {
                        list.add(result.getData().get(i).getEn_name()) ;
                    } else {
                        list.add(result.getData().get(i).getEn_name()) ;
                    }
                }
                adp1.notifyDataSetChanged();
                country.setSelection(countryId);
                //country.setp

            }
        });

userProfileViewModel=ViewModelProviders.of(this).get(UserProfileViewModel.class);
userProfileViewModel.checkCompleteProfile(new SharedPrefrenceModel(this).getId(),new SharedPrefrenceModel(this).getApiToken())
        .observe(this, new Observer<StatusModel>() {
            @Override
            public void onChanged(@Nullable StatusModel statusModel) {
                if(statusModel.getStatus()==1){
                    compelted=1;
                    Log.v("pppppppp","eeeeeeeeefffffffffsd5854");
                    getUserData();
                }
                else {
                    completed.setVisibility(View.GONE);
                }
            }
        });
register.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(compelted==1){
           updateAllData();
        }
        else {

            updateMainData();
        }
    }
});

    }

    @Override
    protected void onResume() {
        if(new SharedPrefrenceModel(MyData.this).isLogined())
            nav_camara.setTitle(R.string.logout);
        else
            nav_camara.setTitle(R.string.login);
        super.onResume();
    }
    private void updateMainData() {
        int i=country.getSelectedItemPosition();
        String country1=list.get(i);
        CustomProgressDialog.showProgress(this);
        userProfileViewModel.updateAllData(this,new SharedPrefrenceModel(this).getId(),new SharedPrefrenceModel(this).getApiToken(),
                name.getText().toString(),email.getText().toString(),phone.getText().toString()
                ,country1,city.getText().toString())
                .observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer userProfileModel) {
                        CustomProgressDialog.clodseProgress();
                        if(userProfileModel==1) {
                            Toast.makeText(getBaseContext(), "done", Toast.LENGTH_LONG).show();
                        UserModel userModel=new UserModel();
                        userModel.setName(name.getText().toString());
                        userModel.setEmail(email.getText().toString());
                        userModel.setCountry(country1);
                        userModel.setCity(city.getText().toString());
                        userModel.setPhone(phone.getText().toString());
                        new SharedPrefrenceModel(MyData.this).storeUserlessApi(userModel);
                        }
                        else Toast.makeText(getBaseContext(),"error try later",Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void updateAllData() {
        int i=country.getSelectedItemPosition();
        String country1=list.get(i);
        String compay_type="";
        if(instit.isChecked())compay_type="Institution";
        else compay_type="Company";
CustomProgressDialog.showProgress(this);
userProfileViewModel.updateAllData(this,new SharedPrefrenceModel(this).getId(),new SharedPrefrenceModel(this).getApiToken(),
name.getText().toString(),email.getText().toString(),phone.getText().toString()
,country1,city.getText().toString(),company_name.getText().toString(),compay_type,phoneCompany.getText().toString(),
        fax.getText().toString(),manager.getText().toString(),conversion.getText().toString())
        .observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer userProfileModel) {
                CustomProgressDialog.clodseProgress();
               if(userProfileModel==1) {
                   UserModel userModel=new UserModel();
                   userModel.setName(name.getText().toString());
                   userModel.setEmail(email.getText().toString());
                   userModel.setCountry(country1);
                   userModel.setCity(city.getText().toString());
                   userModel.setPhone(phone.getText().toString());
                   new SharedPrefrenceModel(MyData.this).storeUserlessApi(userModel);
                   Toast.makeText(getBaseContext(),"done",Toast.LENGTH_LONG).show();
               }
                        else Toast.makeText(getBaseContext(),"error try later",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getUserData() {

        Log.v("pppppppp","eeeeeeeeefffffffffsd5854");
       userProfileViewModel.user_profile(new SharedPrefrenceModel(this).getId(),new SharedPrefrenceModel(this).getApiToken())
               .observe(this, new Observer<UserProfileModel>() {
                   @Override
                   public void onChanged(@Nullable UserProfileModel userProfileResult) {
                       if(userProfileResult!=null){
                           Log.v("ppppp",userProfileResult.getCompany_type());
                           if(userProfileResult.getCompany_type().equals("Institution"))
                               type.check(R.id.instit);
                           else  type.check(R.id.com);
                           company_name.setText(userProfileResult.getCompany_name());

                           phoneCompany.setText(userProfileResult.getCompany_phone());
                           fax.setText(userProfileResult.getFax());

                           manager.setText(userProfileResult.getAdmin_phone());
                           conversion.setText(userProfileResult.getAdmin_conversion());
                           Log.v("ppppppppp",userProfileResult.getCompany_name());
                       }
                   }
               });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment selected;
        FragmentManager fragMan = getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragMan.beginTransaction();

        Intent intent=new Intent(MyData.this,NavigationShow.class);
        switch (id){

            // case  R.id.nav_events: selected=new EventsFragment();break;
            //  case R.id.nav_training:selected=new TrainingFragment();break;
            case R.id.nav_subscriptions:
                intent.putExtra("framid",0);
                startActivity(intent);
                break;
            case R.id.nav_Complaints:
                intent.putExtra("framid",1);
                startActivity(intent);
                break;
            case R.id.nav_myfranchises:
                startActivity(new Intent(MyData.this,MarkerOwnerRegister.class));
                break;
            case R.id.nav_language:
                new SharedPrefrenceModel(this).changeLanguage();
                startActivity(new Intent(this,Main.class));
                this.finish();break;
            case R.id.nav_questions:
                intent.putExtra("framid",2);
                startActivity(intent);
                break;
            case R.id.nav_contact:
                intent.putExtra("framid",11);
                startActivity(intent);
                break;
            case R.id.nav_franshise:
                intent.putExtra("framid",3);
                startActivity(intent);
                break;
            case R.id.nav_terms:
                intent.putExtra("framid",4);
                startActivity(intent);break;
            case R.id.nav_training :
                Intent intent1=new Intent(MyData.this,NavigationShow.class);
                intent1.putExtra("framid",9);
                startActivity(intent1);break;

            case R.id.nav_services :
                Intent intent2=new Intent(MyData.this,NavigationShow.class);
                intent2.putExtra("framid",10);
                startActivity(intent2);break;
            case R.id.nav_events:
                 intent=new Intent(MyData.this,NavigationShow.class);
                intent.putExtra("framid",8);
                startActivity(intent);break;
            case R.id.nav_jobs:
                Intent intent4=new Intent(MyData.this,NavigationShow.class);
                intent4.putExtra("framid",12);
                startActivity(intent4);break;

            case R.id.nav_share:share();break;
            case R.id.nav_logout:logOut();break;
            case R.id.nav_setting:startActivity(new Intent(MyData.this,Setting.class));break;
            default:break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void share(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "https://play.google.com/store/movies/details/Spider_Man_Into_the_Spider_Verse?id=vTg25S6WRVY";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "share franchise");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }
    public void logOut(){
        String token= new SharedPrefrenceModel(this).getTToken();
        String api_token=
                new SharedPrefrenceModel(this).getApiToken();
        CustomProgressDialog.showProgress(this);
        loginViewModel.LogOut(token,api_token).observe(this,outObserver);
        new SharedPrefrenceModel(this).setCompleteRegister(false);

    }}
