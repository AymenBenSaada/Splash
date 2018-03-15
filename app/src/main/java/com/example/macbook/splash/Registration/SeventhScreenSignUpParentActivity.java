package com.example.macbook.splash.Registration;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.macbook.splash.EmailLinkingSecondParentActivity;
import com.example.macbook.splash.FirstScreenSignUpChild_No_Activity;
import com.example.macbook.splash.R;
import com.example.macbook.splash.ViewModels.ParentRegistrationViewModel;

public class SeventhScreenSignUpParentActivity extends Activity {

    ParentRegistrationViewModel parentRegistrationViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent i = getIntent();
        parentRegistrationViewModel = (ParentRegistrationViewModel) i.getSerializableExtra("person");

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_seventh_screen_sign_up_parent);
        Typeface RegularRobotoFont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");

        TextView compteEnfantsInscrits = (TextView)findViewById(R.id.compteEnfantsInscrits);
        String text = "<font color=#888888>Leurs avez-vous dèjà crée des </font> <font color=#3d0d28>compte</font> <font color=#888888> sur </font> <font color=#3d0d28>eDonec </font><font color=#888888>?</font>";
        compteEnfantsInscrits.setText(Html.fromHtml(text));
        compteEnfantsInscrits.setTypeface(RegularRobotoFont);

    }
    public void OuiSeventhScreenSignUp (View v){
        Intent intent = new Intent(this, EmailLinkingSecondParentActivity.class);
        intent.putExtra("person",parentRegistrationViewModel);
        startActivity(intent);
    }
    public void NonSeventhScreenSignUpParent (View v){
        Intent intent = new Intent(this, FirstScreenSignUpChild_No_Activity.class);
        intent.putExtra("person",parentRegistrationViewModel);
        intent.putExtra("nbrIteration","1");
        startActivity(intent);
    }
}
