package com.example.macbook.splash;


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

import com.example.macbook.splash.ViewModels.ParentRegistrationViewModel;


public class EmailLinkingSecondParentActivity extends AppCompatActivity{

    EditText etemail2parent;

    ParentRegistrationViewModel parentRegistrationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent i = getIntent();
        parentRegistrationViewModel = (ParentRegistrationViewModel) i.getSerializableExtra("person");

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_email_linking_second_parent);

        etemail2parent = (EditText)findViewById(R.id.etemail2parent);


        Typeface RegularRobotoFont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        Typeface MediumRobotoFont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Medium.ttf");
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        Button btnContinuer8thScreen = (Button)findViewById(R.id.btnContinuer8thScreen);
        btnContinuer8thScreen.setTypeface(MediumRobotoFont);



        TextView email2parent = (TextView)findViewById(R.id.email2parent);
        String text = "<font color=#888888>Afin de vivre l’expérience </font><font color=#3d0d28>eDonec</font><font color=#888888> en toute sécurité Veuillez enter l'</font><font color=#3d0d28>Email</font><font color=#888888> du </font><font color=#3d0d28>Parent</font><font color=#888888> qui a dèjà enregistré les enfants</font>";
        email2parent.setText(Html.fromHtml(text));
        email2parent.setTypeface(RegularRobotoFont);
    }



    public void LinkingEmailParent (View v){
        Intent intent = new Intent(this, ValidationActivity.class);
        parentRegistrationViewModel.setEmailLinking(etemail2parent.getText().toString());
        intent.putExtra("person",parentRegistrationViewModel);
        startActivity(intent);
    }

}
