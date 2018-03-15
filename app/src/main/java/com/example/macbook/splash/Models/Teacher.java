package com.example.macbook.splash.Models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.macbook.splash.Enum.Gender;
import com.example.macbook.splash.Enum.Status;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by Master on 21/01/2018.
 */

public class Teacher {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("adress")
    private String address;

    @SerializedName("email")
    private String email;

    @SerializedName("gender")
    private Gender gender;

    @SerializedName("birth")
    private Date birth;

    @SerializedName("phone")
    private int phone;

    @SerializedName("status")
    private Status status;

    @Nullable
    @SerializedName("graduationDate")
    private Date graduationDate;

    @Nullable
    @SerializedName("xGroups")
    private List<Group> groups;

    @Nullable
    @SerializedName("suggestions")
    private List<Suggestion> suggestions;

    @Nullable
    private int profil_pictureID;

    @SerializedName("kindergartenId")
    private int kindergartenId;

    public Teacher(int id, String name, String lastName, String address, String email, Gender gender, Date birth,int phone, Status status, Date graduationDate, List<Group> groups, List<Suggestion> suggestions, int kindergartenId) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.gender = gender;
        this.birth = birth;
        this.phone = phone;
        this.status = status;
        this.graduationDate = graduationDate;
        this.groups = groups;
        this.suggestions = suggestions;
        this.kindergartenId = kindergartenId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(Date graduationDate) {
        this.graduationDate = graduationDate;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

    public int getKindergartenId() {
        return kindergartenId;
    }

    public void setKindergartenId(int kindergartenId) {
        this.kindergartenId = kindergartenId;
    }

    @Nullable
    public int getProfil_pictureID() {
        return profil_pictureID;
    }

    public void setProfil_pictureID(@Nullable int profil_pictureID) {
        this.profil_pictureID = profil_pictureID;
    }


}