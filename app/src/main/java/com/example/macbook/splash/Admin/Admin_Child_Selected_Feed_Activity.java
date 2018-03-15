package com.example.macbook.splash.Admin;

import android.content.Intent;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.macbook.splash.Adapters.Admin_Adapters.FeedAdapter;
import com.example.macbook.splash.Adapters.GsonTimeAdapter;
import com.example.macbook.splash.Interfaces.ApiClient;
import com.example.macbook.splash.Interfaces.IChildrenApi;
import com.example.macbook.splash.Models.Admin;
import com.example.macbook.splash.Models.Child;
import com.example.macbook.splash.Models.Post;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_Child_Selected_Feed_Activity extends AppCompatActivity {

    private List<Post> posts;
    private FeedAdapter feedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_child_selected_feed);

        Intent i = getIntent();
        String jsonChild = i.getStringExtra("jsonChild");
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new GsonTimeAdapter())
                .create();
        Type type = new TypeToken<Child>(){}.getType();
        Child child = gson.fromJson(jsonChild,type);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.feedRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        posts = new ArrayList<>();
        loadPosts(child.getId());

        Admin admin = loadAdminFromInternalStorage();

        feedAdapter = new FeedAdapter(this,posts,admin.getKindergartenId());
        recyclerView.setAdapter(feedAdapter);

    }

    private void loadPosts(int childID){
        ApiClient.getClient().create(IChildrenApi.class).getChildPosts(childID).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()){
                    posts = response.body();
                    feedAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(Admin_Child_Selected_Feed_Activity.this,"Erreur au cours du telechargement des postes",Toast.LENGTH_SHORT).show();
            }
        });
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

}
