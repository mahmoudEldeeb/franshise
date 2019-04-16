package com.franshise.franshise.activites;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.LoginViewModel;

public class Setting extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
ImageView notify_image;
LoginViewModel loginViewModel;
Observer<Integer>outObserver;
TextView data,change_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        notify_image=findViewById(R.id.notify_image);
data=findViewById(R.id.data);
        change_password=findViewById(R.id.change_password);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(new SharedPrefrenceModel(this).isNotificationOn()){
            notify_image.setImageResource(R.drawable.open);
        }
        else notify_image.setImageResource(R.drawable.lock);
       notify_image.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(new SharedPrefrenceModel(Setting.this).isLogined()){
               changeNotify();}
               else {
                   Toast.makeText(getBaseContext(), "you must login first", Toast.LENGTH_SHORT);
               startActivity(new Intent(Setting.this,LoginActivity.class));
               }
           }
       });
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new SharedPrefrenceModel(Setting.this).isLogined()){
                    startActivity(new Intent(Setting.this,ChangePassowrs.class));
                }
                else {
                    Toast.makeText(getBaseContext(), "you must login first", Toast.LENGTH_SHORT);
                    startActivity(new Intent(Setting.this,LoginActivity.class));
                }
            }
        });
        loginViewModel= ViewModelProviders.of(this).get(LoginViewModel.class);
        outObserver=new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                Log.v("eee",integer+"");
                if(integer==1){
                    new SharedPrefrenceModel(Setting.this).setLogined(false);
                    startActivity(new Intent(Setting.this,LoginActivity.class));
                    finish();
                }
            }
        };
data.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(new SharedPrefrenceModel(Setting.this).isLogined()){
            startActivity(new Intent(Setting.this,MyData.class));
        }
        else {
            Toast.makeText(getBaseContext(), "you must login first", Toast.LENGTH_SHORT);
            startActivity(new Intent(Setting.this,LoginActivity.class));
        }
    }
});

    }
public void changeNotify(){
    if(new SharedPrefrenceModel(this).isNotificationOn()){
        notify_image.setImageResource(R.drawable.lock);
        new SharedPrefrenceModel(this).setNotification(false);

    }
    else{ notify_image.setImageResource(R.drawable.open);

        new SharedPrefrenceModel(this).setNotification(true);}

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

        Intent intent=new Intent(Setting.this,NavigationShow.class);
        switch (id){

            // case  R.id.nav_events: selected=new EventsFragment();break;
            //  case R.id.nav_training:selected=new TrainingFragment();break;
            case R.id.nav_subscriptions:
                intent.putExtra("framid",0);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_Complaints:
                intent.putExtra("framid",1);
                startActivity(intent);finish();
                break;
            case R.id.nav_myfranchises:
                startActivity(new Intent(Setting.this,MarkerOwnerRegister.class));
                finish();
                break;
            case R.id.nav_language:
                new SharedPrefrenceModel(this).changeLanguage();
                startActivity(new Intent(this,Main.class));
                this.finish();break;
            case R.id.nav_questions:
                intent.putExtra("framid",2);
                startActivity(intent);
                finish();
                break;

            case R.id.nav_franshise:
                intent.putExtra("framid",3);
                startActivity(intent);finish();
                break;
            case R.id.nav_terms:
                intent.putExtra("framid",4);
                startActivity(intent);
                finish();break;
            case R.id.nav_training :
                intent.putExtra("framid",5);
                startActivity(intent);finish();
                break;
            case R.id.nav_services :
                intent.putExtra("framid",6);
                startActivity(intent);finish();
                break;
            case R.id.nav_events :
                intent.putExtra("framid",7);
                startActivity(intent);finish();
                break;
            case R.id.nav_share:share();break;
            case R.id.nav_logout:logOut();break;
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
