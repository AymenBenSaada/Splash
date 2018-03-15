package com.example.macbook.splash;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.macbook.splash.ViewModels.ChildRegistrationViewModel;
import com.example.macbook.splash.ViewModels.ParentRegistrationViewModel;

public class ValidatingChildrenInscriptionPhotoActivity extends AppCompatActivity {

    ParentRegistrationViewModel parentRegistrationViewModel;
    ChildRegistrationViewModel childRegistrationViewModel;
    int nbrIteration;

    ImageView image;
    TextView t;
    Bitmap bmp;
    byte[] byteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent i = getIntent();
        nbrIteration = Integer.parseInt(i.getExtras().getString("nbrIteration"));
        parentRegistrationViewModel = (ParentRegistrationViewModel) i.getSerializableExtra("person");
        childRegistrationViewModel =(ChildRegistrationViewModel) i.getSerializableExtra("child");
        byteArray = i.getByteArrayExtra("photo");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validating_children_inscription_photo);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        image = (ImageView)findViewById(R.id.photochildreninscription);
        image.setImageBitmap(bmp);

    }


    public void ValidationChildPhoto(View view) {
        Intent intent = new Intent(this, SelectingKindergardenSignUpActivity.class);

        //Send IMAGE And Receive her path as a String
        String path = "child"+nbrIteration+".png";
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


}
