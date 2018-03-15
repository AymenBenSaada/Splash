package com.example.macbook.splash;


import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.macbook.splash.Models.Child;
import com.example.macbook.splash.Models.Group;
import com.example.macbook.splash.Models.Teacher;
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
import java.util.ArrayList;
import java.util.List;

public class CommunSuggestionsActivity extends AppCompatActivity {

    private String TeacherFile="teacher.dat";
    private String suggestionFileName="suggestions.dat";
    private String readLogsFileName = "readLogsPerChild.dat";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commun_suggestions);

        final TextView textView = (TextView)findViewById(R.id.texttest);
        final TextView textView1 = (TextView)findViewById(R.id.texttest1);

        Teacher teacher = loadTeacher();
        if(teacher != null){
            textView.setText("FILES DELTED");
        }

        for(int i=1;i<5;i++){
            File file = new File(getFilesDir() + "/" +"media"+i);
            file.delete();
        }
        File f = new File(getFilesDir()+"/"+readLogsFileName);
        f.delete();

        File f1 = new File (getFilesDir() + "/" + suggestionFileName);
        f1.delete();


        File f2 = new File (getFilesDir() + "/" + "teacher_profil_picture_2.dat");
        f2.delete();


        File f3 = new File (getFilesDir() + "/" + "parent_profil_picture_1.dat");
        f3.delete();


        File f4 = new File (getFilesDir() + "/" + "teacher_3.dat");
        f4.delete();


        File f5 = new File (getFilesDir() + "/" + "teacher_2.dat");
        f5.delete();

        File f6 = new File (getFilesDir() + "parent.dat");
        f6.delete();
    }


    @Nullable
    private Teacher loadTeacher(){
        FileInputStream fis = null;
        Teacher teacher = null;
        //CODE

        try {
            fis = this.openFileInput(TeacherFile);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            String Json = sb.toString();
            Type type = new TypeToken<Teacher>(){}.getType();
            Gson gson = new Gson();
            teacher = gson.fromJson(Json,type);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return teacher;
    }

    private List<Child> loadChildren(){
        List<Child> childrenFromInternalStorage = new ArrayList<>();
        FileInputStream fis = null;
        //CODE

        try {
            fis = this.openFileInput(TeacherFile);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            String Json = sb.toString();
            Type type = new TypeToken<Teacher>(){}.getType();
            Gson gson = new GsonBuilder()
                    .create();
            Teacher teacher = gson.fromJson(Json,type);
            for (Group group:teacher.getGroups()
                    ) {
                for (Child child:group.getChildren()
                        ) {
                    childrenFromInternalStorage.add(child);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return childrenFromInternalStorage;
    }

}
