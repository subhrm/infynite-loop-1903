package com.stg.vms;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.login_button_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateTo(AdminDashboard.class);
            }
        });
    }
    private void navigateTo(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
        finish();
    }
}
