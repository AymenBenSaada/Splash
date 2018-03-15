package com.example.macbook.splash.Models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Master on 21/01/2018.
 */

public class Media {

    @Nullable
    private int id;

    @NonNull
    private String path;

    @Nullable
    private int descriminatorId;

    @Nullable
    private String descriminator;

    public Media(@Nullable int id, @NonNull String path, @Nullable int descriminatorId,
                 @Nullable String descriminator) {
        this.id = id;
        this.path = path;
        this.descriminatorId = descriminatorId;
        this.descriminator = descriminator;
    }

    @Nullable
    public int getId() {
        return id;
    }

    public void setId(@Nullable int id) {
        this.id = id;
    }

    @NonNull
    public String getPath() {
        return path;
    }

    public void setPath(@NonNull String path) {
        this.path = path;
    }

    @Nullable
    public int getDescriminatorId() {
        return descriminatorId;
    }

    public void setDescriminatorId(@Nullable int descriminatorId) {
        this.descriminatorId = descriminatorId;
    }

    @Nullable
    public String getDescriminator() {
        return descriminator;
    }

    public void setDescriminator(@Nullable String descriminator) {
        this.descriminator = descriminator;
    }
}
