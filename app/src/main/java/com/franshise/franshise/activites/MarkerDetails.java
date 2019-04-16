package com.franshise.franshise.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.franshise.franshise.R;
import com.franshise.franshise.adapters.MarkerDetailsAdapter;

public class MarkerDetails extends AppCompatActivity {
RecyclerView marker_details_resycle;
MarkerDetailsAdapter markerDetailsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_details);
        marker_details_resycle=findViewById(R.id.marker_details_resycle);
        /*markerDetailsAdapter=new MarkerDetailsAdapter(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        marker_details_resycle.setLayoutManager(mLayoutManager);
        marker_details_resycle.setAdapter(markerDetailsAdapter);

    */}
}
