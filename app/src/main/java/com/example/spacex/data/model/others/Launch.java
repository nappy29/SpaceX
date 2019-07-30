package com.example.spacex.data.model.others;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.saber.stickyheader.stickyData.StickyMainData;

public class Launch implements StickyMainData {

    @Expose
    @SerializedName("mission_name")
    public String mission_name;

    @Expose
    @SerializedName("launch_date_local")
    public String launch_date;

    @Expose
    @SerializedName("launch_year")
    public String launch_year;


    @Expose
    @SerializedName("launch_success")
    public Boolean launch_success;


    @Expose
    @SerializedName("links")
    public JsonObject links;

    @Expose
    @SerializedName("rocket")
    public JsonObject rocket;


    public String getMission_name() {
        return mission_name;
    }

    public String getLaunch_date() {
        return launch_date;
    }

    public String getLaunch_year() {
        return launch_year;
    }

    public Boolean getLaunch_success() {
        return launch_success;
    }

    public String getLinks() {

        return links.get("mission_patch_small").toString();
    }

    public String getRocketId() {
        return rocket.get("rocket_id").toString();
    }
}
