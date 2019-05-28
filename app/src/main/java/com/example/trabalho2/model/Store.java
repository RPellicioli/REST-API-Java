package com.example.trabalho2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Store {
    @SerializedName("isFeatured")
    @Expose
    private Boolean isFeatured;
    @SerializedName("isRefundable")
    @Expose
    private Boolean isRefundable;
    @SerializedName("cost")
    @Expose
    private String cost;

    public Boolean getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public Boolean getIsRefundable() {
        return isRefundable;
    }

    public void setIsRefundable(Boolean isRefundable) {
        this.isRefundable = isRefundable;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}