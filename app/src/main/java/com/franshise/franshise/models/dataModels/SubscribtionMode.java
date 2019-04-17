package com.franshise.franshise.models.dataModels;

public class SubscribtionMode {
    private int id;
    private String name,price,duration;

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
