package com.franshise.franshise.activites;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.franshise.franshise.R;
import com.franshise.franshise.utils.CustomProgressDialog;

public class Policey extends AppCompatActivity {
WebView web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policey);
        web=findViewById(R.id.webview);
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                CustomProgressDialog.showProgress(Policey.this);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                CustomProgressDialog.clodseProgress();
            }
        });
        web.loadUrl(getString(R.string.termss));
        //web.loadUrl("http://192.168.1.121/franchise/api/courses/ar");
        ////getArguments();

    }
}
