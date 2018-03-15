package com.example.macbook.splash.Models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import java.util.List;

/**
 * Created by Master on 21/01/2018.
 */

public class Kindergarten {

    @Nullable
    private int id;

    @NonNull
    private String name;

    @NonNull
    private String address;

    @NonNull
    private String email;

    @NonNull
    private int phone;

    @Nullable
    private int profil_picture_id;


    public Kindergarten(int id, @NonNull String name, @NonNull String address, @NonNull String email, @NonNull int phone, int profil_picture_id) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.profil_picture_id = profil_picture_id;
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
    public int getPhone() {
        return phone;
    }

    public void setPhone(@NonNull int phone) {
        this.phone = phone;
    }

    @Nullable
    public int getProfil_picture_id() {
        return profil_picture_id;
    }

    public void setProfil_picture_id(@Nullable int profil_picture_id) {
        this.profil_picture_id = profil_picture_id;
    }
}
