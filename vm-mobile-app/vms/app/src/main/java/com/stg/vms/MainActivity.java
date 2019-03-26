package com.stg.vms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.stg.vms.data.VMSData;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    VMSData.getInstance().clear();
                    VMSData.getInstance().setAccessToken(null);
                    VMSData.getInstance().setUserProfile(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                navigateToLogin();
            }
        });
        t.start();
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
