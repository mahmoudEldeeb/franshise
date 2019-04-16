package com.franshise.franshise.models.ResultNetworkModels;

import com.franshise.franshise.models.dataModels.UserProfileModel;

import java.util.List;

public class UserProfileResult
{
    UserProfileModel data;
int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UserProfileModel getData() {
        return data;
    }

    public void setData(UserProfileModel data) {
        this.data = data;
    }
}
