package com.franshise.franshise.models.ResultNetworkModels;

import com.franshise.franshise.models.dataModels.FranchiseModel;

import java.util.List;

public class FranchisesResult {
    private int status;
    private List<FranchiseModel>data;

    public int getStatus() {
        return status;
    }

    public List<FranchiseModel> getData() {
        return data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(List<FranchiseModel> data) {
        this.data = data;
    }
}
