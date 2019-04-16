package com.franshise.franshise.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.franshise.franshise.R;

import java.util.List;

public class GiftStyleAdapter extends RecyclerView.Adapter<GiftStyleAdapter.ViewHolder> {
List<Bitmap>bitmapList;

    public GiftStyleAdapter(Context context, List<Bitmap> imagelist) {
bitmapList=imagelist;
    }

    // inflates the row layout from xml when needed
    @Override
    public GiftStyleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_item, parent, false);
        return new GiftStyleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GiftStyleAdapter.ViewHolder viewHolder, int i) {
viewHolder.images.setImageBitmap(bitmapList.get(i));
    }


    // total number of rows
    @Override
    public int getItemCount() {
        return bitmapList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
ImageView images;
ImageButton delete;
        ViewHolder(View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.images);
            delete=itemView.findViewById(R.id.delete);
           // checkBox=itemView.findViewById(R.id.)
            //itemView.setOnClickListener(this);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
                bitmapList.remove(getAdapterPosition());
            notifyDataSetChanged();
        }
    }

}
