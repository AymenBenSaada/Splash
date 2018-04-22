package com.example.macbook.splash.Adapters.Admin_Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macbook.splash.Interfaces.ApiClient;
import com.example.macbook.splash.Interfaces.IKindergartensApi;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by macbook on 21/04/2018.
 */

public class Senders_Enseignants_List_Adapter extends BaseAdapter{

    private List<Teacher> teachers;
    private Activity activity;
    private ArrayList<Teacher> localTeachers = new ArrayList<Teacher>();
    private Group_List_For_Invite_Adapter group_list_for_invite_adapter;
    private List<Group> groups = new ArrayList<>();
    private Senders_Enseignants_List_Adapter senders_enseignants_list_adapter  = this;

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
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = activity.getLayoutInflater().inflate(R.layout.customlayout_adapter_for_senders_teachers_list, null);

        final Teacher teacher = teachers.get(position);

        ViewHolder holder = new ViewHolder();

        holder.photo_teacher_name_in_admin_teachers = (CircleImageView) convertView.findViewById(R.id.photo_teacher_name_in_admin_teachers);
        holder.tv_teacher_name_in_admin_teachers = (TextView) convertView.findViewById(R.id.tv_teacher_name_in_admin_teachers);
        holder.declinebtn_in_teachers_list = (ImageView) convertView.findViewById(R.id.declinebtn_in_senders_teachers_list);
        holder.acceptbtn_in_teachers_list = (ImageView) convertView.findViewById(R.id.acceptbtn_in_senders_teachers_list);

        //region OnClickAccept
        holder.acceptbtn_in_teachers_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Admin admin = loadAdminFromInternalStorage();

                groups = loadAdminGroupes(admin);

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
                @SuppressLint("ViewHolder") View mView = activity.getLayoutInflater().inflate(R.layout.custom_admin_choose_group_for_accepted,null);

                RecyclerView grp_list_RecyclerView = (RecyclerView) mView.findViewById(R.id.grp_list_RecyclerView);
                grp_list_RecyclerView.setLayoutManager(new LinearLayoutManager(activity));
                Group_List_For_Invite_Adapter grp_List_Adapter = new Group_List_For_Invite_Adapter(activity, groups,teacher,admin.getKindergartenId());
                grp_list_RecyclerView.setAdapter(grp_List_Adapter);

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();


                dialog.show();
                
            }
        });
        //endregion

        //region OnClickRefuse

        holder.declinebtn_in_teachers_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refuseInvite(teacher,position);
            }
        });



        //endregion


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

    //region Methods
    private void refuseInvite(Teacher teacher,final int position){

        IKindergartensApi iKindergartensApi = ApiClient.getClient().create(IKindergartensApi.class);

        TeacherInscriptionResponseSubmitViewModel teacherInscriptionResponseSubmitViewModel = new TeacherInscriptionResponseSubmitViewModel(teacher.getRequestId(),0,false);

        //TODO: fix the kinderGarten ID here
        iKindergartensApi.postTeacherInscriptionResponse(11,teacherInscriptionResponseSubmitViewModel).enqueue(new Callback<TeacherInscriptionResponse>() {
            @Override
            public void onResponse(Call<TeacherInscriptionResponse> call, Response<TeacherInscriptionResponse> response) {
                Toast.makeText(activity,"Ensegniant refusé",Toast.LENGTH_LONG).show();

                teachers.remove(position);
                List<Teacher> localTeachers = new ArrayList<Teacher>();
                localTeachers.addAll(teachers);
                teachers.clear();
                teachers.addAll(localTeachers);
                senders_enseignants_list_adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<TeacherInscriptionResponse> call, Throwable t) {

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
    //endregion
}


