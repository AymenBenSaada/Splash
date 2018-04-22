package com.example.macbook.splash.Admin;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.macbook.splash.Adapters.Admin_Adapters.Group_List_Adapter;
import com.example.macbook.splash.Adapters.GsonTimeAdapter;
import com.example.macbook.splash.Interfaces.ApiClient;
import com.example.macbook.splash.Interfaces.ITeachersApi;
import com.example.macbook.splash.Models.Group;
import com.example.macbook.splash.Models.Teacher;
import com.example.macbook.splash.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_Enseignant_Selected_Activity extends AppCompatActivity {

    private Teacher teacher;
    private Group_List_Adapter grp_List_Adapter;
    private List<Group> groups = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_enseignant_selected);

        setTitle("  Enseignant");

        Intent i = getIntent();
        String jsonTeacher = i.getStringExtra("jsonTeacher");
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new GsonTimeAdapter())
                .create();
        Type type = new TypeToken<Teacher>(){}.getType();
        teacher = gson.fromJson(jsonTeacher,type);

        //region setting views
        CircleImageView teacher_profilpicture_in_admin = (CircleImageView) findViewById(R.id.teacher_profilpicture_in_admin);
        TextView teacher_name_in_profile_in_admin = (TextView) findViewById(R.id.teacher_name_in_profile_in_admin);
        RecyclerView grp_list_RecyclerView = (RecyclerView) findViewById(R.id.grp_list_RecyclerView);
        TextView teacher_phone_number_in_admin = (TextView) findViewById(R.id.teacher_phone_number_in_admin);
        TextView teacher_email_in_admin = (TextView) findViewById(R.id.teacher_email_in_admin);
        //endregion

        //region setting font
        Typeface Roboto_Thin_Font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");

        teacher_email_in_admin.setTypeface(Roboto_Thin_Font);
        teacher_name_in_profile_in_admin.setTypeface(Roboto_Thin_Font);
        teacher_phone_number_in_admin.setTypeface(Roboto_Thin_Font);

        //endregion

        File file = new File(getFilesDir() + "/" + "teacher_profile_picture_"+teacher.getId()+".dat");
        if(file.exists())
            teacher_profilpicture_in_admin.setImageURI(Uri.fromFile(file));
        else{
            teacher_profilpicture_in_admin.setImageResource(R.drawable.genericprofile);
        }
        teacher_name_in_profile_in_admin.setText((teacher.getName() + " " + teacher.getLastName()));
        teacher_phone_number_in_admin.setText((""+teacher.getPhone()));
        teacher_email_in_admin.setText(teacher.getEmail());

        downloadTeacherGroups(teacher.getId());
        grp_list_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //TODO:Fix the KG ID
        grp_List_Adapter = new Group_List_Adapter(this, groups,11);
        grp_list_RecyclerView.setAdapter(grp_List_Adapter);
    }

    private void downloadTeacherGroups(int teacher_id){
        ITeachersApi iTeachersApi = ApiClient.getClient().create(ITeachersApi.class);
        iTeachersApi.getTeacherGroups(teacher_id).enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                if(response.isSuccessful()){
                    groups.clear();
                    groups.addAll(response.body());
                    grp_List_Adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {

            }
        });
    }

}
