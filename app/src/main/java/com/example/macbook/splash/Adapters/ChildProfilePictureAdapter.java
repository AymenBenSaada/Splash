package com.example.macbook.splash.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.ObservableArrayList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.example.macbook.splash.Interfaces.ApiClient;
import com.example.macbook.splash.Interfaces.IChildrenApi;
import com.example.macbook.splash.Models.Child;
import com.example.macbook.splash.R;
import com.example.macbook.splash.Repositories.ChildrenRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

/**
 * Created by macbook on 12/02/2018.
 */

public class ChildProfilePictureAdapter extends BaseAdapter {


    private List<Child> childList;
    private ArrayList<Boolean> IsChildSelected;
    private Context activity;
    private LayoutInflater layoutInflater;

    public ChildProfilePictureAdapter(List<Child> childList,ArrayList<Boolean> IsChildSelected, Context activity,LayoutInflater layoutInflater) {
        this.childList = childList;
        this.IsChildSelected = IsChildSelected;
        this.activity = activity;
        this.layoutInflater = layoutInflater;
    }

    public void update(ArrayList<Boolean> IsChildSelected){
        this.IsChildSelected = IsChildSelected;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() { return IsChildSelected.size();}

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

        convertView = layoutInflater.inflate(R.layout.customlayout_camera_children_profile_pictures, null);

        // Setting the view
        CircleImageView profilePicture = (CircleImageView) convertView.findViewById(R.id.childProfilePictureListViewForTeacherSelectingChildren);
        profilePicture.setBorderColor(Color.WHITE);
        ImageView selectedChildMark = (ImageView)convertView.findViewById(R.id.selectedChildMark);

        // writer : Nader
        // Filling the view
        // get child  ID form list of children insert in the method and dont forget to remove the context

        if(position == 0){
            profilePicture.setImageResource(R.drawable.selectall);
        }else{
            File file = new File(activity.getFilesDir() + "/" +"child_profil_picture_"+childList.get(position-1).getId()+".dat");
            profilePicture.setImageURI(Uri.fromFile(file));
        }

        if(IsChildSelected.get(position)){
            selectedChildMark.setVisibility(convertView.VISIBLE);
        }else {
            selectedChildMark.setVisibility(convertView.INVISIBLE);
        }

        return convertView;
    }
}
