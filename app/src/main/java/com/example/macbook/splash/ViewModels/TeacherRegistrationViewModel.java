package com.example.macbook.splash.ViewModels;

/**
 * Created by macbook on 05/02/2018.
 */

public class TeacherRegistrationViewModel extends Person {


    private int GraduationYear;
    private int KindergardenID;

    @Override
    public String toString() {
        return "TeacherRegistrationViewModel{" +
                "GraduationYear=" + GraduationYear +
                ", KindergardenID=" + KindergardenID +
                '}';
    }

    public int getKindergardenID() {return KindergardenID;}

    public void setKindergardenID(int kindergardenID) {KindergardenID = kindergardenID;}

    public int getGraduationYear() {
        return GraduationYear;
    }

    public void setGraduationYear(int graduationYear) {
        GraduationYear = graduationYear;
    }
}

