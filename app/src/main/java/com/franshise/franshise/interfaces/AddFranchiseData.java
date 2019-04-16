package com.franshise.franshise.interfaces;

import android.net.Uri;

import com.franshise.franshise.models.dataModels.CreatFranchiseModel;

import java.util.List;

public interface AddFranchiseData {
    public void addfromFragmentthree(String details,String name,int cat_id);

    public void addfromFragmentfour(Integer origin, String date, int local_exist, int local_under, int out_exist, int out_under, List<String>spaceModels);

    public void addFromFragmentFive(List<Integer> franchiseTypeid, List<Integer> franchiseTypeValue,int propertyCommsion,int marketCommsion,String otherCommsionName,int otherCommsion );
public void addFromFragmentSix(int period, List<String> investModels, List<Integer>countries, Uri image, List<Uri>imageOfProduct);
public CreatFranchiseModel getData();
}
