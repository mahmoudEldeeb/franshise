package com.franshise.franshise.models.dataModels;

import java.io.Serializable;

public class FundingCompanyMode implements Serializable {
private  int id;
private String ar_name,en_name,ar_details,image,en_details;

    public void setId(int id) {
        this.id = id;
    }

    public void setAr_name(String ar_name) {
        this.ar_name = ar_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public void setAr_details(String ar_details) {
        this.ar_details = ar_details;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setEn_details(String en_details) {
        this.en_details = en_details;
    }

    public int getId() {
        return id;
    }

    public String getAr_name() {
        return ar_name;
    }

    public String getEn_name() {
        return en_name;
    }

    public String getAr_details() {
        return ar_details;
    }

    public String getImage() {
        return image;
    }

    public String getEn_details() {
        return en_details;
    }
}
