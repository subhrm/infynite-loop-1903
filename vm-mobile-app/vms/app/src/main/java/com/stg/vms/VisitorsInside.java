package com.stg.vms;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.stg.vms.data.AppMessages;
import com.stg.vms.model.InsideVisitor;
import com.stg.vms.service.VMSService;
import com.stg.vms.util.VMSDialog;

import java.util.List;

public class VisitorsInside extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitors_inside);
        final RecyclerView visitorView = findViewById(R.id.avi_visitorView);
        final View loader = findViewById(R.id.avi_loader);
        loader.setVisibility(View.VISIBLE);
        VMSService.getInsideVisitors(new VMSService.Callback<List<InsideVisitor>>() {
            @Override
            public void onSuccess(List<InsideVisitor> data) {
                visitorView.setLayoutManager(new LinearLayoutManager(VisitorsInside.this));
                visitorView.setAdapter(new VisitorInsideAdaptor(data, new VisitorInsideAdaptor.VisitorClickListener() {
                    @Override
                    public void onCall(String mobileNumber) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + mobileNumber));
                        startActivity(intent);
                    }
                }));
                loader.setVisibility(View.GONE);
            }

            @Override
            public void onError(String errorMsg) {
                loader.setVisibility(View.GONE);
                VMSDialog.showErrorDialog(VisitorsInside.this, "Error", "Error in loading visitors data !", true);
            }

            @Override
            public void onLoginError(String errorMsg) {
                Toast.makeText(VisitorsInside.this, AppMessages.SERVICE_CALL_AUTH_ERROR, Toast.LENGTH_LONG).show();
                startActivity(new Intent(VisitorsInside.this, LoginActivity.class));
                finish();
            }
        });
    }
}
