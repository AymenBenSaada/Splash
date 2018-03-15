package com.example.macbook.splash.Models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Master on 21/01/2018.
 */

public class LoginModelView {

    @NonNull
    private String email;

    @NonNull
    private String password;

    @NonNull
    private boolean rememberMe;

    @Nullable
    private String token;

    @Nullable
    private String gender;

    public LoginModelView(@NonNull String email, @NonNull String password, @NonNull boolean rememberMe) {
        this.email = email;
        this.password = password;
        this.rememberMe = rememberMe;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull
    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(@NonNull boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Nullable
    public String getToken() {
        return token;
    }

    public void setToken(@Nullable String token) {
        this.token = token;
    }

    @Nullable
    public String getGender() {
        return gender;
    }

    public void setGender(@Nullable String gender) {
        this.gender = gender;
    }
}
