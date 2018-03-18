package com.example.macbook.splash;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.macbook.splash.Adapters.KindergardenAdapter;
import com.example.macbook.splash.Admin.AdminMainActivity;
import com.example.macbook.splash.Interfaces.ApiClient;
import com.example.macbook.splash.Interfaces.IKindergartensApi;
import com.example.macbook.splash.Models.Kindergarten;
import com.example.macbook.splash.ViewModels.ChildRegistrationViewModel;
import com.example.macbook.splash.ViewModels.ParentRegistrationViewModel;
import com.example.macbook.splash.ViewModels.Person;
import com.example.macbook.splash.ViewModels.TeacherRegistrationViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class SelectingKindergardenSignUpActivity extends AppCompatActivity {

    Person person;
    ChildRegistrationViewModel childRegistrationViewModel;
    int nbrIteration;
    String text;


    // LIST VIEW Parameters**

    List<String> names = new ArrayList<String>();
    List<String> images = new ArrayList<String>();
    List<String> address = new ArrayList<String>();
    List<String> phone = new ArrayList<String>();

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
        // Toast.makeText(this, "l'Algorithme", Toast.LENGTH_SHORT).show();
        // String s = etResearchKindergarden.getText().toString();
        //test();
        TestSearch();
        //REQUEST POST RESERACH (s)
        //onResponse :
        //nbadel e liste mta3 el kindergardens eli dekhla fel adapter
        //w nexecuti kindergardernAdapter.update
    }

    public void test( ){
        IKindergartensApi apiService = ApiClient.getClient().create(IKindergartensApi.class);
        int i =1;
        apiService.getKindergarten(i).enqueue(new Callback<Kindergarten>() {
            @Override
            public void onResponse(Call<Kindergarten> call, retrofit2.Response<Kindergarten> response) {



                ListView listViewKindergarden = (ListView) findViewById(R.id.listviewKindergarden);

                System.out.println(response.body().getEmail());
                names.add(response.body().getName());
                images.add("https://www.istockphoto.com/resources/images/PhotoFTLP/img_63351521.jpg");
                address.add(response.body().getAddress());
                phone.add(String.valueOf(response.body().getPhone()));
                kindergardenAdapter = new KindergardenAdapter(names,images,address,phone,SelectingKindergardenSignUpActivity.this,getLayoutInflater());

                listViewKindergarden.setAdapter(kindergardenAdapter);
                listViewKindergarden.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Choice(i);
                    }
                });





            }

            @Override
            public void onFailure(Call<Kindergarten> call, Throwable t) {

            }
        });
    }


    public void TestSearch(){
        // final String email = etEmail.getText().toString();
        final String search = etResearchKindergarden.getText().toString();

        // RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                /*------------------------------URL---------------------------------*/
        String url = "https://edonecserver.azurewebsites.net/API/kindergartens?pattern="+search;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject jsonBody = new JSONObject();


        final String mRequestBody = jsonBody.toString();

        JsonRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                // Get the current student (json object) data
                try {



                    for (int i = 0; i < response.length(); i++) {

//

                        JSONObject j = response.getJSONObject(i);

                        String name = j.getString("name");
                        ListView listViewKindergarden = (ListView) findViewById(R.id.listviewKindergarden);

                        // System.out.println(response.body().getEmail());
                        names.add(j.getString("name"));
                        images.add("https://www.istockphoto.com/resources/images/PhotoFTLP/img_63351521.jpg");
                        address.add(j.getString("adress"));
                        phone.add(String.valueOf(j.getString("phone")));
                        kindergardenAdapter = new KindergardenAdapter(names,images,address,phone,SelectingKindergardenSignUpActivity.this,getLayoutInflater());

                        listViewKindergarden.setAdapter(kindergardenAdapter);
                        listViewKindergarden.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Choice(i);
                            }
                        });


                        Log.e("LOG_RESPONSE", String.valueOf(response));

                        Toast.makeText(SelectingKindergardenSignUpActivity.this, "cc" + name, Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SelectingKindergardenSignUpActivity.this, "NN FRR", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return String.format("application/json; charset=utf-8");
            }

            @Override
            public byte[] getBody() {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            mRequestBody, "utf-8");
                    return null;
                }
            }
        };
        //MySingleton.getInstance(this).addToRequestQueue(stringRequest);


        requestQueue.add(stringRequest);
    }
}
