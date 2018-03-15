package com.example.macbook.splash.Models;

import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by Master on 09/02/2018.
 */

public class Group {

    private int id;

    @Nullable
    private String name;

    @Nullable
    private List<Child> children;

    public Group(int id, List<Child> children, String name) {
        this.id = id;
        this.children = children;
        this.name = name;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        id = id;
    }

    @Nullable
    public String getName() {return name;}

    public void setName(@Nullable String name) {this.name = name;}

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }
}
