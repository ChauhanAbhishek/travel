package com.example.rup.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.rup.Event;
import com.example.rup.RupApplication;
import com.example.rup.models.Location;
import com.example.rup.repositories.LocationRepository;

import java.util.List;

public class LocationListViewModel extends ViewModel {
    private LocationRepository locationRepository;

    private LiveData<List<Location>> mObservableLocationList;
    private LiveData<String> mObservableCustomerName;
    private String nextPageToken;
    private LiveData<Event<String>> toastMessage;

    boolean isReqSent = false;
    MutableLiveData<Event<Boolean>> isReqSentEvent;

    public LocationListViewModel()
    {
        locationRepository = RupApplication.getInstance().getApplicationComponent().locationRepository();
        mObservableLocationList =  Transformations.map(locationRepository.getObservableLocationList(), videos -> getLocations(videos));
        isReqSentEvent = new MutableLiveData<>();
        toastMessage = Transformations.map(locationRepository.getToastMessage(),  toastMessage -> toastmsg(toastMessage));
        mObservableCustomerName = Transformations.map(locationRepository.getCustomerName(), customerName -> getCustomerName(customerName));
    }

    public LiveData<String> getmObservableCustomerName() {
        return mObservableCustomerName;
    }



    public void getLocationList(String pageToken) {
        isReqSent=true;
        isReqSentEvent.setValue(new Event<>(true));
        LocationRepository locationRepository = RupApplication.getInstance().getApplicationComponent().locationRepository();
        locationRepository.getTravelLocations(pageToken);
    }
    public List<Location> getLocations(List<Location> locationList)
    {
        isReqSent=false;
        isReqSentEvent.setValue(new Event<>(false));
        return locationList;
    }

    public Event<String> toastmsg(Event<String> toastMessage)
    {
        return toastMessage;
    }

    public String getCustomerName(String customerName)
    {
        return customerName;
    }

    public LiveData<List<Location>> getmObservableLocationList() {
        return mObservableLocationList;
    }

    public LiveData<Event<Boolean>> getIsReqSent() {
        return isReqSentEvent;
    }

    public LiveData<Event<String>> getToastMessage() {
        return toastMessage;
    }

}
