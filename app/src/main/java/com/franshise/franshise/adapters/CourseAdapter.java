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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<EventsModel> list;

    EventsAdapter.Click click;
    Context context;
private static int counterMonth=-1;
private static int counter=0;
    private static int counter2=0;
int count;
    public CourseAdapter(Context c, EventsAdapter.Click cl, List<EventsModel>lis,int co) {
        context=c;
        list=lis;
        count=co+lis.size();
        setHasStableIds(true);

        click=cl;
        Log.v("rrrr",co+lis.size()+"");
    }
    public interface Click{
        public void onclick(EventsModel eventsModel);
    }
    // inflates the row layout from xml when needed
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        if(viewType==0) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.shows_item, parent, false);
            return new CourseAdapter.ViewHolderDta(view);
        }
        else{
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.month_item, parent, false);
            return new CourseAdapter.ViewHolderMonth(view);
        }
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Log.v("eeeee",i+"");
           switch (viewHolder.getItemViewType()) {
               case 0:
                   ViewHolderDta viewHolderDta = (ViewHolderDta) viewHolder;
                   viewHolderDta.details.setText(list.get(counter).getDetails());
                   viewHolderDta.name.setText(list.get(counter).getTitle());
                   viewHolderDta.date.setText(list.get(counter).getDate());
                   Picasso.get()
                           .load(list.get(counter).getImage())
                           .into(viewHolderDta.profile_image);
                   counter++;
                   break;
               case 1:
                   ViewHolderMonth viewHolder1m = (ViewHolderMonth) viewHolder;
                   viewHolder1m.month.setText(context.getResources().getStringArray(R.array.months)[counterMonth - 1]);
                   break;

       }

    }
    // total number of rows
    @Override
    public int getItemCount() {
        Log.v("sssss",list.size()+count+"");
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        Log.v("mmmm", position+"   nnnnn");
        if(position==0)
            counter=0;
        if(counter==list.size())
            counter=0;

            String month = list.get(counter).getDate().substring(5, 7);
            //  Log.v("mmmm", counter);
            int m = Integer.parseInt(month);
            //  Log.v("mmmm", m + "     n");
            if (m == counterMonth) {
                return 0;
            } else {
                counterMonth = m;
                return 1;
            }


    }

    public class ViewHolderDta extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,date,details;
        ImageView profile_image;
        ViewHolderDta(View itemView) {
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


    // stores and recycles views as they are scrolled off screen
    public class ViewHolderMonth extends RecyclerView.ViewHolder {
        TextView month;
        ImageView profile_image;
        ViewHolderMonth(View itemView) {
            super(itemView);
            month = itemView.findViewById(R.id.month);

        }


    }

}


