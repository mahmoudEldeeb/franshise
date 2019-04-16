package com.franshise.franshise.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.franshise.franshise.R;
import com.franshise.franshise.activites.MarkersOfCategory;
import com.franshise.franshise.interfaces.ItemClickListener;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.CategorysModels;
import com.franshise.franshise.models.dataModels.FranchiseModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainCategorysAdapter extends RecyclerView.Adapter<MainCategorysAdapter.ViewHolder> {
List<CategorysModels>categorysModelsList;
Context context;
    public MainCategorysAdapter(Context context,List<CategorysModels>list)
    {
        this.categorysModelsList=list;
        this.context=context;
    }

    public void setData(List<CategorysModels>list){
        categorysModelsList.addAll(list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //String animal = mData.get(position);
        if(new SharedPrefrenceModel(context).getLanguage().equals("ar"))
       holder.cat_title.setText(categorysModelsList.get(position).getAr_name());
       else holder.cat_title.setText(categorysModelsList.get(position).getEn_name());
        Picasso.get()
                .load(context.getResources().getString(R.string.base_image_url)+
                        categorysModelsList.get(position).getImage())
                .into(holder.cat_image);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        try{
        return categorysModelsList.size();}catch (NullPointerException e){
            return 5;
        }
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
            ItemClickListener itemClickListener= (ItemClickListener) context;
            String catName="";
            if(new SharedPrefrenceModel(context).getLanguage().equals("en")){
                catName=categorysModelsList.get(getAdapterPosition()).getEn_name();
            }
            else catName=categorysModelsList.get(getAdapterPosition()).getAr_name();
            itemClickListener.categoryClick(categorysModelsList.get(getAdapterPosition()).getId(),catName);
        }
    }

}
