package com.example.spacex.remote;

import android.util.Log;

import com.example.spacex.data.model.others.Launch;
import com.example.spacex.data.model.others.Rocket;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

//@Singleton
public class ApiHelper implements ApiHelperInterface {

    @Inject
    public ApiHelper(){


        Log.d("apihelper", (getRocketApiCall() == null)?"It is null" : "It is not null" );

    }

    @Override
    public Single<List<Rocket>> getRocketApiCall() {
        return Rx2AndroidNetworking.get(ApiEndpoint.ENDPOINT_Rocket)
                .build()
                .getObjectListSingle(Rocket.class);
    }

    @Override
    public Single<List<Launch>> getRocketLaunchesApiCall() {
        return Rx2AndroidNetworking.get(ApiEndpoint.ENDPOINT_launches)
                .build()
//                .getObjectListObservable(Launch.class);
                .getObjectListSingle(Launch.class);
    }

    @Override
    public void doLogHere() {

        Log.d("Live", "Believing this object might as well be injected");

    }
}