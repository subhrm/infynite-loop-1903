package com.stg.vms;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.stg.vms.data.AppConstants;
import com.stg.vms.data.AppMessages;
import com.stg.vms.data.VMSData;
import com.stg.vms.model.ApproveVisitorRequest;
import com.stg.vms.model.ServiceResponse;
import com.stg.vms.model.UpdateStatusRequest;
import com.stg.vms.model.VisitorProfileRequest;
import com.stg.vms.model.VisitorProfileResponse;
import com.stg.vms.service.VMSService;
import com.stg.vms.util.ImageUtil;
import com.stg.vms.util.InputValidation;
import com.stg.vms.util.VMSDialog;

public class VisitorProfile extends AppCompatActivity {
    private View mainContainer, loader;
    private ImageView visitorImage;
    private TextView name, type, email, mobile, referredBy, lblExpEntry, valExpEntry, lblExpExit, valExpExit, notApproved, visitComplete;
    private Button verifyPhoto, tempExit, exit, reEnable, approve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        final String visitorId = intent.getStringExtra(AppConstants.REQUEST_VISITOR_ID);
        final int encrypted = intent.getIntExtra(AppConstants.REQUEST_ENCRYPTED, 0);
        if (visitorId == null) {
            Toast.makeText(this, AppMessages.VISITOR_PROFILE_DATA_ERROR, Toast.LENGTH_LONG).show();
            onBackPressed();
            finish();
            return;
        }

        setContentView(R.layout.activity_visitor_profile);
        mainContainer = findViewById(R.id.prof_container_main);
        mainContainer.setVisibility(View.GONE);
        loader = findViewById(R.id.prof_loader);
        loader.setVisibility(View.VISIBLE);
        visitorImage = findViewById(R.id.prof_photo);
        name = findViewById(R.id.prof_name);
        type = findViewById(R.id.prof_type);
        email = findViewById(R.id.prof_email);
        mobile = findViewById(R.id.prof_mobile);
        referredBy = findViewById(R.id.prof_refer);
        lblExpEntry = findViewById(R.id.prof_lbl_entry);
        valExpEntry = findViewById(R.id.prof_val_entry);
        lblExpExit = findViewById(R.id.prof_lbl_exit);
        valExpExit = findViewById(R.id.prof_val_exit);
        verifyPhoto = findViewById(R.id.prof_btn_verifyPhoto);
        tempExit = findViewById(R.id.prof_btn_tempExit);
        exit = findViewById(R.id.prof_btn_exit);
        reEnable = findViewById(R.id.prof_btn_reenable);
        approve = findViewById(R.id.prof_btn_approve);
        notApproved = findViewById(R.id.prof_lbl_notApproved);
        visitComplete = findViewById(R.id.prof_lbl_visitComplete);

        loadVisitorProfile(visitorId, encrypted);

        visitorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VisitorProfile.this, ImageViewer.class);
                intent.putExtra(AppConstants.VIEW_IMAGE_REQUEST_KEY, AppConstants.VIEW_IMAGE_REQUEST_EXISTING);
                startActivity(intent);
            }
        });
        ImageButton homeButton = findViewById(R.id.prof_btn_home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
        verifyPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VisitorProfile.this, VerifyPhoto.class);
                intent.putExtra(AppConstants.REQUEST_VISITOR_ID, visitorId);
                intent.putExtra(AppConstants.REQUEST_ENCRYPTED, encrypted);
                startActivity(intent);
            }
        });

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(VisitorProfile.this);
                dialogBuilder.setTitle("Identity Verification");
                dialogBuilder.setMessage("Have you verified Id proof of visitor?");
                dialogBuilder.setCancelable(true);
                dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        approveVisitor();
                    }
                });
                dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                dialogBuilder.create().show();
            }
        });
        tempExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatus(AppConstants.VISITOR_STATUS_TEMP_OUT, "Visitor's gate pass disabled temporarily !", "Error in Temp Exit !");
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatus(AppConstants.VISITOR_STATUS_VISIT_COMPLETED, "Visitor's gate pass has been disabled !", "Error in Exit !");
            }
        });
        reEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStatus(AppConstants.VISITOR_STATUS_INSIDE, "Visitor's gate pass has been re-enabled !", "Error in Re-enabling the gate pass !");
            }
        });
    }

    private void loadVisitorProfile(String visitorId, int encrypted) {
        VMSService.visitorProfile(new VisitorProfileRequest(visitorId, VMSData.getInstance().getUserProfile().getUserRole(), encrypted), new VMSService.Callback<VisitorProfileResponse>() {
            @Override
            public void onSuccess(VisitorProfileResponse data) {
                try {
                    VMSData.getInstance().setVisitorProfile(data);
                    VMSData.getInstance().setVisitorId(String.valueOf(data.getVisitorId()));
                    VMSData.getInstance().setVisitorPhoto(data.getPhoto());
                    visitorImage.setImageBitmap(ImageUtil.base642Bitmap(data.getPhoto()));
                    name.setText(data.getName());
                    type.setText(data.getVisitorType());
                    email.setText(data.getEmail());
                    mobile.setText(data.getMobile());
                    if (TextUtils.isEmpty(data.getReferredBy())) {
                        referredBy.setVisibility(View.GONE);
                    } else {
                        referredBy.setText(data.getReferredBy());
                        referredBy.setVisibility(View.VISIBLE);
                    }
                    verifyPhoto.setVisibility(View.GONE);
                    tempExit.setVisibility(View.GONE);
                    exit.setVisibility(View.GONE);
                    reEnable.setVisibility(View.GONE);
                    approve.setVisibility(View.GONE);
                    notApproved.setVisibility(View.GONE);
                    visitComplete.setVisibility(View.GONE);
                    switch (data.getVisitorStatus()) {
                        case AppConstants.VISITOR_STATUS_INITIAL:
                            if (VMSData.getInstance().isSearchByPhoto() || data.getSpotRegistration() == AppConstants.VISITOR_SPOT_REGISTRATION_YES) {
                                approve.setVisibility(View.VISIBLE);
                            } else {
                                verifyPhoto.setVisibility(View.VISIBLE);
                            }
                            lblExpEntry.setText(AppMessages.LBL_EXPECTED_ENTRY);
                            lblExpExit.setText(AppMessages.LBL_EXPECTED_EXIT);
                            valExpEntry.setText(InputValidation.convertDateTime(data.getExpectedEntry()));
                            valExpExit.setText(InputValidation.convertDateTime(data.getExpectedExit()));
                            break;
                        case AppConstants.VISITOR_STATUS_INSIDE:
                            tempExit.setVisibility(View.VISIBLE);
                            exit.setVisibility(View.VISIBLE);
                            lblExpEntry.setText(AppMessages.LBL_ACTUAL_ENTRY);
                            lblExpExit.setText(AppMessages.LBL_EXPECTED_EXIT);
                            valExpEntry.setText(InputValidation.convertDateTime(data.getActualEntry()));
                            valExpExit.setText(InputValidation.convertDateTime(data.getExpectedExit()));
                            break;
                        case AppConstants.VISITOR_STATUS_TEMP_OUT:
                            reEnable.setVisibility(View.VISIBLE);
                            lblExpEntry.setText(AppMessages.LBL_ACTUAL_ENTRY);
                            lblExpExit.setText(AppMessages.LBL_EXPECTED_EXIT);
                            valExpEntry.setText(InputValidation.convertDateTime(data.getActualEntry()));
                            valExpExit.setText(InputValidation.convertDateTime(data.getExpectedExit()));
                            break;
                        case AppConstants.VISITOR_STATUS_PENDING_APPROVAL:
                            notApproved.setVisibility(View.VISIBLE);
                            lblExpEntry.setText(AppMessages.LBL_EXPECTED_ENTRY);
                            lblExpExit.setText(AppMessages.LBL_EXPECTED_EXIT);
                            valExpEntry.setText(InputValidation.convertDateTime(data.getExpectedEntry()));
                            valExpExit.setText(InputValidation.convertDateTime(data.getExpectedExit()));
                            break;
                        case AppConstants.VISITOR_STATUS_VISIT_COMPLETED:
                            visitComplete.setVisibility(View.VISIBLE);
                            lblExpEntry.setText(AppMessages.LBL_ACTUAL_ENTRY);
                            lblExpExit.setText(AppMessages.LBL_ACTUAL_EXIT);
                            valExpEntry.setText(InputValidation.convertDateTime(data.getActualEntry()));
                            valExpExit.setText(InputValidation.convertDateTime(data.getActualExit()));
                            break;
                        default:
                            lblExpEntry.setText(AppMessages.LBL_EXPECTED_ENTRY);
                            lblExpExit.setText(AppMessages.LBL_EXPECTED_EXIT);
                            valExpEntry.setText(InputValidation.convertDateTime(data.getExpectedEntry()));
                            valExpExit.setText(InputValidation.convertDateTime(data.getExpectedExit()));
                    }
                    mainContainer.setVisibility(View.VISIBLE);
                    loader.setVisibility(View.GONE);
                } catch (Exception e) {
                    Log.e("VisitorProfile", AppMessages.SERVICE_CALL_ERROR, e);
                    VMSDialog.showErrorDialog(VisitorProfile.this, "Error", AppMessages.SERVICE_CALL_ERROR, true);
                }
                loader.setVisibility(View.GONE);
            }

            @Override
            public void onError(String errorMsg) {
                VMSDialog.showErrorDialog(VisitorProfile.this, "Error", errorMsg, true);
                loader.setVisibility(View.GONE);
            }

            @Override
            public void onLoginError(String errorMsg) {
                Toast.makeText(VisitorProfile.this, AppMessages.SERVICE_CALL_AUTH_ERROR, Toast.LENGTH_LONG).show();
                startActivity(new Intent(VisitorProfile.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void approveVisitor() {
        loader.setVisibility(View.VISIBLE);
        VMSService.approveVisitor(new ApproveVisitorRequest(VMSData.getInstance().getVisitorProfile().getVisitorId(), (VMSData.getInstance().getNewPhoto() == null ? VMSData.getInstance().getVisitorPhoto() : VMSData.getInstance().getNewPhoto()), VMSData.getInstance().getUserProfile().getUserId()), new VMSService.Callback<ServiceResponse<Object>>() {
            @Override
            public void onSuccess(ServiceResponse<Object> data) {
                if (data.getStatus() == AppConstants.SERVICE_STATUS_SUCCESS) {
                    loader.setVisibility(View.GONE);
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(VisitorProfile.this);
                    dialogBuilder.setTitle("Success");
                    dialogBuilder.setMessage(AppMessages.VISITOR_APPROVED);
                    dialogBuilder.setCancelable(true);
                    dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            startActivity(new Intent(VisitorProfile.this, AdminDashboard.class));
                            finish();
                        }
                    });
                    dialogBuilder.create().show();
                } else {
                    loader.setVisibility(View.GONE);
                    VMSDialog.showErrorDialog(VisitorProfile.this, "Error", AppMessages.UNEXPECTED_ERROR, false);
                }
            }

            @Override
            public void onError(String errorMsg) {
                loader.setVisibility(View.GONE);
                VMSDialog.showErrorDialog(VisitorProfile.this, "Error", errorMsg, false);
            }

            @Override
            public void onLoginError(String errorMsg) {
                Toast.makeText(VisitorProfile.this, AppMessages.SERVICE_CALL_AUTH_ERROR, Toast.LENGTH_LONG).show();
                startActivity(new Intent(VisitorProfile.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void updateStatus(long status, final String successMsg, final String errMsg) {
        UpdateStatusRequest request = new UpdateStatusRequest();
        request.setVisitorId(VMSData.getInstance().getVisitorProfile().getVisitorId());
        request.setSecurityId(VMSData.getInstance().getUserProfile().getUserId());
        request.setStatus(status);
        VMSService.updateStatus(request, new VMSService.Callback<ServiceResponse<Object>>() {
            @Override
            public void onSuccess(ServiceResponse<Object> data) {
                VMSDialog.showErrorDialog(VisitorProfile.this, "Success", successMsg, true);
            }

            @Override
            public void onError(String errorMsg) {
                VMSDialog.showErrorDialog(VisitorProfile.this, "Error", errMsg, false);
            }

            @Override
            public void onLoginError(String errorMsg) {
                Toast.makeText(VisitorProfile.this, AppMessages.SERVICE_CALL_AUTH_ERROR, Toast.LENGTH_LONG).show();
                startActivity(new Intent(VisitorProfile.this, LoginActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
