package com.stg.vms.service;

import com.stg.vms.data.AppConstants;
import com.stg.vms.model.ApproveVisitorRequest;
import com.stg.vms.model.LocationAccessRequest;
import com.stg.vms.model.LoginRequest;
import com.stg.vms.model.LoginResponse;
import com.stg.vms.model.ServiceResponse;
import com.stg.vms.model.VisitorProfileRequest;
import com.stg.vms.model.VisitorProfileResponse;
import com.stg.vms.model.VisitorsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface VMSServiceInterface {
    @POST("login")
    Call<ServiceResponse<LoginResponse>> login(@Body LoginRequest loginRequest);

    @GET("mobile/getVisitors")
    Call<ServiceResponse<VisitorsResponse>> getVisitors(@Header(AppConstants.SERVICE_HEADER_TOKEN) String accessToken);

    @POST("mobile/getVisitorProfile")
    Call<ServiceResponse<VisitorProfileResponse>> visitorProfile(@Body VisitorProfileRequest visitorProfileRequest, @Header(AppConstants.SERVICE_HEADER_TOKEN) String accessToken);

    @POST("mobile/locationAccess")
    Call<ServiceResponse<Object>> locationAccess(@Body LocationAccessRequest request, @Header(AppConstants.SERVICE_HEADER_TOKEN) String accessToken);

    @POST("mobile/approveVisitor")
    Call<ServiceResponse<Object>> approveVisitor(@Body ApproveVisitorRequest request, @Header(AppConstants.SERVICE_HEADER_TOKEN) String accessToken);
}
