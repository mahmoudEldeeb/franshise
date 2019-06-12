package com.franshise.franshise.activites;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.franshise.franshise.R;
import com.franshise.franshise.adapters.MarkerDetailsAdapter;
import com.franshise.franshise.adapters.ViewpagerAdapter;
import com.franshise.franshise.models.ResultNetworkModels.FranchiseDataMODEL;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.database.FavModel;
import com.franshise.franshise.viewmodels.MarkerViewViewModel;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class MarkerView extends AppCompatActivity {
TextView about;
Button send_to_owner,sen_to_marker,fav;
    RecyclerView marker_details_resycle;
    MarkerDetailsAdapter markerDetailsAdapter;
    MarkerViewViewModel markerViewViewModel;
    Observer<FranchiseDataMODEL>franchiseObserver;
    CircleImageView profile_image2;
    ViewPager slider;
    int currentPage=0;

    ViewpagerAdapter viewpagerAdapter;
    Timer timer;
    String mainImage;
    String name;
    int id;String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_details);
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
                        markerDetailsAdapter = new MarkerDetailsAdapter(MarkerView.this, franchiseDataMODEL.getData(),
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
                Intent intent=new Intent(MarkerView.this,SendToOwnerOrMarker.class);
                intent.putExtra("ownerOrMarker",1);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        sen_to_marker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MarkerView.this,SendToOwnerOrMarker.class);
                intent.putExtra("ownerOrMarker",0);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavModel f=new FavModel(id,name,mainImage);
                f.setUser_id(new SharedPrefrenceModel(MarkerView.this).getId());
                markerViewViewModel.saveFranchise(f,getBaseContext());

            }
        });
    }



}
