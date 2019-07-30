package com.example.spacex.view.ui.rocketlist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import android.util.Log;


import com.example.spacex.data.model.api.RocketResponse;
import com.example.spacex.data.model.others.Launch;
import com.example.spacex.data.model.others.Rocket;
import com.example.spacex.di.PreferenceInfo;
import com.example.spacex.remote.ApiHelperInterface;
import com.example.spacex.util.Connectivity;
import com.example.spacex.util.rx.SchedulerProvider;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RocketListViewModel extends ViewModel {

    @Inject
    ApiHelperInterface apiHelperInterface;

    @Inject
    SchedulerProvider schedulerProvider;

    private MutableLiveData<List<Rocket>> rocketListObservable;

    protected MutableLiveData<String> errorObservable = new MutableLiveData<String>();

    private final RocketResponse rocketResponse = new RocketResponse();

    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Inject
    public RocketListViewModel(){
        Log.d("LiveConstruct", "It is working here just with empty viewmodel as demanded");
//        apiHelperInterface.doLogHere();
//        new RocketListViewModel(apiHelperInterface, schedulerProvider);

        rocketListObservable = new MutableLiveData<>();
    }


//    @Inject
    public RocketListViewModel(ApiHelperInterface aHI, SchedulerProvider sP) {

        Log.d("VM", "ViewModel is not null at all");
//        apiHelperInterface = aHI;
//        schedulerProvider = sP;
//        rocketListObservable = fetchRocketList();
    }



    protected LiveData<List<Rocket>> fetchRocketList(ApiHelperInterface aPH, SchedulerProvider sP, Context context) {

        final MutableLiveData<List<Rocket>> data = new MutableLiveData<>();

        aPH.doLogHere();

//        if(Connectivity.isInternetOn(context))
            mCompositeDisposable.add(aPH
                    .getRocketApiCall()
                    .subscribeOn(sP.io())
                    .observeOn(sP.ui())
                    .subscribe(rocketList -> {

                                    Log.d("LiveData", rocketList.toString());

                                    this.rocketResponse.setRocketList(rocketList);
                                    rocketListObservable.setValue(this.rocketResponse.getRocketList());
                                    rocketListObservable = data;

                            }, throwable -> {
                                // TODO:
                                errorObservable.setValue("No internet Connection ");
                                Log.d("LiveData", "with error here" + throwable);
                            }
                    ));

        return data;
    }

    public MutableLiveData<List<Rocket>> getRocketListObservable() {
        return rocketListObservable;
    }
}
