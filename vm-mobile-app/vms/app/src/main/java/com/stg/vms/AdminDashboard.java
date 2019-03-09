package com.stg.vms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.stg.vms.data.AppConstants;
import com.stg.vms.data.AppMessages;
import com.stg.vms.data.VMSData;
import com.stg.vms.model.TodaysVisitors;
import com.stg.vms.model.VisitorsLastDay;

import java.util.List;

public class AdminDashboard extends AppCompatActivity {
    private static final String TAG = "AppCompatActivity";
    private static final int REQUEST_SCAN_QR_CODE = 1;
    private ImageButton barcodeButton, btnVisitorId;
    private TextView totalVisitors, visitorInside, visitorRemaining;
    private EditText visitorId;
    private Button logout;
    private View visitorsToday, visitorsLastDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (VMSData.getInstance().getUserProfile() == null || !VMSData.getInstance().getUserProfile().getUserRole().equalsIgnoreCase(AppConstants.ROLE_SECURITY_ADMIN)) {
            startActivity(new Intent(AdminDashboard.this, LoginActivity.class));
            finish();
            return;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        barcodeButton = findViewById(R.id.adm_btn_barcode);
        totalVisitors = findViewById(R.id.adm_val_totalVisitor);
        visitorInside = findViewById(R.id.adm_val_insideVisitor);
        visitorRemaining = findViewById(R.id.adm_val_remainVisitor);
        visitorId = findViewById(R.id.adm_input_visitorId);
        btnVisitorId = findViewById(R.id.adm_btn_visitorId);
        logout = findViewById(R.id.adm_btn_logout);
        GraphView graph = findViewById(R.id.graph);
        visitorsToday = findViewById(R.id.adm_container_visitorsToday);
        visitorsLastDay = findViewById(R.id.adm_container_visitorsLastDays);
        if (VMSData.getInstance().getTodaysVisitors() == null) {
            visitorsToday.setVisibility(View.GONE);
        } else {
            TodaysVisitors visitors = VMSData.getInstance().getTodaysVisitors();
            totalVisitors.setText(String.valueOf(visitors.getTotal()));
            visitorInside.setText(String.valueOf(visitors.getInside()));
            visitorRemaining.setText(String.valueOf(visitors.getRemaining()));
            visitorsToday.setVisibility(View.VISIBLE);
        }
        if (VMSData.getInstance().getVisitorLastDays() == null || VMSData.getInstance().getVisitorLastDays().size() == 0) {
            visitorsLastDay.setVisibility(View.GONE);
        } else {
            try {
                List<VisitorsLastDay> visitorsLastDays = VMSData.getInstance().getVisitorLastDays();
                DataPoint[] dataPoints = new DataPoint[visitorsLastDays.size()];
                int i = 0;
                for (VisitorsLastDay visitorsLastDay : visitorsLastDays)
                    dataPoints[i++] = new DataPoint(visitorsLastDay.getDayOfMonth(), visitorsLastDay.getCount());
                graph.addSeries(new LineGraphSeries<>(dataPoints));
            } catch (Exception e) {
                Log.e(TAG, "Error in Graph Creation ", e);
                visitorsLastDay.setVisibility(View.GONE);
            }
        }

        btnVisitorId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(visitorId.getText())) {
                    retrieveVisitorProfile(visitorId.getText().toString());
                }
            }
        });

        barcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(AdminDashboard.this, BarcodeScanner.class), REQUEST_SCAN_QR_CODE);
            }
        });

        /*LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(10, 22),
                new DataPoint(11, 24),
                new DataPoint(12, 13),
                new DataPoint(13, 28),
                new DataPoint(14, 21),
                new DataPoint(15, 17),
                new DataPoint(16, 25),
                new DataPoint(17, 32),
                new DataPoint(18, 27),
                new DataPoint(19, 16)
        });
        graph.addSeries(series);*/
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VMSData.getInstance().setUserProfile(null);
                VMSData.getInstance().setVisitorLastDays(null);
                VMSData.getInstance().setTodaysVisitors(null);
                startActivity(new Intent(AdminDashboard.this, LoginActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SCAN_QR_CODE) {
            if (resultCode == RESULT_OK) {
                String visitorId = data.getStringExtra(BarcodeScanner.BAR_CODE_DATA);
                retrieveVisitorProfile(visitorId);
            } else {
                Toast.makeText(this, AppMessages.BARCODE_SCAN_ERROR, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void retrieveVisitorProfile(String visitorId) {
        Toast.makeText(this, "VisitorId: " + visitorId, Toast.LENGTH_LONG).show();
        startActivity(new Intent(AdminDashboard.this, VisitorProfile.class));
    }
}
