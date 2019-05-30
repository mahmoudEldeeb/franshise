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
import com.franshise.franshise.interfaces.ItemClickListener;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.FundingCompanyMode;
import com.franshise.franshise.models.database.FavModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FundingCompanyAdapter  extends RecyclerView.Adapter<FundingCompanyAdapter.ViewHolder> {
    List<FundingCompanyMode> list;
    Context context;
    public FundingCompanyAdapter(Context ct, List<FundingCompanyMode> favModels) {
        context=ct;
        list=favModels;
    }
    public interface ItemCliclListener{
        public void ItemClick(FundingCompanyMode fundingCompanyMode);
    }

    // inflates the row layout from xml when needed
    @Override
    public FundingCompanyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favourite_item, parent, false);
        return new FundingCompanyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FundingCompanyAdapter.ViewHolder viewHolder, int i) {

        if(new SharedPrefrenceModel(context).getLanguage().equals("ar"))
            viewHolder.name.setText(list.get(i).getAr_name());
        else viewHolder.name.setText(list.get(i).getEn_name());

        Picasso.get()
                .load(context.getResources().getString(R.string.base_image_url)+
                        list.get(i).getImage())
                .into(viewHolder.profile_image);
    }


    // total number of rows
    @Override
    public int getItemCount() {
        return list.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        ImageView profile_image;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            profile_image=itemView.findViewById(R.id.profile_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ItemCliclListener itemClickListener= (ItemCliclListener) context;
           itemClickListener.ItemClick(list.get(getAdapterPosition()));
        }
    }

}

