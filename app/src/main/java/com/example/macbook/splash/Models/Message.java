package com.example.macbook.splash.Models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;

/**
 * Created by Master on 21/01/2018.
 */

public class Message {

    @Nullable
    private int id;

    private String title;

    @NonNull
    private String content;

    @NonNull
    private Date date;

    @Nullable
    private int kindergartenId;

    public Message(int id,String title, @NonNull String content, @NonNull Date date, int kindergartenId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.kindergartenId = kindergartenId;
    }

    @Nullable
    public int getId() {
        return id;
    }

    public void setId(@Nullable int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
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
    public int getKindergartenId() {
        return kindergartenId;
    }

    public void setKindergartenId(@Nullable int adminId) {
        this.kindergartenId = adminId;
    }
}
