package com.example.rup.di.modules;


import com.example.rup.di.annotations.ApplicationScope;
import com.example.rup.repositories.MarvelRepository;
import com.example.rup.service.ApiService;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    private ApiService mApiService;
    @Provides
    @ApplicationScope
    public MarvelRepository repositoryModule(ApiService apiService)
    {
        mApiService = apiService;
        return new MarvelRepository(apiService);
    }
}
