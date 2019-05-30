package com.example.trabalho2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ratings implements Serializable {

    @SerializedName("avgStars")
    @Expose
    private Double avgStars;
    @SerializedName("totalPoints")
    @Expose
    private Integer totalPoints;
    @SerializedName("numberVotes")
    @Expose
    private Integer numberVotes;
    @SerializedName("itemId")
    @Expose
    private Integer itemId;

    public Double getAvgStars() {
        return avgStars;
    }

    public void setAvgStars(Double avgStars) {
        this.avgStars = avgStars;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getNumberVotes() {
        return numberVotes;
    }

    public void setNumberVotes(Integer numberVotes) {
        this.numberVotes = numberVotes;
    }

    public Ratings(){

    }

}