package com.example.macbook.splash.ViewModels;

import android.support.annotation.Nullable;
import android.util.Log;

import com.example.macbook.splash.Enum.Gender;
import com.example.macbook.splash.Models.Child;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by macbook on 06/02/2018.
 */

public class ChildRegistrationViewModel implements Serializable {
    @Nullable
    private int Id;
    private String Name;
    private String LastName;
    private String Birthday;
    private Gender Gender;
    @Nullable
    private String ProfilePicture;
    @Nullable
    private int KindergardenID;

    @Override
    public String toString() {
        return "ChildRegistrationViewModel{" +
                "Name='" + Name + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Birthday=" + Birthday +
                ", Gender=" + Gender +
                ", ProfilePicture='" + ProfilePicture + '\'' +
                ", KindergardenID=" + KindergardenID +
                '}';
    }
    public Child ToChild(){
        return new Child(Id,Name,LastName,"",Birthday,Gender,null,-1,KindergardenID,ProfilePicture);
    }
    @Nullable
    public int getId() {
        return Id;
    }

    public void setId(@Nullable int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public com.example.macbook.splash.Enum.Gender getGender() {
        return Gender;
    }

    public void setGender(com.example.macbook.splash.Enum.Gender gender) {
        Gender = gender;
    }

    @Nullable
    public String getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(@Nullable String profilePicture) {
        ProfilePicture = profilePicture;
    }

    @Nullable
    public int getKindergardenID() {
        return KindergardenID;
    }

    public void setKindergardenID(@Nullable int kindergardenID) {
        KindergardenID = kindergardenID;
    }


    /*public static String formattedDateFromDate( Date inputDdate){
        String   outputFormat = "yyyy-mm-dd'T'hh:mm:ss"; // if inputFormat = "", set a default output format.
        Date parsed = null;
        String outputDate = "";
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        // You can set a different Locale, This example set a locale of Country Mexico.
        //SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, new Locale("es", "MX"));
        //SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, new Locale("es", "MX"));

        try {
            outputDate = df_output.format(inputDdate);
        } catch (Exception e) {
            Log.e("formattedDateFromString", "Exception in formateDateFromstring(): " + e.getMessage());
        }
        return outputDate;

    }*/
}
