package com.example.macbook.splash.Models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;
import java.util.List;

/**
 * Created by Master on 21/01/2018.
 */

public class Admin {

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
    private String gender;

    @Nullable
    private Date birth;

    //Enum
    @NonNull
    private String status;

    @Nullable
    private int kindergartenId;

    @Nullable
    private List<Message> messages;

    public Admin(int id, @NonNull String name, @NonNull String lastName,
                 @NonNull String profession, @NonNull String address, @NonNull String email,
                 @NonNull String gender, Date birth, @NonNull String status, int kindergartenId,
                 List<Message> messages) {

        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.profession = profession;
        this.address = address;
        this.email = email;
        this.gender = gender;
        this.birth = birth;
        this.status = status;
        this.kindergartenId = kindergartenId;
        this.messages = messages;
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
    public String getGender() {
        return gender;
    }

    public void setGender(@NonNull String gender) {
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
    public int getKindergartenId() {
        return kindergartenId;
    }

    public void setKindergartenId(@Nullable int kindergartenId) {
        this.kindergartenId = kindergartenId;
    }

    @Nullable
    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(@Nullable List<Message> messages) {
        this.messages = messages;
    }
}
