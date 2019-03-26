package com.stg.vms;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.stg.vms.data.AppConstants;
import com.stg.vms.data.AppMessages;
import com.stg.vms.data.VMSData;
import com.stg.vms.model.ApproveVisitorRequest;
import com.stg.vms.model.ServiceResponse;
import com.stg.vms.model.VerifyPhotoRequest;
import com.stg.vms.service.VMSService;
import com.stg.vms.util.ImageUtil;
import com.stg.vms.util.VMSDialog;

import java.text.DecimalFormat;

public class VerifyPhoto extends AppCompatActivity {
    private static final int REQUEST_CAMERA_PHOTO = 1;
    ImageView newPhoto, existingPhoto;
    View loader, mainContainer;
    TextView matchPercent;

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
        setContentView(R.layout.activity_verify_photo);
        mainContainer = findViewById(R.id.vp_container_main);
        mainContainer.setVisibility(View.GONE);
        existingPhoto = findViewById(R.id.vp_uploadedImage);
        newPhoto = findViewById(R.id.vp_newImage);
        matchPercent = findViewById(R.id.vp_val_match);
        loader = findViewById(R.id.vp_loader);
        Button btnApprove = findViewById(R.id.vp_btn_approve);
        Button btnReject = findViewById(R.id.vp_btn_reject);

        existingPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerifyPhoto.this, ImageViewer.class);
                intent.putExtra(AppConstants.VIEW_IMAGE_REQUEST_KEY, AppConstants.VIEW_IMAGE_REQUEST_EXISTING);
                startActivity(intent);
            }
        });

        newPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (VMSData.getInstance().getNewPhoto() == null || VMSData.getInstance().getNewPhoto().isEmpty())
                    return;
                Intent intent = new Intent(VerifyPhoto.this, ImageViewer.class);
                intent.putExtra(AppConstants.VIEW_IMAGE_REQUEST_KEY, AppConstants.VIEW_IMAGE_REQUEST_NEW);
                startActivity(intent);
            }
        });

        loader.setVisibility(View.VISIBLE);
        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(VerifyPhoto.this);
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
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VerifyPhoto.this, AdminDashboard.class));
                finish();
            }
        });
        if (VMSData.getInstance().isSearchByPhoto()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    existingPhoto.setImageBitmap(ImageUtil.base642Bitmap(VMSData.getInstance().getVisitorPhoto()));
                    newPhoto.setImageBitmap(ImageUtil.cropImage(getApplicationContext(), ImageUtil.base642Bitmap(VMSData.getInstance().getNewPhoto()), AppConstants.PHOTO_WIDTH, AppConstants.PHOTO_WIDTH));
                    verifyPhoto();
                }
            });
            thread.start();
        } else {
            startActivityForResult(new Intent(this, CameraPhoto.class), REQUEST_CAMERA_PHOTO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA_PHOTO) {
            if (resultCode == RESULT_OK) {
                final String currentPhotoPath = data.getStringExtra(CameraPhoto.PHOTO_PATH);
                if (TextUtils.isEmpty(currentPhotoPath)) {
                    VMSDialog.showErrorDialog(VerifyPhoto.this, "Error", AppMessages.PHOTO_SAVE_ERROR, true);
                    return;
                }
                loader.setVisibility(View.VISIBLE);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap imageBitmap = ImageUtil.processPhoto(currentPhotoPath);
                        existingPhoto.setImageBitmap(ImageUtil.base642Bitmap(VMSData.getInstance().getVisitorPhoto()));
                        newPhoto.setImageBitmap(ImageUtil.cropImage(getApplicationContext(), imageBitmap, AppConstants.PHOTO_WIDTH, AppConstants.PHOTO_WIDTH));
                        VMSData.getInstance().setNewPhoto(ImageUtil.bitmap2Base64(imageBitmap));
                        verifyPhoto();
                    }
                });
                thread.start();
            } else {
                VMSDialog.showErrorDialog(this, "Error", AppMessages.PHOTO_SAVE_ERROR, true);
                loader.setVisibility(View.GONE);
            }
        }
    }

    private void verifyPhoto() {
        VMSService.verifyPhoto(new VerifyPhotoRequest(VMSData.getInstance().getVisitorPhoto(), VMSData.getInstance().getNewPhoto()), new VMSService.Callback<Double>() {
            @Override
            public void onSuccess(Double data) {
                if (data == null)
                    data = (double) 0;
                DecimalFormat df = new DecimalFormat("##.#");
                String value = String.valueOf(df.format(data)) + "%";
                matchPercent.setText(value);
                mainContainer.setVisibility(View.VISIBLE);
                loader.setVisibility(View.GONE);
            }

            @Override
            public void onError(String errorMsg) {
                VMSDialog.showErrorDialog(VerifyPhoto.this, "Error", errorMsg, true);
                loader.setVisibility(View.GONE);
            }

            @Override
            public void onLoginError(String errorMsg) {
                Toast.makeText(VerifyPhoto.this, AppMessages.SERVICE_CALL_AUTH_ERROR, Toast.LENGTH_LONG).show();
                startActivity(new Intent(VerifyPhoto.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void approveVisitor() {
        loader.setVisibility(View.VISIBLE);
        VMSService.approveVisitor(new ApproveVisitorRequest(VMSData.getInstance().getVisitorProfile().getVisitorId(), VMSData.getInstance().getNewPhoto(), VMSData.getInstance().getUserProfile().getUserId()), new VMSService.Callback<ServiceResponse<Object>>() {
            @Override
            public void onSuccess(ServiceResponse<Object> data) {
                if (data.getStatus() == AppConstants.SERVICE_STATUS_SUCCESS) {
                    loader.setVisibility(View.GONE);
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(VerifyPhoto.this);
                    dialogBuilder.setTitle("Success");
                    dialogBuilder.setMessage(AppMessages.VISITOR_APPROVED);
                    dialogBuilder.setCancelable(true);
                    dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            startActivity(new Intent(VerifyPhoto.this, AdminDashboard.class));
                            finish();
                        }
                    });
                    dialogBuilder.create().show();
                } else {
                    loader.setVisibility(View.GONE);
                    VMSDialog.showErrorDialog(VerifyPhoto.this, "Error", AppMessages.UNEXPECTED_ERROR, false);
                }
            }

            @Override
            public void onError(String errorMsg) {
                loader.setVisibility(View.GONE);
                VMSDialog.showErrorDialog(VerifyPhoto.this, "Error", errorMsg, false);
            }

            @Override
            public void onLoginError(String errorMsg) {
                Toast.makeText(VerifyPhoto.this, AppMessages.SERVICE_CALL_AUTH_ERROR, Toast.LENGTH_LONG).show();
                startActivity(new Intent(VerifyPhoto.this, LoginActivity.class));
                finish();
            }
        });
    }
}
