package com.example.macbook.splash.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.macbook.splash.Models.Child;
import com.example.macbook.splash.Models.Group;
import com.example.macbook.splash.Models.Teacher;
import com.example.macbook.splash.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

/**
 * Created by macbook on 06/02/2018.
 */


public class ChildAdapter extends BaseAdapter {


    private Context activity;
    private LayoutInflater layoutInflater;
    private List<Child> listOfChildren;
    private HashMap<Integer,ArrayList<Integer>> hmReadLogs;



    public ChildAdapter(List<Child> listOfChildren, Context activity, LayoutInflater layoutInflater,HashMap<Integer,ArrayList<Integer>> hmReadLogs) {
        this.listOfChildren = listOfChildren;
        this.activity = activity;
        this.layoutInflater = layoutInflater;
        this.hmReadLogs = hmReadLogs;
    }

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


    @Override
    public int getCount() {
        return listOfChildren.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = layoutInflater.inflate(R.layout.customlayout, null);

        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        ViewHolder holder = new ViewHolder();
        //SETTING VIEWS
        holder.imageView = (CircleImageView) view.findViewById(R.id.photo);
        holder.name = (TextView) view.findViewById(R.id.tv_name);
        holder.observationsnbr = (TextView) view.findViewById(R.id.tv_observationsnbr);
        holder.modifie = (TextView) view.findViewById(R.id.tv_modifie);
        holder.notification = (TextView) view.findViewById(R.id.notifications);
        holder.notif = (RelativeLayout) view.findViewById(R.id.notif);
        holder.abreviations = (TextView) view.findViewById(R.id.tvAbreviation);

        //SETTING FONTS
        Typeface myCustomFont = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Thin.ttf");
        holder.name.setTypeface(myCustomFont);
        holder.observationsnbr.setTypeface(myCustomFont);
        holder.modifie.setTypeface(myCustomFont);
        holder.abreviations.setTypeface(myCustomFont);

        //SETTING CONTENT


        File file = new File(activity.getFilesDir() + "/" +"child_profil_picture_"+listOfChildren.get(i).getId()+".dat");
        holder.imageView.setImageURI(Uri.fromFile(file));

        holder.name.setText((listOfChildren.get(i).getName()+" "+listOfChildren.get(i).getLastName()));
        holder.abreviations.setText(("( " + listOfChildren.get(i).getNickName() + " )"));
        holder.observationsnbr.setText(("Nombre d'observations : " + Integer.toString(listOfChildren.get(i).getLogs().size())));
        holder.modifie.setText(("Modifiée le : " + dateFormat.format(listOfChildren.get(i).getBirth())));

        int notificationsNbr = listOfChildren.get(i).getLogs().size() - CalculateReadLogsPerChild(listOfChildren.get(i).getId(),hmReadLogs);

        if (notificationsNbr == 0) {
            holder.notif.setVisibility(View.INVISIBLE);
        } else {
            holder.notif.setVisibility(View.VISIBLE);
            holder.notification.setText(Integer.toString(notificationsNbr));
        }

        return view;
    }

    static class ViewHolder {
        CircleImageView imageView;
        TextView name;
        TextView observationsnbr;
        TextView modifie;
        TextView notification;
        RelativeLayout notif;
        TextView abreviations;
    }


    // UTILS


    private int CalculateReadLogsPerChild(int child_ID, HashMap<Integer,ArrayList<Integer>> hmReadLogs){
        int result = 0;
        if (hmReadLogs.get(child_ID) != null){
            result = hmReadLogs.get(child_ID).size();
        }
        return result;
    }

}