package com.franshise.franshise.models.ResultNetworkModels;

import com.franshise.franshise.models.dataModels.FundingCompanyMode;

import java.util.List;

public class FundCompanyModelResult {
    int status;
    List<FundingCompanyMode>data;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(List<FundingCompanyMode> data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public List<FundingCompanyMode> getData() {
        return data;
    }
}
