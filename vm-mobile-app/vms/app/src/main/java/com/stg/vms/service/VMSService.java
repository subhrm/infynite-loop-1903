package com.stg.vms.service;

import android.support.annotation.NonNull;
import android.util.Log;

import com.stg.vms.data.AppConstants;
import com.stg.vms.data.AppMessages;
import com.stg.vms.model.LoginRequest;
import com.stg.vms.model.LoginResponse;
import com.stg.vms.model.ServiceResponse;

import retrofit2.Call;
import retrofit2.Response;

public class VMSService {
    private static final String TAG = "VMSService";

    public static void login(LoginRequest loginRequest, final Callback<LoginResponse> callback) {
        VMSServiceInterface serviceInterface = ApiClient.getClient().create(VMSServiceInterface.class);
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

    public interface Callback<T> {
        void onSuccess(T data);

        void onError(String errorMsg);
    }
}
