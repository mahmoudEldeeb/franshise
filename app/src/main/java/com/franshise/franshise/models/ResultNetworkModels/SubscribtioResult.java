package com.franshise.franshise.models.ResultNetworkModels;

import com.franshise.franshise.models.dataModels.SubscribtionMode;

import java.util.List;

public class SubscribtioResult {
    List<SubscribtionMode>data;
    int status;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SubscribtionMode> getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public void setData(List<SubscribtionMode> data) {
        this.data = data;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
