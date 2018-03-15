package com.example.macbook.splash.SwipePagers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.macbook.splash.Admin.Admin_Demandes_Enseignant_Fragment;
import com.example.macbook.splash.Admin.Admin_Demandes_Parent_Fragment;
import com.example.macbook.splash.Admin.Admin_Groupes_OnGroupSel_Children_Fragment;
import com.example.macbook.splash.Admin.Admin_Groupes_OnGroupSel_Teachers_Fragment;

/**
 * Created by macbook on 04/03/2018.
 */

public class AdminGroupesOnGroupSelected extends FragmentStatePagerAdapter {

    int nbrOfTabs;

    public AdminGroupesOnGroupSelected(FragmentManager fm, int nbrOfTabs) {
        super(fm);
        this.nbrOfTabs = nbrOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return new Admin_Groupes_OnGroupSel_Children_Fragment();
            case 1 :
                return new Admin_Groupes_OnGroupSel_Teachers_Fragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return nbrOfTabs;
    }
}
