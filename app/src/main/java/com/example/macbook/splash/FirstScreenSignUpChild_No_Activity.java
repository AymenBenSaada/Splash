package com.example.macbook.splash;

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
import com.example.macbook.splash.ViewModels.ChildRegistrationViewModel;
import com.example.macbook.splash.ViewModels.ParentRegistrationViewModel;

public class FirstScreenSignUpChild_No_Activity extends Activity {

    ParentRegistrationViewModel parentRegistrationViewModel;
    int nbrIteration;

    Spinner sexSpinnerEnfant;
    EditText nomEnfant;
    EditText prenomEnfant;
    String[] Sex = {"Femelle", "Male"};
    Gender gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent i = getIntent();
        nbrIteration = Integer.parseInt(i.getExtras().getString("nbrIteration"));
        parentRegistrationViewModel = (ParentRegistrationViewModel) i.getSerializableExtra("person");


        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_first_screen_sign_up_child__no_);

        Typeface RegularRobotoFont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        Typeface MediumRobotoFont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Medium.ttf");


        Button btnContinuerSignUpChildScreen = (Button)findViewById(R.id.btnContinuerSignUpChildScreen);
        btnContinuerSignUpChildScreen.setTypeface(MediumRobotoFont);


        TextView nomEtPrenomEnfant = (TextView)findViewById(R.id.nomEtPrenomEnfant);
        String text = "<font color=#888888>Quel est le </font> <font color=#3d0d28>Nom </font><font color=#888888>et </font><font color=#3d0d28>Prénom</font><font color=#888888> de votre </font><font color=#3d0d28> enfant</font><font color=#888888> numéro </font><font color=#3d0d28>"+nbrIteration+"</font><font color=#888888>:</font>";
        nomEtPrenomEnfant.setText(Html.fromHtml(text));
        nomEtPrenomEnfant.setTypeface(RegularRobotoFont);

        //SPINNER
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,Sex);
        sexSpinnerEnfant = (Spinner) findViewById(R.id.sexSpinnerEnfant);
        sexSpinnerEnfant.setAdapter(arrayAdapter);

        nomEnfant = findViewById(R.id.etNomEnfant);
        prenomEnfant = findViewById(R.id.etPrenomEnfant);
    }

    public void SecondScreenSignUpChild_No_Activity (View view){
        Intent intent = new Intent(this, SecondScreenSignUpChild_No_Activity.class);

        if(sexSpinnerEnfant.getSelectedItem().toString().equals("Femelle"))
        {
            gender = Gender.Female;
        }
        else
        {
            gender = Gender.Male;
        }
        if(prenomEnfant.getText().toString().isEmpty())
        {

                prenomEnfant.setError("Veuillez insérer le prénom de votre enfant");
                prenomEnfant.requestFocus();
                return;

        }
        if(nomEnfant.getText().toString().isEmpty())
        {

            nomEnfant.setError("Veuillez insérer le nom de votre enfant");
            nomEnfant.requestFocus();
            return;

        }
        ChildRegistrationViewModel childRegistrationViewModel = new ChildRegistrationViewModel();

        childRegistrationViewModel.setName(prenomEnfant.getText().toString());
        childRegistrationViewModel.setLastName(nomEnfant.getText().toString());
        childRegistrationViewModel.setGender(gender);

        intent.putExtra("child",childRegistrationViewModel);
        intent.putExtra("person",parentRegistrationViewModel);
        intent.putExtra("nbrIteration",nbrIteration+"");

        startActivity(intent);
    }
}
