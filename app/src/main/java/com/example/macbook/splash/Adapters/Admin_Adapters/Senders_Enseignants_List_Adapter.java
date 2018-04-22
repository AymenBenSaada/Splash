package com.example.macbook.splash.Adapters.Admin_Adapters;

import android.app.Activity;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.macbook.splash.Models.Teacher;
import com.example.macbook.splash.R;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by macbook on 21/04/2018.
 */

public class Senders_Enseignants_List_Adapter extends RecyclerView.Adapter<Senders_Enseignants_List_Adapter.SendersList>{

    private List<Teacher> teachers;
    private Activity activity;


    public Senders_Enseignants_List_Adapter(List<Teacher> teachers, Activity activity) {
        this.teachers = teachers;
        this.activity = activity;
    }




    public class SendersList extends RecyclerView.ViewHolder {

        private TextView tv_teacher_name_in_admin_teachers;
        private CircleImageView photo_teacher_name_in_admin_teachers;
        private ImageView declinebtn_in_teachers_list,acceptbtn_in_teachers_list;

        private SendersList(View view) {
            super(view);
            photo_teacher_name_in_admin_teachers = (CircleImageView) view.findViewById(R.id.photo_teacher_name_in_admin_teachers);
            tv_teacher_name_in_admin_teachers = (TextView) view.findViewById(R.id.tv_teacher_name_in_admin_teachers);
            declinebtn_in_teachers_list = (ImageView) view.findViewById(R.id.declinebtn_in_senders_teachers_list);
            acceptbtn_in_teachers_list = (ImageView) view.findViewById(R.id.acceptbtn_in_senders_teachers_list);
        }
    }
    @Override
    public int getItemCount() {
        return teachers.size();
    }

    @Override
    public SendersList onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.customlayout_adapter_for_senders_teachers_list, parent,false);

        return new SendersList(view);
    }

    @Override
    public void onBindViewHolder(SendersList holder, int position) {
        final Teacher teacher = teachers.get(position);




        //SETTING FONTS
        final Typeface myCustomFont = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Thin.ttf");
        holder.tv_teacher_name_in_admin_teachers.setTypeface(myCustomFont);

        //SETTING CONTENT
        if (fileExist("teacher_profile_picture_"+teacher.getId()+".dat")){
            File file = new File(activity.getFilesDir(),"teacher_profile_picture_"+teacher.getId()+".dat");
            holder.photo_teacher_name_in_admin_teachers.setImageURI(Uri.fromFile(file));
        }

        holder.tv_teacher_name_in_admin_teachers.setText((teacher.getName() + " " + teacher.getLastName()));

    }


    /*
    private List<Teacher> teachers;
    private Activity activity;

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

        convertView = activity.getLayoutInflater().inflate(R.layout.customlayout_adapter_for_admin_teachers_list, null);

        final Teacher teacher = teachers.get(position);

        ViewHolder holder = new ViewHolder();

        holder.photo_teacher_name_in_admin_teachers = (CircleImageView) convertView.findViewById(R.id.photo_teacher_name_in_admin_teachers);
        holder.tv_teacher_name_in_admin_teachers = (TextView) convertView.findViewById(R.id.tv_teacher_name_in_admin_teachers);
        holder.declinebtn_in_teachers_list = (ImageView) convertView.findViewById(R.id.declinebtn_in_senders_teachers_list);
        holder.acceptbtn_in_teachers_list = (ImageView) convertView.findViewById(R.id.acceptbtn_in_senders_teachers_list);

        //SETTING FONTS
        final Typeface myCustomFont = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Thin.ttf");
        holder.tv_teacher_name_in_admin_teachers.setTypeface(myCustomFont);

        //SETTING CONTENT
        if (fileExist("teacher_profile_picture_"+teacher.getId()+".dat")){
            File file = new File(activity.getFilesDir(),"teacher_profile_picture_"+teacher.getId()+".dat");
            holder.photo_teacher_name_in_admin_teachers.setImageURI(Uri.fromFile(file));
        }

        holder.tv_teacher_name_in_admin_teachers.setText((teacher.getName() + " " + teacher.getLastName()));

        return null;
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
*/
    private boolean fileExist(String fname){
        File file = activity.getFileStreamPath(fname);
        return file.exists();
    }
}

