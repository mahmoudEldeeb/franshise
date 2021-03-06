package com.franshise.franshise.activites;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
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

import com.franshise.franshise.MainActivity;
import com.franshise.franshise.R;
import com.franshise.franshise.adapters.NotificationsAdapter;
import com.franshise.franshise.fragments.ComplaintsFragment;
import com.franshise.franshise.fragments.EventsFragment;
import com.franshise.franshise.fragments.FavouriteFragment;
import com.franshise.franshise.fragments.HomeFragment;
import com.franshise.franshise.fragments.MainCategorys;
import com.franshise.franshise.fragments.Notifications;
import com.franshise.franshise.fragments.Search;
import com.franshise.franshise.fragments.SubscriptionsFragment;
import com.franshise.franshise.fragments.TrainingFragment;
import com.franshise.franshise.fragments.WebViewFragment;
import com.franshise.franshise.interfaces.ItemClickListener;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.LoginViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ItemClickListener , NotificationsAdapter.FranchiseClicked {
    MenuItem nav_camara;
    private BottomNavigationView bottomNavigation;
    LoginViewModel loginViewModel;
    Observer<Integer>outObserver;
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
        setContentView(R.layout.activity_main2);
        if(!new SharedPrefrenceModel(this).isLogined()){
            startActivity(new Intent(Main.this,LoginActivity.class));
            finish();
        }

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

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigation.getLayoutParams();
        //layoutParams.setBehavior(new BottomNavigationViewBehavior());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();

         nav_camara = menu.findItem(R.id.nav_logout);
        if(!new SharedPrefrenceModel(this).isLogined())
        nav_camara.setTitle(R.string.login);


        FragmentManager fragMan = getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragMan.beginTransaction();
        fragTransaction.replace(R.id.fragment, new HomeFragment() );
        fragTransaction.commit();
        loginViewModel= ViewModelProviders.of(this).get(LoginViewModel.class);
        outObserver=new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
             Log.v("eee",integer+"");
             SharedPrefrenceModel shared=new SharedPrefrenceModel(Main.this);
             if(integer==1){
                 if(shared.isLogined())
                     nav_camara.setTitle(R.string.logout);

                 shared.setLogined(false);
                 shared.setCompleteRegister(false);
             startActivity(new Intent(Main.this,LoginActivity.class));
            // finish();
             }
            }
        };
    }
    HomeFragment homeFragment=new HomeFragment();
    Search search=new Search();
    FavouriteFragment favouriteFragment=new FavouriteFragment();
    MainCategorys mainCategorys=new MainCategorys();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment=new HomeFragment();
            FragmentManager fragMan = getSupportFragmentManager();
            FragmentTransaction fragTransaction = fragMan.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment=new HomeFragment();
                    fragTransaction.replace(R.id.fragment, homeFragment ).addToBackStack(null);
                    fragTransaction.commit();
                    break;
                case R.id.navigation_dashboard:
                    selectedFragment=new MainCategorys();
                    fragTransaction.replace(R.id.fragment, mainCategorys ).addToBackStack(null);
                    fragTransaction.commit();break;
                case R.id.fav_dashboard:
                    selectedFragment=new FavouriteFragment();
                    fragTransaction.replace(R.id.fragment, favouriteFragment ).addToBackStack(null);
                    fragTransaction.commit();break;
                case R.id.search:
                    selectedFragment=new Search();
                    fragTransaction.replace(R.id.fragment, search ,"search").addToBackStack(null);
                    fragTransaction.commit();
                    break;
                case R.id.notification:
                    selectedFragment=new Notifications();
                    fragTransaction.replace(R.id.fragment, selectedFragment );
                    fragTransaction.commit();
                    break;
            }
               return true;
        }
    };
boolean searchback=false;
    @Override
    protected void onResume() {
        if(new SharedPrefrenceModel(Main.this).isLogined())
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
            final Search fragment = (Search) getSupportFragmentManager().findFragmentByTag("search");
            if (fragment != null) {
                if (fragment.isVisible() && !fragment.issearch()) {
                    searchback = fragment.showSearch();
                } else
                    super.onBackPressed();
            } else
                super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();




        return super.onOptionsItemSelected(item);
    }
///22

    @SuppressWarnings("StatementWithEmptyBody")
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
                startActivity(new Intent(Main.this,MarkerOwnerRegister.class));
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
            case R.id.nav_contact:
                selected=new WebViewFragment();
                Bundle b2=new Bundle();b2.putString("url",getResources().getString(R.string.contact_us));
                selected.setArguments(b2);
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
            case R.id.nav_training :
                Intent intent1=new Intent(Main.this,NavigationShow.class);
                intent1.putExtra("framid",9);
                startActivity(intent1);break;

            case R.id.nav_services :
                Intent intent2=new Intent(Main.this,NavigationShow.class);
                intent2.putExtra("framid",10);
                startActivity(intent2);break;
            case R.id.nav_events:
                Intent intent=new Intent(Main.this,NavigationShow.class);
                intent.putExtra("framid",8);
                startActivity(intent);break;
            case R.id.nav_jobs:
            Intent intent4=new Intent(Main.this,AddJobs.class);
            startActivity(intent4);break;
            case R.id.nav_share:share();break;
            case R.id.nav_logout:logOut();break;
            case R.id.nav_setting:startActivity(new Intent(Main.this,Setting.class));break;
            case R.id.nav_funding:startActivity(new Intent(Main.this,FundingCompanies.class));break;
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
    @Override
    public void categoryClick(int id, String catName) {
        Intent intent=new Intent(Main.this,FranchiseOfCategory.class);
        intent.putExtra("id",id);
        intent.putExtra("catName",catName);
        startActivity(intent);
    }

    @Override
    public void franchiseClick(int id, String title) {
        Intent intent=new Intent(Main.this,FranchiseView.class);
        intent.putExtra("id",id);
        intent.putExtra("title",title);
        startActivity(intent);
    }

    public void logOut(){
       String token= new SharedPrefrenceModel(this).getTToken();
       String api_token=
               new SharedPrefrenceModel(this).getApiToken();
        CustomProgressDialog.showProgress(this);
       loginViewModel.LogOut(token,api_token).observe(this,outObserver);
        new SharedPrefrenceModel(this).setCompleteRegister(false);

    }

    @Override
    public void longClicked(int id) {

    }

    @Override
    public void clicked(int id, String title) {
        Intent intent=new Intent(Main.this,FranchiseView.class);
        intent.putExtra("id",id);
        intent.putExtra("title",title);
        startActivity(intent);
    }
}

