package com.example.spacex.remote;

import com.example.spacex.data.model.others.Launch;
import com.example.spacex.data.model.others.Rocket;

import java.util.List;

import io.reactivex.Single;

public interface ApiHelperInterface {
    Single<List<Rocket>> getRocketApiCall();

    Single<List<Launch>> getRocketLaunchesApiCall();

    void doLogHere();

}