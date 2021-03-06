package com.franshise.franshise.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.franshise.franshise.R;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.FundingCompanyMode;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class FundingCompanyDetails extends Fragment {


    public FundingCompanyDetails() {
        // Required empty public constructor
    }

TextView title,details;
    ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_funding_company_details, container, false);
        title=view.findViewById(R.id.title);
        details=view.findViewById(R.id.details);
        image=view.findViewById(R.id.image);
        Bundle bundle=getArguments();
        FundingCompanyMode fundingCompanyMode= (FundingCompanyMode) bundle.getSerializable("fundCompany");
//title.setText(fundingCompanyMode.);
        if(new SharedPrefrenceModel(getActivity()).getLanguage().equals("ar"))
        {
            String data=fundingCompanyMode.getAr_details().replace("<p>","");
            data=data.replace("</p>","");
            Log.v("vvvvv",fundingCompanyMode.getAr_name());
          title.setText(fundingCompanyMode.getAr_name());
        details.setText(data);
        }
        else   {
            String data=fundingCompanyMode.getEn_details().replace("<p>","");
            data=data.replace("</p>","");
            title.setText(fundingCompanyMode.getEn_name());
            details.setText(data);
        }
        /*

         */
        Picasso.get()
                .load(getActivity().getResources().getString(R.string.base_image_url)+
                        fundingCompanyMode.getImage())
                .into(image);
        return view;
    }

}
