package com.stg.vms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import info.androidhive.barcode.BarcodeReader;

public class AdminDashboard extends AppCompatActivity {
    ImageButton barcodeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        barcodeButton = findViewById(R.id.adm_btn_barcode);

        barcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(AdminDashboard.this, BarcodeScanner.class));
                startActivity(new Intent(AdminDashboard.this, VisitorProfile.class));
            }
        });

        GraphView graph = findViewById(R.id.graph);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
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
        graph.addSeries(series);
    }
}
