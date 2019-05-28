package com.example.trabalho2.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Images {

    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("featured")
    @Expose
    private String featured;
    @SerializedName("background")
    @Expose
    private String background;
    @SerializedName("information")
    @Expose
    private String information;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getFeatured() {
        return featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

}