package com.example.macbook.splash;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.macbook.splash.ViewModels.ChildRegistrationViewModel;
import com.example.macbook.splash.ViewModels.ParentRegistrationViewModel;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;

public class ValidatingChildrenInscriptionPhotoActivity extends AppCompatActivity {

    ParentRegistrationViewModel parentRegistrationViewModel;
    ChildRegistrationViewModel childRegistrationViewModel;
    int nbrIteration;
    private String photoURI;
    ImageView imageView;
    TextView t;
    Bitmap bmp;
    String compressedPhotoURIforSendeing;
    byte[] byteArray;
    private File photoFileCompressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent i = getIntent();

        nbrIteration = Integer.parseInt(i.getExtras().getString("nbrIteration"));
        parentRegistrationViewModel = (ParentRegistrationViewModel) i.getSerializableExtra("person");
        childRegistrationViewModel =(ChildRegistrationViewModel) i.getSerializableExtra("child");
        byteArray = i.getByteArrayExtra("photo");
        photoURI = i.getStringExtra("photoUriString");
        photoFileCompressed = new File(photoURI);
        Uri test = Uri.parse(photoURI);

        compressImage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validating_children_inscription_photo);
        imageView = (CircleImageView)findViewById(R.id.photochildreninscription);
        imageView.setImageURI(Uri.parse(photoURI));
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();



    }


    public void ValidationChildPhoto(View view) {
        Intent intent = new Intent(this, SelectingKindergardenSignUpActivity.class);

        //Send IMAGE And Receive her path as a String
        String path = compressedPhotoURIforSendeing;
        childRegistrationViewModel.setProfilePicture(path);

      //  parentRegistrationViewModel.getChildren().add(childRegistrationViewModel);

        intent.putExtra("nbrIteration",nbrIteration+"");
        intent.putExtra("child",childRegistrationViewModel);
        intent.putExtra("person",parentRegistrationViewModel);

        startActivity(intent);

    }

    public void NonValidationChildPhoto(View view) {
        Intent intent = new Intent(this, CameraInscriptionChildrenActivity.class);

        intent.putExtra("nbrIteration",nbrIteration+"");
        intent.putExtra("child",childRegistrationViewModel);
        intent.putExtra("person",parentRegistrationViewModel);

        startActivity(intent);
    }
    private void compressImage() {
        int gear;
        gear = Luban.THIRD_GEAR;
        compressSingleListener(gear);
    }

    private void compressSingleListener(int gear)  {

        Luban.compress(getApplicationContext(),photoFileCompressed)
                .putGear(gear)
                .launch(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }
                    @Override
                    public void onSuccess(File file) {
                        Log.i("TAG", file.getAbsolutePath());
                        compressedPhotoURIforSendeing = Uri.fromFile(file).getPath();
                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
    }


}
