package com.example.macbook.splash.Registration;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.macbook.splash.Enum.Gender;
import com.example.macbook.splash.R;
import com.example.macbook.splash.ViewModels.Person;

public class ThirdScreenSignUpActivity extends Activity {

    Person person;
    String account;
    Spinner sexSpinner;
    EditText nom;
    EditText prenom;
    String[] Sex = {"Femelle", "Male"};
    Gender gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent i = getIntent();
        account = i.getExtras().getString("account");
        person = (Person) i.getSerializableExtra("person");

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_third_screen_sign_up);
        Typeface RegularRobotoFont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        Typeface MediumRobotoFont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Medium.ttf");


        Button btnContinuer3rdScreen = (Button)findViewById(R.id.btnContinuer3rdScreen);
        btnContinuer3rdScreen.setTypeface(MediumRobotoFont);


        TextView parentOuEnseignant = (TextView)findViewById(R.id.nomEtPrenom);
        String text = "<font color=#888888>Quel est votre </font> <font color=#3d0d28>Nom </font><font color=#888888>et </font><font color=#3d0d28>Prénom</font><font color=#888888>?</font>";
        parentOuEnseignant.setText(Html.fromHtml(text));
        parentOuEnseignant.setTypeface(RegularRobotoFont);

        //SPINNER
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,Sex);
        sexSpinner = (Spinner) findViewById(R.id.sexSpinner);
        sexSpinner.setAdapter(arrayAdapter);
        nom = findViewById(R.id.etNom);
        prenom = findViewById(R.id.etPrenom);
    }
    public void FourthScreenSignUp (View v){
        Intent intent = new Intent(this, FourthScreenSignUpActivity.class);
        if(sexSpinner.getSelectedItem().toString().equals("Femelle")){
            gender = Gender.Femelle;
        }else{
            gender = Gender.Male;
        }
        if(prenom.getText().toString().isEmpty())
        {

                prenom.setError("Veuillez insérer votre prénom");
                prenom.requestFocus();
                return;

        }
        if(nom.getText().toString().isEmpty())
        {

            nom.setError("Veuillez insérer votre nom");
            nom.requestFocus();
            return;

        }
        person.setGender(gender);
        person.setName(prenom.getText().toString());
        person.setLastName(prenom.getText().toString());
        intent.putExtra("person",person);
        intent.putExtra("account",account);
        startActivity(intent);
    }
}
