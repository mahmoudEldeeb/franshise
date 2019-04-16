package com.franshise.franshise.models.dataModels;

import android.arch.lifecycle.LiveData;

import com.franshise.franshise.models.BannarModel;

import java.util.List;

public class FranchisesCategoryModel {
    private int id;
    private String en_name,ar_name,image;

    private List<BannarModel>images;
private List<FranchiseModel>franchises;

    public List<FranchiseModel> getFranchises() {
        return franchises;
    }

    public void setFranchises(List<FranchiseModel> franchises) {
        this.franchises = franchises;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getEn_name() {
        return en_name;
    }

    public String getAr_name() {
        return ar_name;
    }

    public String getImage() {
        return image;
    }

    public List<BannarModel> getImages() {
        return images;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public void setAr_name(String ar_name) {
        this.ar_name = ar_name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setImages(List<BannarModel> images) {
        this.images = images;
    }
}
