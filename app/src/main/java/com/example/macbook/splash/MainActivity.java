package com.example.macbook.splash;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.macbook.splash.Admin.AdminMainActivity;
import com.example.macbook.splash.Interfaces.ApiClient;
import com.example.macbook.splash.Interfaces.IRegistrationInterface;
import com.example.macbook.splash.Models.User;
import com.example.macbook.splash.Registration.SecondScreenSignUpActivity;
import com.example.macbook.splash.SwipePagers.TeacherPager;
import com.google.gson.JsonObject;

import junit.framework.Test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends Activity {


    EditText etEmail;
    EditText etMdp;
    Boolean valid_login=false;
    List<User> users = new ArrayList<>();
    //private UserLoginTask mAuthTask = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TO HIDE THE TITLE BAR
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //HIDE THE ANDROID KEYBOARD BY DEFAULT
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);




        Typeface RegularRobotoFont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        Typeface MediumRobotoFont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Medium.ttf");

        TextView tvEmail = (TextView)findViewById(R.id.tvEmail);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etMdp = (EditText)findViewById(R.id.etMdp);
        TextView tvMdp = (TextView)findViewById(R.id.tvMdp);
        Button btnInscription = (Button)findViewById(R.id.btnInscription);
        TextView dejaMembre = (TextView)findViewById(R.id.dejaMembre);
        Button btnSIdentifier = (Button)findViewById(R.id.btnSIdentifier);

        btnSIdentifier.setPaintFlags(btnSIdentifier.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        tvEmail.setTypeface(RegularRobotoFont);
        etEmail.setTypeface(RegularRobotoFont);
        tvMdp.setTypeface(RegularRobotoFont);
        //btnInscription.setTypeface(MediumRobotoFont);
        dejaMembre.setTypeface(RegularRobotoFont);

        btnInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestLogin();
            }
        });

    }




    public void hideKeyboard(View view)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }




    public void Inscription (View v){
        final Intent intent = new Intent(this, SecondScreenSignUpActivity.class);

        String email = etEmail.getText().toString().trim();
        if (email.isEmpty()){
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }
        if(!(email.indexOf("@")>0))
        {
            etEmail.setError("Veuillez insérer un Email valid");
            etEmail.requestFocus();
            return;
        }
        if(etMdp.getText().toString().trim().isEmpty()) {
            etMdp.setError("Le mot de passe est obligatior!");
            etMdp.requestFocus();
            return;
        }
        if(etMdp.getText().toString().trim().length()<8) {
            etMdp.setError("Le mot dois contenire au moins 8 charactère");
            etMdp.requestFocus();
            return;
        }
        IRegistrationInterface registrationInterface = ApiClient.getClient().create(IRegistrationInterface.class);
        registrationInterface.checkEmail(email).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, retrofit2.Response<Boolean> response) {
                if(!response.body()) {
                    ImageView edonecLogo = (ImageView) findViewById(R.id.edonecLogo);
                    Pair pair = new Pair<View, String>(edonecLogo, "imgTransition");
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pair);

                    intent.putExtra("email", etEmail.getText().toString());
                    intent.putExtra("mdp", etMdp.getText().toString());


                    startActivity(intent, options.toBundle());
                }
                else {
                    etEmail.setError("Cet utilisateur existe déjà");
                    etEmail.requestFocus();
                    return;
                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network error", Toast.LENGTH_LONG).show();


            }
        });
    }

    public void SIdentifier (View v){





        Intent intent = new Intent(this, LoggedTeacherMainActivity.class);
        //Intent intent = new Intent(this, AdminMainActivity.class);
        startActivity(intent);
    }




    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
        return;
    }


    public void TestLogin(){
        final String email = etEmail.getText().toString();
        final String password = etMdp.getText().toString();

        // RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                /*------------------------------URL---------------------------------*/
        String url = "https://edonecserver.azurewebsites.net/Auth/login";
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("email", email);
            jsonBody.put("password", password);
            final String mRequestBody = jsonBody.toString();

            JsonRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url,null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    // Get the current student (json object) data
                    try {Log.e("LOG_RESPONSE", String.valueOf(response));
                        String userId = response.getString("userId");
                        String role = response.getString("userDescriptor");

                        if (role.equals("Admin")){
                            Intent intent = new Intent(MainActivity.this, AdminMainActivity.class);
                            intent.putExtra("userId",userId);
                            storePeref(Integer.parseInt(userId),"Admin",true);
                            startActivity(intent);
                        }
                        else  if (role.equals("Teacher")){
                            Intent intent = new Intent(MainActivity.this, LoggedTeacherMainActivity.class);
                            storePeref(Integer.parseInt(userId),"Teacher",true);

                            intent.putExtra("userId",userId);
                            startActivity(intent);
                        }
                        else {
                            Intent intent = new Intent(MainActivity.this, LoggedParentMainActivity.class);
                            storePeref(Integer.parseInt(userId),"Parent",true);
                            intent.putExtra("userId",userId);
                            startActivity(intent);
                        }





                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "NN FRR", Toast.LENGTH_SHORT).show();
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
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //region SetSharedPereferences
    public void storePeref(int userId, String accountType, Boolean isConnected){
        SharedPreferences sharedisConnecterdPereferences = getSharedPreferences("AccountStatus",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedisConnecterdPereferences.edit();
        editor.putString("AccountType",accountType);
        editor.putBoolean("isConnected",isConnected);
        editor.putInt("userId",userId);
        editor.apply();


    }

    //endregion




}
