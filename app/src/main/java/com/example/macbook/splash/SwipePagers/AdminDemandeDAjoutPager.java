package com.example.macbook.splash.SwipePagers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.macbook.splash.Admin.Admin_Demandes_Enseignant_Fragment;
import com.example.macbook.splash.Admin.Admin_Demandes_Parent_Fragment;
import com.example.macbook.splash.Interfaces.ApiClient;
import com.example.macbook.splash.Interfaces.IKindergartensApi;
import com.example.macbook.splash.Interfaces.ITeachersApi;
import com.example.macbook.splash.Models.Teacher;
import com.example.macbook.splash.Models.TeacherInscriptionRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by macbook on 04/03/2018.
 */

public class AdminDemandeDAjoutPager extends FragmentStatePagerAdapter {

    int nbrOfTabs;

    public AdminDemandeDAjoutPager(FragmentManager fm, int nbrOfTabs) {
        super(fm);
        this.nbrOfTabs = nbrOfTabs;
    }
    private IKindergartensApi iKindergartensApi;
    private ITeachersApi iTeachersApi;
    private List<Teacher> listOfTeacherSenders = new ArrayList<>();

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1 :{

                return new Admin_Demandes_Enseignant_Fragment();

            }
            case 0 :
                return new Admin_Demandes_Parent_Fragment();

            default:
                return null;
        }
    }



    @Override
    public int getCount() {
        return nbrOfTabs;
    }
}
