package com.example.macbook.splash.Adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.macbook.splash.Models.Kindergarten;
import com.example.macbook.splash.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by macbook on 06/02/2018.
 */

public class KindergardenAdapter extends BaseAdapter {


    private int[] IMAGES;
    private String[] NAMES;
    private String[] USERNAME;
    private String[] ADRESS;
    List<String> names = new ArrayList<String>();
    List<String> images = new ArrayList<String>();
    List<String> address = new ArrayList<String>();
    List<String> phone = new ArrayList<String>();

    public KindergardenAdapter(List<String> names, List<String> images, List<String> address, List<String> phone, Context activity, LayoutInflater layoutInflater) {
        this.names = names;
        this.images = images;
        this.address = address;
        this.phone = phone;
        this.activity = activity;
        this.layoutInflater = layoutInflater;
    }

    private Context activity;
    private LayoutInflater layoutInflater;
    //private List<Kindergarten> kindergartenList;

    public KindergardenAdapter(int[] IMAGES, String[] NAMES, String[] USERNAME, String[] ADRESS, Context activity, LayoutInflater layoutInflater) {
        this.IMAGES = IMAGES;
        this.NAMES = NAMES;
        this.USERNAME = USERNAME;
        this.ADRESS = ADRESS;
        this.layoutInflater = layoutInflater;
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return names.size();
    }

    /*private void update(List<Kindergarten> kindergartenList){
        this.kindergartenList = kindergartenList;
        notifyDataSetChanged();
    }*/

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = layoutInflater.inflate(R.layout.customlayout_kindergarden, null);

        //SETTING VIEWS
        ImageView imageView = (ImageView) view.findViewById(R.id.photoKindergarden);
        TextView name = (TextView) view.findViewById(R.id.tv_nameKindergardenList);
        TextView tvKindergardenUserName = (TextView) view.findViewById(R.id.tvKindergardenUserName);
        TextView tvAdresseKindergardenList = (TextView) view.findViewById(R.id.tvAdresseKindergardenList);

        //SETTING FONTS
        Typeface myCustomFont = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Thin.ttf");
        name.setTypeface(myCustomFont);
        tvAdresseKindergardenList.setTypeface(myCustomFont);
        tvKindergardenUserName.setTypeface(myCustomFont);

        //SETTING CONTENT
        imageView.setImageResource(R.drawable.aymen);
        name.setText(""+(names.get(i)));
        tvKindergardenUserName.setText("( "+phone.get(i)+" )");
        tvAdresseKindergardenList.setText(""+address.get(i));


        return view;
    }
}