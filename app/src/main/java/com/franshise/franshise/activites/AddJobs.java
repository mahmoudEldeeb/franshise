package com.franshise.franshise.activites;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.franshise.franshise.R;
import com.franshise.franshise.fragments.FundingCompanyInfo;
import com.franshise.franshise.fragments.Jobs;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.LoginViewModel;

import java.util.Locale;

public class AddJobs extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
Button add_job,jobs;
NavigationView navigationView;
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

        setContentView(R.layout.activity_add_jobs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // getSupportActionBar().setLogo(R.drawable.logo2);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        add_job=findViewById(R.id.add_job);
        jobs=findViewById(R.id.jobs);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragMan = getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragMan.beginTransaction();
        fragTransaction.replace(R.id.fram, new Jobs() );
        fragTransaction.commit();

        add_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragMan = getSupportFragmentManager();
                FragmentTransaction fragTransaction = fragMan.beginTransaction();
                fragTransaction.replace(R.id.fram, new com.franshise.franshise.fragments.AddJobs() );
                fragTransaction.commit();

            }
        });
        jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragMan = getSupportFragmentManager();
                FragmentTransaction fragTransaction = fragMan.beginTransaction();
                fragTransaction.replace(R.id.fram, new com.franshise.franshise.fragments.Jobs() );
                fragTransaction.commit();
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


    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent intent=new Intent(AddJobs.this,NavigationShow.class);
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
                startActivity(new Intent(AddJobs.this,MarkerOwnerRegister.class));
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
                Intent intent1=new Intent(AddJobs.this,NavigationShow.class);
                intent1.putExtra("framid",9);
                startActivity(intent1);break;

            case R.id.nav_services :
                Intent intent2=new Intent(AddJobs.this,NavigationShow.class);
                intent2.putExtra("framid",10);
                startActivity(intent2);break;
            case R.id.nav_events:
                Intent intent3=new Intent(AddJobs.this,NavigationShow.class);
                intent3.putExtra("framid",8);
                startActivity(intent3);break;
            case R.id.nav_jobs:
                Intent intent4=new Intent(AddJobs.this,AddJobs.class);
                //intent4.putExtra("framid",12);
                startActivity(intent4);break;
            case R.id.nav_share:share();break;
            case R.id.nav_logout:logOut();break;
            case R.id.nav_setting:startActivity(new Intent(AddJobs.this,Setting.class));break;
            case R.id.nav_funding:startActivity(new Intent(AddJobs.this,FundingCompanies.class));break;
            default:break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void logOut(){
        String token= new SharedPrefrenceModel(this).getTToken();
        String api_token=
                new SharedPrefrenceModel(this).getApiToken();
        LoginViewModel loginViewModel;
        Observer<Integer> outObserver;
        MenuItem nav_camara;
        Menu menu = navigationView.getMenu();

        nav_camara = menu.findItem(R.id.nav_logout);

        loginViewModel= ViewModelProviders.of(this).get(LoginViewModel.class);
        outObserver=new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                Log.v("eee",integer+"");
                SharedPrefrenceModel shared=new SharedPrefrenceModel(AddJobs.this);
                if(integer==1){
                    if(shared.isLogined())
                        nav_camara.setTitle(R.string.logout);

                    shared.setLogined(false);
                    shared.setCompleteRegister(false);
                    startActivity(new Intent(AddJobs.this,LoginActivity.class));
                    // finish();
                }
            }
        };
        CustomProgressDialog.showProgress(this);

        loginViewModel.LogOut(token,api_token).observe(this,outObserver);
        new SharedPrefrenceModel(this).setCompleteRegister(false);

    }
    public void share(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "https://play.google.com/store/apps/details?id=com.franshise.franshise";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "share franchise");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }}
