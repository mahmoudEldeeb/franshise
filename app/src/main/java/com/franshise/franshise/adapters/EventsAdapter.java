package com.franshise.franshise.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.franshise.franshise.R;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {


    public EventsAdapter(Context context) {

    }

    // inflates the row layout from xml when needed
    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shows_item, parent, false);
        return new EventsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsAdapter.ViewHolder viewHolder, int i) {

    }


    // total number of rows
    @Override
    public int getItemCount() {
        return 20;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            // myTextView = itemView.findViewById(R.id.tvAnimalName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }

}


