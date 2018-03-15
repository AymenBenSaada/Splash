package com.example.macbook.splash.Models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;

/**
 * Created by Master on 21/01/2018.
 */

public abstract class User {

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
}
