package com.example.macbook.splash;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.macbook.splash.ViewModels.ChildRegistrationViewModel;
import com.example.macbook.splash.ViewModels.ParentRegistrationViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class SecondScreenSignUpChild_No_Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ParentRegistrationViewModel parentRegistrationViewModel;
    ChildRegistrationViewModel childRegistrationViewModel;
    int nbrIteration;

    EditText etDateDeNaissanceEnfant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent i = getIntent();
        nbrIteration = Integer.parseInt(i.getExtras().getString("nbrIteration"));
        parentRegistrationViewModel = (ParentRegistrationViewModel) i.getSerializableExtra("person");
        childRegistrationViewModel =(ChildRegistrationViewModel) i.getSerializableExtra("child");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen_sign_up_child__no_);

        Typeface RegularRobotoFont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        Typeface MediumRobotoFont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Medium.ttf");
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Button btnContinuerSecondScreenSignUpChildNo = (Button)findViewById(R.id.btnContinuerSecondScreenSignUpChildNo);
        btnContinuerSecondScreenSignUpChildNo.setTypeface(MediumRobotoFont);

        etDateDeNaissanceEnfant = (EditText)findViewById(R.id.etDateDeNaissanceEnfant);

        TextView dateDeNaissanceEnfant = (TextView)findViewById(R.id.dateDeNaissanceEnfant);
        String text = "<font color=#888888>Date de naissance de votre</font><font color=#3d0d28> enfant</font><font color=#888888> numéro </font><font color=#3d0d28>"+nbrIteration+"</font><font color=#888888>:</font>";
        dateDeNaissanceEnfant.setText(Html.fromHtml(text));
        dateDeNaissanceEnfant.setTypeface(RegularRobotoFont);
    }



    public void ThirdScreenSignUpChild_No_Activity (View view){


        String dateString = etDateDeNaissanceEnfant.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            etDateDeNaissanceEnfant.setError("Veuillez insérer une date valide (JJ/MM/AAAA)");
            etDateDeNaissanceEnfant.requestFocus();
            e.printStackTrace();
        }
        childRegistrationViewModel.setBirthday(dateString);
       // parentRegistrationViewModel.addChild(parentRegistrationViewModel.getChildren(),childRegistrationViewModel);

        Intent intent = new Intent (this, CameraInscriptionChildrenActivity.class);
        intent.putExtra("child",childRegistrationViewModel);
        intent.putExtra("person",parentRegistrationViewModel);
        intent.putExtra("nbrIteration",nbrIteration+"");
        startActivity(intent);
    }




    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,i);
        c.set(Calendar.MONTH,i1);
        c.set(Calendar.DAY_OF_MONTH,i2);
        String CurrentDateString = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE).format(c.getTime());
        etDateDeNaissanceEnfant.setText(CurrentDateString);
    }

    public void onClickImageBirthday(View v) {
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }

}
