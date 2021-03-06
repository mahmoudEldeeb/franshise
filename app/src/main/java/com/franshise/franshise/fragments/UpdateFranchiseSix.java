package com.franshise.franshise.fragments;


import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.adapters.GiftStyleAdapter;
import com.franshise.franshise.adapters.ScolerTypeAdapter;
import com.franshise.franshise.interfaces.AddFranchiseData;
import com.franshise.franshise.interfaces.FragmentTransformer;
import com.franshise.franshise.models.ResultNetworkModels.DataResult;
import com.franshise.franshise.models.ResultNetworkModels.PeriodResult;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.PeriodMode;
import com.franshise.franshise.viewmodels.CreateFranchiseViewModel;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateFranchiseSix extends Fragment implements GiftStyleAdapter.DeleteListener{


    public UpdateFranchiseSix() {
        // Required empty public constructor
    }


    LinearLayout spaceParent,spaceParent2;
    TextView re,re2;
    int i=1;
    Button add,add2,next;
    LayoutInflater layoutInflater;
    RecyclerView image_res1;
    List<Bitmap> imagelist;
    FrameLayout image_franchise;
    ImageView images;
    Button addimage_product;
    int imageType;
    List<String>investArray;
    List<Integer>countryArray;
    CreateFranchiseViewModel createFranchiseViewModel;
    GiftStyleAdapter giftStyleAdapter;
    FragmentTransformer fragmentTransformer;
    Button addimage_franchise;
    ImageButton delete;
    List<Uri>imagesOfProdust;
    Uri imageFranchiseUri;
    Spinner period_spinner;
    List<PeriodMode>periodModes;
    List<String>countryList;
    List<Integer>countryIdList;
    List<Integer>marketList=new ArrayList<>();
    int periodId;
    ScolerTypeAdapter scolerTypeAdapter;
int period_id=-1;
    ArrayList<String>invesList=new ArrayList<>();

    ArrayList<String>imagesList=new ArrayList<>();
    String mainImage;
    int modelNumber=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layoutInflater=inflater;
        View view=inflater.inflate(R.layout.fragment_update_franchise_six, container, false);
        spaceParent=view.findViewById(R.id.spaceParent);
        spaceParent2=view.findViewById(R.id.spaceParent2);
        image_res1=view.findViewById(R.id.image_res1);
        add2=view.findViewById(R.id.add2);
        next=view.findViewById(R.id.next);
        addimage_franchise=view.findViewById(R.id.addimage_franchise);
        image_franchise=view.findViewById(R.id.image_franchise);
        images=view.findViewById(R.id.images);
        delete=view.findViewById(R.id.delete);
        period_spinner=view.findViewById(R.id.period_spinner);
        addimage_product=view.findViewById(R.id.addimage_product);

        imagesOfProdust=new ArrayList<>();
        investArray=new ArrayList<>();
        countryArray=new ArrayList<>();
        Bundle b=getArguments();
        if(b!=null) {
            modelNumber=b.getInt("modelNumber");
            invesList=b.getStringArrayList("invesList");
            for (int i = 0; i < modelNumber; i++) {
               try{ addElement1(invesList.get(i));}
               catch (IndexOutOfBoundsException e){
                   addElement1("");
               }
            }
            period_id=b.getInt("period");
            marketList=b.getIntegerArrayList("marketList");
            mainImage=b.getString("mainImage");
            imagesList=b.getStringArrayList("imagesList");

             Picasso.get()
                    .load(getActivity().getResources().getString(R.string.base_image_url)+
                            mainImage)
                    .into(images);
            image_franchise.setVisibility(View.VISIBLE);


        }
        //
        fragmentTransformer= (FragmentTransformer) getActivity();
        createFranchiseViewModel= ViewModelProviders.of(this).get(CreateFranchiseViewModel.class);

        List<String>list=new ArrayList<>();
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        period_spinner.setAdapter(adp1);
        createFranchiseViewModel.getPeriod().observe(this, new Observer<PeriodResult>() {
            @Override
            public void onChanged(@Nullable PeriodResult periodResult) {
                periodModes=periodResult.getData();
                for (int i=0;i<periodResult.getData().size();i++){
                    list.add(periodResult.getData().get(i).getName());
                }
                adp1.notifyDataSetChanged();
                chooseperiod();
            }
        });
        countryList=new ArrayList<>();
        countryIdList=new ArrayList<>();

        createFranchiseViewModel.getCountry(0).observe(this, new Observer<DataResult>() {
            @Override
            public void onChanged(@Nullable DataResult dataResult) {
                for(int i=0;i<dataResult.getData().size();i++) {
                    countryIdList.add(dataResult.getData().get(i).getId());
                    if (new SharedPrefrenceModel(getActivity()).getLanguage().equals("en")) {
                        countryList.add(dataResult.getData().get(i).getEn_name()) ;
                    } else {
                        countryList.add(dataResult.getData().get(i).getEn_name()) ;
                    }
                }
                for (int i=0;i<marketList.size();i++) {
                    addElement2();
                }

            }
        });

        period_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                periodId=periodModes.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // createFranchiseViewModel.create(imagesOfProdust,imageFranchiseUri,getActivity());
                // fragmentTransformer.goToNextFragment(7);
                getInvestModelsValues();
                Log.v("tttt","1");
                //   File fil=FileU

            }
        });

        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addElement2();
            }
        });

        addimage_franchise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageType=0;
                if(permission())
                    addImages1();
                else requestPermission();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                images.setImageBitmap(null);
                image_franchise.setVisibility(View.GONE);
            }
        });
        addimage_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageType=1;
                if(permission()) {
                    addImages1();
                }
                else requestPermission();
            }
        });
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        image_res1.setLayoutManager(mLayoutManager1);
        imagelist=new ArrayList<>();
        giftStyleAdapter=new GiftStyleAdapter(getActivity(),UpdateFranchiseSix.this::delete,imagelist,imagesList);
        image_res1.setAdapter(giftStyleAdapter);
        return  view;
    }

    private void chooseperiod() {
        Log.v("qqqqq",period_id+"");
        for (int i=0;i<periodModes.size();i++){
            Log.v("qqqqq",periodModes.get(i).getId()+"");
            if(periodModes.get(i).getId()==period_id){
                period_spinner.setSelection(i);
            }
        }
    }
    private boolean permission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        else return false;
    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);

    }

    private void addElement1(String val) {

        View v1=layoutInflater.inflate(R.layout.space_model, spaceParent, false);
        re=v1.findViewById(R.id.re);
        TextView meter=v1.findViewById(R.id.meter);
        EditText value=v1.findViewById(R.id.value);
       try {
           value.setText(val);
       }catch (NullPointerException e){

       }
        meter.setVisibility(View.GONE);
        ImageButton delete=v1.findViewById(R.id.delete);
        delete.setVisibility(View.GONE);

        re.setText("Model "+i);
        spaceParent.addView(v1,spaceParent.getChildCount());
        i++;
    }
    private void addElement2() {
        View v3=layoutInflater.inflate(R.layout.country_model, spaceParent2, false);
        re2=v3.findViewById(R.id.re);
        ImageButton delete=v3.findViewById(R.id.delete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View v2=spaceParent2.getChildAt( spaceParent2.indexOfChild(v3));
                spaceParent2.removeView(v2);
            }
        });

        Spinner spinner=v3.findViewById(R.id.value);
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, countryList);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp1);
        for(int i=0;i<countryIdList.size();i++){
            for(int j=0;j<marketList.size();j++) {
                if (marketList.get(j) ==countryIdList.get(i))
                    spinner.setSelection(i);
            }
        }

        re2.setText("Country ");
        spaceParent2.addView(v3,spaceParent2.getChildCount());

    }
    int PICK_IMAGE=1;
    private void addImages1(){   Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        pickIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();

                    final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    if (imageType == 0) {
                        images.setImageBitmap(selectedImage);
                        image_franchise.setVisibility(View.VISIBLE);
                        imageFranchiseUri=imageUri;
                    } else {
                        imagelist.add(selectedImage);
                        imagesOfProdust.add(imageUri);
                        giftStyleAdapter.notifyDataSetChanged();
                    }


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void getInvestModelsValues(){
        Log.v("tttt","0");
        investArray.clear();
        for(int i=0;i<spaceParent.getChildCount();i++) {
            View v2 = spaceParent.getChildAt(i);
            EditText s = v2.findViewById(R.id.value);
            if(s.getText().toString().isEmpty()){
                investArray.clear();
                Toast.makeText(getActivity(),"fill data of model"+i,Toast.LENGTH_SHORT).show();
            }
            else {
                investArray.add(s.getText().toString());
            }


        }
        if(investArray.size()==spaceParent.getChildCount())
        {
            getCountry();
        }
    }

    private void getCountry() {
        countryArray.clear();
        for(int i=0;i<spaceParent2.getChildCount();i++) {
            View v2 = spaceParent2.getChildAt(i);
            Spinner s = v2.findViewById(R.id.value);
            Log.v("rrrrrrr",countryIdList.get(s.getSelectedItemPosition())+"");
            countryArray.add(countryIdList.get(s.getSelectedItemPosition()));
        }
        if(countryArray.size()==spaceParent2.getChildCount())
        {

            AddFranchiseData addFranchiseData= (AddFranchiseData) getActivity();

            addFranchiseData.addFromFragmentSix(periodId,investArray,countryArray,imageFranchiseUri,imagesOfProdust);


        }
    }

    @Override
    public void delete(int type, int position) {
        Log.v("tttttt",position+"   ");
        if(type==0){
            imagesOfProdust.remove(position);
           // imagelist.remove(position);
        }
    }
}

