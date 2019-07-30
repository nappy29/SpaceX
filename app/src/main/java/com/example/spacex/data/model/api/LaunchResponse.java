package com.example.spacex.data.model.api;

import com.example.spacex.data.model.others.Launch;

import java.util.List;

public class LaunchResponse{

    private List<Launch> launchList;

    public void setLaunchList(List<Launch> launches) {
        this.launchList = launches;
    }

    public List<Launch> getLaunchList() {
        return launchList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LaunchResponse)) {
            return false;
        }

        LaunchResponse that = (LaunchResponse) o;

        return launchList.equals(that.launchList);

    }

    @Override
    public int hashCode() {
        int result = launchList == null? launchList.hashCode() : null;
        return result;
    }
}
