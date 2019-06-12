package com.franshise.franshise.activites;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.franshise.franshise.R;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.EventsModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.squareup.picasso.Picasso;

public class ShowEvents extends AppCompatActivity {
TextView title,details,date,type;
ImageView image;
EventsModel eventsModel;
    WebView web;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_events);
        web=findViewById(R.id.web);
        type=findViewById(R.id.type);
        back=findViewById(R.id.back);
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                CustomProgressDialog.showProgress(ShowEvents.this);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                CustomProgressDialog.clodseProgress();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        Bundle b=getArguments();
        int t=getIntent().getIntExtra("type",0);
        if(t==0){

            type.setText(R.string.events);
        }else if(t==1){
            type.setText(R.string.services);
        }
        else if(t==2) {
            type.setText(R.string.training);
        }

        else if(t==3) {
            type.setText(R.string.jobs);
        }


        String RootUrl="http://204.93.167.45/~helix/franchise/api/page-details/";
        RootUrl+=new SharedPrefrenceModel(this).getLanguage()+"/";
        eventsModel= (EventsModel) getIntent().getSerializableExtra("eventModel");

        RootUrl+=eventsModel.getId();
        web.loadUrl(RootUrl);

//        http://204.93.167.45/~helix/franchise/api/page-details/ar/12



    }


}
