package com.example.rup.di.modules;


import com.example.rup.di.annotations.ApplicationScope;
import com.example.rup.repositories.LocationRepository;
import com.example.rup.service.ApiService;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    private ApiService mApiService;
    @Provides
    @ApplicationScope
    public LocationRepository repositoryModule(ApiService apiService)
    {
        mApiService = apiService;
        return new LocationRepository(apiService);
    }
}
