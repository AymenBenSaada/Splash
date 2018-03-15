package com.example.macbook.splash.Models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;

/**
 * Created by Master on 21/01/2018.
 */

public class Post {

    @Nullable
    private int id;

    @NonNull
    private String content;

    @NonNull
    private Date date;

    @NonNull
    private String legend;

    @NonNull
    private int teacherId;

    @Nullable
    private int[] childrenIds;

    public Post(int id, @NonNull String content, @NonNull Date date, @NonNull String legend, @NonNull int teacherId) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.legend = legend;
        this.teacherId = teacherId;
    }

    @Nullable
    public int getId() {
        return id;
    }

    public void setId(@Nullable int id) {
        this.id = id;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    public void setContent(@NonNull String content) {
        this.content = content;
    }

    @NonNull
    public Date getDate() {
        return date;
    }

    public void setDate(@NonNull Date date) {
        this.date = date;
    }

    @NonNull
    public String getLegend() {
        return legend;
    }

    public void setLegend(@NonNull String legend) {
        this.legend = legend;
    }

    @NonNull
    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(@NonNull int teacherId) {
        this.teacherId = teacherId;
    }

    @Nullable
    public int[] getChildrenIds() {
        return childrenIds;
    }

    public void setChildrenIds(@Nullable int[] childrenIds) {
        this.childrenIds = childrenIds;
    }
}
