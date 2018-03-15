package com.example.macbook.splash.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.macbook.splash.Models.Child;
import com.example.macbook.splash.R;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

/**
 * Created by macbook on 21/02/2018.
 */

public class NewLogChildrenListAdapter extends BaseAdapter {

    private List<Child> children;
    private Activity activity;
    private LayoutInflater layoutInflater;

    public NewLogChildrenListAdapter(List<Child> children, Activity activity, LayoutInflater layoutInflater) {
        this.children = children;
        this.activity = activity;
        this.layoutInflater = layoutInflater;
    }

    public void update(List<Child> new_children){
        this.children = new_children;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return children.size();
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

        convertView = layoutInflater.inflate(R.layout.customlayout_childlist_in_newlog, null);

        CircleImageView child_profilpicture_in_newlog = (CircleImageView)convertView.findViewById(R.id.child_profilpicture_in_newlog);
        TextView childfullname_in_newlog = (TextView)convertView.findViewById(R.id.childfullname_in_newlog);
        TextView childnickname_in_newlog = (TextView)convertView.findViewById(R.id.childnickname_in_newlog);

        //SETTING FONTS
        Typeface myCustomFont = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Thin.ttf");
        Typeface myCustomFont1 = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Light.ttf");
        childfullname_in_newlog.setTypeface(myCustomFont);
        childnickname_in_newlog.setTypeface(myCustomFont);

        //SETTING CONTENT
        File file=new File(activity.getFilesDir(), "child_profil_picture_"+ children.get(position).getId()+".dat");

        child_profilpicture_in_newlog.setImageURI(Uri.fromFile(file));

        childfullname_in_newlog.setText((children.get(position).getName()+" "+children.get(position).getLastName()));
        childnickname_in_newlog.setText(("( "+children.get(position).getNickName()+" )"));

        return convertView;
    }


}
