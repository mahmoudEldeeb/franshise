package com.franshise.franshise.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.franshise.franshise.R;
import com.franshise.franshise.interfaces.ItemClickListener;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.CategorysModels;
import com.franshise.franshise.models.dataModels.FranchiseModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FranchiseListAdapter  extends RecyclerView.Adapter<FranchiseListAdapter.ViewHolder> {
    List<FranchiseModel> categorysModelsList;
    Context context;

    public FranchiseListAdapter(Context c, List<FranchiseModel> data) {
    context=c;
    categorysModelsList=data;
    }
public void setData( List<FranchiseModel> data){
    categorysModelsList= data;
}

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        try{
                view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_category_item, parent, false);
            return new ViewHolder(view);
        }
                catch (OutOfMemoryError e){
            return null;
                }

    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cat_title.setText(categorysModelsList.get(position).getName()+"");
       Picasso.get()
                .load(context.getResources().getString(R.string.base_image_url) +
                        categorysModelsList.get(position).getImage())
                .into(holder.cat_image);
    }

    @Override
    public int getItemCount() {
            return categorysModelsList.size();

    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView cat_title;
        ImageView cat_image;

        ViewHolder(View itemView) {
            super(itemView);
            cat_title = itemView.findViewById(R.id.cat_title);
            cat_image = itemView.findViewById(R.id.cat_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ItemClickListener itemClickListener = (ItemClickListener) context;
            int p=getAdapterPosition();
            ((ItemClickListener) context).franchiseClick(categorysModelsList.get(p).getId(),categorysModelsList.get(p).getName());
        }
    }

}