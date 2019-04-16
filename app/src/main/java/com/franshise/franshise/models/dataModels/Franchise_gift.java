package com.franshise.franshise.models.dataModels;

import java.util.List;

public class Franchise_gift {
    //List<Integer>
    private int value;
String ar_name,en_name;

    public String getAr_name() {
        return ar_name;
    }

    public String getEn_name() {
        return en_name;
    }

    public void setAr_name(String ar_name) {
        this.ar_name = ar_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
