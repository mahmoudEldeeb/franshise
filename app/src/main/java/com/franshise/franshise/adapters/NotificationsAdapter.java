package com.franshise.franshise.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.franshise.franshise.R;
import com.franshise.franshise.models.dataModels.CategorysModels;
import com.franshise.franshise.models.dataModels.FranchiseModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
    List<FranchiseModel> list;
Context context;
    public NotificationsAdapter(Context context, List<FranchiseModel> l) {
this.context=context;
list=l;
    }
int row_index=-1;
    // inflates the row layout from xml when needed
    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notifications_item, parent, false);
        return new NotificationsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.title.setText(list.get(i).getName());
        viewHolder.details.setText(list.get(i).getDetails());
        Picasso.get()
                .load(context.getResources().getString(R.string.base_image_url) +
                        list.get(i).getImage())
                .into(viewHolder.image);
        if(row_index==i){
            viewHolder.noti_layout.setBackgroundColor(context.getResources().getColor(R.color.login_gray));

        }
        else
        {
            viewHolder.noti_layout.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

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
            noti_layout=itemView.findViewById(R.id.noti_layout);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position=getAdapterPosition();
                    if(row_index==position) {
                        row_index = -1;
                    }
                    else{
                        row_index=position;
                    }
                    FranchiseClicked franchiseClicked= (FranchiseClicked) context;
                    notifyDataSetChanged();

                    if(row_index!=-1) {

                        franchiseClicked.longClicked(list.get(position).getId());
                    }

                    return false;
                }
            });
        }

        @Override
        public void onClick(View view) {
row_index=-1;
            FranchiseClicked franchiseClicked= (FranchiseClicked) context;
            franchiseClicked.clicked(list.get(getAdapterPosition()).getId(),list.get(getAdapterPosition()).getName());
            notifyDataSetChanged();
        }
    }

    public interface FranchiseClicked{
        public void longClicked(int id);
        public void clicked(int id,String title);
    }
}

