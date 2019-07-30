package com.example.spacex.di.module;

import android.app.Application;
import android.content.Context;

import com.example.spacex.data.local.pref.AppPreferenceHelper;
import com.example.spacex.data.local.pref.PreferenceHelper;
import com.example.spacex.di.PreferenceInfo;
import com.example.spacex.remote.ApiHelper;
import com.example.spacex.remote.ApiHelperInterface;
import com.example.spacex.util.rx.AppConstants;
import com.example.spacex.util.rx.AppSchedulerProvider;
import com.example.spacex.util.rx.SchedulerProvider;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

//@Module(subcomponents = ViewModelSubComponent.class)
@Module(includes = ViewModelModule.class)
public class AppModule {

    @Singleton
    @Provides
    Context provideContext(Application application) {
        return application;
    }

    @PreferenceInfo
    @Provides
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    PreferenceHelper providePreferencesHelper(AppPreferenceHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    SchedulerProvider provideSchedulerProvider(AppSchedulerProvider appSchedulerProvider) {
        return appSchedulerProvider;
    }

    @Provides
    @Singleton
    Gson ProvideGson(){
        return new Gson();
    }

    @Provides
    @Singleton
    ApiHelperInterface provideApiHelperInterface(ApiHelper apiHelper) {
        return apiHelper;
    }

//    @Singleton
//    @Provides
//    ViewModelProvider.Factory provideViewModelFactory(
//            ViewModelSubComponent.Builder viewModelSubComponent) {
//
//        return new RocketViewModelFactory(viewModelSubComponent.build());
//    }
}