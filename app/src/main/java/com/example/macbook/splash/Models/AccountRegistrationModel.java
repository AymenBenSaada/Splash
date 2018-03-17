package com.example.macbook.splash.Models;

import com.example.macbook.splash.Enum.Gender;
import com.example.macbook.splash.Enum.Status;
import com.example.macbook.splash.ViewModels.Person;

import java.util.Date;

/**
 * Created by nader on 3/17/18.
 */

public class AccountRegistrationModel {
    private String Email;
    private String Password;
    private String Name;
    private String LastName;

    public AccountRegistrationModel(Person person){
        setEmail(person.getEmail());

        setPassword(person.getPassword());

        setName(person.getName());

        setLastName(person.getLastName());
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
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
}
