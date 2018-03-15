package com.example.macbook.splash;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.macbook.splash.ViewModels.ChildRegistrationViewModel;
import com.example.macbook.splash.ViewModels.ParentRegistrationViewModel;
import com.example.macbook.splash.ViewModels.Person;
import com.example.macbook.splash.ViewModels.TeacherRegistrationViewModel;

public class ValidationActivity extends AppCompatActivity {

    Person person;

    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Intent i = getIntent();
        person = (Person) i.getSerializableExtra("person");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);
        Button btnValidateInscription = findViewById(R.id.btnValidateInscription);
        Typeface RegularRobotoFont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        Typeface MediumRobotoFont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Medium.ttf");

        btnValidateInscription.setTypeface(MediumRobotoFont);



        TextView tvValider = (TextView)findViewById(R.id.tvValider);
        TextView test = (TextView)findViewById(R.id.test);

        if(person instanceof ParentRegistrationViewModel){

            if(((ParentRegistrationViewModel) person).getEmailLinking() == null){
                //PARENT WITH CHILDREN
                String s = "";
                String children = "";
                test.setText(person.toString());
                for (ChildRegistrationViewModel child:((ParentRegistrationViewModel) person).getChildren()
                     ) {
                    s = "Rawdha n°"+child.getKindergardenID()+", "+s;
                    children+= children+child.toString();
                }
                test.setText(person.toString()+"  Children "+children);
                text = "<font color=#888888>Un email de confirmation vous a été envoyé ainsi qu'au </font> <font color=#3d0d28>"+s+"</font> <font color=#888888> pour finaliser votre </font> <font color=#3d0d28>inscription</font>";
            }
            else {//PARENT WITHOUT CHILDREN
                text = "<font color=#888888>Un email de confirmation vous a été envoyé ainsi qu'au </font> <font color=#3d0d28>"+((ParentRegistrationViewModel) person).getEmailLinking()+"</font> <font color=#888888> pour finaliser votre </font> <font color=#3d0d28>inscription</font>";
                test.setText(person.toString());
            }

        }else {//TEACHER
            text = "<font color=#888888>Un email de confirmation vous a été envoyé ainsi qu'au </font> <font color=#3d0d28> Rawdha n°"+((TeacherRegistrationViewModel) person).getKindergardenID()+"</font> <font color=#888888> pour finaliser votre </font> <font color=#3d0d28>inscription</font>";
            test.setText(person.toString());
        }


        tvValider.setText(Html.fromHtml(text));
        tvValider.setTypeface(RegularRobotoFont);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

    }
    public void validate (View v){
        setContentView(R.layout.ok);
        ActionBar actionBar = getSupportActionBar();
        //ENVOIE VERS LA BACK-END

        actionBar.hide();
    }
}
