package com.example.macbook.splash.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.macbook.splash.Adapters.Admin_Adapters.Messages_Adapter;
import com.example.macbook.splash.Models.Admin;
import com.example.macbook.splash.Models.Message;
import com.example.macbook.splash.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class Admin_Messages_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_messages);

        ListView messagesListView = findViewById(R.id.messagesListView);
        Messages_Adapter messagesAdapter = new Messages_Adapter(loadKindergartenMessagesFromInternalStorage(loadAdminFromInternalStorage().getKindergartenId()),this);
        messagesListView.setAdapter(messagesAdapter);

    }

    private List<Message> loadKindergartenMessagesFromInternalStorage(int kindergarten_id){
        List<Message> messages = null;
        if(fileExist("kg_messages_"+kindergarten_id+".dat")) {
            //region getmessagesfromInternalStorage
            FileInputStream fis = null;
            try {
                fis = this.openFileInput("kg_messages_"+kindergarten_id+".dat");
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String text;

                while ((text = br.readLine()) != null) {
                    sb.append(text).append("\n");
                }

                String Json = sb.toString();
                Type type = new TypeToken<List<Message>>() {
                }.getType();
                Gson gson = new GsonBuilder()
                        .create();

                messages = gson.fromJson(Json, type);
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
            //endregion
        }
        return messages;
    }

    private boolean fileExist(String fname){
        File file = this.getFileStreamPath(fname);
        return file.exists();
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
