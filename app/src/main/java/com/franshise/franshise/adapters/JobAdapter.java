package com.franshise.franshise.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.franshise.franshise.R;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.EventsModel;
import com.franshise.franshise.models.dataModels.JobModel;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ViewHolder> {

    List<JobModel> list;

    JobAdapter.Click click;
    Context context;

    public JobAdapter(Context c, JobAdapter.Click cl, List<JobModel>lis) {
        context=c;
        list=lis;
        click=cl;
    }
    public interface Click{
        public void onclick(JobModel jobModel);
    }
    // inflates the row layout from xml when needed
    @Override
    public JobAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.services_item, parent, false);
        return new JobAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.name.setText(list.get(i).name+"");
        if(new SharedPrefrenceModel(context).getLanguage().equals("en"))
        viewHolder.details.setText(list.get(i).countries.getEn_name());
        else
         viewHolder.details.setText(list.get(i).countries.getAr_name());
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

