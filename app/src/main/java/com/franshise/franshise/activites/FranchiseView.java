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
import com.franshise.franshise.models.database.FavModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.LoginViewModel;
import com.franshise.franshise.viewmodels.MarkerViewViewModel;
import com.squareup.picasso.Picasso;

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
    int id;String title,image;
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


        about=findViewById(R.id.about);
        sen_to_marker=findViewById(R.id.send_to_marker);
        send_to_owner=findViewById(R.id.send_to_own);
        profile_image2=findViewById(R.id.profile_image2);
        slider=findViewById(R.id.slider);
        fav=findViewById(R.id.fav);
        marker_details_resycle=findViewById(R.id.marker_details_resycle);
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
                    markerDetailsAdapter = new MarkerDetailsAdapter(FranchiseView.this, franchiseDataMODEL.getData(),franchiseDataMODEL.getFranchiseType());
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
        sen_to_marker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(new SharedPrefrenceModel(FranchiseView.this).isLogined()) {
                Intent intent=new Intent(FranchiseView.this,SendToOwnerOrMarker.class);
                intent.putExtra("ownerOrMarker",0);
                intent.putExtra("id",id);
                intent.putExtra("title",name);
                intent.putExtra("image",mainImage);
                startActivity(intent);
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
                if(integer==1){
                    new SharedPrefrenceModel(FranchiseView.this).setLogined(false);
                    startActivity(new Intent(FranchiseView.this,LoginActivity.class));
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

            case R.id.nav_franshise:
                intent.putExtra("framid",3);
                startActivity(intent);
                break;
            case R.id.nav_terms:
                intent.putExtra("framid",4);
                startActivity(intent);break;
            case R.id.nav_training :
                intent.putExtra("framid",5);
                startActivity(intent);
                break;
            case R.id.nav_services :
                intent.putExtra("framid",6);
                startActivity(intent);
                break;
            case R.id.nav_events :
                intent.putExtra("framid",7);
                startActivity(intent);
                break;
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
