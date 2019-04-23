package com.franshise.franshise.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.franshise.franshise.R;
import com.franshise.franshise.activites.UpdateFranchise;
import com.franshise.franshise.models.dataModels.FranchiseModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LastFranchiseAdapter extends RecyclerView.Adapter<LastFranchiseAdapter.ViewHolder> {
    List<FranchiseModel> list;
    Context context;
    public LastFranchiseAdapter(Context context, List<FranchiseModel> l) {
        this.context=context;
        list=l;
    }
    int row_index=-1;
    // inflates the row layout from xml when needed
    @Override
    public LastFranchiseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.last_franchise_item, parent, false);
        return new LastFranchiseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LastFranchiseAdapter.ViewHolder viewHolder, int i) {
        viewHolder.title.setText(list.get(i).getName());
        viewHolder.details.setText(list.get(i).getDetails());
        Picasso.get()
                .load(context.getResources().getString(R.string.base_image_url) +
                        list.get(i).getImage())
                .into(viewHolder.image);
    }
    // total number of rows
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void removeitem() {
        list.remove(row_index);
        notifyItemRemoved(row_index);
        notifyDataSetChanged();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title,details;
        CircleImageView image;
        LinearLayout noti_layout;
        ViewHolder(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            title=itemView.findViewById(R.id.title);
            details=itemView.findViewById(R.id.details);
            itemView.setOnClickListener(this);
         }

        @Override
        public void onClick(View view) {
            LastFranchiseAdapter.FranchiseClicked franchiseClicked= (LastFranchiseAdapter.FranchiseClicked) context;
            franchiseClicked.clicked(list.get(getAdapterPosition()).getId(),list.get(getAdapterPosition()).getName());
            notifyDataSetChanged();

        }
    }

    public interface FranchiseClicked{
        public void clicked(int id,String title);
    }
}


