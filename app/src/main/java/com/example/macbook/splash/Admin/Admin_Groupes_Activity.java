package com.example.macbook.splash.Admin;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macbook.splash.Adapters.Admin_Adapters.Group_List_Adapter;
import com.example.macbook.splash.Adapters.GsonTimeAdapter;
import com.example.macbook.splash.Interfaces.ApiClient;
import com.example.macbook.splash.Interfaces.IKindergartensApi;
import com.example.macbook.splash.Models.Admin;
import com.example.macbook.splash.Models.Group;
import com.example.macbook.splash.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_Groupes_Activity extends AppCompatActivity {

    private List<Group> groups;
    private Group_List_Adapter group_list_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_groupes);

        setTitle("  Liste des groupes");

        RecyclerView grp_list_RecyclerView_admin_groupes_activity = (RecyclerView) findViewById(R.id.grp_list_RecyclerView_admin_groupes_activity);
        grp_list_RecyclerView_admin_groupes_activity.setLayoutManager(new LinearLayoutManager(this));
        final Admin admin = loadAdminFromInternalStorage();

        if(admin!= null){
            groups = loadAdminGroupes(admin);
            if(groups!=null){
                group_list_adapter = new Group_List_Adapter(this,groups,admin.getKindergartenId());
                grp_list_RecyclerView_admin_groupes_activity.setAdapter(group_list_adapter);
            }
        }


        FloatingActionButton fab_add_group = (FloatingActionButton) findViewById(R.id.fab_add_group);
        fab_add_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Admin_Groupes_Activity.this);
                View mView = getLayoutInflater().inflate(R.layout.customlayout_dialog_new_group,null);

                final EditText new_group = (EditText) mView.findViewById(R.id.new_group);
                Button btn_add_new_group = (Button) mView.findViewById(R.id.btn_add_new_group);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();

                btn_add_new_group.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Group group = new Group(0,null,new_group.getText().toString());

                        IKindergartensApi iKindergartensApi = ApiClient.getClient().create(IKindergartensApi.class);
                        iKindergartensApi.postKindergartenGroups(admin.getKindergartenId(),group).enqueue(new Callback<Group>() {
                            @Override
                            public void onResponse(Call<Group> call, Response<Group> response) {
                                if(response.isSuccessful()){
                                    groups.add(group);
                                    saveNewGroupsAsFileInTheInternalStorage(groups,"kg_groups_"+admin.getKindergartenId()+".dat");
                                    group_list_adapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                    Toast.makeText(Admin_Groupes_Activity.this,"Groupe ajouté avec succès !",Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Group> call, Throwable t) {

                            }
                        });
                    }
                });


                dialog.show();
            }
        });
    }


    private List<Group> loadAdminGroupes(Admin admin){

        return loadKindergartenGroupsFromInternalStorage(admin.getKindergartenId());

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

    private void saveNewGroupsAsFileInTheInternalStorage(List<Group> new_groups,String file_name){
        File file = new File(getFilesDir() + "/"+file_name);
        file.delete();
        FileOutputStream fos = null;

        try {
            fos = this.openFileOutput(file_name, Context.MODE_PRIVATE);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new GsonTimeAdapter())
                    .create();
            String json = gson.toJson(new_groups);
            fos.write(json.getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<Group> loadKindergartenGroupsFromInternalStorage(int kg_id){
        FileInputStream fis = null;
        //CODE

        try {
            fis = this.openFileInput("kg_groups_"+ kg_id +".dat");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            String Json = sb.toString();
            Type type = new TypeToken<List<Group>>(){}.getType();
            Gson gson = new GsonBuilder()
                    .create();
            List<Group> groups = gson.fromJson(Json,type);
            return groups;


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
}
