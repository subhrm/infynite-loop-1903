package com.stg.vms;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stg.vms.data.AppMessages;
import com.stg.vms.util.ImageUtil;
import com.stg.vms.util.VMSDialog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class VerifyPhoto extends AppCompatActivity {
    private static final int REQUEST_TAKE_PHOTO = 1, MY_CAMERA_REQUEST_CODE = 100;

    String currentPhotoPath;
    ImageView newPhoto, existingPhoto;
    View loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_photo);
        existingPhoto = findViewById(R.id.vp_uploadedImage);
        newPhoto = findViewById(R.id.vp_newImage);
        loader = findViewById(R.id.vp_loader);
        loader.setVisibility(View.VISIBLE);
        Picasso.get().load("https://scontent.fhyd1-2.fna.fbcdn.net/v/t1.0-0/p160x160/10924753_1061170647241868_1753486472586550907_n.jpg?_nc_cat=105&_nc_ht=scontent.fhyd1-2.fna&oh=e7253dfa0a03a1a8ef2c984ebbdea248&oe=5CDE02B3").placeholder(R.drawable.ic_photo).into(existingPhoto);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        else
            dispatchTakePictureIntent();
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "VISITOR_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",    /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e(this.getClass().getSimpleName(), "Error while creating image file.", ex);
                VMSDialog.showErrorDialog(this, "Error", "Error while creating image file.", true);
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.stg.vms.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                loader.setVisibility(View.VISIBLE);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap imageBitmap = ImageUtil.processPhoto(currentPhotoPath);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                newPhoto.setImageBitmap(imageBitmap);
                                loader.setVisibility(View.GONE);
                            }
                        });
                    }
                });
                thread.start();
                //saveBase64Data(ImageUtil.bitmap2Base64(imageBitmap));
            } else {
                Toast.makeText(this, AppMessages.PHOTO_SAVE_ERROR, Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        }
    }
    /*private void saveBase64Data(String data) {
        try {
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File file = new File(storageDir, "base64.txt");
            FileOutputStream stream = new FileOutputStream(file);
            try {
                stream.write(data.getBytes());
            } finally {
                stream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
