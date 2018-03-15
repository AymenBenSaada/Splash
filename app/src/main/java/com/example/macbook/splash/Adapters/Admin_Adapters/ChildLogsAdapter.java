package com.example.macbook.splash.Adapters.Admin_Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.macbook.splash.Models.Log;
import com.example.macbook.splash.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by macbook on 11/03/2018.
 */

public class ChildLogsAdapter extends BaseAdapter {

    private List<Log> logs;
    private Activity activity;

    public ChildLogsAdapter(List<Log> logs, Activity activity) {
        this.logs = logs;
        this.activity = activity;
    }

    private CircleImageView first_letter_of_the_writer_name;

    @Override
    public int getCount() {
        return logs.size();
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

        convertView = activity.getLayoutInflater().inflate(R.layout.customlayout_logs_per_child,null);

        first_letter_of_the_writer_name = (CircleImageView)convertView.findViewById(R.id.first_letter_of_the_writer_name);
        TextView log_title = (TextView)convertView.findViewById(R.id.log_title);
        TextView log_text = (TextView)convertView.findViewById(R.id.log_text);

        //SETTING FONTS
        Typeface myCustomFont = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Thin.ttf");
        log_text.setTypeface(myCustomFont);
        log_title.setTypeface(myCustomFont);

        getTheLetterImageFromTheLogWriterFirstName(logs.get(position).getWriter());
        log_title.setText(logs.get(position).getTitle());
        log_text.setText(logs.get(position).getContent());

        return convertView;
    }

    private void getTheLetterImageFromTheLogWriterFirstName(String name){
        String uri = "@drawable/letter_";  // where myresource (without the extension) is the file

        uri += name.charAt(0);
        int imageResource = activity.getResources().getIdentifier(uri, null, activity.getPackageName());

        Drawable res = activity.getResources().getDrawable(imageResource);
        first_letter_of_the_writer_name.setImageDrawable(res);
    }
}
