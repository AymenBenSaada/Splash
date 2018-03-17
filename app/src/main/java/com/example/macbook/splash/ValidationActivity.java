package com.example.macbook.splash;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.macbook.splash.Interfaces.ApiClient;
import com.example.macbook.splash.Interfaces.IRegistrationInterface;
import com.example.macbook.splash.Interfaces.ITeachersApi;
import com.example.macbook.splash.Models.AccountRegistrationModel;
import com.example.macbook.splash.Models.Post;
import com.example.macbook.splash.Models.RegistrationResponseModel;
import com.example.macbook.splash.Models.Teacher;
import com.example.macbook.splash.Models.TeacherInscriptionRequestSubmitViewModel;
import com.example.macbook.splash.ViewModels.ChildRegistrationViewModel;
import com.example.macbook.splash.ViewModels.ParentRegistrationViewModel;
import com.example.macbook.splash.ViewModels.Person;
import com.example.macbook.splash.ViewModels.TeacherRegistrationViewModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValidationActivity extends AppCompatActivity {

    Person person;
    int userID;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Intent i = getIntent();
        person = (Person) i.getSerializableExtra("person");


        super.onCreate(savedInstanceState);
        registerTeacher();
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
            // register teacher
            registerTeacher();
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
    //region TeacherRegistration
    public void registerTeacher()
    {
        IRegistrationInterface registrationService =  ApiClient.getClient().create(IRegistrationInterface.class);
        registrationService.registerTeacher(new AccountRegistrationModel(person)).enqueue(new Callback<RegistrationResponseModel>() {
            @Override
            public void onResponse(Call<RegistrationResponseModel> call, final Response<RegistrationResponseModel> response) {
                Log.e("pushing Account done!", "success");

                final ITeachersApi service = ApiClient.getClient().create(ITeachersApi.class);
                userID = response.body().getUserId();
                service.putTeacher(userID,person.ToTeacher()).
                        enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response2) {
                                final ITeachersApi service2 = ApiClient.getClient().create(ITeachersApi.class);

                                Log.e("pushing teacher done!", "success");
                                service2.requestLinkTeacherToKG(new TeacherInscriptionRequestSubmitViewModel(response.body().getUserId(),((TeacherRegistrationViewModel) person).getKindergardenID()))
                                        .enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                Log.e("linking teacher done!", "success");

                                            }
                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                Log.e("failed 3", "failure");

                                            }
                                        });
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("failed 1", "failure");
                            }
                        });
            }

            @Override
            public void onFailure(Call<RegistrationResponseModel> call, Throwable t) {
                Log.e("failed 0", "failure");

            }
        });


    }
//endregion


    //region ParentRegistration
    public void parentTeacher()
    {
        IRegistrationInterface registrationService =  ApiClient.getClient().create(IRegistrationInterface.class);
        registrationService.registerParent(new AccountRegistrationModel(person)).enqueue(new Callback<RegistrationResponseModel>() {
            @Override
            public void onResponse(Call<RegistrationResponseModel> call, final Response<RegistrationResponseModel> response) {
                Log.e("pushing Account done!", "success");
            }

            @Override
            public void onFailure(Call<RegistrationResponseModel> call, Throwable t) {
                Log.e("failed 0", "failure");
            }
        });
    }
//endregion


}

