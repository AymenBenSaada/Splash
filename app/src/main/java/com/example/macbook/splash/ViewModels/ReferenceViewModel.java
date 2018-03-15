package com.example.macbook.splash.ViewModels;

import android.support.annotation.Nullable;

import com.example.macbook.splash.Models.Reference;

import java.io.Serializable;

/**
 * Created by macbook on 08/02/2018.
 */

public class ReferenceViewModel implements Serializable {

    @Nullable
    private int Id;

    private String Title;

    private String Link;

    private boolean IsClicked = false;

    public ReferenceViewModel(Reference reference) {
        Title = reference.getTitle();
        Link = reference.getLink();
    }

    @Nullable
    public int getId() {
        return Id;
    }

    public void setId(@Nullable int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public boolean isClicked() {
        return IsClicked;
    }

    public void setClicked(boolean clicked) {
        IsClicked = clicked;
    }
}
