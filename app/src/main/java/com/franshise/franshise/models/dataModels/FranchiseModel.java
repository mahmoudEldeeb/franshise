package com.franshise.franshise.models.dataModels;

import com.franshise.franshise.models.BannarModel;

import java.io.Serializable;
import java.util.List;

public class
FranchiseModel implements Serializable
{
    private  int id;
    private int franchise_type_id;
    private int franchise_type_value;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    private int user_id;
    private String details;
    private String name;
    private int category_id;


    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    private DataModel countries;

    private String establish_date,image,other_commission;
            private int existing_local_branch,undercost_local_branch,existing_outside_branch,undercost_outside_branch
                    ,other_commission_value,owner_ship_commission,marketing_commission;
            private List<DataModel>franchise_market;
            private List<BannarModel>images;
           private PeriodMode periods;

    public int getFranchise_type_id() {
        return franchise_type_id;
    }

    public int getFranchise_type_value() {
        return franchise_type_value;
    }

    public void setFranchise_type_id(int franchise_type_id) {
        this.franchise_type_id = franchise_type_id;
    }

    public void setFranchise_type_value(int franchise_type_value) {
        this.franchise_type_value = franchise_type_value;
    }

    public PeriodMode getPeriods() {
        return periods;
    }

    public void setPeriods(PeriodMode periods) {
        this.periods = periods;
    }

    public DataModel getCountries() {
        return countries;
    }

    public void setCountries(DataModel countries) {
        this.countries = countries;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setEstablish_date(String establish_date) {
        this.establish_date = establish_date;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setOther_commission(String other_commission) {
        this.other_commission = other_commission;
    }
    public void setExisting_local_branch(int existing_local_branch) {
        this.existing_local_branch = existing_local_branch;
    }
    public void setUndercost_local_branch(int undercost_local_branch) {
        this.undercost_local_branch = undercost_local_branch;
    }

    public void setExisting_outside_branch(int existing_outside_branch) {
        this.existing_outside_branch = existing_outside_branch;
    }

    public void setUndercost_outside_branch(int undercost_outside_branch) {
        this.undercost_outside_branch = undercost_outside_branch;
    }

    public void setOther_commission_value(int other_commission_value) {
        this.other_commission_value = other_commission_value;
    }

    public void setOwner_ship_commission(int owner_ship_commission) {
        this.owner_ship_commission = owner_ship_commission;
    }

    public void setMarketing_commission(int marketing_commission) {
        this.marketing_commission = marketing_commission;
    }

    public void setFranchise_market(List<DataModel> franchise_market) {
        this.franchise_market = franchise_market;
    }

    public void setImages(List<BannarModel> images) {
        this.images = images;
    }

    public void setPorts(List<PortModel> ports) {
        this.ports = ports;
    }

    public int getId() {
        return id;
    }

    public String getDetails() {
        return details;
    }

    public String getEstablish_date() {
        return establish_date;
    }

    public String getImage() {
        return image;
    }

    public String getOther_commission() {
        return other_commission;
    }

    public int getExisting_local_branch() {
        return existing_local_branch;
    }

    public int getUndercost_local_branch() {
        return undercost_local_branch;
    }

    public int getExisting_outside_branch() {
        return existing_outside_branch;
    }

    public int getUndercost_outside_branch() {
        return undercost_outside_branch;
    }

    public int getOther_commission_value() {
        return other_commission_value;
    }

    public int getOwner_ship_commission() {
        return owner_ship_commission;
    }

    public int getMarketing_commission() {
        return marketing_commission;
    }

    public List<DataModel> getFranchise_market() {
        return franchise_market;
    }

    public List<BannarModel> getImages() {
        return images;
    }

    public List<PortModel> getPorts() {
        return ports;
    }

    private List<PortModel>ports;



}
