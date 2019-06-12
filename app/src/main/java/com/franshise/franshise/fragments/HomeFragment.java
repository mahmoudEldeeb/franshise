package com.franshise.franshise.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.activites.FranchiseView;
import com.franshise.franshise.activites.LoginActivity;
import com.franshise.franshise.activites.Main;
import com.franshise.franshise.adapters.MainCategorysAdapter;
import com.franshise.franshise.adapters.MarkerAdapter;
import com.franshise.franshise.adapters.ViewpagerAdapter;
import com.franshise.franshise.interfaces.ItemClickListener;
import com.franshise.franshise.models.BannarModel;
import com.franshise.franshise.models.ResultNetworkModels.BannersResult;
import com.franshise.franshise.models.ResultNetworkModels.CategorysResult;
import com.franshise.franshise.models.ResultNetworkModels.FranchisesResult;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.HomeViewModel;
import com.franshise.franshise.viewmodels.LoginViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements MarkerAdapter.EventListener {
ProgressBar progress;
   static int count=0;
   static FranchisesResult franchisesResult1=new FranchisesResult();
   static CategorysResult categorysResult=new CategorysResult();
   static BannersResult bannersResult=new BannersResult();

    public HomeFragment() {
        // Required empty public constructor

    }
RecyclerView marker_recycle,main_recycle;
    int currentPage;
    MarkerAdapter markerAdapter;
    MainCategorysAdapter mainCategorysAdapter;
ViewPager slider;
ViewpagerAdapter viewpagerAdapter;
List<BannarModel>homeBannerList;
Timer timer;
HomeViewModel homeViewModel;
static CategorysResult result;
    Observer<BannersResult> bannerObserver;
    Observer<CategorysResult>categoryObserver;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        slider=view.findViewById(R.id.slider);
        main_recycle=view.findViewById(R.id.main_recycle);
        marker_recycle=view.findViewById(R.id.marker_recycle);
        progress=view.findViewById(R.id.progress);
        homeBannerList=new ArrayList<>();
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

        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
      marker_recycle.setLayoutManager(mLayoutManager1);
      RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),3);
        main_recycle.setLayoutManager(mLayoutManager);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);


        bannerObserver = new Observer<BannersResult>() {
            @Override
            public void onChanged(@Nullable BannersResult result) {
                bannersResult=result;
                viewpagerAdapter =new ViewpagerAdapter(getActivity(),result);
                viewpagerAdapter.notifyDataSetChanged();
                slider.setAdapter(viewpagerAdapter);
            }
        };

        categoryObserver = new Observer<CategorysResult>() {
            @Override
            public void onChanged(@Nullable CategorysResult result) {
                //CustomProgressDialog.clodseProgress();
                progress.setVisibility(View.GONE);
                if(result.getStatus()==1) {
                    categorysResult=result;
                    mainCategorysAdapter = new MainCategorysAdapter(getActivity(),result.getData());
                    main_recycle.setAdapter(mainCategorysAdapter);
                    count=1;
                }
                else Toast.makeText(getActivity(),"connection error",Toast.LENGTH_LONG).show();

            }
        };

if (count==0){

    homeViewModel.specialFranchise().observe(this, new Observer<FranchisesResult>() {
        @Override
        public void onChanged(@Nullable FranchisesResult franchisesResult) {
            if(franchisesResult.getStatus()==1){
                franchisesResult1=franchisesResult;
                markerAdapter=new MarkerAdapter(getActivity(),HomeFragment.this::onEvent,franchisesResult.getData());
                marker_recycle.setAdapter(markerAdapter);
            }
        }
    });
    homeViewModel.getbannesr(new SharedPrefrenceModel(getActivity()).getLanguage())
            .observe(HomeFragment.this, bannerObserver);
 //   CustomProgressDialog.showProgress(getActivity());
    progress.setVisibility(View.VISIBLE);
    homeViewModel.getCategorys().observe(HomeFragment.this,categoryObserver);

}
else {

    mainCategorysAdapter = new MainCategorysAdapter(getActivity(),categorysResult.getData());
    main_recycle.setAdapter(mainCategorysAdapter);
    markerAdapter=new MarkerAdapter(getActivity(),HomeFragment.this::onEvent,franchisesResult1.getData());
    marker_recycle.setAdapter(markerAdapter);

    viewpagerAdapter =new ViewpagerAdapter(getActivity(),bannersResult);
    viewpagerAdapter.notifyDataSetChanged();
    slider.setAdapter(viewpagerAdapter);

}

        return view;
    }
    @Override
    public void onEvent(int data, String title) {
        Log.v("qqqq",data+"");
        Intent intent=new Intent(getActivity(), FranchiseView.class);
        intent.putExtra("id",data);
        intent.putExtra("title",title);
        startActivity(intent);
    }
}
