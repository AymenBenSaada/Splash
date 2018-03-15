package com.example.macbook.splash;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.macbook.splash.ViewModels.ChildRegistrationViewModel;
import com.example.macbook.splash.ViewModels.ParentRegistrationViewModel;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraUtils;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.Gesture;
import com.otaliastudios.cameraview.GestureAction;

import java.io.ByteArrayOutputStream;

public class CameraInscriptionChildrenActivity extends AppCompatActivity {

    ParentRegistrationViewModel parentRegistrationViewModel;
    ChildRegistrationViewModel childRegistrationViewModel;
    int nbrIteration;


    CameraView camera;
    public Bitmap bmp1;
    byte[] bmp1Array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent i = getIntent();
        nbrIteration = Integer.parseInt(i.getExtras().getString("nbrIteration"));
        parentRegistrationViewModel = (ParentRegistrationViewModel) i.getSerializableExtra("person");
        childRegistrationViewModel =(ChildRegistrationViewModel) i.getSerializableExtra("child");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_inscription_children);
        camera = (CameraView)findViewById(R.id.camera);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        camera.mapGesture(Gesture.PINCH, GestureAction.ZOOM); // Pinch to zoom!
        camera.mapGesture(Gesture.TAP, GestureAction.FOCUS_WITH_MARKER); // Tap to focus!
        camera.mapGesture(Gesture.SCROLL_VERTICAL,GestureAction.EXPOSURE_CORRECTION);




        camera.addCameraListener(new CameraListener() {
            public void onPictureTaken(byte[] picture) {
                // Create a bitmap or a file...
                CameraUtils.BitmapCallback photo = new CameraUtils.BitmapCallback() {
                    @Override
                    public void onBitmapReady(Bitmap bitmap) {
                        bmp1 = Bitmap.createScaledBitmap(bitmap, 480, 325, true);
                      //  bmp1=bitmap;
                        Log.e("DEBUG",""+bmp1.getByteCount());

                        int dimension = getSquareCropDimensionForBitmap(bmp1);
                        bmp1 = ThumbnailUtils.extractThumbnail(bmp1, dimension, dimension);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp1.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                        bmp1Array = stream.toByteArray();
                        transition();
                    }
                };
                // CameraUtils will read EXIF orientation for you, in a worker thread.
                CameraUtils.decodeBitmap(picture,photo);
            }
        });
    }


    //I added this method because people keep asking how
    //to calculate the dimensions of the bitmap...see comments below

    public int getSquareCropDimensionForBitmap(Bitmap bitmap)
    {
        //use the smallest dimension of the image to crop to
        return Math.min(bitmap.getWidth(), bitmap.getHeight());
    }

    @Override
    protected void onResume() {
        super.onResume();
        camera.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        camera.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        camera.destroy();
    }

    public void capturePhoto(View view) {
        camera.capturePicture();
    }

    public void transition(){
        Intent intent = new Intent(this, ValidatingChildrenInscriptionPhotoActivity.class);

        intent.putExtra("photo",bmp1Array);
        intent.putExtra("child",childRegistrationViewModel);
        intent.putExtra("person",parentRegistrationViewModel);
        intent.putExtra("nbrIteration",nbrIteration+"");

        startActivity(intent);
    }
}
