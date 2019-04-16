package com.franshise.franshise.models.dataModels;

import android.net.Uri;

import java.io.Serializable;
import java.util.List;

public class CreatFranchiseModel implements Serializable {
    private String details;
    private int origin;

    private int cat_id;
    private String date;

    private String otherCommsionName;
    private int loca_exist;
    private int local_under;
    private int out_exist;
    private int out_under;
    private int propertyCommsion;
    private int marketCommsion;
    private int otherCommsion;
private String name;
    private int periodId;
    List<String> spaceModels;
    List<String> investModels;
    List<Integer>marketCounties;
    Uri franchiseImage;
    List<Uri>imageOfProduct;
    List<Integer>franchiseTypeIds;
    List<Integer>franchiseTypeValues;

    public List<Integer> getFranchiseTypeIds() {
        return franchiseTypeIds;
    }

    public List<Integer> getFranchiseTypeValues() {
        return franchiseTypeValues;
    }

    public void setFranchiseTypeIds(List<Integer> franchiseTypeIds) {
        this.franchiseTypeIds = franchiseTypeIds;
    }

    public void setFranchiseTypeValues(List<Integer> franchiseTypeValues) {
        this.franchiseTypeValues = franchiseTypeValues;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCat_id() {
        return cat_id;
    }

    public String getName() {
        return name;
    }
    public int getPeriodId() {
        return periodId;
    }

    public List<String> getInvestModels() {
        return investModels;
    }

    public List<Integer> getMarketCounties() {
        return marketCounties;
    }

    public Uri getFranchiseImage() {
        return franchiseImage;
    }

    public List<Uri> getImageOfProduct() {
        return imageOfProduct;
    }

    public void setPeriodId(int periodId) {
        this.periodId = periodId;
    }

    public void setInvestModels(List<String> investModels) {
        this.investModels = investModels;
    }

    public void setMarketCounties(List<Integer> marketCounties) {
        this.marketCounties = marketCounties;
    }

    public void setFranchiseImage(Uri franchiseImage) {
        this.franchiseImage = franchiseImage;
    }

    public void setImageOfProduct(List<Uri> imageOfProduct) {
        this.imageOfProduct = imageOfProduct;
    }


    public String getOtherCommsionName() {
        return otherCommsionName;
    }


    public int getPropertyCommsion() {
        return propertyCommsion;
    }

    public int getMarketCommsion() {
        return marketCommsion;
    }

    public int getOtherCommsion() {
        return otherCommsion;
    }

    public void setOtherCommsionName(String otherCommsionName) {
        this.otherCommsionName = otherCommsionName;
    }


    public void setPropertyCommsion(int propertyCommsion) {
        this.propertyCommsion = propertyCommsion;
    }

    public void setMarketCommsion(int marketCommsion) {
        this.marketCommsion = marketCommsion;
    }

    public void setOtherCommsion(int otherCommsion) {
        this.otherCommsion = otherCommsion;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getOrigin() {
        return origin;
    }


    public String getDate() {
        return date;
    }

    public int getLoca_exist() {
        return loca_exist;
    }

    public int getLocal_under() {
        return local_under;
    }

    public int getOut_exist() {
        return out_exist;
    }

    public int getOut_under() {
        return out_under;
    }

    public List<String> getSpaceModels() {
        return spaceModels;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLoca_exist(int loca_exist) {
        this.loca_exist = loca_exist;
    }

    public void setLocal_under(int local_under) {
        this.local_under = local_under;
    }

    public void setOut_exist(int out_exist) {
        this.out_exist = out_exist;
    }

    public void setOut_under(int out_under) {
        this.out_under = out_under;
    }

    public void setSpaceModels(List<String> spaceModels) {
        this.spaceModels = spaceModels;
    }
}
