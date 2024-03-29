package com.example.rup.service;


import com.example.rup.models.Travel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("{urlId}")
    Single<Response<Travel>> getTravelLocations(@Path("urlId") String urlId);

}
