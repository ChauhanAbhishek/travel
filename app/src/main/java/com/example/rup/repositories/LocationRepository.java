package com.example.rup.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.rup.Event;
import com.example.rup.models.Location;
import com.example.rup.models.Travel;
import com.example.rup.service.ApiService;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class LocationRepository {

    ApiService mApiService;

    private MutableLiveData<Event<String>> toastMessage;
    private  MutableLiveData<Boolean> mIsDataLoadingError;
    private MutableLiveData<List<Location>> mObservableLocationList;
    private MutableLiveData<String> customerName;


    public LocationRepository(ApiService apiService)
    {
        mApiService=apiService;
        mObservableLocationList = new MutableLiveData<>();
        mIsDataLoadingError = new MutableLiveData<>();
        toastMessage = new MutableLiveData<>();
        customerName = new MutableLiveData<>();
    }

    public LiveData<Event<String>> getToastMessage() {
        return toastMessage;
    }

    public LiveData <String> getCustomerName() {
        return customerName;
    }


    public LiveData<List<Location>> getObservableLocationList()
    {
        return mObservableLocationList;
    }

    public LiveData<Boolean> getmIsDataLoadingError() {
        return mIsDataLoadingError;
    }

    public void getTravelLocations(String pageToken)
    {
        Single<Response<Travel>> travelResponse;


        travelResponse = mApiService.getTravelLocations(pageToken);


        travelResponse.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Response<Travel>>() {
                    @Override
                    public void onSuccess(Response<Travel> response) {
                        if(response.isSuccessful()) {
                            //videoListAdapter.updateList(response.body().getItems());
                            mObservableLocationList.setValue(response.body().getLocations());

                            Log.d("cnrrrs",response.toString() + response.body().getCustName());

                            customerName.setValue(response.body().getCustName());
//                            viewModelNonUIChangesListener.setPurchaseToken(response.body().getNextPageToken());
                            Log.d("cnrr",response.toString() + response.body());
                        } else {
                            //todo handle errors
                            Log.d("cnrr","errrr");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
//                        //todo handle errors
//                        viewModelNonUIChangesListener.errorOccurred();
//                        toastMessage.setValue(new Event<>("Something has gone wrong"));
                        Log.d("cnrr",e.toString());

                    }
                });
    }
}