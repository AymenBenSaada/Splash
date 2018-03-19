package com.example.macbook.splash.ViewModels;



import android.util.Log;

import com.example.macbook.splash.Enum.Gender;
import com.example.macbook.splash.Enum.Status;
import com.example.macbook.splash.Models.Parent;
import com.example.macbook.splash.Models.Teacher;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
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
    private String datest;
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
        parentRegistrationViewModel.setBirth(this.datest);

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
        teacherRegistrationViewModel.setBirth(this.datest);

        return teacherRegistrationViewModel;
    }
    public Teacher ToTeacher(){

        TeacherRegistrationViewModel teacherRegistrationViewModel = this.ConvertToTeacher();
        Date graduationDate = new Date(teacherRegistrationViewModel.getGraduationYear());
        Teacher teacher = new Teacher(0,this.Name,this.LastName,this.Adress,this.Email,
                this.Gender,this.Birth,this.Phone,this.Status,graduationDate,null,null,-1);

        return teacher;
    }
    public Parent ToParent(){
        ParentRegistrationViewModel parentRegistrationViewModel = this.ConvertToParent();
        Parent parent = new Parent(0,this.Name,this.LastName,null,this.Adress,this.Email,
                this.Gender,this.Birth,this.Phone,this.Status.toString(),parentRegistrationViewModel.getChildrenCount());
        return parent;
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

    public String getBirth() {
        return datest;
    }

    public void setBirth(String birth) {
        datest = formattedDateFromString("dd/mm/yyyy","yyyy-mm-dd'T'hh:mm:ss",birth);
       ////////////////////// this needs to be fixed ////////////////////
        Birth = new Date();
    }

    public static String formattedDateFromString(String inputFormat, String outputFormat, String inputDate){
        if(inputFormat.equals("")){ // if inputFormat = "", set a default input format.
            inputFormat = "yyyy-MM-dd hh:mm:ss";
        }
        if(outputFormat.equals("")){
            outputFormat = "EEEE d 'de' MMMM 'del' yyyy"; // if inputFormat = "", set a default output format.
        }
        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        // You can set a different Locale, This example set a locale of Country Mexico.
        //SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, new Locale("es", "MX"));
        //SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, new Locale("es", "MX"));

        try {
            parsed = df_input.parse(inputDate);

            outputDate = df_output.format(parsed);
        } catch (Exception e) {
            Log.e("formattedDateFromString", "Exception in formateDateFromstring(): " + e.getMessage());
        }
        return outputDate;

    }


}
