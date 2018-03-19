package com.example.macbook.splash.Models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.macbook.splash.Enum.Gender;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Master on 14/01/2018.
 */

public class Child {

    @SerializedName("id")
    @Nullable
    private int id;

    @SerializedName("name")
    @NonNull
    private String name;

    @SerializedName("lastName")
    @NonNull
    private String lastName;

    @SerializedName("nickName")
    @NonNull
    private String nickName;

    @SerializedName("birth")
    @Nullable
    private String birth;

    @SerializedName("sex")
    @Nullable
    private Gender sex;

    @Nullable
    private List<Log> logs;

    @Nullable
    private List<Post> posts;

    @Nullable
    private int profil_pictureID;

    @Nullable
    private int kindergartenId;

    @Nullable
    public String getProfilePictureLink() {
        return profilePictureLink;
    }

    public void setProfilePictureLink(@Nullable String profilePictureLink) {
        this.profilePictureLink = profilePictureLink;
    }

    @Nullable
    private String profilePictureLink;

    public Child(int id, @NonNull String name, @NonNull String lastName,@NonNull String nickName, String birth, Gender sex,List<Log> logs,int profil_pictureID,@Nullable int kindergartenId,String ProfilePictureLink) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.nickName = nickName;
        this.birth = birth;
        this.sex = sex;
        this.logs = logs;
        this.profil_pictureID = profil_pictureID;
        this.kindergartenId = kindergartenId;
        this.profilePictureLink = ProfilePictureLink;
    }

    @Nullable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NonNull
    public String getNickName() {
        return nickName;
    }

    public void setNickName(@NonNull String nickName) {
        this.nickName = nickName;
    }

    @Nullable
    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    @Nullable
    public Gender getSex() {
        return sex;
    }

    public void setSex(Gender sex) {
        this.sex = sex;
    }

    @Nullable
    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    @Nullable
    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(@Nullable List<Post> posts) {
        this.posts = posts;
    }

    @Nullable
    public int getProfil_pictureID() {
        return profil_pictureID;
    }

    public void setProfil_pictureID(@Nullable int profil_pictureID) {
        this.profil_pictureID = profil_pictureID;
    }

    @Nullable
    public int getKindergartenId() {
        return kindergartenId;
    }

    public void setKindergartenId(@Nullable int kindergartenId) {
        this.kindergartenId = kindergartenId;
    }


 /*   public static String formattedDateFromDate( Date inputDdate){
        String   inputFormat = "EEE MMM DD HH:mm:ss Z yyyy";
        String   outputFormat = "yyyy-mm-dd'T'hh:mm:ss"; // if inputFormat = "", set a default output format.
        Date parsed = null;
        String outputDate = "";
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());

        // You can set a different Locale, This example set a locale of Country Mexico.
        //SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, new Locale("es", "MX"));
        //SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, new Locale("es", "MX"));

        try {
            parsed = df_input.parse(inputDdate.toString());
            outputDate = df_output.format(parsed);
        } catch (Exception e) {
            android.util.Log.e("formattedDateFromString", "Exception in formateDateFromstring(): " + e.getMessage());
        }
        return outputDate;

    }*/
}
