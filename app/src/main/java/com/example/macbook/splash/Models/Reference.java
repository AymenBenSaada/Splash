package com.example.macbook.splash.Models;

import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * Created by macbook on 08/02/2018.
 */

public class Reference implements Serializable {

    @Nullable
    private int Id;

    private String Title;

    private String Link;

    public Reference(String title, String link) {
        Title = title;
        Link = link;
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

}
