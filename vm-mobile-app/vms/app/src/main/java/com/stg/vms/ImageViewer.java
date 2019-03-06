package com.stg.vms;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.ortiz.touchview.TouchImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ImageViewer extends AppCompatActivity {
    private TouchImageView image;
    private Target target;
    private View loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        ImageButton backButton = findViewById(R.id.imv_btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        loader = findViewById(R.id.imv_loader);
        image = findViewById(R.id.imv_image);
        image.setVisibility(View.GONE);
        loader.setVisibility(View.VISIBLE);
        // Image loader
        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                image.setImageBitmap(bitmap);
                image.setVisibility(View.VISIBLE);
                loader.setVisibility(View.GONE);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        Picasso.get().load("https://www.bittbox.com/wp-content/uploads/12-user-profile-material-design-app.jpg").into(target);
    }

    @Override
    public void onBackPressed() {
        Picasso.get().cancelRequest(target);
        super.onBackPressed();
    }
}
