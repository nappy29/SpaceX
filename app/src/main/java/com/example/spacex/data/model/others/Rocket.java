package com.example.spacex.data.model.others;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Rocket implements Serializable {

    @Expose
    @SerializedName("rocket_name")
    public String rocketName;

    @Expose
    @SerializedName("rocket_id")
    public String rocketId;

    @Expose
    @SerializedName("engines")
    public JsonObject rocketEngineNumber;

    @Expose
    @SerializedName("country")
    public String rocketCountry;

    @Expose
    @SerializedName("wikipedia")
    public String rocketSource;

    @Expose
    @SerializedName("description")
    public String rocketDescription;


    @Expose
    @SerializedName("flickr_images")
    public List<String> rocketImageUrl;

    public String getRocketName() {
        return rocketName;
    }

    public String getRocketId() {
        return rocketId;
    }

    public String getRocketEngineNumber() {
        return rocketEngineNumber.get("number").toString();
    }

    public String getRocketCountry() {
        return rocketCountry;
    }

    public String getRocketSource() {
        return rocketSource;
    }

    public String getRocketDescription() {
        return rocketDescription;
    }

    public String getRocketImageUrl() {
        return rocketImageUrl.get(0);
    }
}
