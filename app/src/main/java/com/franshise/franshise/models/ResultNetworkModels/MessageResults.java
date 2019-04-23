package com.franshise.franshise.models.ResultNetworkModels;

import com.franshise.franshise.models.dataModels.MessageModel;

import java.util.List;

public class MessageResults {
    List<MessageModel>data;

    public void setData(List<MessageModel> data) {
        this.data = data;
    }

    public List<MessageModel> getData() {
        return data;
    }
}
