package com.stg.vms.service;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.stg.vms.data.AppConstants;
import com.stg.vms.data.AppMessages;
import com.stg.vms.data.VMSData;
import com.stg.vms.model.ApproveVisitorRequest;
import com.stg.vms.model.InsideVisitor;
import com.stg.vms.model.LocationAccessRequest;
import com.stg.vms.model.LocationAccessResponse;
import com.stg.vms.model.LoginRequest;
import com.stg.vms.model.LoginResponse;
import com.stg.vms.model.SearchByPhotoRequest;
import com.stg.vms.model.SearchByPhotoResponse;
import com.stg.vms.model.ServiceResponse;
import com.stg.vms.model.UpdateStatusRequest;
import com.stg.vms.model.VerifyPhotoRequest;
import com.stg.vms.model.VerifyPhotoResponse;
import com.stg.vms.model.VisitorProfileRequest;
import com.stg.vms.model.VisitorProfileResponse;
import com.stg.vms.model.VisitorsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class VMSService {
    private static final String TAG = "VMSService";
    private static final VMSServiceInterface serviceInterface = ApiClient.getClient().create(VMSServiceInterface.class);
    private static final VMSPhotoService photoServiceInterface = ApiClient.getFaceServiceClient().create(VMSPhotoService.class);

    public static void login(LoginRequest loginRequest, final Callback<LoginResponse> callback) {
        Call<ServiceResponse<LoginResponse>> call = serviceInterface.login(loginRequest);
        call.enqueue(new retrofit2.Callback<ServiceResponse<LoginResponse>>() {
            @Override
            public void onResponse(@NonNull Call<ServiceResponse<LoginResponse>> call, @NonNull Response<ServiceResponse<LoginResponse>> response) {
                try {
                    ServiceResponse<LoginResponse> responseData = response.body();
                    if (responseData != null && responseData.getStatus() == AppConstants.SERVICE_STATUS_SUCCESS) {
                        callback.onSuccess(responseData.getData());
                    } else {
                        callback.onError(responseData != null ? responseData.getMessage() : AppMessages.SERVICE_CALL_ERROR);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error in parsing login service response", e);
                    callback.onError(AppMessages.SERVICE_CALL_ERROR);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ServiceResponse<LoginResponse>> call, @NonNull Throwable t) {
                Log.e(TAG, "Error in service call.", t);
                callback.onError(AppMessages.SERVICE_CALL_ERROR);
            }
        });
    }

    public static void getVisitors(final Callback<VisitorsResponse> callback) {
        if (TextUtils.isEmpty(VMSData.getInstance().getAccessToken())) {
            callback.onLoginError(AppMessages.SERVICE_CALL_AUTH_ERROR);
            return;
        }
        Call<ServiceResponse<VisitorsResponse>> call = serviceInterface.getVisitors(VMSData.getInstance().getAccessToken());
        call.enqueue(new retrofit2.Callback<ServiceResponse<VisitorsResponse>>() {
            @Override
            public void onResponse(Call<ServiceResponse<VisitorsResponse>> call, Response<ServiceResponse<VisitorsResponse>> response) {
                ServiceResponse<VisitorsResponse> responseData = response.body();
                if (responseData != null && responseData.getStatus() == AppConstants.SERVICE_STATUS_SUCCESS) {
                    callback.onSuccess(responseData.getData());
                } else if (responseData != null && responseData.getStatus() == AppConstants.SERVICE_STATUS_LOGIN_ERROR) {
                    callback.onLoginError(AppMessages.SERVICE_CALL_AUTH_ERROR);
                } else {
                    callback.onError(responseData != null ? responseData.getMessage() : AppMessages.SERVICE_CALL_ERROR);
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse<VisitorsResponse>> call, Throwable t) {
                Log.e(TAG, "Error in service call.", t);
                callback.onError(AppMessages.SERVICE_CALL_ERROR);
            }
        });
    }

    public static void searchByPhoto(SearchByPhotoRequest request, final Callback<SearchByPhotoResponse> callback) {
        Call<SearchByPhotoResponse> call = photoServiceInterface.searchByPhoto(request, VMSData.getInstance().getAccessToken());
        call.enqueue(new retrofit2.Callback<SearchByPhotoResponse>() {
            @Override
            public void onResponse(Call<SearchByPhotoResponse> call, Response<SearchByPhotoResponse> response) {
                try {
                    callback.onSuccess(response.body());
                } catch (Exception e) {
                    Log.e(TAG, "Error in face service call: ", e);
                    callback.onError(AppMessages.SEARCH_BY_FACE_ERROR);
                }
            }

            @Override
            public void onFailure(Call<SearchByPhotoResponse> call, Throwable t) {
                Log.e(TAG, "Error in face service call.", t);
                callback.onError(AppMessages.SEARCH_BY_FACE_ERROR);
            }
        });
    }

    public static void verifyPhoto(VerifyPhotoRequest request, final Callback<Double> callback) {
        Call<VerifyPhotoResponse> call = photoServiceInterface.verifyPhoto(request);
        call.enqueue(new retrofit2.Callback<VerifyPhotoResponse>() {
            @Override
            public void onResponse(Call<VerifyPhotoResponse> call, Response<VerifyPhotoResponse> response) {
                try {
                    callback.onSuccess(Double.valueOf(response.body().getSimilarity()) * 100);
                } catch (Exception e) {
                    Log.e(TAG, "Error in verify Photo: ", e);
                    callback.onSuccess(0d);
                }
            }

            @Override
            public void onFailure(Call<VerifyPhotoResponse> call, Throwable t) {
                callback.onSuccess(0d);
                Log.e(TAG, "Error in verify Photo: ", t);
            }
        });
    }

    public static void visitorProfile(VisitorProfileRequest visitorProfileRequest, final Callback<VisitorProfileResponse> callback) {
        Call<ServiceResponse<VisitorProfileResponse>> call = serviceInterface.visitorProfile(visitorProfileRequest, VMSData.getInstance().getAccessToken());
        call.enqueue(new retrofit2.Callback<ServiceResponse<VisitorProfileResponse>>() {
            @Override
            public void onResponse(Call<ServiceResponse<VisitorProfileResponse>> call, Response<ServiceResponse<VisitorProfileResponse>> response) {
                ServiceResponse<VisitorProfileResponse> responseData = response.body();
                if (responseData != null && responseData.getStatus() == AppConstants.SERVICE_STATUS_SUCCESS) {
                    callback.onSuccess(responseData.getData());
                } else if (responseData != null && responseData.getStatus() == AppConstants.SERVICE_STATUS_LOGIN_ERROR) {
                    callback.onLoginError(AppMessages.SERVICE_CALL_AUTH_ERROR);
                } else {
                    callback.onError(responseData != null ? responseData.getMessage() : AppMessages.SERVICE_CALL_ERROR);
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse<VisitorProfileResponse>> call, Throwable t) {
                Log.e(TAG, "Error in service call.", t);
                callback.onError(AppMessages.SERVICE_CALL_ERROR);
            }
        });
    }

    public static void locationAccess(LocationAccessRequest request, final Callback<LocationAccessResponse> callback) {
        Call<ServiceResponse<LocationAccessResponse>> call = serviceInterface.locationAccess(request, VMSData.getInstance().getAccessToken());
        call.enqueue(new retrofit2.Callback<ServiceResponse<LocationAccessResponse>>() {
            @Override
            public void onResponse(Call<ServiceResponse<LocationAccessResponse>> call, Response<ServiceResponse<LocationAccessResponse>> response) {
                if (response == null || response.body() == null)
                    callback.onError(AppMessages.SERVICE_CALL_ERROR);
                else if (response.body().getStatus() == AppConstants.SERVICE_STATUS_LOGIN_ERROR)
                    callback.onLoginError(AppMessages.SERVICE_CALL_AUTH_ERROR);
                else if (response.body().getStatus() == AppConstants.SERVICE_STATUS_ERROR)
                    callback.onError(response.body().getMessage());
                else
                    callback.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<ServiceResponse<LocationAccessResponse>> call, Throwable t) {
                callback.onError(AppMessages.SERVICE_CALL_ERROR);
            }
        });
    }

    public static void approveVisitor(ApproveVisitorRequest request, final Callback<ServiceResponse<Object>> callback) {
        Call<ServiceResponse<Object>> call = serviceInterface.approveVisitor(request, VMSData.getInstance().getAccessToken());
        call.enqueue(new retrofit2.Callback<ServiceResponse<Object>>() {
            @Override
            public void onResponse(Call<ServiceResponse<Object>> call, Response<ServiceResponse<Object>> response) {
                if (response == null || response.body() == null)
                    callback.onError(AppMessages.SERVICE_CALL_ERROR);
                else if (response.body().getStatus() == AppConstants.SERVICE_STATUS_LOGIN_ERROR)
                    callback.onLoginError(AppMessages.SERVICE_CALL_AUTH_ERROR);
                else
                    callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ServiceResponse<Object>> call, Throwable t) {
                callback.onError(AppMessages.SERVICE_CALL_ERROR);
            }
        });
    }

    public static void updateStatus(UpdateStatusRequest request, final Callback<ServiceResponse<Object>> callback) {
        Call<ServiceResponse<Object>> call = serviceInterface.updateStatus(request, VMSData.getInstance().getAccessToken());
        call.enqueue(new retrofit2.Callback<ServiceResponse<Object>>() {
            @Override
            public void onResponse(Call<ServiceResponse<Object>> call, Response<ServiceResponse<Object>> response) {
                if (response == null || response.body() == null)
                    callback.onError(AppMessages.SERVICE_CALL_ERROR);
                else if (response.body().getStatus() == AppConstants.SERVICE_STATUS_LOGIN_ERROR)
                    callback.onLoginError(AppMessages.SERVICE_CALL_AUTH_ERROR);
                else
                    callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ServiceResponse<Object>> call, Throwable t) {
                callback.onError(AppMessages.SERVICE_CALL_ERROR);
            }
        });
    }

    public static void getInsideVisitors(final Callback<List<InsideVisitor>> callback) {
        if (TextUtils.isEmpty(VMSData.getInstance().getAccessToken())) {
            callback.onLoginError(AppMessages.SERVICE_CALL_AUTH_ERROR);
            return;
        }
        Call<ServiceResponse<List<InsideVisitor>>> call = serviceInterface.visitorsInside(VMSData.getInstance().getAccessToken());
        call.enqueue(new retrofit2.Callback<ServiceResponse<List<InsideVisitor>>>() {
            @Override
            public void onResponse(Call<ServiceResponse<List<InsideVisitor>>> call, Response<ServiceResponse<List<InsideVisitor>>> response) {
                ServiceResponse<List<InsideVisitor>> responseData = response.body();
                if (responseData != null && responseData.getStatus() == AppConstants.SERVICE_STATUS_SUCCESS) {
                    callback.onSuccess(responseData.getData());
                } else if (responseData != null && responseData.getStatus() == AppConstants.SERVICE_STATUS_LOGIN_ERROR) {
                    callback.onLoginError(AppMessages.SERVICE_CALL_AUTH_ERROR);
                } else {
                    callback.onError(responseData != null ? responseData.getMessage() : AppMessages.SERVICE_CALL_ERROR);
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse<List<InsideVisitor>>> call, Throwable t) {
                Log.e(TAG, "Error in service call.", t);
                callback.onError(AppMessages.SERVICE_CALL_ERROR);
            }
        });
    }

    public interface Callback<T> {
        void onSuccess(T data);

        void onError(String errorMsg);

        void onLoginError(String errorMsg);
    }
}
