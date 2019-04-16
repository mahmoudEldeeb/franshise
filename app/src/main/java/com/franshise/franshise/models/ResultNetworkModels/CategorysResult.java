package com.franshise.franshise.models.ResultNetworkModels;

import com.franshise.franshise.models.BannarModel;
import com.franshise.franshise.models.dataModels.CategorysModels;

import java.util.List;

public class CategorysResult {
    private int status;
    List<CategorysModels> data;

    public int getStatus() {
        return status;
    }

    public List<CategorysModels> getData() {
        return data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(List<CategorysModels> data) {
        this.data = data;
    }
}
