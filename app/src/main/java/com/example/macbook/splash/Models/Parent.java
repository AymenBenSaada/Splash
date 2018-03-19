package com.example.macbook.splash.Models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.macbook.splash.Enum.Gender;

import java.util.Date;
import java.util.List;

/**
 * Created by Master on 21/01/2018.
 */

public class Parent {

    @Nullable
    private int id;

    @NonNull
    private String name;

    @NonNull
    private String lastName;


    @NonNull
    private String profession;

    @NonNull
    private String address;

    @NonNull
    private String email;

    //Enum
    @NonNull
    private Gender gender;

    @Nullable
    private Date birth;

    @Nullable
    private int phone;

    //Enum
    @NonNull
    private String status;

    @Nullable
    private int childCount;

    @Nullable
    private List<Child> children;

    public Parent(int id, @NonNull String name, @NonNull String lastName,
                  @NonNull String profession, @NonNull String address, @NonNull String email,
                  @NonNull Gender gender, Date birth,int Phone, @NonNull String status, int childCount) {

        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.profession = profession;
        this.address = address;
        this.email = email;
        this.gender = gender;
        this.birth = birth;
        this.status = status;
        this.childCount = childCount;
        this.phone=Phone;
    }

    @Nullable
    public int getId() {
        return id;
    }

    public void setId(@Nullable int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
    }

    @NonNull
    public String getProfession() {
        return profession;
    }

    public void setProfession(@NonNull String profession) {
        this.profession = profession;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public Gender getGender() {
        return gender;
    }

    public void setGender(@NonNull Gender gender) {
        this.gender = gender;
    }

    @Nullable
    public Date getBirth() {
        return birth;
    }

    public void setBirth(@Nullable Date birth) {
        this.birth = birth;
    }

    @NonNull
    public String getStatus() {
        return status;
    }

    public void setStatus(@NonNull String status) {
        this.status = status;
    }

    @Nullable
    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(@Nullable int childCount) {
        this.childCount = childCount;
    }

    @Nullable
    public int getPhone() {
        return phone;
    }

    public void setPhone(@Nullable int phone) {
        this.phone = phone;
    }

    @Nullable
    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(@Nullable List<Child> children) {
        this.children = children;
    }
}
