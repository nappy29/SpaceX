package com.example.spacex.view.ui.rocketdetails;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.ObservableField;
import android.util.Log;

import com.example.spacex.data.model.api.LaunchResponse;
import com.example.spacex.data.model.others.Launch;
import com.example.spacex.data.model.others.Rocket;
import com.example.spacex.remote.ApiHelperInterface;
import com.example.spacex.util.rx.SchedulerProvider;
import com.google.gson.Gson;

import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class RocketViewModel extends ViewModel {

    @Inject
    Gson gson;

    public ObservableField<Rocket> rocket = new ObservableField<>();
    private final ObservableField<Boolean> isLoading = new ObservableField<>(false);

    private List<List<Launch>> listsOfLaunchesByYearAndId = new ArrayList<List<Launch>>();

    private final MutableLiveData<Rocket> observableRocket = new MutableLiveData<>();

    private final LaunchResponse launchResponse = new LaunchResponse();

    private final CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    protected MutableLiveData<String> errorObservable = new MutableLiveData<String>();

    private ValueLineSeries valueLineSeries = new ValueLineSeries();

    private List<String> arrayofyears = new ArrayList<>();

    @Inject
    public RocketViewModel(){

        Log.d("DetailsLiveModel", "It is working here just with empty viewmodel as demanded");

        valueLineSeries.addPoint(new ValueLinePoint("Jan", 2));

    }

    public void setRocket(Rocket rocket){
        this.rocket.set(rocket);
    }

    public Rocket getRocket(){
       return this.rocket.get();
    }

    protected LiveData<List<Launch>> fetchLaunchList(ApiHelperInterface aPH, SchedulerProvider sP, Context context, String rocketID){

        final MutableLiveData<List<Launch>> data = new MutableLiveData<>();

        mCompositeDisposable.add(aPH
                .getRocketLaunchesApiCall()
                .subscribeOn(sP.io())
                .observeOn(sP.ui())
                .subscribe(launchList -> {

                    Log.d("LiveData", launchList.toString());

//                    groupLaunchesByYear(launchList);

                    getListOfRocketLaunchesById(launchList, rocketID);
                    this.launchResponse.setLaunchList(launchList);
                    data.setValue(this.launchResponse.getLaunchList());

                }, throwable -> {
                    // TODO:

                    errorObservable.setValue("No internet Connection ");
                    Log.d("LiveData", "with error here" + throwable);

                } ));

        return data;
    }


    protected void groupLaunchesByYear(List<Launch> launches){

            Observable.fromIterable(launches)
                    .groupBy(launch -> launch.launch_year)
    //                .toSortedList()
                    .flatMapSingle(Observable::toList)
                    .subscribe(group -> {

                        ArrayList<String> list = new ArrayList<String>();
                        listsOfLaunchesByYearAndId.add(group);

                        for(Launch launch : group) {
                            valueLineSeries.addPoint(new ValueLinePoint(launch.launch_year, group.size()));
                            list.add(launch.launch_year + " " + launch.getRocketId() + " " + group.size());

                            // Get an array of Launch years for all groups
                            arrayofyears.add(launch.launch_year);

                            break;

                        }

                        Log.d("Grouped",  list.toString());
                    });

            // this is just some noise data to ensure that the last valuelines are displayed correctly
        valueLineSeries.addPoint(new ValueLinePoint("Jan", 2));


    }

    protected ValueLineSeries getValueLines(){

        return valueLineSeries;
    }

    public List<List<Launch>> getGroupOfList(){

        return listsOfLaunchesByYearAndId;
    }

    public void getListOfRocketLaunchesById(List<Launch> list, String id) {


        List<Launch> new_lists = new ArrayList<>();
        for(Launch launch : list) {

                String id_edited = "\""+ id + "\"";

                if (launch.getRocketId().equalsIgnoreCase(id_edited)){

                    new_lists.add(launch);
                }
            }

        if(new_lists.size() > 0)
            groupLaunchesByYear(new_lists);

        else
            Log.d("Launch state", " Launch list is " + new_lists.size());

    }

    public String[] getArrayOfYearsFromGroups(){

        return arrayofyears.toArray(new String[arrayofyears.size()]);
    }
}
