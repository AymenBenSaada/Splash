package com.example.macbook.splash.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.macbook.splash.LogsPerChildActivity;
import com.example.macbook.splash.Models.Log;
import com.example.macbook.splash.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

/**
 * Created by macbook on 15/02/2018.
 */

public class LogsAdapter extends BaseAdapter {

    private List<Log> childLogs;
    private Context context;
    private LayoutInflater layoutInflater;
    private int childID;
    private TextView log_title;
    private HashMap<Integer,ArrayList<Integer>> hmReadLogs;


    public LogsAdapter(List<Log> childLogs, Context context, LayoutInflater layoutInflater, int childID, HashMap<Integer,ArrayList<Integer>> hmReadLogs) {
        this.childLogs = childLogs;
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.childID = childID;
        this.hmReadLogs = hmReadLogs;
    }

    private CircleImageView first_letter_of_the_writer_name;


    @Override
    public int getCount() {
        return childLogs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void update(HashMap<Integer,ArrayList<Integer>> new_hmReadLogs){
        this.hmReadLogs = new_hmReadLogs;
        notifyDataSetChanged();
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        convertView = layoutInflater.inflate(R.layout.customlayout_logs_per_child, null);

        first_letter_of_the_writer_name = (CircleImageView)convertView.findViewById(R.id.first_letter_of_the_writer_name);
        log_title = (TextView)convertView.findViewById(R.id.log_title);
        final TextView log_text = (TextView)convertView.findViewById(R.id.log_text);

        //SETTING FONTS
        Typeface myCustomFont = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Thin.ttf");
        Typeface myCustomFont1 = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");

        //region log_title.setTypeface(changingFont); log_text.setTypeface(myCustomFont);

        if (hmReadLogs == null){
            log_title.setTypeface(myCustomFont1);
            log_text.setTypeface(myCustomFont1);
        }else{
            if (hmReadLogs.get(childID) == null){
                log_title.setTypeface(myCustomFont1);
                log_text.setTypeface(myCustomFont1);
            }else{
                if (hmReadLogs.get(childID).contains(childLogs.get(position).getId())){
                    log_title.setTypeface(myCustomFont);
                    log_text.setTypeface(myCustomFont);
                    convertView.setBackgroundColor(context.getResources().getColor(R.color.gris6e));
                }else{
                    log_title.setTypeface(myCustomFont1);
                    log_text.setTypeface(myCustomFont1);
                }
            }
        }
        //endregion

        getTheLetterImageFromTheLogWriterFirstName(childLogs.get(position).getWriter());

        log_title.setText(childLogs.get(position).getTitle());
        log_text.setText(childLogs.get(position).getContent());


        return convertView;
    }


    private void getTheLetterImageFromTheLogWriterFirstName(String name){
        String uri = "@drawable/letter_";  // where myresource (without the extension) is the file

        uri += name.charAt(0);
        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());

        Drawable res = context.getResources().getDrawable(imageResource);
        first_letter_of_the_writer_name.setImageDrawable(res);
    }
}
