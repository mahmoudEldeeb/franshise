package com.franshise.franshise.models.ResultNetworkModels;


        import com.franshise.franshise.models.dataModels.FranchiseData;

public class  FranchiseResultsView{
    private int status;
    private FranchiseData data;

    public int getStatus() {
        return status;
    }

    public FranchiseData getData() {
        return data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(FranchiseData data) {
        this.data = data;
    }
}
