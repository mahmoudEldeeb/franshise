package com.franshise.franshise.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.activites.AddMarker;
import com.franshise.franshise.activites.FranchiseView;
import com.franshise.franshise.activites.LoginActivity;
import com.franshise.franshise.activites.MarkerOwnerRegister;
import com.franshise.franshise.adapters.FacouriteAdapter;
import com.franshise.franshise.adapters.LastFranchiseAdapter;
import com.franshise.franshise.adapters.MessagesAdapter;
import com.franshise.franshise.adapters.NotificationsAdapter;
import com.franshise.franshise.models.ResultNetworkModels.CategorysResult;
import com.franshise.franshise.models.ResultNetworkModels.FranchisesResult;
import com.franshise.franshise.models.ResultNetworkModels.MessageResults;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.MarkerOwnerRegisterViewModel;
import com.franshise.franshise.viewmodels.NotificationViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class Notifications extends Fragment {
    RecyclerView notification_res;
    LastFranchiseAdapter notificationsAdapter;
static int count=0;
NotificationViewModel notificationViewModel;
Observer<FranchisesResult>resultObserver;
    Observer<StatusModel>markerOwnerObserver;
    Observer<MessageResults>messagesObserver;
    public Notifications() {
        // Required empty public constructor
    }
    MessagesAdapter messagesAdapter;
    MarkerOwnerRegisterViewModel markerOwnerRegisterViewModel;
static FranchisesResult franchisesResult=new FranchisesResult();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_notifications, container, false);
        notification_res=view.findViewById(R.id.notification_res);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        notification_res.setLayoutManager(mLayoutManager);

        notificationViewModel=  ViewModelProviders.of(this).get(NotificationViewModel.class);
        resultObserver=new Observer<FranchisesResult>() {
            @Override
            public void onChanged(@Nullable FranchisesResult categorysResult) {
                //mainCategorysAdapter=new FranchiseListAdapter(c,categorysResult.getData());
               notificationsAdapter=new LastFranchiseAdapter(getActivity(),categorysResult.getData());
                notification_res.setAdapter(notificationsAdapter);
                franchisesResult=categorysResult;
                count=1;
                Log.v("wwww","ccccccccc");
                CustomProgressDialog.clodseProgress();
            }
        };

        messagesObserver=new Observer<MessageResults>() {
            @Override
            public void onChanged(@Nullable MessageResults messageResults) {
                CustomProgressDialog.clodseProgress();
                Log.v("wwww","ffffffffffffffffff");
                messagesAdapter=new MessagesAdapter(getActivity(),messageResults.getData());
                notification_res.setAdapter(messagesAdapter);
            }
        };

        markerOwnerObserver=new Observer<StatusModel>() {
            @Override
            public void onChanged(@Nullable StatusModel statusModel) {
                if(statusModel.getStatus()==1) {
                    //proDialog.dismiss();
                   // CustomProgressDialog.clodseProgress();
                    new SharedPrefrenceModel(getActivity()).setCompleteRegister(true);
                    notificationViewModel.getMessages(new SharedPrefrenceModel(getActivity()).getId())
                            .observe(Notifications.this,messagesObserver);

                }
                else {
                   // CustomProgressDialog.clodseProgress();
                    notificationViewModel.getLastFranchise().observe(getActivity(), resultObserver);

                }
            }

        };


        markerOwnerRegisterViewModel= ViewModelProviders.of(this).get(MarkerOwnerRegisterViewModel.class);
      if(new SharedPrefrenceModel(getActivity()).isLogined()) {
          CustomProgressDialog.showProgress(getActivity());
          if (!new SharedPrefrenceModel(getActivity()).getCompleteregister()) {
              markerOwnerRegisterViewModel.checkCompleteProfile(new SharedPrefrenceModel(getActivity()).getId(), new SharedPrefrenceModel(getActivity())
                      .getApiToken()).observe(this, markerOwnerObserver);

          } else {

              notificationViewModel.getMessages(new SharedPrefrenceModel(getActivity()).getId())
                      .observe(Notifications.this, messagesObserver);
          }
      }else {
          Toast.makeText(getActivity(),"you must login first",Toast.LENGTH_SHORT).show();
          startActivity(new Intent(getActivity(), LoginActivity.class));
      }
        return view;

    }





}
