package com.example.rup.di.modules;

import android.content.Context;

import com.example.rup.PrefUtils;
import com.example.rup.di.annotations.ApplicationScope;

import dagger.Module;
import dagger.Provides;


@Module(includes = {ApplicationContextModule.class})
public class DataModule {

    @Provides
    @ApplicationScope
    PrefUtils prefUtils(Context context) {
        return new PrefUtils(context);
    }
}
