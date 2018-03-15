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

import com.example.macbook.splash.Enum.Status;
import com.example.macbook.splash.R;
import com.example.macbook.splash.SelectingKindergardenSignUpActivity;
import com.example.macbook.splash.ViewModels.ParentRegistrationViewModel;
import com.example.macbook.splash.ViewModels.Person;
import com.example.macbook.splash.ViewModels.TeacherRegistrationViewModel;

public class FifthScreenSignUpActivity extends Activity {

    Person person;
    String account,anneeDiplome;
    Status status;
    Spinner etatCivileSpinner;
    EditText adresse;
    EditText tel;

    String[] etatCivlie = {"Marié(e)", "Divorcé(e)", "Veuf(ve)", "Célibataire(e)"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent i = getIntent();
        account = i.getExtras().getString("account");
        anneeDiplome = i.getExtras().getString("anneeDiplome");
        person = (Person) i.getSerializableExtra("person");

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fifth_screen_sign_up);
        Typeface RegularRobotoFont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        Typeface MediumRobotoFont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Medium.ttf");


        Button btnContinuer5thScreen = (Button)findViewById(R.id.btnContinuer5thScreen);
        btnContinuer5thScreen.setTypeface(MediumRobotoFont);


        TextView infosupp = (TextView)findViewById(R.id.infosupp);
        String text = "<font color=#888888>Quelques </font> <font color=#3d0d28>Informations </font><font color=#888888>supplémentaires:</font>";
        infosupp.setText(Html.fromHtml(text));
        infosupp.setTypeface(RegularRobotoFont);

        //SPINNER
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,etatCivlie);
        etatCivileSpinner = (Spinner) findViewById(R.id.etatCivilSpinner);
        etatCivileSpinner.setAdapter(arrayAdapter);
        adresse = findViewById(R.id.etAdresse);
        tel = findViewById(R.id.etTel);
    }

    public void SixthScreenSignUp (View v){
        Intent intent;

        switch (etatCivileSpinner.getSelectedItem().toString()){
            case "Célibataire(e)":
                status=Status.Celibataire;
                break;
            case "Marié(e)":
                status=Status.Marie;
                break;
            case "Veuf(ve)":
                status=Status.Veuf;
                break;
            default:
                status=Status.Divorce;
        }

        person.setStatus(status);
        person.setAdress(adresse.getText().toString());
        person.setPhone(Integer.parseInt(tel.getText().toString()));

        if(account.equals("parent")) {

        intent = new Intent(this, SixthScreenSignUpParentActivity.class);
        ParentRegistrationViewModel parentRegistrationViewModel = (ParentRegistrationViewModel)person.ConvertToParent();
            intent.putExtra("person",parentRegistrationViewModel);
            intent.putExtra("account",account);
            startActivity(intent);

        }else{

        intent = new Intent(this, SelectingKindergardenSignUpActivity.class);
        TeacherRegistrationViewModel teacherRegistrationViewModel = (TeacherRegistrationViewModel)person.ConvertToTeacher();
        teacherRegistrationViewModel.setGraduationYear(Integer.parseInt(anneeDiplome));

        intent.putExtra("person",teacherRegistrationViewModel);
        intent.putExtra("account",account);
        startActivity(intent);
        }
    }
}
