package com.example.macbook.splash.Adapters.Admin_Adapters;

import android.app.Activity;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.macbook.splash.Models.Admin;
import com.example.macbook.splash.Models.Group;
import com.example.macbook.splash.Models.Teacher;
import com.example.macbook.splash.Models.TeacherInscriptionResponse;
import com.example.macbook.splash.Models.TeacherInscriptionResponseSubmitViewModel;
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
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by macbook on 21/04/2018.
 */

public class Senders_Enseignants_List_Adapter extends BaseAdapter{

    private List<Teacher> teachers;
    private Activity activity;
    private ArrayList<Teacher> localTeachers = new ArrayList<Teacher>();
    private Group_List_For_Invite_Adapter group_list_for_invite_adapter;
    private List<Group> groups = new ArrayList<>();


    public Senders_Enseignants_List_Adapter(List<Teacher> teachers, Activity activity) {
        this.teachers = teachers;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return teachers.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = activity.getLayoutInflater().inflate(R.layout.customlayout_adapter_for_senders_teachers_list, null);

        final Teacher teacher = teachers.get(position);

        ViewHolder holder = new ViewHolder();

        holder.photo_teacher_name_in_admin_teachers = (CircleImageView) convertView.findViewById(R.id.photo_teacher_name_in_admin_teachers);
        holder.tv_teacher_name_in_admin_teachers = (TextView) convertView.findViewById(R.id.tv_teacher_name_in_admin_teachers);
        holder.declinebtn_in_teachers_list = (ImageView) convertView.findViewById(R.id.declinebtn_in_senders_teachers_list);
        holder.acceptbtn_in_teachers_list = (ImageView) convertView.findViewById(R.id.acceptbtn_in_senders_teachers_list);
        holder.acceptbtn_in_teachers_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Admin admin = loadAdminFromInternalStorage();

                if(admin!= null){
                    groups = loadAdminGroupes(admin);

                }


                //TODO : CALL THE GroupLIST here
                // fil parametre hot el TeacherList w el Groups


            }
        });
        //SETTING FONTS
        final Typeface myCustomFont = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Thin.ttf");
        holder.tv_teacher_name_in_admin_teachers.setTypeface(myCustomFont);

        //SETTING CONTENT
        if (fileExist("teacher_profile_picture_"+teacher.getId()+".dat")){
            File file = new File(activity.getFilesDir(),"teacher_profile_picture_"+teacher.getId()+".dat");
            holder.photo_teacher_name_in_admin_teachers.setImageURI(Uri.fromFile(file));
        }

        holder.tv_teacher_name_in_admin_teachers.setText((teacher.getName() + " " + teacher.getLastName()));

        return convertView;
    }

    static class ViewHolder {
        CircleImageView photo_teacher_name_in_admin_teachers;
        TextView tv_teacher_name_in_admin_teachers;
        ImageView declinebtn_in_teachers_list;
        ImageView acceptbtn_in_teachers_list;
    }

    private boolean fileExist(String fname){
        File file = activity.getFileStreamPath(fname);
        return file.exists();
    }


    private List<Group> loadAdminGroupes(Admin admin){

        return loadKindergartenGroupsFromInternalStorage(admin.getKindergartenId());

    }

    private Admin loadAdminFromInternalStorage(){
        FileInputStream fis = null;
        //CODE

        try {
            fis = activity.openFileInput("admin.dat");
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

    private List<Group> loadKindergartenGroupsFromInternalStorage(int kg_id){
        FileInputStream fis = null;
        //CODE

        try {
            fis = activity.openFileInput("kg_groups_"+ kg_id +".dat");
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


