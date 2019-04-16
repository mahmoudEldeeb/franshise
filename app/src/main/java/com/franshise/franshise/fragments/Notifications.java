package com.franshise.franshise.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.franshise.franshise.R;
import com.franshise.franshise.adapters.FacouriteAdapter;
import com.franshise.franshise.adapters.NotificationsAdapter;
import com.franshise.franshise.models.ResultNetworkModels.CategorysResult;
import com.franshise.franshise.models.ResultNetworkModels.FranchisesResult;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.NotificationViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class Notifications extends Fragment {
    RecyclerView notification_res;
    NotificationsAdapter notificationsAdapter;
static int count=0;
NotificationViewModel notificationViewModel;
Observer<FranchisesResult>resultObserver;
    public Notifications() {
        // Required empty public constructor
    }
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
               notificationsAdapter=new NotificationsAdapter(getActivity(),categorysResult.getData());
                notification_res.setAdapter(notificationsAdapter);
                franchisesResult=categorysResult;
                count=1;
                CustomProgressDialog.clodseProgress();
            }
        };
if(count==0) {
    CustomProgressDialog.showProgress(getActivity());
    notificationViewModel.getLastFranchise().observe(getActivity(), resultObserver);
}
else {

    notificationsAdapter=new NotificationsAdapter(getActivity(),franchisesResult.getData());
    notification_res.setAdapter(notificationsAdapter);
}
        return view;

    }

}
