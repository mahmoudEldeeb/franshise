package com.franshise.franshise.models.dataModels;

import com.google.gson.JsonArray;

import org.json.JSONArray;

import java.util.List;

public class FranchiseData {
    FranchiseModel franchise;
   List<Franchise_gift> Franchise_gift;

    public List<com.franshise.franshise.models.dataModels.Franchise_gift> getFranchise_type() {
        return franchise_type;
    }

    public void setFranchise_type(List<com.franshise.franshise.models.dataModels.Franchise_gift> franchise_type) {
        this.franchise_type = franchise_type;
    }

    List<Franchise_gift> franchise_type;

    public List<com.franshise.franshise.models.dataModels.Franchise_gift> getFranchise_gift() {
        return Franchise_gift;
    }

    public void setFranchise_gift(List<com.franshise.franshise.models.dataModels.Franchise_gift> franchise_gift) {
        Franchise_gift = franchise_gift;
    }

    public FranchiseModel getFranchise() {
        return franchise;
    }

    public void setFranchise(FranchiseModel franchise) {
        this.franchise = franchise;
    }
    // List<>

}
