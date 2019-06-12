package com.franshise.franshise.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.franshise.franshise.R;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.DataModel;
import com.franshise.franshise.models.dataModels.EventsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<EventsModel> list;

    EventsAdapter.Click click;
    Context context;
    private static int counterMonth=-1;

    private List<Integer> monthPositions=new ArrayList<>();
    List<DataModel>countryData=new ArrayList<>();
    int count;
    public CoursesAdapter(Context c, EventsAdapter.Click cl, List<EventsModel>lis,List<DataModel> co) {

        context=c;
        list=lis;
        setHasStableIds(true);
        click=cl;
        countryData=co;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view ;

        Log.v("aaaaaaaaaa",i+"");
      //  if(i==0) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.show_course, viewGroup, false);
            return new CoursesAdapter.ViewHolderDta(view);
       /*}
        else{
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.month_item, viewGroup, false);
            return new CoursesAdapter.ViewHolderMonth(view);
        }*/
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
       /* switch (viewHolder.getItemViewType()) {
            case 0:*/

        int month = Integer.parseInt(list.get(i).getDate().substring(5, 7));

        CoursesAdapter.ViewHolderDta viewHolderDta = (CoursesAdapter.ViewHolderDta) viewHolder;
        if (i == 0) {
            viewHolderDta.month.setText(context.getResources().getStringArray(R.array.months)[month - 1]);
        } else {
            int premonth = Integer.parseInt(list.get(i - 1).getDate().substring(5, 7));

            if (month == premonth) {
                ((ViewHolderDta) viewHolder).month.setVisibility(View.GONE);
            } else {
                viewHolderDta.month.setText(context.getResources().getStringArray(R.array.months)[month - 1]);
            }

        }
        try{
        for(int j=0;j<countryData.size();j++){
            Log.v("eeee","ggggggggggg");
            if(Integer.parseInt(list.get(i).getCountry_id())==countryData.get(j).getId())
            { Log.v("eeee","fffffff");
                if(new SharedPrefrenceModel(context).getLanguage().equals("en"))
                      viewHolderDta.country.setText(countryData.get(j).getEn_name());
                else viewHolderDta.country.setText(countryData.get(j).getAr_name());}
        }
        }catch (NullPointerException e){}
        catch (NumberFormatException e){}
            viewHolderDta.details.setText(list.get(i).getDetails());
            viewHolderDta.name.setText(list.get(i).getTitle());
            viewHolderDta.date.setText(list.get(i).getDate());
            Picasso.get()
                    .load(list.get(i).getImage())
                    .into(viewHolderDta.profile_image);



              /*  Picasso.get()
                        .load(list.get(counter).getImage())
                        .into(viewHolderDta.profile_image);*/

               /* Picasso.get()
                        .load(list.get(0).getImage())
                        .into(viewHolderDta.profile_image);*/
              //  counter++;
                /*Log.v("qqqqqqqq",context.getResources().getString(R.string.base_image_url) +"    b   "+
                        list.get(counter).getImage());
*/
              /*  break;
            case 1:
                CoursesAdapter.ViewHolderMonth viewHolder1m = (CoursesAdapter.ViewHolderMonth) viewHolder;
                viewHolder1m.month.setText(context.getResources().getStringArray(R.array.months)[counterMonth - 1]);
                monthPositions.add(i);
                break;*/

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        try {
            /*if(position==0){
                counter=0;counterMonth=-1;}
            else if(counter==list.size()){
                counter=0;
                counterMonth=-1;}
*/
             //   Log.v("rrrrrr", counter + "");
            int pos=0;
                if(position==0){
                     pos=0;
                counterMonth=-1;
                }
                else
             pos=position-getCountOfPrevious(position);
                Log.v("aaaaaaaa",pos+"");
                String month = list.get(pos).getDate().substring(5, 7);
                //  Log.v("mmmm", counter);
                int m = Integer.parseInt(month);
                //  Log.v("mmmm", m + "     n");
                if (m == counterMonth) {
                    return 0;
                } else {
                    counterMonth = m;
                    return 1;
        }
        }catch (Exception e) {
            return super.getItemViewType(position);
        }
    }

    public void clear() {
        list.clear();
        count=0;
        counterMonth=-1;
        notifyDataSetChanged();
    }

    public interface Click{
        public void onclick(EventsModel eventsModel);
    }


    public class ViewHolderDta extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,date,details,country,month;
        ImageView profile_image;
        LinearLayout course;
        ViewHolderDta(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            profile_image = itemView.findViewById(R.id.profile_image);
            details = itemView.findViewById(R.id.details);
            date = itemView.findViewById(R.id.date);
            month = itemView.findViewById(R.id.month);
            course=itemView.findViewById(R.id.course);
            country=itemView.findViewById(R.id.country);
            course.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
try {
    click.onclick(list.get(getAdapterPosition()));
}catch (Exception e){}
        }
    }

    private int getCountOfPrevious(int adapterPosition) {
        int sum=0;
        for(int i=0;i<monthPositions.size();i++){
            if(adapterPosition>monthPositions.get(i)) {
                Log.v("dddddd","ddd");
                sum += 1;
            }
        }
        return sum;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolderMonth extends RecyclerView.ViewHolder {
        TextView month,type;

        ViewHolderMonth(View itemView) {
            super(itemView);
            month = itemView.findViewById(R.id.month);


        }


    }
}
