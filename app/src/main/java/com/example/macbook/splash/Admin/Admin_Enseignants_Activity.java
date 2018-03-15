package com.example.macbook.splash.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.macbook.splash.Adapters.Admin_Adapters.Enseignants_List_Adapter;
import com.example.macbook.splash.Adapters.GsonTimeAdapter;
import com.example.macbook.splash.Models.Admin;
import com.example.macbook.splash.Models.Group;
import com.example.macbook.splash.Models.Teacher;
import com.example.macbook.splash.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Admin_Enseignants_Activity extends AppCompatActivity {
    private List<Teacher> teachers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_enseignants);

        setTitle("  Les enseignants");

        ListView listView = findViewById(R.id.listviewTeachers);

        teachers = loadAdminTeachers();

        Enseignants_List_Adapter enseignants_list_adapter = new Enseignants_List_Adapter(teachers,this,0);

        listView.setAdapter(enseignants_list_adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                transitionToAnotherActivity(teachers.get(position));
            }
        });
    }




    //region load teachers from InternalStorage methods
    private List<Teacher> loadAdminTeachers(){
        Admin admin = loadAdminFromInternalStorage();
        List<Teacher> result = new ArrayList<>();
        if ( loadKindergartenTeachersFromInternalStorage(admin.getKindergartenId()) != null){
            result = loadKindergartenTeachersFromInternalStorage(admin.getKindergartenId());
        }
        return result;
    }

    private Admin loadAdminFromInternalStorage(){
        FileInputStream fis = null;
        //CODE

        try {
            fis = this.openFileInput("admin.dat");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            String Json = sb.toString();
            Type type = new TypeToken<Admin>(){}.getType();
            Gson gson = new GsonBuilder()
                    .create();
            Admin admin = gson.fromJson(Json,type);
            return admin;


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<Teacher> loadKindergartenTeachersFromInternalStorage(int kg_id){
        FileInputStream fis = null;
        //CODE

        try {
            fis = this.openFileInput("kg_teachers_"+ kg_id +".dat");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            String Json = sb.toString();
            Type type = new TypeToken<List<Teacher>>(){}.getType();
            Gson gson = new GsonBuilder()
                    .create();
            List<Teacher> teachers = gson.fromJson(Json,type);
            return teachers;


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //endregion

    private void transitionToAnotherActivity(Teacher teacher){
        Intent i = new Intent(this,Admin_Enseignant_Selected_Activity.class);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new GsonTimeAdapter())
                .create();
        String jsonTeacher = gson.toJson(teacher);
        i.putExtra("jsonTeacher",jsonTeacher);

        startActivity(i);
    }


}
