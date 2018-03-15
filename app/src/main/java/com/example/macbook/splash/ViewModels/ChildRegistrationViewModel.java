package com.example.macbook.splash.ViewModels;

import android.support.annotation.Nullable;

import com.example.macbook.splash.Enum.Gender;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by macbook on 06/02/2018.
 */

public class ChildRegistrationViewModel implements Serializable {
    @Nullable
    private int Id;
    private String Name;
    private String LastName;
    private Date Birthday;
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

    public Date getBirthday() {
        return Birthday;
    }

    public void setBirthday(Date birthday) {
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
}
