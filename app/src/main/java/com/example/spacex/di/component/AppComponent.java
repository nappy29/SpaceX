package com.example.spacex.di.component;

import android.app.Application;

import com.example.spacex.SpaceRocketX;
import com.example.spacex.di.builder.MainActivityModule;
import com.example.spacex.di.module.AppModule;
import com.example.spacex.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AppModule.class, AndroidInjectionModule.class, MainActivityModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(SpaceRocketX spaceRocketX);
//    void inject(ApiHelper apiHelper);
}