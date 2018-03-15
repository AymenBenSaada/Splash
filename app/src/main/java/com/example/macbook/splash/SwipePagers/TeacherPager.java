package com.example.macbook.splash.SwipePagers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.macbook.splash.Fragments.CameraFragment;
import com.example.macbook.splash.Fragments.FeedFragment;
import com.example.macbook.splash.Fragments.LogsFragment;
import com.example.macbook.splash.Fragments.SuggestionsFragment;

/**
 * Created by macbook on 10/02/2018.
 */

public class TeacherPager extends FragmentStatePagerAdapter {


    int tabCount;

    public TeacherPager(FragmentManager fm,int tabCount){
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                SuggestionsFragment suggestionsFragment = new SuggestionsFragment();
                return suggestionsFragment;
            case 1:
                CameraFragment cameraFragment = new CameraFragment();
                return cameraFragment;
            case 2:
                LogsFragment logsFragment = new LogsFragment();
                return logsFragment;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
