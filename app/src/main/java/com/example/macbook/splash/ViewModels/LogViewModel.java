package com.example.macbook.splash.ViewModels;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Master on 21/01/2018.
 */

public class LogViewModel {

    @NonNull
    private int id;

    @NonNull
    private String title;

    @NonNull
    private String content;

    @NonNull
    private Date date;

    @NonNull
    private int childId;

    @NonNull
    private int writerId;

    public LogViewModel(@NonNull int id, @NonNull String title, @NonNull String content,
               @NonNull Date date, @NonNull int childId, @NonNull int writerId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.childId = childId;
        this.writerId = writerId;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
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
    public int getChildId() {
        return childId;
    }

    public void setChildId(@NonNull int childId) {
        this.childId = childId;
    }

    @NonNull
    public int getWriterId() {
        return writerId;
    }

    public void setWriterId(@NonNull int writerId) {
        this.writerId = writerId;
    }
}