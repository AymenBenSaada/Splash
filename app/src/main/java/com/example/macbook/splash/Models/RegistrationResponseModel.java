package com.example.macbook.splash.Models;

/**
 * Created by nader on 3/17/18.
 */

public class RegistrationResponseModel {
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public RegistrationResponseModel(int userId, String userDescriptor) {
        this.userId = userId;
        this.userDescriptor = userDescriptor;
    }

    private int userId ;
    private String userDescriptor;
}
