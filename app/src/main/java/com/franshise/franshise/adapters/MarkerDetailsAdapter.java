package com.franshise.franshise.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.franshise.franshise.R;
import com.franshise.franshise.activites.FranchiseView;
import com.franshise.franshise.models.SharedPrefrenceModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MarkerDetailsAdapter extends RecyclerView.Adapter<MarkerDetailsAdapter.ViewHolder> {
String detailsTitels[];
    String detailsTitelsinarabic[];
Context context;
SimpleDateFormat sdf;
    Date datenow;Date endDate;
    List<String>franchiseType;
    List<String> franchiseResultModel;
    public MarkerDetailsAdapter(Context c, List<String> franchiseResultModel, List<String> f) {
           context=c;

        sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            endDate = sdf.parse(new SharedPrefrenceModel(context).getDate());

            Date date = new Date();
            datenow= sdf.parse(sdf.format(date));
            if(!endDate.after(datenow)) {
                franchiseType=f;
                detailsTitels=context.getResources().getStringArray(R.array.markersdetails);
                detailsTitelsinarabic=context.getResources().getStringArray(R.array.markersdetailsarabic);
                this.franchiseResultModel=franchiseResultModel.subList(0,4+f.size());
            }
            else {
                detailsTitels=context.getResources().getStringArray(R.array.markersdetails);
                detailsTitelsinarabic=context.getResources().getStringArray(R.array.markersdetailsarabic);
                this.franchiseResultModel=franchiseResultModel;
                franchiseType=f;}
        } catch (ParseException e) {
            detailsTitels=context.getResources().getStringArray(R.array.markersdetails);
            detailsTitelsinarabic=context.getResources().getStringArray(R.array.markersdetailsarabic);
            this.franchiseResultModel=franchiseResultModel;
            franchiseType=f;

            e.printStackTrace();
        }

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
            if(i>=4&&i<4+franchiseType.size()&&franchiseType.size()!=0){
String[] titles=franchiseType.get(i-4).split("/");
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
        try {
            endDate = sdf.parse(new SharedPrefrenceModel(context).getDate());

            Date date = new Date();
            datenow= sdf.parse(sdf.format(date));
            if(!endDate.after(datenow)) {

                if (i > 3 + franchiseType.size()) {
                    //viewHolder.itemView.setVisibility(View.GONE);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
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
