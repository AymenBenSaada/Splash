package com.example.macbook.splash.Admin;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.macbook.splash.Adapters.GsonTimeAdapter;
import com.example.macbook.splash.Models.Child;
import com.example.macbook.splash.Models.Teacher;
import com.example.macbook.splash.R;
import com.example.macbook.splash.SwipePagers.AdminDemandeDAjoutPager;
import com.example.macbook.splash.SwipePagers.AdminGroupesOnGroupSelected;
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
import java.util.Date;
import java.util.List;

public class Admin_Groupes_OnGroupSelected_Activity extends AppCompatActivity implements Admin_Groupes_OnGroupSel_Children_Fragment.OnFragmentInteractionListener,Admin_Groupes_OnGroupSel_Teachers_Fragment.OnFragmentInteractionListener{

    public int groupID;
    public int kindergartenID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_groupes_on_group_selected);


        Intent intent = getIntent();

        kindergartenID = intent.getIntExtra("kindergartenID",-1);
        groupID = intent.getIntExtra("groupID",-1);
        String groupName = intent.getStringExtra("groupName");

        setTitle("  "+groupName.toUpperCase());


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout_children_teacher_list_in_groupes);
        tabLayout.addTab(tabLayout.newTab().setText("ENFANTS"));
        tabLayout.addTab(tabLayout.newTab().setText("ENSEIGNANTS"));
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager_children_teacher_list_in_groupes);
        final PagerAdapter pagerAdapter = new AdminGroupesOnGroupSelected(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }




    public int getGroupID() {
        return groupID;
    }

    public int getKindergartenID() {
        return kindergartenID;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
