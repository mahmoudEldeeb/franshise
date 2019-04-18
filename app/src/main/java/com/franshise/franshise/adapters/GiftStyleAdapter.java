package com.franshise.franshise.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.franshise.franshise.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GiftStyleAdapter extends RecyclerView.Adapter<GiftStyleAdapter.ViewHolder> {
List<Bitmap>bitmapList;
    ArrayList<String> imagesList=new ArrayList<>();
    Context context;
    DeleteListener deleteListener;
    public GiftStyleAdapter(Context c,DeleteListener delete, List<Bitmap> imagelist, ArrayList<String> imagesList) {
bitmapList=imagelist;
        this.imagesList=imagesList;
        context=c;
        deleteListener=delete;
    }

    public GiftStyleAdapter(Context c, List<Bitmap> imagelist) {
        bitmapList=imagelist;
       // this.imagesList=imagesList;
        context=c;
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
        if(imagesList.size()>0){
            if(i>=imagesList.size())
            {

                viewHolder.images.setImageBitmap(bitmapList.get(i-imagesList.size()));
            }
            else {

                Picasso.get()
                        .load(context.getResources().getString(R.string.base_image_url)+
                                imagesList.get(i))
                        .into(viewHolder.images);
            }
        }
        else
        viewHolder.images.setImageBitmap(bitmapList.get(i));

    }


    // total number of rows
    @Override
    public int getItemCount() {
        return bitmapList.size()+imagesList.size();
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
            int position=getAdapterPosition();
            DeleteListener d= (DeleteListener) context;
            if(position<imagesList.size()) {
                imagesList.remove(position);
                d.delete(1,position);
            }
            else {
                bitmapList.remove(position - imagesList.size());
            deleteListener.delete(0,position-imagesList.size());
            }
            notifyDataSetChanged();
        }
    }
public  interface DeleteListener{
        public void delete(int type,int position);
}
}
