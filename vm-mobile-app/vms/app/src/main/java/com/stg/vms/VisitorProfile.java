package com.stg.vms;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.rohitarya.picasso.facedetection.transformation.FaceCenterCrop;
import com.rohitarya.picasso.facedetection.transformation.core.PicassoFaceDetector;
import com.squareup.picasso.Picasso;
import com.stg.vms.data.VMSData;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VisitorProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor_profile);
        ImageView visitorImage = findViewById(R.id.prof_photo);
        PicassoFaceDetector.initialize(this);
        Picasso.get().load("https://scontent.fhyd1-2.fna.fbcdn.net/v/t1.0-0/p160x160/10924753_1061170647241868_1753486472586550907_n.jpg?_nc_cat=105&_nc_ht=scontent.fhyd1-2.fna&oh=e7253dfa0a03a1a8ef2c984ebbdea248&oe=5CDE02B3")
                .placeholder(R.drawable.ic_person).fit().centerInside().transform(new FaceCenterCrop(300, 300)).into(visitorImage);

        visitorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VisitorProfile.this, ImageViewer.class));
            }
        });
        ImageButton homeButton = findViewById(R.id.prof_btn_home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Button verifyPhotoButton = findViewById(R.id.prof_btn_verifyPhoto);
        verifyPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VisitorProfile.this, VerifyPhoto.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PicassoFaceDetector.releaseDetector();
    }
}
