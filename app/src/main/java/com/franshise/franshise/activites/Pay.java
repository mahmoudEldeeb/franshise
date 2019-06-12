package com.franshise.franshise.activites;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.PayModel;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.LoginViewModel;
import com.franshise.franshise.viewmodels.SubscribtionViewModel;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Locale;

public class Pay extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
WebView webView;
    Button pick_image,subscribe;
    ImageView bank_image;
    NavigationView navigationView;
    SubscribtionViewModel subscribtionViewModel;
    int sub_id;
    Uri imageBankUri;
    TextView from,email;
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

        setContentView(R.layout.activity_pay2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // getSupportActionBar().setLogo(R.drawable.logo2);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        toolbar.setContentInsetsAbsolute(0, 0);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
webView=findViewById(R.id.web);
from=findViewById(R.id.from);
email=findViewById(R.id.email);
        bank_image=findViewById(R.id.bank_image);
        pick_image=findViewById(R.id.pick_image);
        subscribe=findViewById(R.id.subscribe);
         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        from.setText(new SharedPrefrenceModel(this).getName());

        email.setText(new SharedPrefrenceModel(this).getEmail());
        subscribtionViewModel= ViewModelProviders.of(this).get(SubscribtionViewModel.class);
        CustomProgressDialog.showProgress(this);
        subscribtionViewModel.pay_way().observe(this, new Observer<PayModel>() {
            @Override
            public void onChanged(@Nullable PayModel payModel) {
                CustomProgressDialog.clodseProgress();
                if(payModel.status==1){
                    webView.loadData(payModel.data,"text/html",null);
                }
                else Toast.makeText(getBaseContext(),payModel.message,Toast.LENGTH_SHORT).show();
            }
        });
        sub_id=getIntent().getIntExtra("id",0);


        pick_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(permission())
                    addImages1();
                else requestPermission();
            }
        });
        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new SharedPrefrenceModel(Pay.this).isLogined()) {
                    upload();
                }else startActivity(new Intent(Pay.this, LoginActivity.class));
            }
        });
    }

    private void upload() {
        if(imageBankUri!=null&&sub_id!=-1){
            CustomProgressDialog.showProgress(this);
            subscribtionViewModel.new_subscription(new SharedPrefrenceModel(this).getId(),sub_id,imageBankUri,this)
                    .observe(this, new Observer<StatusModel>() {
                        @Override
                        public void onChanged(@Nullable StatusModel statusModel) {
                            CustomProgressDialog.clodseProgress();
                            Toast.makeText(Pay.this,statusModel.getMessage(),Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Pay.this,CompleteProcess.class));
                            finish();
                        }
                    });
            Log.v("rrrrrrrrrr","fff");
        }
        else Toast.makeText(this,R.string.fill_data,Toast.LENGTH_SHORT).show();
    }

    int PICK_IMAGE=0;
    private void addImages1(){   Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);}
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK) {

                try {
                    final Uri imageUri = data.getData();

                    final InputStream imageStream = this.getContentResolver().openInputStream(imageUri);

    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                      bank_image.setImageBitmap(selectedImage);
                     bank_image.setVisibility(View.VISIBLE);
                    imageBankUri=imageUri;

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
                }catch (OutOfMemoryError e){Toast.makeText(this, "Choose small image ", Toast.LENGTH_LONG).show();}

            } else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }

        }
    }
    private boolean permission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        else return false;
    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);

    }


    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent intent=new Intent(Pay.this,NavigationShow.class);
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
                startActivity(new Intent(Pay.this,MarkerOwnerRegister.class));
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
                Intent intent1=new Intent(Pay.this,NavigationShow.class);
                intent1.putExtra("framid",9);
                startActivity(intent1);break;

            case R.id.nav_services :
                Intent intent2=new Intent(Pay.this,NavigationShow.class);
                intent2.putExtra("framid",10);
                startActivity(intent2);break;
            case R.id.nav_events:
                Intent intent3=new Intent(Pay.this,NavigationShow.class);
                intent3.putExtra("framid",8);
                startActivity(intent3);break;
            case R.id.nav_jobs:
                Intent intent4=new Intent(Pay.this,AddJobs.class);
                //intent4.putExtra("framid",12);
                startActivity(intent4);break;
            case R.id.nav_share:share();break;
            case R.id.nav_logout:logOut();break;
            case R.id.nav_setting:startActivity(new Intent(Pay.this,Setting.class));break;
            case R.id.nav_funding:startActivity(new Intent(Pay.this,FundingCompanies.class));break;
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
                SharedPrefrenceModel shared=new SharedPrefrenceModel(Pay.this);
                if(integer==1){
                    if(shared.isLogined())
                        nav_camara.setTitle(R.string.logout);

                    shared.setLogined(false);
                    shared.setCompleteRegister(false);
                    startActivity(new Intent(Pay.this,LoginActivity.class));
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
}
