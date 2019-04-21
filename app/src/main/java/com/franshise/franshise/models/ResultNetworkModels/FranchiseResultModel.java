package com.franshise.franshise.models.ResultNetworkModels;

import com.franshise.franshise.models.dataModels.FranchiseModel;

import java.util.List;

public class FranchiseResultModel {
    int status;

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    private List<FranchiseModel> data;

    public void setData(List<FranchiseModel> data) {
        this.data = data;
    }

    public List<FranchiseModel> getData() {
        return data;
    }
}

