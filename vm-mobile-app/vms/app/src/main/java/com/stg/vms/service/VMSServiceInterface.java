package com.stg.vms.service;

import com.stg.vms.model.LoginRequest;
import com.stg.vms.model.LoginResponse;
import com.stg.vms.model.ServiceResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface VMSServiceInterface {
    @POST("login")
    Call<ServiceResponse<LoginResponse>> login(@Body LoginRequest loginRequest);
}
