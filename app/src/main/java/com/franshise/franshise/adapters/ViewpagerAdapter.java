package com.franshise.franshise.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.franshise.franshise.R;
import com.franshise.franshise.activites.MarkersOfCategory;
import com.franshise.franshise.models.BannarModel;
import com.franshise.franshise.models.ResultNetworkModels.BannersResult;
import com.franshise.franshise.models.ResultNetworkModels.CategorysResult;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewpagerAdapter extends PagerAdapter {
    Context context;
    List<BannarModel> bannersList;

    public ViewpagerAdapter(Context c, BannersResult list)
    {
    this.bannersList=list.getData();
    context=c;
    }


    public ViewpagerAdapter(Context c, CategorysResult categorysResult) {
        context=c;
        bannersList=categorysResult.getData().get(0).getImages();
    }

    public ViewpagerAdapter(Context baseContext, List<BannarModel> images) {
        context=baseContext;
        bannersList=images;
    }

    public void setdata(BannersResult bannersResult){
        bannersList.addAll(bannersResult.getData());

}
    @Override
    public int getCount() {
        try {

            return bannersList.size();
        }catch (NullPointerException e){
            return 0;
        }
    }


    @Override
    public Object instantiateItem(ViewGroup parent, int position) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewItem = inflater.inflate(R.layout.slider_item, parent, false);
        ImageView imageView=viewItem.findViewById(R.id.image_slider);
        Picasso.get()
                .load(context.getResources().getString(R.string.base_image_url)+
                        bannersList.get(position).getImagePath())
                .into(imageView);
        parent.addView(viewItem);


       // imageView.setImageResource(R.drawable.company);

        return viewItem;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        // TODO Auto-generated method stub
        return view == ((View)object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewPager) container).removeView((View) object);
    }

}
