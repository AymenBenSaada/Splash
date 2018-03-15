package com.example.macbook.splash.Registration;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.macbook.splash.R;
import com.example.macbook.splash.ViewModels.Person;

public class SecondScreenSignUpActivity extends Activity {

    String email;
    String mdp;
    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Bundle extras = getIntent().getExtras();
        Intent i = getIntent();
        email = i.getExtras().getString("email");
        mdp = i.getExtras().getString("mdp");

        person = new Person();
        person.setEmail(email);
        person.setPassword(mdp);

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_second_screen_sign_up);
        Typeface RegularRobotoFont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");

        TextView parentOuEnseignant = (TextView)findViewById(R.id.parentOuEnseignant);
        String text = "<font color=#888888>Alors, vous êtes un </font> <font color=#3d0d28>Parent</font> <font color=#888888> ou un </font> <font color=#3d0d28>Enseignant </font><font color=#888888>?</font>";
        parentOuEnseignant.setText(Html.fromHtml(text));
        parentOuEnseignant.setTypeface(RegularRobotoFont);




    }
    public void ParentThirdScreenSignUp (View v){
        Intent intent = new Intent(this, ThirdScreenSignUpActivity.class);
        intent.putExtra("person",person);
        intent.putExtra("account","parent");
        startActivity(intent);
    }
    public void EnseignantThirdScreenSignUp (View v){
        Intent intent = new Intent(this, ThirdScreenSignUpActivity.class);
        intent.putExtra("person",person);
        intent.putExtra("account","teacher");
        startActivity(intent);
    }


}
