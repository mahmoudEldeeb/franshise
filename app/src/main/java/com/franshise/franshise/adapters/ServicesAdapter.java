package com.franshise.franshise.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.franshise.franshise.R;
import com.franshise.franshise.models.dataModels.EventsModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

    List<EventsModel> list;

    Click click;
    Context context;

    public ServicesAdapter(Context c,Click cl, List<EventsModel>lis) {
        context=c;
        list=lis;
        click=cl;
    }
    public interface Click{
        public void onclick(EventsModel eventsModel);
    }
    // inflates the row layout from xml when needed
    @Override
    public ServicesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.services_item, parent, false);
        return new ServicesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesAdapter.ViewHolder viewHolder, int i) {
        viewHolder.details.setText(list.get(i).getDetails());
        viewHolder.name.setText(list.get(i).getTitle());

    }


    // total number of rows
    @Override
    public int getItemCount() {
        return list.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,date,details;


        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            details = itemView.findViewById(R.id.details);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            click.onclick(list.get(getAdapterPosition()));
        }
    }

}


