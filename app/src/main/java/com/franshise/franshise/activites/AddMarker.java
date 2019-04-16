package com.franshise.franshise.activites;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.adapters.FranchiseListAdapter;
import com.franshise.franshise.adapters.NotificationsAdapter;
import com.franshise.franshise.adapters.ViewpagerAdapter;
import com.franshise.franshise.models.ResultNetworkModels.CategorysResult;
import com.franshise.franshise.models.ResultNetworkModels.FranchisesResult;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.MarkerOfCategoryViewModel;
import com.franshise.franshise.viewmodels.NotificationViewModel;

public class AddMarker extends AppCompatActivity implements NotificationsAdapter.FranchiseClicked{
    Button add_franchise,add_franchise1;
    RecyclerView notification_res;
    NotificationsAdapter notificationsAdapter;
RecyclerView myfranchise_res;
    NotificationViewModel notificationViewModel;
    Observer<FranchisesResult>resultObserver;
    Observer<StatusModel>deleteObserver;
    int deletedId=-1;
    Button delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_marker);
        add_franchise=findViewById(R.id.add_franchise);
        myfranchise_res=findViewById(R.id.myfranchise_res);
        delete=findViewById(R.id.delete);
        add_franchise1=findViewById(R.id.add_franchise1);
        add_franchise1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddMarker.this,AddFranchise.class));
                finish();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deletedId!=-1){
                notificationViewModel.deleteFranchise(deletedId).observe(AddMarker.this,deleteObserver);
                }
               else Toast.makeText(getBaseContext(),"you must choose item first",Toast.LENGTH_SHORT);
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        myfranchise_res.setLayoutManager(mLayoutManager);
        //CustomProgressDialog.showProgress(this);
        notificationViewModel=  ViewModelProviders.of(this).get(NotificationViewModel.class);
        resultObserver=new Observer<FranchisesResult>() {
            @Override
            public void onChanged(@Nullable FranchisesResult categorysResult) {
                //mainCategorysAdapter=new FranchiseListAdapter(c,categorysResult.getData());
                notificationsAdapter=new NotificationsAdapter(AddMarker.this,categorysResult.getData());
                myfranchise_res.setAdapter(notificationsAdapter);
                add_franchise.setVisibility(View.GONE);
                CustomProgressDialog.clodseProgress();
            }
        };

        deleteObserver=new Observer<StatusModel>() {
            @Override
            public void onChanged(@Nullable StatusModel result) {
                if(result.getStatus()==1)
                {
              //      notification_res.getAdapter().notifyItemRemoved(0);;
                    notificationsAdapter.removeitem();
                    Toast.makeText(getBaseContext(),"done",Toast.LENGTH_SHORT).show();
                }
                else {

                    Toast.makeText(getBaseContext(),"error try later",Toast.LENGTH_SHORT).show();
                }
               // CustomProgressDialog.clodseProgress();
            }
        };
        notificationViewModel.getMyFranchise(new SharedPrefrenceModel(this).getId(),this).observe(this,resultObserver);
        add_franchise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddMarker.this,AddFranchise.class));
                finish();
            }
        });

    }

    @Override
    public void longClicked(int id) {
        delete.setAlpha(1);
        deletedId=id;
    }

    @Override
    protected void onResume() {
        delete.setAlpha(0.5f);
        super.onResume();
    }

    @Override
    public void clicked(int id,String title) {
        Intent intent=new Intent(AddMarker.this,FranchiseView.class);
        intent.putExtra("id",id);
        intent.putExtra("title",title);
        startActivity(intent);
        //
    }
}
