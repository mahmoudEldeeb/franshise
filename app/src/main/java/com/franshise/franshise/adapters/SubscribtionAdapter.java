package com.franshise.franshise.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.franshise.franshise.R;
import com.franshise.franshise.activites.Pay;
import com.franshise.franshise.interfaces.ItemClickListener;
import com.franshise.franshise.models.dataModels.SubscribtionMode;
import com.franshise.franshise.models.database.FavModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SubscribtionAdapter extends RecyclerView.Adapter<SubscribtionAdapter.ViewHolder> {
    List<SubscribtionMode> list;
    Context context;
    int row_index=-1;
OnClick onClick;
    public SubscribtionAdapter(Context ct,OnClick click, List<SubscribtionMode> favModels) {
        context=ct;
        list=favModels;
        Log.v("eeeeeeeeeeeee",""+list.size());
        onClick=click;
    }

    // inflates the row layout from xml when needed
    @Override
    public SubscribtionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subscriprion_item2, parent, false);
        return new SubscribtionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscribtionAdapter.ViewHolder viewHolder, int i) {
        Log.v("eeee",i+"");
       viewHolder.name.setText(list.get(i).getName());
       viewHolder.price.setText(list.get(i).getPrice());
       viewHolder.price.setText(list.get(i).getDuration());
       viewHolder.details.setText(list.get(i).getDetails());
       viewHolder.subscripe.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(context, Pay.class);
               intent.putExtra("id",list.get(i).getId());
               context.startActivity(intent);
           }
       });
    /*   if(i==row_index)
           viewHolder.check.setImageResource(R.drawable.subcheck);
       else
           viewHolder.check.setImageResource(R.drawable.sub);*/

    }


    // total number of rows
    @Override
    public int getItemCount() {
        return list.size();
    }
    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,price,details;
        ImageView check;
        Button subscripe;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            subscripe=itemView.findViewById(R.id.subscripe);
            price=itemView.findViewById(R.id.price);
            details=itemView.findViewById(R.id.details);

         //   itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
           // row_index=getAdapterPosition();

      // onClick.getId(list.get(getAdapterPosition()).getId());

           // notifyDataSetChanged();

        }
    }
    public interface OnClick{
        public void getId(int id);
    }
}