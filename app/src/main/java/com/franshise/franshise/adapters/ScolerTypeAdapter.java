package com.franshise.franshise.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.franshise.franshise.R;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.DataModel;

import java.util.ArrayList;
import java.util.List;

public class ScolerTypeAdapter   extends RecyclerView.Adapter<ScolerTypeAdapter.ViewHolder> {
int row_index=-1;
List<Integer>chosenlist=new ArrayList<>();

    public String getScolervalue() {
        return scolervalue;
    }

    private String scolervalue="";
    private int id=-1;

    public int getId() {
        return id;
      //  String s=context.viewHolderData.scholrshipedit.getText().toString();
    }

    List<DataModel>list;
Context context;
String arrayOfscolerValues[];
int []listPosition;
ScolerTypeAdapter.ViewHolder viewHolderData;
    public ScolerTypeAdapter(Context c, List<DataModel>l) {
    list=l;
        arrayOfscolerValues=new String[l.size()];
        listPosition=new int[l.size()];
    context=c;
    }

    // inflates the row layout from xml when needed
    @Override
    public ScolerTypeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scolrship_type, parent, false);
        viewHolderData=new ScolerTypeAdapter.ViewHolder(view);
        return viewHolderData;
    }

    @Override
    public void onBindViewHolder(@NonNull ScolerTypeAdapter.ViewHolder viewHolder, int i) {
        Log.v("wwww,",i+"   "+listPosition[i]);
        if(row_index==i){
            viewHolder.scholrshipedit.setVisibility(View.VISIBLE);

        }
        else
        { viewHolder.scholrshipedit.setText("");
            viewHolder.scholrshipedit.setVisibility(View.GONE);
        }
        if(new SharedPrefrenceModel(context).getLanguage().equals("en")){
            viewHolder.scholrshipBox.setText(list.get(i).getAr_name());
        }
        else
            viewHolder.scholrshipBox.setText(list.get(i).getEn_name());

    }
    // total number of rows
    @Override
    public int getItemCount() {
        return list.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        CheckBox scholrshipBox;
EditText scholrshipedit;
        ViewHolder(View itemView) {
            super(itemView);
            scholrshipedit = itemView.findViewById(R.id.scholrshipedit);
            scholrshipBox=itemView.findViewById(R.id.scholrshipBox);

            scholrshipBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                   if(isChecked)
                   {
                       Log.v("www","111111111111111111" +"      "+getAdapterPosition());
                       listPosition[getAdapterPosition()]=1;
                       row_index=getAdapterPosition();
                       notifyItemChanged(getAdapterPosition());
                   }
                   else {row_index=-1;
                       listPosition[getAdapterPosition()]=0;
                       notifyItemChanged(getAdapterPosition());
                       Log.v("www","0000000000000000000" +"      "+getAdapterPosition());
                   }

                }
            });

            scholrshipedit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
               // scolervalue= s.toString();
                 //   Log.v("eeee",s.toString());

                }
            });
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        }
    }
    public List<Integer>getScolerCheckId(){
        return chosenlist;
    }

    public List<String> getScolerCheckValues(){
        List<String>values=new ArrayList<>();
        for(int i=0;i<arrayOfscolerValues.length;i++){
            if(listPosition[i]==1){
                values.add(arrayOfscolerValues[i]);
            }
        }
        return values;
    }
public interface ScolerTypeListener{
        public void check(int id,int position);
}
}
