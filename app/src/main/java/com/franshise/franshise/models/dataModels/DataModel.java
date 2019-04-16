package com.franshise.franshise.models.dataModels;

public class DataModel {

    private String en_name;
private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEn_name() {
        return en_name;
    }

    public String getAr_name() {
        return ar_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public void setAr_name(String ar_name) {
        this.ar_name = ar_name;
    }

    private String ar_name;


    private int min,max;

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
