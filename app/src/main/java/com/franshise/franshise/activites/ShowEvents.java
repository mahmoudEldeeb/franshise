package com.franshise.franshise.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.franshise.franshise.R;
import com.franshise.franshise.models.dataModels.EventsModel;
import com.squareup.picasso.Picasso;

public class ShowEvents extends AppCompatActivity {
TextView title,details,date;
ImageView image;
EventsModel eventsModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_events);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /* getSupportActionBar().setLogo(R.drawable.logo2);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(true);*/
        title=findViewById(R.id.title);
        details=findViewById(R.id.details);
        image=findViewById(R.id.image);
        date=findViewById(R.id.date);
eventsModel= (EventsModel) getIntent().getSerializableExtra("eventModel");
title.setText(eventsModel.getTitle());
details.setText(eventsModel.getDetails());
date.setText(eventsModel.getDate());

        Picasso.get()
                .load(eventsModel.getImage())
                .into(image);
    }
}
