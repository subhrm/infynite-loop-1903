package com.stg.vms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import info.androidhive.barcode.BarcodeReader;

public class BarcodeScanner extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {
    public static final String BAR_CODE_DATA = "BAR_CODE_DATA";
    BarcodeReader barcodeReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);
    }

    @Override
    public void onScanned(Barcode barcode) {
        barcodeReader.playBeep();
        /*VMSData.getOurInstance().setQrCodeData(barcode.displayValue);
        startActivity(new Intent(BarcodeScanner.this, VisitorProfile.class));*/
        Intent intent = new Intent();
        intent.putExtra(BAR_CODE_DATA, barcode.displayValue);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {

    }
}
