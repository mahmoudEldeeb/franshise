package com.franshise.franshise.activites;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.adapters.MarkerDetailsAdapter;
import com.franshise.franshise.adapters.ViewpagerAdapter;
import com.franshise.franshise.fragments.ComplaintsFragment;
import com.franshise.franshise.fragments.SubscriptionsFragment;
import com.franshise.franshise.fragments.WebViewFragment;
import com.franshise.franshise.models.ResultNetworkModels.FranchiseDataMODEL;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.UserModel;
import com.franshise.franshise.models.dataModels.FranchiseModel;
import com.franshise.franshise.models.database.FavModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.LoginViewModel;
import com.franshise.franshise.viewmodels.MarkerViewViewModel;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class FranchiseView extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView about;
    Button send_to_owner,sen_to_marker,fav;
    RecyclerView marker_details_resycle;
    MarkerDetailsAdapter markerDetailsAdapter;
    MarkerViewViewModel markerViewViewModel;
    Observer<FranchiseDataMODEL>franchiseObserver;
    CircleImageView profile_image2;
    ViewPager slider;
    int currentPage=0;
    LoginViewModel loginViewModel;
    Observer<Integer>outObserver;
    ViewpagerAdapter viewpagerAdapter;
    Timer timer;
    String mainImage;
    String name;
    TextView owner_phone,owner_name,owner_email,click_here;
    int id;String title,image;
    LinearLayout contacts,see_more;
    Date datenow;Date endDate;
    SimpleDateFormat sdf ;
    MenuItem nav_camara;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchise_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // getSupportActionBar().setLogo(R.drawable.logo2);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();

         nav_camara = menu.findItem(R.id.nav_logout);
        if(!new SharedPrefrenceModel(this).isLogined())
            nav_camara.setTitle(R.string.login);

        navigationView.setNavigationItemSelectedListener(this);


        about=findViewById(R.id.about);
        sen_to_marker=findViewById(R.id.send_to_marker);
        send_to_owner=findViewById(R.id.send_to_own);
        profile_image2=findViewById(R.id.profile_image2);
        slider=findViewById(R.id.slider);
        fav=findViewById(R.id.fav);
        contacts=findViewById(R.id.contacts);
        owner_phone=findViewById(R.id.owner_phone);
        owner_email=findViewById(R.id.owner_email);
        owner_name=findViewById(R.id.owner_name);
        see_more=findViewById(R.id.see_more);
        click_here=findViewById(R.id.click_here);
        marker_details_resycle=findViewById(R.id.marker_details_resycle);
        marker_details_resycle.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        marker_details_resycle.setLayoutManager(mLayoutManager);
//CustomProgressDialog.showProgress(this);
        //  homeBannerList=new ArrayList<>();

        currentPage=slider.getCurrentItem();
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == slider.getChildCount()-1) {
                    currentPage = 0;
                }
                else currentPage++;
                slider.setCurrentItem(currentPage);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 6000, 4000);


        markerViewViewModel= ViewModelProviders.of(this).get(MarkerViewViewModel.class);
        franchiseObserver=new Observer<FranchiseDataMODEL>() {
            @Override
            public void onChanged(@Nullable FranchiseDataMODEL franchiseDataMODEL) {
                {
                    Log.v("qqqq",franchiseDataMODEL.getImages().size()+"");

                    viewpagerAdapter =new ViewpagerAdapter(getBaseContext(),franchiseDataMODEL.getImages());
                    viewpagerAdapter.notifyDataSetChanged();
                    slider.setAdapter(viewpagerAdapter);

                    about.setText(franchiseDataMODEL.getAbout());
                    Picasso.get()
                            .load(getBaseContext().getResources().getString(R.string.base_image_url)+
                                    franchiseDataMODEL.getMainImage())
                            .into(profile_image2);
                    mainImage=franchiseDataMODEL.getMainImage();
                    // name=franchiseDataMODEL.getName();
                    //Log.v("ssss",name);
                   // int i=franchiseDataMODEL.getData().size()-1;
                    //franchiseDataMODEL.getData().remove(i);
                    getData(franchiseDataMODEL.getUser_id());
                    markerDetailsAdapter = new MarkerDetailsAdapter(FranchiseView.this, franchiseDataMODEL.getData(),
                            franchiseDataMODEL.getFranchiseType());
                    marker_details_resycle.setAdapter(markerDetailsAdapter);

                }
            }
        };
        int id =getIntent().getIntExtra("id",0);
        name=getIntent().getStringExtra("title");
        markerViewViewModel.getFranchises(this,id).observe(this,franchiseObserver);
        send_to_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(new SharedPrefrenceModel(FranchiseView.this).isLogined()) {

                Intent intent=new Intent(FranchiseView.this,SendToOwnerOrMarker.class);
                intent.putExtra("ownerOrMarker",1);
                intent.putExtra("id",id);
                startActivity(intent);
                }
                else {
                    Toast.makeText(getBaseContext(),"you must login first",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(FranchiseView.this,LoginActivity.class));
                }
            }
        });
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        sen_to_marker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Date end_Date=
                try {
                    endDate = sdf.parse(new SharedPrefrenceModel(FranchiseView.this).getDate());
                    Date date = new Date();
                    datenow = sdf.parse(sdf.format(date));
                }catch (ParseException e) {


                    Log.v("qqqqqq",e.getMessage());
                    e.printStackTrace();
                }

                if(new SharedPrefrenceModel(FranchiseView.this).isLogined()) {
                    if(new SharedPrefrenceModel(FranchiseView.this).getSubscribe()>0)
                        {
                            if(endDate.after(datenow)){
                                Intent intent = new Intent(FranchiseView.this, SendToOwnerOrMarker.class);
                                intent.putExtra("ownerOrMarker", 0);
                                intent.putExtra("id", id);
                                intent.putExtra("title", name);
                                intent.putExtra("image", mainImage);
                                startActivity(intent);
                            }
                            else {
                                    Log.v("qqqqqq","xxxxxxxxxxxxxxxxxxxxxxx");
                                Toast.makeText(getBaseContext(),"you must subscribe first",Toast.LENGTH_SHORT).show();
                                Intent intent= new Intent(FranchiseView.this,NavigationShow.class);
                                intent.putExtra("framid",0);
                                startActivity(intent);
                            }
                        }
                        else{
                        Intent intent = new Intent(FranchiseView.this, SendToOwnerOrMarker.class);
                        intent.putExtra("ownerOrMarker", 0);
                        intent.putExtra("id", id);
                        intent.putExtra("title", name);
                        intent.putExtra("image", mainImage);
                        startActivity(intent);
                        }
                    }

                else {
                Toast.makeText(getBaseContext(),"you must login first",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(FranchiseView.this,LoginActivity.class));
            }



            }

        });
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new SharedPrefrenceModel(FranchiseView.this).isLogined()) {
                    FavModel f = new FavModel(id, name, mainImage);
                    f.setUser_id(new SharedPrefrenceModel(FranchiseView.this).getId());
                    markerViewViewModel.saveFranchise(f, getBaseContext());
                }
                else {
                    Toast.makeText(getBaseContext(),"you must login first",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(FranchiseView.this,LoginActivity.class));
                }
            }
        });
        loginViewModel= ViewModelProviders.of(this).get(LoginViewModel.class);
        outObserver=new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                Log.v("eee",integer+"");
                SharedPrefrenceModel shared=new SharedPrefrenceModel(FranchiseView.this);
                if(integer==1){
                    if(shared.isLogined())
                        nav_camara.setTitle(R.string.logout);

                    shared.setLogined(false);
                    shared.setCompleteRegister(false);
                    startActivity(new Intent(FranchiseView.this,LoginActivity.class));
                    finish();
                }
            }
        };
click_here.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent= new Intent(FranchiseView.this,NavigationShow.class);
        intent.putExtra("framid",0);
        startActivity(intent);
    }
});
    }

    @Override
    protected void onResume() {
        if(new SharedPrefrenceModel(FranchiseView.this).isLogined())
            nav_camara.setTitle(R.string.logout);
        else
            nav_camara.setTitle(R.string.login);
        super.onResume();
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

    public void getData(int id){

        try {
            endDate = sdf.parse(new SharedPrefrenceModel(FranchiseView.this).getDate());
        Date date = new Date();
        datenow= sdf.parse(sdf.format(date));

        if(new SharedPrefrenceModel(FranchiseView.this).isLogined()) {
            if(endDate.after(datenow)){

                markerViewViewModel.getOwnerData(id).observe(this, new Observer<UserModel>() {
                    @Override
                    public void onChanged(@Nullable UserModel userModel) {
                        Log.v("ooooo",userModel.getEmail());
                        contacts.setVisibility(View.VISIBLE);
                        owner_name.setText(userModel.getName());
                        owner_email.setText(userModel.getEmail());
                        owner_phone.setText(userModel.getPhone());

                    }
                });
           }
           else see_more.setVisibility(View.VISIBLE);

        }

        } catch (ParseException e) {
            e.printStackTrace();
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

        Intent intent=new Intent(FranchiseView.this,NavigationShow.class);
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
                startActivity(new Intent(FranchiseView.this,MarkerOwnerRegister.class));
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
                Intent intent1=new Intent(FranchiseView.this,NavigationShow.class);
                intent1.putExtra("framid",9);
                startActivity(intent1);break;

            case R.id.nav_services :
                Intent intent2=new Intent(FranchiseView.this,NavigationShow.class);
                intent2.putExtra("framid",10);
                startActivity(intent2);break;
            case R.id.nav_events:
                Intent intent3=new Intent(FranchiseView.this,NavigationShow.class);
                intent3.putExtra("framid",8);
                startActivity(intent3);break;
            case R.id.nav_jobs:
                Intent intent4=new Intent(FranchiseView.this,NavigationShow.class);
                intent4.putExtra("framid",12);
                startActivity(intent4);break;
            case R.id.nav_share:share();break;
            case R.id.nav_logout:logOut();break;
            case R.id.nav_setting:startActivity(new Intent(FranchiseView.this,Setting.class));break;
            default:break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void share(){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "https://play.google.com/store/apps/details?id=com.franshise.franshise";
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

    }

}
