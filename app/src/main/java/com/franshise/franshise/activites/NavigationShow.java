package com.franshise.franshise.activites;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import android.widget.FrameLayout;

import com.franshise.franshise.R;
import com.franshise.franshise.fragments.ComplaintsFragment;
import com.franshise.franshise.fragments.EventsFragment;
import com.franshise.franshise.fragments.ServicesFragment;
import com.franshise.franshise.fragments.SubscriptionsFragment;
import com.franshise.franshise.fragments.TrainingFragment;
import com.franshise.franshise.fragments.WebViewFragment;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.LoginViewModel;

public class NavigationShow extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
FrameLayout fram;
    LoginViewModel loginViewModel;
    Observer<Integer>outObserver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_show);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        int i=getIntent().getIntExtra("framid",0);
        Fragment selected;

        FragmentManager fragMan = getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragMan.beginTransaction();
        switch (i){
            case 0:
                selected=new SubscriptionsFragment();
            fragTransaction.replace(R.id.fragment, selected );
            fragTransaction.commit();
            break;
            case 1:selected=new ComplaintsFragment();
                fragTransaction.replace(R.id.fragment, selected );
                fragTransaction.commit();
                break;

            case 2:selected=new WebViewFragment();
                Bundle b=new Bundle();b.putString("url",getResources().getString(R.string.questionss));
                selected.setArguments(b);
                fragTransaction.replace(R.id.fragment, selected );
                fragTransaction.commit();break;

            case 3:selected=new WebViewFragment();
                b=new Bundle();b.putString("url",getResources().getString(R.string.Commercial_Concession));
                selected.setArguments(b);
                fragTransaction.replace(R.id.fragment, selected );
                fragTransaction.commit();
                break;
            case 4:selected=new WebViewFragment();
                b=new Bundle();b.putString("url",getResources().getString(R.string.termss));
                selected.setArguments(b);
                fragTransaction.replace(R.id.fragment, selected );
                fragTransaction.commit();break;
            case 5:selected=new WebViewFragment();
                b=new Bundle();b.putString("url",getResources().getString(R.string.courses));
                selected.setArguments(b);
                fragTransaction.replace(R.id.fragment, selected );
                fragTransaction.commit();
                break;
            case 6 :selected=new WebViewFragment();
                b=new Bundle();b.putString("url",getResources().getString(R.string.service));
                selected.setArguments(b);
                fragTransaction.replace(R.id.fragment, selected );
                fragTransaction.commit();
                break;
            case 7 :selected=new WebViewFragment();
                b=new Bundle();b.putString("url",getResources().getString(R.string.conferances));
                selected.setArguments(b);
                fragTransaction.replace(R.id.fragment, selected );
                fragTransaction.commit();
                break;
            case 8:selected=new EventsFragment();
                fragTransaction.replace(R.id.fragment, selected );
                fragTransaction.commit();
                break;

            case 9:selected=new TrainingFragment();
                fragTransaction.replace(R.id.fragment, selected );
                fragTransaction.commit();
                break;
            case 10:selected=new ServicesFragment();
                fragTransaction.replace(R.id.fragment, selected );
                fragTransaction.commit();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        loginViewModel= ViewModelProviders.of(this).get(LoginViewModel.class);
        outObserver=new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                Log.v("eee",integer+"");
                if(integer==1){
                    new SharedPrefrenceModel(NavigationShow.this).setLogined(false);
                    startActivity(new Intent(NavigationShow.this,LoginActivity.class));
                    finish();
                }
            }
        };
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment selected;

        FragmentManager fragMan = getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragMan.beginTransaction();
        switch (id){
            // case  R.id.nav_events: selected=new EventsFragment();break;
            //  case R.id.nav_training:selected=new TrainingFragment();break;
            case R.id.nav_subscriptions:selected=new SubscriptionsFragment();
                fragTransaction.replace(R.id.fragment, selected );
                fragTransaction.commit();
                break;
            case R.id.nav_Complaints:selected=new ComplaintsFragment();
                fragTransaction.replace(R.id.fragment, selected );
                fragTransaction.commit();
                break;
            case R.id.nav_myfranchises:
                startActivity(new Intent(NavigationShow.this,MarkerOwnerRegister.class));
                break;
            case R.id.nav_language:
                new SharedPrefrenceModel(this).changeLanguage();
                startActivity(new Intent(this,Main.class));
                this.finish();break;
            case R.id.nav_questions:selected=new WebViewFragment();
                Bundle b=new Bundle();b.putString("url",getResources().getString(R.string.questionss));
                selected.setArguments(b);
                fragTransaction.replace(R.id.fragment, selected );
                fragTransaction.commit();break;

            case R.id.nav_franshise:selected=new WebViewFragment();
                b=new Bundle();b.putString("url",getResources().getString(R.string.Commercial_Concession));
                selected.setArguments(b);
                fragTransaction.replace(R.id.fragment, selected );
                fragTransaction.commit();
                break;
            case R.id.nav_terms:selected=new WebViewFragment();
                b=new Bundle();b.putString("url",getResources().getString(R.string.termss));
                selected.setArguments(b);
                fragTransaction.replace(R.id.fragment, selected );
                fragTransaction.commit();break;
            case R.id.nav_training :selected=new TrainingFragment();
                fragTransaction.replace(R.id.fragment, selected );
                fragTransaction.commit();
                break;
            case R.id.nav_services :selected=new ServicesFragment();
                fragTransaction.replace(R.id.fragment, selected );
                fragTransaction.commit();
                break;
            case R.id.nav_events :selected=new EventsFragment();
                fragTransaction.replace(R.id.fragment, selected );
                fragTransaction.commit();
                break;
            case R.id.nav_share:share();break;
            case R.id.nav_logout:logOut();
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

    }
}
