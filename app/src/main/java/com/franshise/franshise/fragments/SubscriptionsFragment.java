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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.franshise.franshise.R;
import com.franshise.franshise.activites.LoginActivity;
import com.franshise.franshise.adapters.SubscribtionAdapter;
import com.franshise.franshise.models.ResultNetworkModels.SubscribtioResult;
import com.franshise.franshise.models.SharedPrefrenceModel;
import com.franshise.franshise.models.dataModels.StatusModel;
import com.franshise.franshise.utils.CustomProgressDialog;
import com.franshise.franshise.viewmodels.SubscribtionViewModel;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubscriptionsFragment extends Fragment implements SubscribtionAdapter.OnClick{
    SubscribtionViewModel subscribtionViewModel;
RecyclerView sub_res;
SubscribtionAdapter subscribtionAdapter;
int sub_id=-1;
ImageView bank_image;

    public SubscriptionsFragment() {
        // Required empty public constructor
    }
Button pick_image,subscribe;
    Uri imageBankUri;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_subscriptions, container, false);
        bank_image=view.findViewById(R.id.bank_image);
        sub_res=view.findViewById(R.id.sub_res);
        pick_image=view.findViewById(R.id.pick_image);
        subscribe=view.findViewById(R.id.subscribe);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        sub_res.setLayoutManager(mLayoutManager);
CustomProgressDialog.showProgress(getActivity());
        subscribtionViewModel= ViewModelProviders.of(this).get(SubscribtionViewModel.class);
        subscribtionViewModel.subscription_types().observe(this, new Observer<SubscribtioResult>() {
            @Override
            public void onChanged(@Nullable SubscribtioResult subscribtioResult) {
                CustomProgressDialog.clodseProgress();
                if(subscribtioResult.getStatus()==1) {
                    subscribtionAdapter = new SubscribtionAdapter(getActivity(),SubscriptionsFragment.this::getId, subscribtioResult.getData());
                    sub_res.setAdapter(subscribtionAdapter);

                }else Toast.makeText(getActivity(),subscribtioResult.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        pick_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(permission())
                    addImages1();
                else requestPermission();
            }
        });
subscribe.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(new SharedPrefrenceModel(getActivity()).isLogined()) {
            upload();
        }else startActivity(new Intent(getActivity(), LoginActivity.class));
    }
});
        return view;
    }

    private void upload() {
        if(imageBankUri!=null&&sub_id!=-1){
            CustomProgressDialog.showProgress(getActivity());
            subscribtionViewModel.new_subscription(new SharedPrefrenceModel(getActivity()).getId(),sub_id,imageBankUri,getActivity())
                    .observe(this, new Observer<StatusModel>() {
                        @Override
                        public void onChanged(@Nullable StatusModel statusModel) {
                            CustomProgressDialog.clodseProgress();
                            Toast.makeText(getActivity(),statusModel.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
            Log.v("rrrrrrrrrr","fff");
        }
        else Toast.makeText(getActivity(),R.string.fill_data,Toast.LENGTH_SHORT).show();
    }

    int PICK_IMAGE=0;
    private void addImages1(){   Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
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
                        bank_image.setImageBitmap(selectedImage);
                    bank_image.setVisibility(View.VISIBLE);
                        imageBankUri=imageUri;

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
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

    @Override
    public void getId(int id) {
        sub_id=id;
        Log.v("eeeeeeeeee",id+"");
    }
}
