package com.example.macbook.splash.Admin;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.macbook.splash.R;
import com.example.macbook.splash.SwipePagers.AdminDemandeDAjoutPager;

public class Admin_Demandes_Activity extends AppCompatActivity implements Admin_Demandes_Parent_Fragment.OnFragmentInteractionListener,Admin_Demandes_Enseignant_Fragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_demandes);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout_parent_teacher_list_in_admin);
        tabLayout.addTab(tabLayout.newTab().setText("PARENTS"));
        tabLayout.addTab(tabLayout.newTab().setText("ENSEIGNANTS"));
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager_parent_teacher_list_in_admin);
        final PagerAdapter pagerAdapter = new AdminDemandeDAjoutPager(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
