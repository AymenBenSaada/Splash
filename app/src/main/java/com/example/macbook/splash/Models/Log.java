package com.example.macbook.splash.Models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;
import java.util.List;

/**
 * Created by Master on 21/01/2018.
 */

public class Log {

    @NonNull
    private int id;

    @NonNull
    private String title;

    @NonNull
    private String content;

    @NonNull
    private Date date;

    @Nullable
    private String writer;

    @Nullable
    private List<Comment> comments;

    public Log(@NonNull int id, @NonNull String title, @NonNull String content,
               @NonNull Date date, @Nullable String writer, @Nullable List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.writer = writer;
        this.comments = comments;
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

    @Nullable
    public String getWriter() {
        return writer;
    }

    public void setWriter(@Nullable String writer) {
        this.writer = writer;
    }

    @Nullable
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(@Nullable List<Comment> comments) {
        this.comments = comments;
    }
}