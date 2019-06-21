package com.example.rup;

import android.app.Application;

import com.example.rup.di.component.ApplicationComponent;
import com.example.rup.di.component.DaggerApplicationComponent;
import com.example.rup.di.modules.ApplicationContextModule;

public class RupApplication extends Application
{
    private static RupApplication instance;
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationContextModule(new ApplicationContextModule(this))
                .build();
    }

    public static RupApplication getInstance() {
        return instance;
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
