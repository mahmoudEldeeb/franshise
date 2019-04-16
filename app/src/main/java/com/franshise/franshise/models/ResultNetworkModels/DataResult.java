package com.franshise.franshise.models.ResultNetworkModels;

import com.franshise.franshise.models.dataModels.DataModel;

import java.util.List;

public class DataResult {
    List<DataModel>data;
private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataModel> getData() {
        return data;
    }

    public void setData(List<DataModel> data) {
        this.data = data;
    }
}
