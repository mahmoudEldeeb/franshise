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
import com.franshise.franshise.fragments.HomeFragment;
import com.franshise.franshise.interfaces.ItemClickListener;
import com.franshise.franshise.models.ResultNetworkModels.BannersResult;
import com.franshise.franshise.models.ResultNetworkModels.FranchisesResult;
import com.franshise.franshise.models.dataModels.FranchiseModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MarkerAdapter extends RecyclerView.Adapter<MarkerAdapter.ViewHolder> {

    EventListener itemClickListener;
    List<FranchiseModel> bannersList;
    Context context;
    public interface EventListener {
        void onEvent(int data,String title);
    }
    public MarkerAdapter(Context c, EventListener eventListener, List<FranchiseModel> result) {
            this.bannersList=result;
            this.itemClickListener=eventListener;
            this.context=c;
    }

    // inflates the row layout from xml when needed
    @Override
    public MarkerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.marker_item, parent, false);
        return new MarkerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarkerAdapter.ViewHolder viewHolder, int position) {
        Picasso.get()
                .load(context.getResources().getString(R.string.base_image_url)+
                        bannersList.get(position).getImage())
                .into(viewHolder.marker_image);
       // viewHolder.marker_image.setImageResource(R.drawable.company);
    }


    // total number of rows
    @Override
    public int getItemCount() {
        return bannersList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView marker_image;

        ViewHolder(View itemView) {
            super(itemView);
            marker_image = itemView.findViewById(R.id.marker_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        itemClickListener.onEvent(bannersList.get(getAdapterPosition()).getId(),bannersList.get(getAdapterPosition()).getName());
        }
    }

}
