package com.stg.vms.service;

import com.stg.vms.data.AppConstants;
import com.stg.vms.model.SearchByPhotoRequest;
import com.stg.vms.model.SearchByPhotoResponse;
import com.stg.vms.model.VerifyPhotoRequest;
import com.stg.vms.model.VerifyPhotoResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface VMSPhotoService {
    @POST("find-visitor-by-face")
    @Headers("Content-Type:application/json")
    Call<SearchByPhotoResponse> searchByPhoto(@Body SearchByPhotoRequest searchByPhotoRequest, @Header(AppConstants.SERVICE_HEADER_TOKEN) String accessToken);

    @POST("face-similarity-b64")
    @Headers("Content-Type:application/json")
    Call<VerifyPhotoResponse> verifyPhoto(@Body VerifyPhotoRequest request);
}
