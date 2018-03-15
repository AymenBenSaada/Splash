package com.example.macbook.splash.ViewModels;

import com.example.macbook.splash.Enum.Gender;
import com.example.macbook.splash.Enum.Status;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by macbook on 05/02/2018.
 */

public class Person implements Serializable {
    private String Email;
    private String Password;
    private String Adress;
    private com.example.macbook.splash.Enum.Status Status;
    private int Phone;
    private String Name;
    private String LastName;
    private Gender Gender;
    private Date Birth;

    public ParentRegistrationViewModel ConvertToParent(){
        ParentRegistrationViewModel parentRegistrationViewModel = new ParentRegistrationViewModel();

        parentRegistrationViewModel.setName(this.Name);
        parentRegistrationViewModel.setLastName(this.LastName);
        parentRegistrationViewModel.setAdress(this.Adress);
        parentRegistrationViewModel.setEmail(this.Email);
        parentRegistrationViewModel.setPassword(this.Password);
        parentRegistrationViewModel.setPhone(this.Phone);
        parentRegistrationViewModel.setStatus(this.Status);
        parentRegistrationViewModel.setGender(this.Gender);
        parentRegistrationViewModel.setBirth(this.Birth);

        return parentRegistrationViewModel;
    }

    public TeacherRegistrationViewModel ConvertToTeacher(){
        TeacherRegistrationViewModel teacherRegistrationViewModel = new TeacherRegistrationViewModel();

        teacherRegistrationViewModel.setName(this.Name);
        teacherRegistrationViewModel.setLastName(this.LastName);
        teacherRegistrationViewModel.setAdress(this.Adress);
        teacherRegistrationViewModel.setEmail(this.Email);
        teacherRegistrationViewModel.setPassword(this.Password);
        teacherRegistrationViewModel.setPhone(this.Phone);
        teacherRegistrationViewModel.setStatus(this.Status);
        teacherRegistrationViewModel.setGender(this.Gender);
        teacherRegistrationViewModel.setBirth(this.Birth);

        return teacherRegistrationViewModel;
    }


    @Override
    public String toString() {
        return "PersonViewModel{" +
                "Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                ", Adress='" + Adress + '\'' +
                ", Status='" + Status + '\'' +
                ", Phone=" + Phone +
                ", Name='" + Name + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Gender=" + Gender +
                ", Birth=" + Birth +
                '}';
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

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public Status getStatus() {
        return Status;
    }

    public void setStatus(Status status) {
        Status = status;
    }

    public int getPhone() {
        return Phone;
    }

    public void setPhone(int phone) {
        Phone = phone;
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

    public com.example.macbook.splash.Enum.Gender getGender() {
        return Gender;
    }

    public void setGender(com.example.macbook.splash.Enum.Gender gender) {
        Gender = gender;
    }

    public Date getBirth() {
        return Birth;
    }

    public void setBirth(Date birth) {
        Birth = birth;
    }

}
