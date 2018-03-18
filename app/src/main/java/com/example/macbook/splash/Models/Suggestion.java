package com.example.macbook.splash.Models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.macbook.splash.Enum.SuggestionCategory;
import com.example.macbook.splash.Models.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by macbook on 08/02/2018.
 */

public class Suggestion implements Serializable, Comparable<Suggestion>{

    @Nullable
    private int Id;
    private String Title;
    private String Text;
    private java.util.Date Date;
    private int Recompense;
    private SuggestionCategory Category;
    private int Difficulty;
    @Nullable
    private String JsonReferences;
    @Nullable
    private String videoYoutube;

    private String imageUrl;

    public Suggestion(int Id, String title, String text, java.util.Date date, int recompense, SuggestionCategory category, int difficulty, List<Reference> References, String videoYoutube) {
        this.Id = Id;
        Title = title;
        Text = text;
        Date = date;
        Recompense = recompense;
        Category = category;
        Difficulty = difficulty;
        Gson json = new Gson();
        JsonReferences = json.toJson(References);
        this.videoYoutube = videoYoutube;
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

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }





    public int getRecompense() {
        return Recompense;
    }

    public void setRecompense(int recompense) {
        Recompense = recompense;
    }

    public SuggestionCategory getCategory() {
        return Category;
    }

    public void setCategory(SuggestionCategory category) {
        Category = category;
    }

    public int getDifficulty() {
        return Difficulty;
    }

    public void setDifficulty(int difficulty) {
        Difficulty = difficulty;
    }

    @Nullable
    public List<com.example.macbook.splash.Models.Reference> getReferences() {

        Gson json = new Gson();
        Type type = new TypeToken<List<Reference>>(){}.getType();
        return json.fromJson(JsonReferences,type);

    }

    @Nullable
    public String getReferencesJson() {
        return JsonReferences;
    }

    public void setReferences(@Nullable com.example.macbook.splash.Models.Reference reference) {
        Gson json = new Gson();
        JsonReferences = json.toJson(reference);
    }

    @Nullable
    public String getVideoYoutube() {
        return videoYoutube;
    }

    public void setVideoYoutube(@Nullable String videoYoutube) {
        this.videoYoutube = videoYoutube;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int compareTo(@NonNull Suggestion o) {
        return getDate().compareTo(o.getDate());
    }

}


