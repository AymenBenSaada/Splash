package com.example.macbook.splash.Adapters.Admin_Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.macbook.splash.Adapters.ChildAdapter;
import com.example.macbook.splash.Adapters.GsonTimeAdapter;
import com.example.macbook.splash.Admin.Admin_Enseignants_Activity;
import com.example.macbook.splash.Interfaces.ApiClient;
import com.example.macbook.splash.Interfaces.IGroupsApi;
import com.example.macbook.splash.Interfaces.IKindergartensApi;
import com.example.macbook.splash.Models.Admin;
import com.example.macbook.splash.Models.Child;
import com.example.macbook.splash.Models.Group;
import com.example.macbook.splash.Models.Teacher;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by macbook on 03/03/2018.
 */

public class Enseignants_List_Adapter extends BaseAdapter{

    private List<Teacher> teachers;
    private Activity activity;
    @Nullable
    private int group_id;

    public Enseignants_List_Adapter(List<Teacher> teachers, Activity activity, @Nullable int group_id) {
        this.teachers = teachers;
        this.activity = activity;
        this.group_id = group_id;
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

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = activity.getLayoutInflater().inflate(R.layout.customlayout_adapter_for_admin_teachers_list, null);

        final Teacher teacher = teachers.get(position);

        ViewHolder holder = new ViewHolder();

        //SETTING VIEWS
        holder.photo_teacher_name_in_admin_teachers = (CircleImageView) convertView.findViewById(R.id.photo_teacher_name_in_admin_teachers);
        holder.tv_teacher_name_in_admin_teachers = (TextView) convertView.findViewById(R.id.tv_teacher_name_in_admin_teachers);
        holder.declinebtn_in_teachers_list = (ImageView) convertView.findViewById(R.id.declinebtn_in_teachers_list);

        //SETTING FONTS
        final Typeface myCustomFont = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Thin.ttf");
        holder.tv_teacher_name_in_admin_teachers.setTypeface(myCustomFont);

        //SETTING CONTENT
        File file = new File(activity.getFilesDir(),"teacher_profile_picture_"+teacher.getId()+".dat");
        try{
            if(file.exists())
            holder.photo_teacher_name_in_admin_teachers.setImageURI(Uri.fromFile(file));
            else {
                holder.photo_teacher_name_in_admin_teachers.setImageResource(R.drawable.genericprofile);

            }
        }catch (Exception e){
            holder.photo_teacher_name_in_admin_teachers.setImageResource(R.drawable.genericprofile);
        }

        holder.tv_teacher_name_in_admin_teachers.setText((teacher.getName() + " " + teacher.getLastName()));

        if (activity instanceof Admin_Enseignants_Activity){
            //region condition verififed = delete teacher from kindergarten
            holder.declinebtn_in_teachers_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
                    View mView = activity.getLayoutInflater().inflate(R.layout.customlayout_dialog_validating_action,null);

                    TextView etes_vous_sur = (TextView) mView.findViewById(R.id.etes_vous_sur);
                    CardView cardView_ok = (CardView) mView.findViewById(R.id.cardview_ok);
                    CardView cardView_not_ok = (CardView) mView.findViewById(R.id.cardview_not_ok);

                    etes_vous_sur.setTypeface(myCustomFont);

                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();

                    cardView_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DeleteTeacherFromKindergartenRequest(teacher.getId(), teacher.getKindergartenId());
                            dialog.dismiss();
                        }
                    });

                    cardView_not_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });
            //endregion
        }else{
            //region else = delete teacher from group
            holder.declinebtn_in_teachers_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
                    View mView = activity.getLayoutInflater().inflate(R.layout.customlayout_dialog_validating_action,null);

                    TextView etes_vous_sur = (TextView) mView.findViewById(R.id.etes_vous_sur);
                    CardView cardView_ok = (CardView) mView.findViewById(R.id.cardview_ok);
                    CardView cardView_not_ok = (CardView) mView.findViewById(R.id.cardview_not_ok);

                    etes_vous_sur.setTypeface(myCustomFont);

                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();

                    cardView_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            UnlinkTeacherFromGroupRequest(teacher.getId(), group_id);
                            dialog.dismiss();
                        }
                    });

                    cardView_not_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });
            //endregion
        }


        return convertView;
    }

    static class ViewHolder {
        CircleImageView photo_teacher_name_in_admin_teachers;
        TextView tv_teacher_name_in_admin_teachers;
        ImageView declinebtn_in_teachers_list;
    }

    //region Methods
    private void UnlinkTeacherFromGroupRequest(final int teacher_id, final int groupID){
        IGroupsApi iGroupsApi = ApiClient.getClient().create(IGroupsApi.class);
        iGroupsApi.unLinkTeacherToGroup(teacher_id,groupID).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    int k = getTheIndexInTheListFromID(teacher_id,teachers);
                    teachers.remove(k);
                    saveGroupTeachersList(teachers,groupID);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void DeleteTeacherFromKindergartenRequest(final int teacher_id, final int kindergartenID){
        IKindergartensApi iKindergartensApi = ApiClient.getClient().create(IKindergartensApi.class);
        iKindergartensApi.deleteKindergartenTeacher(teacher_id,kindergartenID).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    int k = getTheIndexInTheListFromID(teacher_id,teachers);
                    teachers.remove(k);
                    saveKindergartenTeachersList(teachers,kindergartenID);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private int getTheIndexInTheListFromID (int teacher_id, List<Teacher> teacherList){
        int i = 0;
        while (i<teacherList.size()){
            if(teacherList.get(i).getId() == teacher_id){
                break;
            }else {
                i++;
            }
        }
        return i;
    }

    private void saveGroupTeachersList(List<Teacher> teachers,int group_id){
        File file = new File(activity.getFilesDir() + "/"+"kg_group_teachers_"+group_id+".dat");
        file.delete();
        FileOutputStream fos = null;

        try {
            fos = activity.openFileOutput("kg_group_teachers_"+group_id+".dat", Context.MODE_PRIVATE);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new GsonTimeAdapter())
                    .create();
            String json = gson.toJson(teachers);
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

    private void saveKindergartenTeachersList(List<Teacher> teachers,int kindergarten_id){
        File file = new File(activity.getFilesDir() + "/"+"kg_teachers_"+kindergarten_id+".dat");
        file.delete();
        FileOutputStream fos = null;

        try {
            fos = activity.openFileOutput("kg_teachers_"+kindergarten_id+".dat", Context.MODE_PRIVATE);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new GsonTimeAdapter())
                    .create();
            String json = gson.toJson(teachers);
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

    //endregion
}