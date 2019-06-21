package com.example.rup.di.modules;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.rup.BuildConfig;
import com.example.rup.Utils;
import com.example.rup.di.annotations.ApplicationScope;
import com.example.rup.service.ApiService;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;


@Module(includes = ApplicationContextModule.class)
public class NetworkModule {

    @ApplicationScope
    @Provides
    @Named ("online")
    Interceptor networkInterceptor(Context context) {

        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                Log.d("cnrr","intercepted");
                if (Utils.isNetworkAvailable(context)) {
                    int maxAge = 5; // read from cache for 5 seconds
                    Log.d("cnrr","intercepted t");

                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                } else {
                    Log.d("cnrr","intercepted f");

                    int maxStale = 60 * 5; // tolerate 5 minutes stale
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                }
            }
        };
    }

    @ApplicationScope
    @Provides
    @Named ("offline")
    Interceptor offlineInterceptor(Context context) {

      return new Interceptor() {
          @Override
          public Response intercept(Chain chain) throws IOException {
              Request request = chain.request();
              if (!Utils.isNetworkAvailable(context)) {
                  int maxStale = 60 * 5; // Offline cache available for 30 days
                  request = request.newBuilder()
                          .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                          .removeHeader("Pragma")
                          .build();
              }
              return chain.proceed(request);
          }
      };
    }

    @ApplicationScope
    @Provides
    HttpLoggingInterceptor httpLoggingInterceptor(Context context) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(message -> {
            if(BuildConfig.DEBUG) {
                Log.d("Network",message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        return interceptor;

    }

    @ApplicationScope
    @Provides
    Cache cache(File file) {
        return new Cache(file,10*1000*1000);
    }

    @ApplicationScope
    @Provides
    File file(Context context) {
        return new File(context.getCacheDir(),"okhttp_cache");
    }

    @ApplicationScope
    @Provides
    OkHttpClient okHttpClient(Cache cache,@Named ("offline") Interceptor offlineInterceptor,@Named ("online")Interceptor networkInterceptor,Context context) {
        return new OkHttpClient.Builder()
                .addInterceptor(offlineInterceptor)
                .addNetworkInterceptor(networkInterceptor)
                .cache(cache)
                .build();
    }

    @Provides
    @ApplicationScope
    ApiService apiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @ApplicationScope
    ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return objectMapper;
    }

    @Provides
    @ApplicationScope
    Retrofit retrofit(OkHttpClient okHttpClient,ObjectMapper objectMapper) {
        return new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/youtube/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @ApplicationScope
    public Picasso picasso(Context context, OkHttpClient okHttpClient) {
        return new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
    }


}
