package com.franshise.franshise.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.franshise.franshise.R;

import java.util.List;

public class MarkerDetailsAdapter extends RecyclerView.Adapter<MarkerDetailsAdapter.ViewHolder> {
String detailsTitels[];
    String detailsTitelsinarabic[];
Context context;

    List<String>franchiseType;
    List<String> franchiseResultModel;
    public MarkerDetailsAdapter(Context c, List<String> franchiseResultModel, List<String> f) {
           context=c;
        detailsTitels=context.getResources().getStringArray(R.array.markersdetails);
        detailsTitelsinarabic=context.getResources().getStringArray(R.array.markersdetailsarabic);
        this.franchiseResultModel=franchiseResultModel;
        franchiseType=f;
    }

    // inflates the row layout from xml when needed
    @Override
    public MarkerDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.marker_detail_item, parent, false);
        return new MarkerDetailsAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MarkerDetailsAdapter.ViewHolder viewHolder, int i) {
            if(i>=7&&i<7+franchiseType.size()&&franchiseType.size()!=0){
String[] titles=franchiseType.get(i-7).split("/");
viewHolder.title.setText(titles[0]);
                viewHolder.titlearabic.setText(titles[1]);
                viewHolder.details.setText(franchiseResultModel.get(i));
            }
            else if(i>7){
                viewHolder.titlearabic.setText(detailsTitelsinarabic[i-franchiseType.size()]);
                viewHolder.title.setText(detailsTitels[i-franchiseType.size()]);
                viewHolder.details.setText(franchiseResultModel.get(i));            }
            else {
                 viewHolder.titlearabic.setText(detailsTitelsinarabic[i]);
                 viewHolder.title.setText(detailsTitels[i]);
                 viewHolder.details.setText(franchiseResultModel.get(i));

            }



    }


    @Override
    public int getItemCount() {
        return franchiseResultModel.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,titlearabic,details;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_eng);
            titlearabic=itemView.findViewById(R.id.title_arabic);
            details=itemView.findViewById(R.id.details);
        }


    }

}
