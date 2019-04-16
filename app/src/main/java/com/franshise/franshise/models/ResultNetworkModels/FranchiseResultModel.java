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

    private FranchiseModel data;

    public FranchiseModel getData() {
        return data;
    }

    public void setData(FranchiseModel data) {
        this.data = data;
    }
}

