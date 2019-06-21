package com.example.rup.repositories;

import android.util.Log;

import com.example.rup.models.Travel;
import com.example.rup.service.ApiService;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class MarvelRepository {

    ApiService mApiService;

    public MarvelRepository(ApiService apiService)
    {
        mApiService=apiService;
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
//                            mObservableVideoList.setValue(response.body().getItems());
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
