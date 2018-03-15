package com.example.macbook.splash.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.macbook.splash.Adapters.Admin_Adapters.Admin_Groupes_Children_List_Adapter;
import com.example.macbook.splash.Adapters.Admin_Adapters.Enseignants_List_Adapter;
import com.example.macbook.splash.Adapters.GsonTimeAdapter;
import com.example.macbook.splash.Models.Admin;
import com.example.macbook.splash.Models.Child;
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

public class Admin_ChildrenList_Activity extends AppCompatActivity {

    private List<Child> children;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_children_list);

        setTitle("  Les enfants");

        ListView listView = findViewById(R.id.listviewAdminListOfChildren);

        children = new ArrayList<>();
        if (loadAdminChildren() != null ){
            children = loadAdminChildren();
        }

        Admin_Groupes_Children_List_Adapter children_list_adapter = new Admin_Groupes_Children_List_Adapter(children,this,0);

        listView.setAdapter(children_list_adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                transitionToAnotherActivity(children.get(position));
            }
        });
    }


    //region Methods
    private List<Child> loadAdminChildren(){
        Admin admin = loadAdminFromInternalStorage();
        List<Child> result = new ArrayList<>();
        if ( loadKindergartenChildrenFromInternalStorage(admin.getKindergartenId()) != null){
            result = loadKindergartenChildrenFromInternalStorage(admin.getKindergartenId());
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

    private List<Child> loadKindergartenChildrenFromInternalStorage(int kg_id){
        FileInputStream fis = null;
        //CODE

        try {
            fis = this.openFileInput("kg_children_"+ kg_id +".dat");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            String Json = sb.toString();
            Type type = new TypeToken<List<Child>>(){}.getType();
            Gson gson = new GsonBuilder()
                    .create();
            List<Child> children = gson.fromJson(Json,type);
            return children;


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

    private void transitionToAnotherActivity(Child child){
        Intent i = new Intent(this,Admin_Enseignant_Selected_Activity.class);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new GsonTimeAdapter())
                .create();
        String jsonTeacher = gson.toJson(child);
        i.putExtra("jsonChild",jsonTeacher);

        startActivity(i);
    }

}
