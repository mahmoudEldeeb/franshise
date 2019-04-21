package com.franshise.franshise.activites;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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
import android.support.v7.widget.GridLayoutManager;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.franshise.franshise.R;
import com.franshise.franshise.adapters.FranchiseListAdapter;
import com.franshise.franshise.adapters.ViewpagerAdapter;
import com.franshise.franshise.interfaces.ItemClickListener;
import com.franshise.franshise.models.ResultNetworkModels.CategorysResult;
import com.franshise.franshise.models.ResultNetworkModels.DataResult;
import com.franshise.franshise.models.ResultNetworkModels.FranchiseResultModel;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.FranchiseModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.LoginViewModel;
import com.franshise.franshise.viewmodels.MarkerOfCategoryViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FranchiseOfCategory extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ItemClickListener {
    RecyclerView markers_resycel;
    FranchiseListAdapter mainCategorysAdapter;
    Observer<CategorysResult> categoryBanner;
    MarkerOfCategoryViewModel markerOfCategoryViewModel;
    ViewPager slider;
    ViewpagerAdapter viewpagerAdapter;
    Timer timer;int currentPage=0;
    TextView catName;
    LoginViewModel loginViewModel;
    Observer<Integer>outObserver;
    Spinner spinner_type;
    List<String>franchiseTypeList;
    List<Integer>franchiseTypeIdList;
    int count=0;
    ImageButton back;
    List<FranchiseModel> data1=new ArrayList<>(),dataChanged;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_franchise_of_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        spinner_type=findViewById(R.id.spinner_type);
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
        markers_resycel=findViewById(R.id.markers_resycel);
        markers_resycel.setNestedScrollingEnabled(false);
        slider=findViewById(R.id.slider);
        back=findViewById(R.id.back);
        //      mainCategorysAdapter=new MainCategorysAdapter(this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        catName=findViewById(R.id.catName);

        franchiseTypeList=new ArrayList<>();

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
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,3);
        markers_resycel.setLayoutManager(mLayoutManager);
        Context c=this;
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, franchiseTypeList);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        franchiseTypeList.add(getString(R.string.all));
        spinner_type.setAdapter(adp1);
        franchiseTypeIdList=new ArrayList<>();
        dataChanged=new ArrayList<>();
        markerOfCategoryViewModel=  ViewModelProviders.of(this).get(MarkerOfCategoryViewModel.class);
        markerOfCategoryViewModel.getfranchisetype().observe(this, new Observer<DataResult>() {
            @Override
            public void onChanged(@Nullable DataResult dataResult) {
                for (int i = 0; i < dataResult.getData().size(); i++){
                    franchiseTypeIdList.add(dataResult.getData().get(i).getId());
                    if (new SharedPrefrenceModel(c).getLanguage().equals("ar"))
                        franchiseTypeList.add(dataResult.getData().get(i).getAr_name());
                     else
                        franchiseTypeList.add(dataResult.getData().get(i).getEn_name());
                adp1.notifyDataSetChanged();

                }
            }


        });
        categoryBanner=new Observer<CategorysResult>() {
            @Override
            public void onChanged(@Nullable CategorysResult categorysResult) {
                viewpagerAdapter=new ViewpagerAdapter(FranchiseOfCategory.this, categorysResult);
                slider.setAdapter(viewpagerAdapter);
                data1.addAll(categorysResult.getData().get(0).getFranchises());
                Log.v("aaaaaaaaaaa",data1.size()+"");
                dataChanged.addAll(data1);
                mainCategorysAdapter=new FranchiseListAdapter(c,dataChanged);
                markers_resycel.setAdapter(mainCategorysAdapter);
                count=1;

            }
        };
        catName.setText(getIntent().getStringExtra("catName"));
        markerOfCategoryViewModel.getCategorysBanner(getIntent().getIntExtra("id",0)).observe(this,categoryBanner);

   spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
       @Override
       public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

           if (position == 0) {
               if (count != 0) {
                   dataChanged.clear();
                   dataChanged.addAll(data1);
                   mainCategorysAdapter.notifyDataSetChanged();
               }
           } else {
               markerOfCategoryViewModel.getFranchiseByType(franchiseTypeIdList.get(position - 1),getIntent().getIntExtra("id",0)).
                       observe(FranchiseOfCategory.this, new Observer<FranchiseResultModel>() {
                           @Override
                           public void onChanged(@Nullable FranchiseResultModel franchiseResultModel) {
                               Log.v("wwwww",franchiseTypeIdList.get(position-1)+"");
                               dataChanged.clear();
                               dataChanged.addAll(franchiseResultModel.getData());
                               mainCategorysAdapter.notifyDataSetChanged();
                           }
                       });
           }
       }

       @Override
       public void onNothingSelected(AdapterView<?> parent) {

       }
   });
   back.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           onBackPressed();
       }
   });
    }

    @Override
    public void categoryClick(int id, String catName) {

    }
    @Override
    public void franchiseClick(int id,String title) {
        Intent intent=new Intent(FranchiseOfCategory.this,FranchiseView.class);
        intent.putExtra("id",id);
        intent.putExtra("title",title);
        startActivity(intent);
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

        Intent intent=new Intent(FranchiseOfCategory.this,NavigationShow.class);
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
                startActivity(new Intent(FranchiseOfCategory.this,MarkerOwnerRegister.class));
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
                Intent intent1=new Intent(FranchiseOfCategory.this,NavigationShow.class);
                intent1.putExtra("framid",9);
                startActivity(intent1);break;

            case R.id.nav_services :
                Intent intent2=new Intent(FranchiseOfCategory.this,NavigationShow.class);
                intent2.putExtra("framid",10);
                startActivity(intent2);break;
            case R.id.nav_events:
                Intent intent3=new Intent(FranchiseOfCategory.this,NavigationShow.class);
                intent3.putExtra("framid",8);
                startActivity(intent3);break;

            case R.id.nav_share:share();break;
            case R.id.nav_logout:logOut();break;
            case R.id.nav_setting:startActivity(new Intent(FranchiseOfCategory.this,Setting.class));break;
            default:break;
        }

        loginViewModel= ViewModelProviders.of(this).get(LoginViewModel.class);
        outObserver=new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                Log.v("eee",integer+"");
                if(integer==1){
                    new SharedPrefrenceModel(FranchiseOfCategory.this).setLogined(false);
                    startActivity(new Intent(FranchiseOfCategory.this,LoginActivity.class));
                    finish();
                }
            }
        };
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
