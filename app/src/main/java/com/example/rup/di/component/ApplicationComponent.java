package com.example.rup.di.component;

import com.example.rup.PrefUtils;
import com.example.rup.di.annotations.ApplicationScope;
import com.example.rup.di.modules.DataModule;
import com.example.rup.di.modules.NetworkModule;
import com.example.rup.di.modules.RepositoryModule;
import com.example.rup.repositories.MarvelRepository;
import com.example.rup.service.ApiService;
import com.squareup.picasso.Picasso;

import dagger.Component;


@ApplicationScope
@Component(modules = {NetworkModule.class, DataModule.class, RepositoryModule.class})
public interface ApplicationComponent {
    MarvelRepository marvelReposotory();
}
