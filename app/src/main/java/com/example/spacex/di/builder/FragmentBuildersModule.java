package com.example.spacex.di.builder;

import com.example.spacex.view.ui.rocketdetails.RocketDetailsFragment;
import com.example.spacex.view.ui.rocketlist.RocketListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract RocketDetailsFragment contributeRocketDetailsFragment();

    @ContributesAndroidInjector
    abstract RocketListFragment contributeRocketListFragment();
}
