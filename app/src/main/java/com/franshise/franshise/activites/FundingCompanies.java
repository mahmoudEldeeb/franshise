package com.franshise.franshise.activites;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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

import com.franshise.franshise.R;
import com.franshise.franshise.adapters.FundingCompanyAdapter;
import com.franshise.franshise.fragments.ComplaintsFragment;
import com.franshise.franshise.fragments.FundingCompanyDetails;
import com.franshise.franshise.fragments.FundingCompanyInfo;
import com.franshise.franshise.fragments.HomeFragment;
import com.franshise.franshise.fragments.SubscriptionsFragment;
import com.franshise.franshise.fragments.WebViewFragment;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.FundingCompanyMode;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.LoginViewModel;

import java.util.Locale;

public class FundingCompanies extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FundingCompanyAdapter.ItemCliclListener {
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

        setContentView(R.layout.activity_funding_companies);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragMan = getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragMan.beginTransaction();
        fragTransaction.replace(R.id.fram, new FundingCompanyInfo() );
        fragTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else { FragmentManager fm = getSupportFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
            } else {
                super.onBackPressed();
            }
        }

    }


    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent intent=new Intent(FundingCompanies.this,NavigationShow.class);
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
                startActivity(new Intent(FundingCompanies.this,MarkerOwnerRegister.class));
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
                Intent intent1=new Intent(FundingCompanies.this,NavigationShow.class);
                intent1.putExtra("framid",9);
                startActivity(intent1);break;

            case R.id.nav_services :
                Intent intent2=new Intent(FundingCompanies.this,NavigationShow.class);
                intent2.putExtra("framid",10);
                startActivity(intent2);break;
            case R.id.nav_events:
                Intent intent3=new Intent(FundingCompanies.this,NavigationShow.class);
                intent3.putExtra("framid",8);
                startActivity(intent3);break;
            case R.id.nav_jobs:
                Intent intent4=new Intent(FundingCompanies.this,NavigationShow.class);
                intent4.putExtra("framid",12);
                startActivity(intent4);break;
            case R.id.nav_share:share();break;
            case R.id.nav_logout:logOut();break;
            case R.id.nav_setting:startActivity(new Intent(FundingCompanies.this,Setting.class));break;
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
        Observer<Integer>outObserver;
        MenuItem nav_camara;
        Menu menu = navigationView.getMenu();

        nav_camara = menu.findItem(R.id.nav_logout);

        loginViewModel= ViewModelProviders.of(this).get(LoginViewModel.class);
        outObserver=new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                Log.v("eee",integer+"");
                SharedPrefrenceModel shared=new SharedPrefrenceModel(FundingCompanies.this);
                if(integer==1){
                    if(shared.isLogined())
                        nav_camara.setTitle(R.string.logout);

                    shared.setLogined(false);
                    shared.setCompleteRegister(false);
                    startActivity(new Intent(FundingCompanies.this,LoginActivity.class));
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

    }
    @Override
    public void ItemClick(FundingCompanyMode fundingCompanyMode) {

        FragmentManager fragMan = getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragMan.beginTransaction();
        FundingCompanyDetails fundingCompanyDetails=new FundingCompanyDetails();
        Bundle bundle=new Bundle();
        bundle.putSerializable("fundCompany",fundingCompanyMode);
        fundingCompanyDetails.setArguments(bundle);
        fragTransaction.add(R.id.fram,  fundingCompanyDetails).addToBackStack(null);
        fragTransaction.commit();
    }

}
