package com.franshise.franshise.models.ResultNetworkModels;

import com.franshise.franshise.models.dataModels.PeriodMode;

import java.util.List;

public class PeriodResult {
    List<PeriodMode>data;

    public void setData(List<PeriodMode> data) {
        this.data = data;
    }

    public List<PeriodMode> getData() {
        return data;
    }
}
