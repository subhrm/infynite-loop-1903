package com.stg.vms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;

import com.ortiz.touchview.TouchImageView;
import com.stg.vms.data.AppConstants;
import com.stg.vms.data.VMSData;
import com.stg.vms.util.ImageUtil;

public class ImageViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String request = intent.getStringExtra(AppConstants.VIEW_IMAGE_REQUEST_KEY);
        if (TextUtils.isEmpty(request)) {
            onBackPressed();
            finish();
            return;
        }
        setContentView(R.layout.activity_image_viewer);
        ImageButton backButton = findViewById(R.id.imv_btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        View loader = findViewById(R.id.imv_loader);
        TouchImageView image = findViewById(R.id.imv_image);
        image.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);
        // Image loader
        if (request.equalsIgnoreCase(AppConstants.VIEW_IMAGE_REQUEST_EXISTING)) {
            image.setImageBitmap(ImageUtil.base642Bitmap(VMSData.getInstance().getVisitorPhoto()));
        } else if (request.equalsIgnoreCase(AppConstants.VIEW_IMAGE_REQUEST_NEW)) {
            image.setImageBitmap(ImageUtil.base642Bitmap(VMSData.getInstance().getNewPhoto()));
        } else {
            onBackPressed();
            finish();
            return;
        }
        image.setVisibility(View.VISIBLE);
        loader.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
