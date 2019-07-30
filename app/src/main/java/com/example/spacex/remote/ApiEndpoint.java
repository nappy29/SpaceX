package com.example.spacex.remote;

import com.example.spacex.BuildConfig;

public class ApiEndpoint {

    private ApiEndpoint(){

    }

    public static final String ENDPOINT_Rocket = BuildConfig.BASE_URL + "/rockets";
    public static final String ENDPOINT_launches = BuildConfig.BASE_URL + "/launches";
}