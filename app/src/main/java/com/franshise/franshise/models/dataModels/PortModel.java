package com.franshise.franshise.models.dataModels;

import java.io.Serializable;

public class PortModel implements Serializable {
    private String space;
    private String total_Investment;

    public String getSpace() {
        return space;
    }

    public String getTotal_Investment() {
        return total_Investment;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public void setTotal_Investment(String total_Investment) {
        this.total_Investment = total_Investment;
    }
}
