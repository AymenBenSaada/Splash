package com.example.macbook.splash.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.macbook.splash.Models.Comment;
import com.example.macbook.splash.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by macbook on 15/02/2018.
 */

public class LogAdapter extends BaseAdapter {

    private List<Comment> comments;
    private Context context;
    private LayoutInflater layoutInflater;

    public LogAdapter(List<Comment> comments, Context context, LayoutInflater layoutInflater) {
        this.comments = comments;
        this.context = context;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        int resultat = 0;
        if(comments != null){
            resultat = comments.size();
        }
        return resultat;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void updateComments(List<Comment> new_comments){
        this.comments = new_comments;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.customlayout_comment_into_log_per_child, null);

        TextView comment_writer_name = (TextView)convertView.findViewById(R.id.comment_writer_name);
        TextView comment_content = (TextView)convertView.findViewById(R.id.comment_content);
        TextView comment_date = (TextView)convertView.findViewById(R.id.comment_date);

        //SETTING FONTS
        Typeface myCustomFont = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Thin.ttf");
        Typeface myCustomFont1 = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");

        comment_writer_name.setTypeface(myCustomFont1);
        comment_content.setTypeface(myCustomFont);
        comment_date.setTypeface(myCustomFont1);



        //SETTING CONTENT
        comment_writer_name.setText((comments.get(position).getWriter()+":"));
        comment_content.setText(comments.get(position).getContent());


        long msDiff = Calendar.getInstance().getTimeInMillis() - toCalendar(comments.get(position).getDate()).getTimeInMillis();
        long daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);
        comment_date.setText(Long.toString(daysDiff) + " j");

        return convertView;
    }


    public static Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

}
