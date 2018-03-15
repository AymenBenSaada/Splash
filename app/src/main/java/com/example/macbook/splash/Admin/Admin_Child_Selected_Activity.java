package com.example.macbook.splash.Admin;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.macbook.splash.Adapters.GsonTimeAdapter;
import com.example.macbook.splash.Interfaces.ApiClient;
import com.example.macbook.splash.Interfaces.IChildrenApi;
import com.example.macbook.splash.LogsPerChildActivity;
import com.example.macbook.splash.Models.Child;
import com.example.macbook.splash.Models.Parent;
import com.example.macbook.splash.Models.Teacher;
import com.example.macbook.splash.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_Child_Selected_Activity extends AppCompatActivity {

    private String jsonChild;
    private TextView parent1_phone_number_in_admin,parent2_phone_number_in_admin,parent1_email_in_admin,parent2_email_in_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_child_selected);

        setTitle("  Enfant");

        Intent i = getIntent();
        jsonChild = i.getStringExtra("jsonChild");
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new GsonTimeAdapter())
                .create();
        Type type = new TypeToken<Child>(){}.getType();
        Child child = gson.fromJson(jsonChild,type);

        //region setting views
        ImageView child_profile_picture = findViewById(R.id.child_profilpicture_in_admin);
        TextView child_name_in_profile_in_admin = findViewById(R.id.child_name_in_profile_in_admin);
        TextView child_nickname_in_profile_in_admin = findViewById(R.id.child_nickname_in_profile_in_admin);
        ImageView child_selected_feed = findViewById(R.id.child_selected_feed);
        ImageView child_selected_logs = findViewById(R.id.child_selected_logs);
        parent1_phone_number_in_admin = findViewById(R.id.parent1_phone_number_in_admin);
        parent2_phone_number_in_admin = findViewById(R.id.parent2_phone_number_in_admin);
        parent1_email_in_admin = findViewById(R.id.parent1_email_in_admin);
        parent2_email_in_admin = findViewById(R.id.parent2_email_in_admin);
        //endregion

        //region setting font
        Typeface Roboto_Thin_Font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");

        child_name_in_profile_in_admin.setTypeface(Roboto_Thin_Font);
        child_nickname_in_profile_in_admin.setTypeface(Roboto_Thin_Font);
        parent1_phone_number_in_admin.setTypeface(Roboto_Thin_Font);
        parent2_phone_number_in_admin.setTypeface(Roboto_Thin_Font);
        parent1_email_in_admin.setTypeface(Roboto_Thin_Font);
        parent2_email_in_admin.setTypeface(Roboto_Thin_Font);

        //endregion

        File file = new File(getFilesDir() + "/" + "child_profile_picture_"+child.getId()+".dat");
        child_profile_picture.setImageURI(Uri.fromFile(file));

        child_name_in_profile_in_admin.setText((child.getName() + " "+ child.getLastName()));
        child_nickname_in_profile_in_admin.setText(child.getNickName());

        getChildParent(child.getId());

        child_selected_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //region transition to the next Activity = child feed
                Intent i = new Intent(Admin_Child_Selected_Activity.this,Admin_Child_Selected_Feed_Activity.class);
                i.putExtra("jsonChild",jsonChild);
                startActivity(i);
                //endregion
            }
        });

        child_selected_logs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //region transition to the next Activity = child logs
                Intent i = new Intent(Admin_Child_Selected_Activity.this,Admin_Child_Selected_Logs_Activity.class);
                i.putExtra("jsonChild",jsonChild);
                startActivity(i);
                //endregion
            }
        });


    }

    private void getChildParent (int child_id){
        IChildrenApi iChildrenApi = ApiClient.getClient().create(IChildrenApi.class);
        iChildrenApi.getChildParents(child_id).enqueue(new Callback<List<Parent>>() {
            @Override
            public void onResponse(Call<List<Parent>> call, Response<List<Parent>> response) {
                if(response.isSuccessful()){
                    if(response.body().get(0) != null){
                        parent1_email_in_admin.setText(response.body().get(0).getEmail());
                        parent1_phone_number_in_admin.setText(Integer.toString(response.body().get(0).getPhone()));
                    }
                    if (response.body().get(1) != null){
                        parent2_email_in_admin.setText(response.body().get(1).getEmail());
                        parent2_phone_number_in_admin.setText(Integer.toString(response.body().get(1).getPhone()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Parent>> call, Throwable t) {

            }
        });
    }
}
