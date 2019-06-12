package com.franshise.franshise.models.dataModels;

import java.io.Serializable;

public class SubscribtionMode implements Serializable {
    private int id;
    private String name;
    private String price;
    private String duration;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    private String details;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDuration() {
        return duration;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
