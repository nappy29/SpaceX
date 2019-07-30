package com.example.spacex.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.spacex.view.ui.RocketViewModelFactory;
import com.example.spacex.view.ui.rocketdetails.RocketViewModel;
import com.example.spacex.view.ui.rocketlist.RocketListViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RocketListViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsRocketListViewModel(RocketListViewModel rocketListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RocketViewModel.class)
    @SuppressWarnings("unused")
    abstract ViewModel bindsRocketDetailsViewModel(RocketViewModel rocketViewModel);


    @Binds
    @SuppressWarnings("unused")
    abstract ViewModelProvider.Factory bindsViewModelFactory(RocketViewModelFactory viewModelFactory);
}
