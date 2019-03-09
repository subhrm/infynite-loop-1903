package com.stg.vms.service;

import com.stg.vms.data.AppConstants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;
    private static Retrofit retrofitFace = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_SERVICE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static Retrofit getFaceServiceClient() {
        if (retrofitFace == null) {
            retrofitFace = new Retrofit.Builder()
                    .baseUrl(AppConstants.BASE_SERVICE_URL_SEARCH_BY_PHOTO)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitFace;
    }
}
