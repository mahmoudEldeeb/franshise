package com.franshise.franshise.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.franshise.franshise.R;
import com.franshise.franshise.models.dataModels.EventsModel;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

List<EventsModel>list;

    Click click;
Context context;

    public EventsAdapter(Context c,Click cl, List<EventsModel>lis) {
        context=c;
        list=lis;
        click=cl;
    }
    public interface Click{
        public void onclick(EventsModel eventsModel);
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
            viewHolder.details.setText(list.get(i).getDetails());
            viewHolder.name.setText(list.get(i).getTitle());
            viewHolder.date.setText(list.get(i).getDate());
        Picasso.get()
                .load(list.get(i).getImage())
                .into(viewHolder.profile_image);

    }


    // total number of rows
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,date,details;
        ImageView profile_image;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            profile_image = itemView.findViewById(R.id.profile_image);
            details = itemView.findViewById(R.id.details);
            date = itemView.findViewById(R.id.date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            click.onclick(list.get(getAdapterPosition()));
        }
    }

}


