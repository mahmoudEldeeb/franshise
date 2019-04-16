package com.franshise.franshise.activites;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.franshise.franshise.R;
import com.franshise.franshise.adapters.FranchiseListAdapter;
import com.franshise.franshise.adapters.MainCategorysAdapter;
import com.franshise.franshise.adapters.ViewpagerAdapter;
import com.franshise.franshise.interfaces.ItemClickListener;
import com.franshise.franshise.models.ResultNetworkModels.BannersResult;
import com.franshise.franshise.models.ResultNetworkModels.CategorysResult;
import com.franshise.franshise.viewmodels.HomeViewModel;
import com.franshise.franshise.viewmodels.MarkerOfCategoryViewModel;

import java.util.Timer;
import java.util.TimerTask;

public class MarkersOfCategory extends AppCompatActivity implements ItemClickListener {
RecyclerView markers_resycel;
FranchiseListAdapter mainCategorysAdapter;
Observer<CategorysResult>categoryBanner;
MarkerOfCategoryViewModel markerOfCategoryViewModel;
ViewPager slider;
ViewpagerAdapter viewpagerAdapter;
Timer timer;int currentPage=0;
TextView catName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_of_category);
        markers_resycel=findViewById(R.id.markers_resycel);
        markers_resycel.setNestedScrollingEnabled(false);
        slider=findViewById(R.id.slider);

  //      mainCategorysAdapter=new MainCategorysAdapter(this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo2);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        catName=findViewById(R.id.catName);

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
        markerOfCategoryViewModel=  ViewModelProviders.of(this).get(MarkerOfCategoryViewModel.class);
        categoryBanner=new Observer<CategorysResult>() {
            @Override
            public void onChanged(@Nullable CategorysResult categorysResult) {
                viewpagerAdapter=new ViewpagerAdapter(MarkersOfCategory.this, categorysResult);
                slider.setAdapter(viewpagerAdapter);
                mainCategorysAdapter=new FranchiseListAdapter(c,categorysResult.getData().get(0).getFranchises());
                markers_resycel.setAdapter(mainCategorysAdapter);

            }
        };
        catName.setText(getIntent().getStringExtra("catName"));
        markerOfCategoryViewModel.getCategorysBanner(getIntent().getIntExtra("id",0)).observe(this,categoryBanner);
    }

    @Override
    public void categoryClick(int id, String catName) {

    }
    @Override
    public void franchiseClick(int id,String title) {
        Intent intent=new Intent(MarkersOfCategory.this,FranchiseView.class);
        intent.putExtra("id",id);
        intent.putExtra("title",title);
        startActivity(intent);
    }
}
