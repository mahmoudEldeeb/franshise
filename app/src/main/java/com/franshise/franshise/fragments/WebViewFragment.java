package com.franshise.franshise.fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.franshise.franshise.R;
import com.franshise.franshise.utils.CustomProgressDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebViewFragment extends Fragment {


    public WebViewFragment() {
        // Required empty public constructor
    }

WebView web;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_web_view, container, false);
        web=view.findViewById(R.id.web);
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                CustomProgressDialog.showProgress(getActivity());
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                CustomProgressDialog.clodseProgress();
            }
        });
        Bundle b=getArguments();
        web.loadUrl(b.getString("url"));
        //web.loadUrl("http://192.168.1.121/franchise/api/courses/ar");
        ////getArguments();
        return view;
    }

}
