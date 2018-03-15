package com.example.macbook.splash.Registration;


import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.macbook.splash.R;
import com.example.macbook.splash.ViewModels.ParentRegistrationViewModel;


public class SixthScreenSignUpParentActivity extends AppCompatActivity{

    ParentRegistrationViewModel parentRegistrationViewModel;
    EditText etnbrEnfants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent i = getIntent();
        parentRegistrationViewModel = (ParentRegistrationViewModel) i.getSerializableExtra("person");

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sixth_screen_sign_up_parent);

        Typeface RegularRobotoFont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        Typeface MediumRobotoFont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Medium.ttf");

        TextView enfantsInscrits = (TextView)findViewById(R.id.enfantsInscrits);
        String text = "<font color=#888888>Combien avez-vous d'</font><font color=#3d0d28>Enfants</font> inscrits dans des <font color=#3d0d28>établissements</font> certifiés <font color=#3d0d28>eDonec</font><font color=#888888>?</font>";
        enfantsInscrits.setText(Html.fromHtml(text));
        enfantsInscrits.setTypeface(RegularRobotoFont);

        etnbrEnfants = (EditText)findViewById(R.id.etnbrEnfants);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        Button btnContinuer6thScreen = (Button)findViewById(R.id.btnContinuer6thScreen);
        btnContinuer6thScreen.setTypeface(MediumRobotoFont);
    }



    public void SeventhScreenSignUpParent (View v){
        Intent intent = new Intent(this, SeventhScreenSignUpParentActivity.class);
        parentRegistrationViewModel.setChildrenCount(Integer.parseInt(etnbrEnfants.getText().toString()));
        intent.putExtra("person",parentRegistrationViewModel);
        startActivity(intent);
    }

}
