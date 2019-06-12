package com.franshise.franshise.fragments;


import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class MarkerOwnerRegisterPageSix extends Fragment implements GiftStyleAdapter.DeleteListener {


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
    public MarkerOwnerRegisterPageSix() {
        // Required empty public constructor
    }
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
int periodId;
ScolerTypeAdapter scolerTypeAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layoutInflater=inflater;
        View view=inflater.inflate(R.layout.fragment_marker_owner_register_page_six, container, false);
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
        int modelNumber=b.getInt("modelNumber");
        for(int i=0;i<modelNumber;i++) {
            addElement1();
        }
       // addElement2();
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
        addElement2();

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
         giftStyleAdapter=new GiftStyleAdapter(getActivity(),MarkerOwnerRegisterPageSix.this::delete,imagelist);
        image_res1.setAdapter(giftStyleAdapter);
        return  view;
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

    private void addElement1() {
        View v1=layoutInflater.inflate(R.layout.space_model, spaceParent, false);
        re=v1.findViewById(R.id.re);
        TextView meter=v1.findViewById(R.id.meter);
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

        re2.setText("Country ");
        spaceParent2.addView(v3,spaceParent2.getChildCount());

    }
    int PICK_IMAGE=1;
    private void addImages1(){
        /*Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        pickIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);
*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
if(imageType==0)
{

    Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
    getIntent.setType("image/*");

    Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    pickIntent.setType("image/*");
    Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

    startActivityForResult(chooserIntent, PICK_IMAGE);

}
           else{





    Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
    getIntent.setType("image/*");
    getIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
    Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    pickIntent.setType("image/*");
    pickIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
    Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

    startActivityForResult(chooserIntent, PICK_IMAGE);

           }

        }
        else {

        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK) {

                try {

                        if (imageType == 0) {

                            final Uri imageUri = data.getData();
                            final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            images.setImageBitmap(selectedImage);
                            image_franchise.setVisibility(View.VISIBLE);
                            imageFranchiseUri=imageUri;
                            Log.v("ppppp1",imageFranchiseUri+"");
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                                ClipData clipData = data.getClipData();
                                if(clipData==null){
                                    final Uri imageUri = data.getData();
                                    final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                                    imagelist.add(selectedImage);
                                    imagesOfProdust.add(imageUri);
                                    Log.v("rrrrrr",imageUri+"");
                                    giftStyleAdapter.notifyDataSetChanged();

                                }
                                else {
                                    for(int i=0;i<clipData.getItemCount();i++) {
                                        ClipData.Item item = clipData.getItemAt(i);
                                        Uri uri = item.getUri();
                                        Log.v("qwwwwwwww", i + "   11");
                                        final InputStream imageStream = getActivity().getContentResolver().openInputStream(uri);
                                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                                        imagelist.add(selectedImage);
                                        imagesOfProdust.add(uri);
                                        giftStyleAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                            else {
                                Log.v("rrrrrr","reeeeeeeeeeeee");
                                final Uri imageUri = data.getData();
                                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                                imagelist.add(selectedImage);
                                imagesOfProdust.add(imageUri);
                                giftStyleAdapter.notifyDataSetChanged();
                            }
                        }


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
                }
                catch (OutOfMemoryError e){Toast.makeText(getActivity(), "Choose small image ", Toast.LENGTH_LONG).show();}


        } else {
                Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        }
    }
    private boolean checkImageSize(Bitmap selectedImage) {
        Bitmap bitmap = selectedImage;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageInByte = stream.toByteArray();
        long lengthbmp = imageInByte.length;
        if(lengthbmp/1024<=500)return true;
        else return false;

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
        Log.v("tttt","2");
        countryArray.clear();
        for(int i=0;i<spaceParent2.getChildCount();i++) {
            View v2 = spaceParent2.getChildAt(i);
            Spinner s = v2.findViewById(R.id.value);
            countryArray.add(countryIdList.get(s.getSelectedItemPosition()));
        }
        if(countryArray.size()==spaceParent2.getChildCount())
        {
            if(imageFranchiseUri==null||imagesOfProdust.size()==0){
                Toast.makeText(getActivity(),R.string.fill_data,Toast.LENGTH_SHORT).show();
            }
            else {
                AddFranchiseData addFranchiseData= (AddFranchiseData) getActivity();
                for(int i=0;i<countryArray.size();i++){
                    Log.v("rrrr",countryArray.get(i)+"");
                }
                addFranchiseData.addFromFragmentSix(periodId,investArray,countryArray,imageFranchiseUri,imagesOfProdust);
                Log.v("eee","dddd");
               // fragmentTransformer.goToNextFragment(7);
            }
        }
    }


    @Override
    public void delete(int type, int position) {

    }
}

