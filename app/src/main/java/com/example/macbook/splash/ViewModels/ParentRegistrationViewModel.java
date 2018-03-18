package com.example.macbook.splash.ViewModels;

import android.support.annotation.Nullable;
import android.util.Log;

import com.example.macbook.splash.Models.Child;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by macbook on 05/02/2018.
 */

public class ParentRegistrationViewModel extends Person{

    @Nullable
    private int ChildrenCount = 0;

    @Nullable
    private List<ChildRegistrationViewModel> Children;

    @Nullable
    private String EmailLinking;



    @Override
    public String toString() {
        return "ParentRegistrationViewModel{" +
                "ChildrenCount=" + ChildrenCount +
                ", Children=" + Children +
                ", EmailLinking='" + EmailLinking + '\'' +
                '}';
    }


    @Nullable
    public String getEmailLinking() {return EmailLinking;}

    public void setEmailLinking(@Nullable String emailLinking) {EmailLinking = emailLinking;}

    public List<ChildRegistrationViewModel> getChildren() {
        return Children;
    }

    public void setChildren(List<ChildRegistrationViewModel> children) {
        Children = children;
    }

    public void addChild(List<ChildRegistrationViewModel> children, ChildRegistrationViewModel child){
        children.add(child);

    }

    public int getChildrenCount() {
        return ChildrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        ChildrenCount = childrenCount;
    }
}

