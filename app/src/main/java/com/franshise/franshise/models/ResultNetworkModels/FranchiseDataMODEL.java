package com.franshise.franshise.models.ResultNetworkModels;

import com.franshise.franshise.models.BannarModel;

import java.io.Serializable;
import java.util.List;

public class FranchiseDataMODEL implements Serializable {
    List<String>data;
    List<String>franchiseType;
    List<BannarModel>images;
    int status;
String mainImage;
String about ;
String name;

    public List<String> getFranchiseType() {
        return franchiseType;
    }

    public void setFranchiseType(List<String> franchiseType) {
        this.franchiseType = franchiseType;
    }
    //////7

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getMainImage() {
        return mainImage;
    }

    public List<String> getData() {
        return data;
    }

    public List<BannarModel> getImages() {
        return images;
    }

    public int getStatus() {
        return status;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public void setImages(List<BannarModel> images) {
        this.images = images;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
