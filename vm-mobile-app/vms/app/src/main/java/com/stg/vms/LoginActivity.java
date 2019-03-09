package com.stg.vms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.stg.vms.data.AppConstants;
import com.stg.vms.data.AppMessages;
import com.stg.vms.data.VMSData;
import com.stg.vms.model.LoginRequest;
import com.stg.vms.model.LoginResponse;
import com.stg.vms.model.UserProfile;
import com.stg.vms.service.VMSService;
import com.stg.vms.util.InputValidation;

import java.util.Date;

public class LoginActivity extends AppCompatActivity {
    private View loader;
    private TextInputLayout emailLayout, passwordLayout;
    private EditText email, password;
    private TextView errorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loader = findViewById(R.id.login_loader);
        emailLayout = findViewById(R.id.login_inputLayout_email);
        passwordLayout = findViewById(R.id.login_inputLayout_password);
        email = findViewById(R.id.login_input_email);
        password = findViewById(R.id.login_input_password);
        errorView = findViewById(R.id.login_error);

        Button loginButton = findViewById(R.id.login_button_login);

        errorView.setText("");
        errorView.setVisibility(View.GONE);
        loader.setVisibility(View.GONE);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        emailLayout.setErrorEnabled(false);
        passwordLayout.setErrorEnabled(false);
        errorView.setVisibility(View.GONE);
        if (!InputValidation.isValidEmail(email.getText())) {
            emailLayout.setError(AppMessages.INPUT_VALIDATION_ERROR_EMAIL);
            return;
        }
        if (TextUtils.isEmpty(password.getText())) {
            passwordLayout.setError(AppMessages.INPUT_VALIDATION_ERROR_PASSWORD);
            return;
        }
        loader.setVisibility(View.VISIBLE);
        VMSService.login(new LoginRequest(email.getText().toString(), password.getText().toString()), new VMSService.Callback<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse data) {
                if (data != null) {
                    VMSData.getInstance().setUserProfile(new UserProfile(data.getUserId(), data.getName(), data.getEmail(), data.getUserRole(), new Date()));

                    if (data.getUserRole().equalsIgnoreCase(AppConstants.ROLE_SECURITY_ADMIN)) {
                        VMSData.getInstance().setAccessToken(data.getToken());
                        navigateTo(AdminDashboard.class);
                    } else if (data.getUserRole().equalsIgnoreCase(AppConstants.ROLE_SECURITY_STAFF)) {
                        VMSData.getInstance().setAccessToken(data.getToken());
                        navigateTo(SecurityStaffDashboard.class);
                    } else {
                        errorView.setText(AppMessages.SERVICE_CALL_AUTH_ERROR);
                        errorView.setVisibility(View.VISIBLE);
                    }
                } else {
                    errorView.setText(AppMessages.SERVICE_CALL_ERROR);
                    errorView.setVisibility(View.VISIBLE);
                }
                loader.setVisibility(View.GONE);
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                errorView.setText(errorMsg);
                errorView.setVisibility(View.VISIBLE);
                loader.setVisibility(View.GONE);
            }

            @Override
            public void onLoginError(String errorMsg) {
                loader.setVisibility(View.GONE);
            }
        });
    }

    private void navigateTo(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
        finish();
    }
}
