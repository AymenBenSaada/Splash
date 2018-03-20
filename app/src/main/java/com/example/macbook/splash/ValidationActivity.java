package com.example.macbook.splash;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.macbook.splash.Interfaces.ApiClient;
import com.example.macbook.splash.Interfaces.IChildrenApi;
import com.example.macbook.splash.Interfaces.IParentsApi;
import com.example.macbook.splash.Interfaces.IRegistrationInterface;
import com.example.macbook.splash.Interfaces.ITeachersApi;
import com.example.macbook.splash.Models.AccountRegistrationModel;
import com.example.macbook.splash.Models.Child;
import com.example.macbook.splash.Models.ChildInscriptionRequestSubmitViewModel;
import com.example.macbook.splash.Models.Post;
import com.example.macbook.splash.Models.RegistrationResponseModel;
import com.example.macbook.splash.Models.Teacher;
import com.example.macbook.splash.Models.TeacherInscriptionRequestSubmitViewModel;
import com.example.macbook.splash.ViewModels.ChildRegistrationViewModel;
import com.example.macbook.splash.ViewModels.ParentRegistrationViewModel;
import com.example.macbook.splash.ViewModels.Person;
import com.example.macbook.splash.ViewModels.TeacherRegistrationViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.net.URI;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValidationActivity extends AppCompatActivity {

    Person person;
    int userID;
    String text;
    int childCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Intent i = getIntent();
        person = (Person) i.getSerializableExtra("person");


        super.onCreate(savedInstanceState);
        registerParent();
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
                userID = response.body().getUserId();
                putTeacherAfterAccountRegistration();
            }

            @Override
            public void onFailure(Call<RegistrationResponseModel> call, Throwable t) {
                Log.e("failed 0", "failure");

            }
        });

    }
    //endregion

    //region TeacherResponseBodyAfterAccountRegistration
    public void putTeacherAfterAccountRegistration(){

        final ITeachersApi service = ApiClient.getClient().create(ITeachersApi.class);

        service.putTeacher(userID,person.ToTeacher()).
                enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response2) {

                        Log.e("pushing teacher done!", "success");

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("failed 1", "failure");
                    }
                });
    }
    //endregion

    //region LinkingTeacherTOKinderGarten
    public void linkTeacherToKG(){
                    final ITeachersApi service2 = ApiClient.getClient().create(ITeachersApi.class);
                    service2.requestLinkTeacherToKG(userID,new TeacherInscriptionRequestSubmitViewModel(userID,((TeacherRegistrationViewModel) person).getKindergardenID()))
                        .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response3) {
                        Log.e("linking teacher done!", "success");

                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("failed 3", "failure");

                    }
                });
    }
    //endregion




    //region ParentRegistration
    public void registerParent()
    {
        IRegistrationInterface registrationService =  ApiClient.getClient().create(IRegistrationInterface.class);
        AccountRegistrationModel accountRegistrationModel = new AccountRegistrationModel(person);

        registrationService.registerParent(accountRegistrationModel).enqueue(new Callback<RegistrationResponseModel>() {
            @Override
            public void onResponse(Call<RegistrationResponseModel> call, final Response<RegistrationResponseModel> response) {
                Log.e("pushing Account done!", "success");
                userID = response.body().getUserId();
                putParentAfterAccountRegistration();


            }

            @Override
            public void onFailure(Call<RegistrationResponseModel> call, Throwable t) {
                Log.e("failed 0", "failure");
            }
        });
    }
    //endregion

    //region PutParentAfterAccountRegistration
    public void putParentAfterAccountRegistration(){
        final IParentsApi parentsApi = ApiClient.getClient(). create(IParentsApi.class);

        parentsApi.putParent(userID,person.ToParent()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("pushing Parent done!", "success");
                childCount = ((ParentRegistrationViewModel) person).getChildrenCount();
                ChildRegistrationViewModel child = ((ParentRegistrationViewModel) person).getChildren().get(0);

                if(childCount == 1) {
                    postOneChild(child.ToChild());
                }
                else{
                    postMultipleChildren(0,child.ToChild());
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    //endregion
    public void postMultipleChildren(final int i,final Child child){
        final IChildrenApi childrenApi = ApiClient.getClient().create(IChildrenApi.class);
        Gson gson = new Gson();
        String S = gson.toJson(child);
        childrenApi.postChild(child).enqueue(new Callback<Child>() {
            @Override
            public void onResponse(Call<Child> call, Response<Child> response) {
                Log.e("Child Model done!", "success");
                postChildProfilePicture(response.body().getId(),child,i);


            }

            @Override
            public void onFailure(Call<Child> call, Throwable t) {

            }
        });
    }

    //region PostOneChildAfterAccountRegistration

    public void postOneChild(final Child child)
    {
        final IChildrenApi childrenApi = ApiClient.getClient().create(IChildrenApi.class);
        Gson gson = new Gson();
        String S = gson.toJson(child);
        childrenApi.postChild(child).enqueue(new Callback<Child>() {
            @Override
            public void onResponse(Call<Child> call, Response<Child> response) {
                Log.e("Child Model done!", "success");
                postChildProfilePicture(response.body().getId(),child,0);

                
            }

            @Override
            public void onFailure(Call<Child> call, Throwable t) {

            }
        });
    }
    //endregion

    //region PutChildProfilePictureAfterChildRegistration

    public void postChildProfilePicture(int Id, final Child child, final int i){
        final IChildrenApi childrenProfilePictureApi = ApiClient.getClient().create(IChildrenApi.class);
        String Ss= child.getProfilePictureLink();
        URI s = URI.create(child.getProfilePictureLink());
        File file = new File (Ss);

        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        final int childId = Id;
        final Child childFinal = child;
        childrenProfilePictureApi.postChildProfilePicture(Id,filePart).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("Child Model done!", "success");
                linkTokindergarten(childId,childFinal,i);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }
    //endregion

    //region LinkChildToKinderGarten
    public void linkTokindergarten(final int Id,Child child,final int i) {
        final IChildrenApi service2 = ApiClient.getClient().create(IChildrenApi.class);
        service2.requestLinkChildToKG(userID, new ChildInscriptionRequestSubmitViewModel(userID, child.getKindergartenId(),Id))
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response3) {
                        Log.e("linking Child done!", "success");
                        linkChildToParent(Id,userID,i);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("failed 3", "failure");
                    }
                });
    }

    //endregion
    public void linkChildToParent(int childId,int parentId, final int i){
        final IChildrenApi service3 = ApiClient.getClient().create(IChildrenApi.class);
        service3.linkParentToChild(childId,parentId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if((i+1)<childCount)
                {
                    ChildRegistrationViewModel child = ((ParentRegistrationViewModel) person).getChildren().get(i+1);
                    postMultipleChildren(i+1,child.ToChild());

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }



}

