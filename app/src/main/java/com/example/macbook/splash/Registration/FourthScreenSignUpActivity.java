package com.example.macbook.splash.Registration;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.macbook.splash.DatePickerFragment;
import com.example.macbook.splash.R;
import com.example.macbook.splash.Registration.FifthScreenSignUpActivity;
import com.example.macbook.splash.ViewModels.Person;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FourthScreenSignUpActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private FragmentManager supportFragmentManager;
    Person person;
    String account;
    EditText anneeDiplome;
    EditText dateDeNaissance;
    TextView tvDiplome;
    TextView diplomeBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent i = getIntent();
        account = i.getExtras().getString("account");
        person = (Person) i.getSerializableExtra("person");

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fourth_screen_sign_up);

        anneeDiplome = (EditText)findViewById(R.id.anneeDiplome);
        diplomeBar = (TextView)findViewById(R.id.diplomeBar);
        tvDiplome = (TextView)findViewById(R.id.tvDiplome);

        if (account.equals("parent")){
            anneeDiplome.setVisibility(View.INVISIBLE);
            tvDiplome.setVisibility(View.INVISIBLE);
            diplomeBar.setVisibility(View.INVISIBLE);
        }

        Typeface RegularRobotoFont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        Typeface MediumRobotoFont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Medium.ttf");
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Button btnContinuer4rdScreen = (Button)findViewById(R.id.btnContinuer4rdScreen);
        btnContinuer4rdScreen.setTypeface(MediumRobotoFont);

        dateDeNaissance = (EditText)findViewById(R.id.etAnnee);

        TextView dates = (TextView)findViewById(R.id.dates);
        String text = "<font color=#888888>Quelques </font> <font color=#3d0d28>Dates</font><font color=#888888>:</font>";
        dates.setText(Html.fromHtml(text));
        dates.setTypeface(RegularRobotoFont);
    }



    public void FifthScreenSignUp (View v){
        Intent intent = new Intent(this, FifthScreenSignUpActivity.class);
        String dateString = dateDeNaissance.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        person.setBirth(convertedDate);

        intent.putExtra("person",person);
        intent.putExtra("account",account);
        intent.putExtra("anneeDiplome", anneeDiplome.getText().toString());
        startActivity(intent);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,i);
        c.set(Calendar.MONTH,i1);
        c.set(Calendar.DAY_OF_MONTH,i2);
        String CurrentDateString = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE).format(c.getTime());
        dateDeNaissance.setText(CurrentDateString);
    }

    public void onClickImageBirthday(View v) {
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }

}
