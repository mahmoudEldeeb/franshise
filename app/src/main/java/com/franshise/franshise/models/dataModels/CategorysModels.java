package com.franshise.franshise.models.dataModels;

import com.franshise.franshise.models.BannarModel;

import java.util.List;

public class CategorysModels {
    private  int id;
    private String en_name,ar_name,image;
List<BannarModel>images;
List<FranchiseModel>franchises;

    public List<FranchiseModel> getFranchises() {
        return franchises;
    }

    public void setFranchises(List<FranchiseModel> franchises) {
        this.franchises = franchises;
    }

    public List<BannarModel> getImages() {
        return images;
    }

    public void setImages(List<BannarModel> images) {
        this.images = images;
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

    public void setId(int id) {
        this.id = id;
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
}
