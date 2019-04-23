package com.franshise.franshise.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.franshise.franshise.R;
import com.franshise.franshise.models.dataModels.EventsModel;
import com.franshise.franshise.models.dataModels.MessageModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    List<MessageModel> list;

    MessagesAdapter.Click click;
    Context context;

    public MessagesAdapter(Context c, List<MessageModel>lis) {
        context=c;
        list=lis;

    }
    public interface Click{
        public void onclick(EventsModel eventsModel);
    }
    // inflates the row layout from xml when needed
    @Override
    public MessagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);
        return new MessagesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesAdapter.ViewHolder viewHolder, int i) {
        viewHolder.email.setText(list.get(i).getEmail());
        viewHolder.from.setText("from: "+list.get(i).getName());
        viewHolder.message.setText(list.get(i).getMessage());

    }


    // total number of rows
    @Override
    public int getItemCount() {
        return list.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView from,message,phone,email;


        ViewHolder(View itemView) {
            super(itemView);
            from = itemView.findViewById(R.id.from);
            email = itemView.findViewById(R.id.email);
            phone = itemView.findViewById(R.id.phone);
            message = itemView.findViewById(R.id.message);
            email.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {


            Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + list.get(getAdapterPosition()).getEmail()));

            context.startActivity(intent);

        }
    }

}