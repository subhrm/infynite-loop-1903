package com.stg.vms;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.stg.vms.data.AppConstants;
import com.stg.vms.data.AppMessages;
import com.stg.vms.data.VMSData;
import com.stg.vms.model.SearchByPhotoRequest;
import com.stg.vms.model.SearchByPhotoResponse;
import com.stg.vms.model.TodaysVisitors;
import com.stg.vms.model.VisitorsLastDay;
import com.stg.vms.model.VisitorsResponse;
import com.stg.vms.service.VMSService;
import com.stg.vms.util.ImageUtil;
import com.stg.vms.util.InputValidation;
import com.stg.vms.util.VMSDialog;

import java.util.List;

public class AdminDashboard extends AppCompatActivity {
    private static final String TAG = "AppCompatActivity";
    private static final int REQUEST_SCAN_QR_CODE = 1, REQUEST_CAMERA_PHOTO = 2;
    private boolean reloadVisitors = true, activityForResult = false;
    private TextView totalVisitors, visitorInside, visitorRemaining, lblVisitorsLastDays;
    private EditText visitorId;
    private View visitorsToday, visitorsLastDay, loader;
    private GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (VMSData.getInstance().getUserProfile() == null || !VMSData.getInstance().getUserProfile().getUserRole().equalsIgnoreCase(AppConstants.ROLE_SECURITY_ADMIN)) {
            startActivity(new Intent(AdminDashboard.this, LoginActivity.class));
            finish();
            return;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        ImageView btnSearchByPhoto = findViewById(R.id.adm_btn_searchByPhoto);
        ImageButton barcodeButton = findViewById(R.id.adm_btn_barcode);
        totalVisitors = findViewById(R.id.adm_val_totalVisitor);
        visitorInside = findViewById(R.id.adm_val_insideVisitor);
        visitorRemaining = findViewById(R.id.adm_val_remainVisitor);
        visitorId = findViewById(R.id.adm_input_visitorId);
        ImageButton btnVisitorId = findViewById(R.id.adm_btn_visitorId);
        Button logout = findViewById(R.id.adm_btn_logout);
        graph = findViewById(R.id.graph);
        visitorsToday = findViewById(R.id.adm_container_visitorsToday);
        lblVisitorsLastDays = findViewById(R.id.adm_lbl_visitorLastDays);
        visitorsLastDay = findViewById(R.id.adm_container_visitorsLastDays);
        loader = findViewById(R.id.adm_loader);
        visitorsToday.setVisibility(View.GONE);
        visitorsLastDay.setVisibility(View.GONE);

        btnVisitorId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(visitorId.getText())) {
                    retrieveVisitorProfile(visitorId.getText().toString(), 0);
                }
            }
        });

        barcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityForResult = true;
                startActivityForResult(new Intent(AdminDashboard.this, BarcodeScanner.class), REQUEST_SCAN_QR_CODE);
            }
        });

        btnSearchByPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityForResult = true;
                startActivityForResult(new Intent(AdminDashboard.this, CameraPhoto.class), REQUEST_CAMERA_PHOTO);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VMSData.getInstance().setUserProfile(null);
                VMSData.getInstance().setAccessToken(null);
                startActivity(new Intent(AdminDashboard.this, LoginActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        activityForResult = false;
        if (requestCode == REQUEST_SCAN_QR_CODE) {
            if (resultCode == RESULT_OK) {
                String visitorId = data.getStringExtra(BarcodeScanner.BAR_CODE_DATA);
                Log.e(TAG, "Visitor Id: "+visitorId);
                retrieveVisitorProfile(visitorId, 1);
            } else {
                Toast.makeText(this, AppMessages.BARCODE_SCAN_ERROR, Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == REQUEST_CAMERA_PHOTO) {
            if (resultCode == RESULT_OK) {
                final String currentPhotoPath = data.getStringExtra(CameraPhoto.PHOTO_PATH);
                if (TextUtils.isEmpty(currentPhotoPath)) {
                    Toast.makeText(this, AppMessages.PHOTO_SAVE_ERROR, Toast.LENGTH_LONG).show();
                    return;
                }
                //Toast.makeText(this, "Photo Path: " + currentPhotoPath, Toast.LENGTH_LONG).show();
                loader.setVisibility(View.VISIBLE);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap imageBitmap = ImageUtil.processPhoto(currentPhotoPath);
                        final String base64Image = ImageUtil.bitmap2Base64(imageBitmap);
                        VMSService.searchByPhoto(new SearchByPhotoRequest(base64Image), new VMSService.Callback<SearchByPhotoResponse>() {
                            @Override
                            public void onSuccess(SearchByPhotoResponse data) {
                                loader.setVisibility(View.GONE);
                                if (data == null || data.getVisitorId() < 1) {
                                    VMSDialog.showErrorDialog(AdminDashboard.this, "Error", AppMessages.SEARCH_BY_FACE_ERROR, false);
                                    return;
                                }
                                VMSData.getInstance().setSearchByPhoto(true);
                                VMSData.getInstance().setNewPhoto(base64Image);
                                retrieveVisitorProfile(String.valueOf(data.getVisitorId()), 0);
                            }

                            @Override
                            public void onError(String errorMsg) {
                                VMSDialog.showErrorDialog(AdminDashboard.this, "Error", errorMsg, false);
                                loader.setVisibility(View.GONE);
                            }

                            @Override
                            public void onLoginError(String errorMsg) {
                                Toast.makeText(AdminDashboard.this, AppMessages.SERVICE_CALL_AUTH_ERROR, Toast.LENGTH_LONG).show();
                                startActivity(new Intent(AdminDashboard.this, LoginActivity.class));
                                finish();
                            }
                        });

                    }
                });
                thread.start();
            } else {
                Toast.makeText(this, AppMessages.PHOTO_SAVE_ERROR, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void retrieveVisitorProfile(String visitorId, int encrypted) {
        Intent intent = new Intent(AdminDashboard.this, VisitorProfile.class);
        intent.putExtra(AppConstants.REQUEST_VISITOR_ID, visitorId);
        intent.putExtra(AppConstants.REQUEST_ENCRYPTED, encrypted);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        reloadVisitors = !activityForResult;
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        VMSData.getInstance().setVisitorProfile(null);
        VMSData.getInstance().setNewPhoto(null);
        VMSData.getInstance().setVisitorPhoto(null);
        VMSData.getInstance().setSearchByPhoto(false);
        InputValidation.hideKeyboard(AdminDashboard.this);
        if (!reloadVisitors)
            return;
        loader.setVisibility(View.VISIBLE);
        visitorsToday.setVisibility(View.GONE);
        visitorsLastDay.setVisibility(View.GONE);
        VMSService.getVisitors(new VMSService.Callback<VisitorsResponse>() {
            @Override
            public void onSuccess(VisitorsResponse data) {
                if (data != null && data.getTodaysVisitors() != null) {
                    TodaysVisitors visitors = data.getTodaysVisitors();
                    totalVisitors.setText(String.valueOf(visitors.getTotal()));
                    visitorInside.setText(String.valueOf(visitors.getInside()));
                    visitorRemaining.setText(String.valueOf(visitors.getRemaining()));
                    visitorsToday.setVisibility(View.VISIBLE);
                }
                if (data != null && data.getVisitorLastDays() != null && data.getVisitorLastDays().size() > 0) {
                    try {
                        List<VisitorsLastDay> visitorsLastDays = data.getVisitorLastDays();
                        String visitorLastDaysLbl = AppMessages.LBL_VISITOR_LAST_DAYS.replace("{}", String.valueOf(visitorsLastDays.size())) + (visitorsLastDays.size() > 1 ? "s" : "");
                        lblVisitorsLastDays.setText(visitorLastDaysLbl);
                        DataPoint[] dataPoints = new DataPoint[visitorsLastDays.size()];
                        int i = 0;
                        for (VisitorsLastDay visitorsLastDay : visitorsLastDays)
                            dataPoints[i++] = new DataPoint(visitorsLastDay.getDayOfMonth(), visitorsLastDay.getCount());
                        graph.addSeries(new LineGraphSeries<>(dataPoints));
                        visitorsLastDay.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        Log.e(TAG, "Error in Graph Creation ", e);
                        visitorsLastDay.setVisibility(View.GONE);
                    }
                }
                loader.setVisibility(View.GONE);
            }

            @Override
            public void onError(String errorMsg) {
                loader.setVisibility(View.GONE);
                Toast.makeText(AdminDashboard.this, errorMsg, Toast.LENGTH_LONG).show();
                Log.e(TAG, "Error in fetching visitors: " + errorMsg);
            }

            @Override
            public void onLoginError(String errorMsg) {
                Toast.makeText(AdminDashboard.this, AppMessages.SERVICE_CALL_AUTH_ERROR, Toast.LENGTH_LONG).show();
                startActivity(new Intent(AdminDashboard.this, LoginActivity.class));
                finish();
            }
        });
    }
}
