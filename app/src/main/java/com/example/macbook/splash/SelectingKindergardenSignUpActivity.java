package com.example.macbook.splash;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macbook.splash.Adapters.KindergardenAdapter;
import com.example.macbook.splash.ViewModels.ChildRegistrationViewModel;
import com.example.macbook.splash.ViewModels.ParentRegistrationViewModel;
import com.example.macbook.splash.ViewModels.Person;
import com.example.macbook.splash.ViewModels.TeacherRegistrationViewModel;

import java.util.ArrayList;

public class SelectingKindergardenSignUpActivity extends AppCompatActivity {

    Person person;
    ChildRegistrationViewModel childRegistrationViewModel;
    int nbrIteration;
    String text;


    // LIST VIEW Parameters
    int[] IMAGES = {R.drawable.aymen, R.drawable.miras, R.drawable.zac, R.drawable.nader, R.drawable.med, R.drawable.sarra};
    String[] NAMES = {"Aymen Gharbi", "Miras Ayed", "Zac Hatira", "Nader Zouaoui", "Boussa MedLamine", "Jaziri Sarra"};
    String[] USERNAME = {"El Gen", "Mo9ded", "Zak", "Nounou", "Hayssam", "Sorsor" };
    String[] ADRESS = {"Ennasr", "Sousse", "Sfax", "Tunis", "Gafsa", "Bizerte" };
    KindergardenAdapter kindergardenAdapter;
    EditText etResearchKindergarden;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent i = getIntent();
        person =(Person) i.getSerializableExtra("person");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecting_kindergarden_sign_up);

        //LE MESSAGE A AFFICHER
        if (person instanceof ParentRegistrationViewModel){
            text = "<font color=#888888>Dans quel </font><font color=#3d0d28>établissement </font><font color=#888888>certifié </font><font color=#3d0d28>eDonec </font><font color=#888888>votre </font> <font color=#3d0d28>enfant </font><font color=#888888>est </font><font color=#3d0d28>inscrit</font><font color=#888888>?</font>";
        }else{
            text = "<font color=#888888>Dans quel </font><font color=#3d0d28>établissement </font><font color=#888888>certifié </font><font color=#3d0d28>eDonec </font><font color=#888888>vous </font> <font color=#3d0d28>travaillez </font><font color=#888888>?</font>";
        }

        etResearchKindergarden = (EditText)findViewById(R.id.etResearchKindergarden);

        ListView listViewKindergarden = (ListView) findViewById(R.id.listviewKindergarden);
        kindergardenAdapter = new KindergardenAdapter(IMAGES,NAMES,USERNAME,ADRESS,this,getLayoutInflater());
        listViewKindergarden.setAdapter(kindergardenAdapter);
        listViewKindergarden.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Choice(i);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Typeface RegularRobotoFont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");

        TextView txtEtablissement = (TextView)findViewById(R.id.txtEtablissement);

        txtEtablissement.setText(Html.fromHtml(text));
        txtEtablissement.setTypeface(RegularRobotoFont);

    }


    public void Choice(int c){
        Intent i = getIntent();
        if (person instanceof ParentRegistrationViewModel){
            ParentRegistrationViewModel parentRegistrationViewModel = (ParentRegistrationViewModel) person;
            nbrIteration = Integer.parseInt(i.getExtras().getString("nbrIteration"));
            childRegistrationViewModel =(ChildRegistrationViewModel) i.getSerializableExtra("child");
            childRegistrationViewModel.setKindergardenID(c);

            //INITIALIZING CHILDREN LIST IN THE PARENT OBJECT
            if(nbrIteration == 1){
                parentRegistrationViewModel.setChildren(new ArrayList<ChildRegistrationViewModel>());
            }

            parentRegistrationViewModel.addChild(parentRegistrationViewModel.getChildren(),childRegistrationViewModel);


            if(nbrIteration==parentRegistrationViewModel.getChildrenCount()){
                i = new Intent(this,ValidationActivity.class);
            }

            else{
                i = new Intent(this,FirstScreenSignUpChild_No_Activity.class);
                i.putExtra("nbrIteration",(nbrIteration+1)+"");
            }

            i.putExtra("person", parentRegistrationViewModel);
            startActivity(i);
        }

        else{//SI LA REQUETE VIENT D'UN TEACHER
            TeacherRegistrationViewModel teacherRegistrationViewModel = (TeacherRegistrationViewModel)person;
            teacherRegistrationViewModel.setKindergardenID(c);
            i = new Intent(this,ValidationActivity.class);
            i.putExtra("person", teacherRegistrationViewModel);
            startActivity(i);
        }

    }



    public void ResearchKindergardenButtonClick(View view){
        Toast.makeText(this, "l'Algorithme", Toast.LENGTH_SHORT).show();
        String s = etResearchKindergarden.getText().toString();
        //REQUEST POST RESERACH (s)
        //onResponse :
        //nbadel e liste mta3 el kindergardens eli dekhla fel adapter
        //w nexecuti kindergardernAdapter.update
    }



}
