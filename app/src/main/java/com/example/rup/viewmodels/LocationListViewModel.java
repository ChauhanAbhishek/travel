package com.example.rup.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rup.Event;
import com.example.rup.RupApplication;
import com.example.rup.models.Location;
import com.example.rup.repositories.MarvelRepository;

import java.util.List;

public class LocationListViewModel extends ViewModel {
    private MarvelRepository marvelRepository;

    private LiveData<List<Location>> mObservableLocationList;
    private LiveData<String> customerName;
    private String nextPageToken;
    private LiveData<Event<String>> toastMessage;
    private MutableLiveData<Event<Boolean>> mPlayerVisibility;

    boolean isReqSent = false;
    MutableLiveData<Event<Boolean>> isReqSentEvent;


    public void getLocationList(String pageToken) {
        MarvelRepository marvelRepository = RupApplication.getInstance().getApplicationComponent().marvelReposotory();
        marvelRepository.getTravelLocations(pageToken);
    }

}
