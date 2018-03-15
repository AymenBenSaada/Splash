package com.example.macbook.splash.ViewModels;

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

public class SuggestionViewModel implements Serializable, Comparable<SuggestionViewModel>{

    @Nullable
    private int Id;
    private String Title;
    private String Text;
    private java.util.Date Date;
    private int Recompense;
    private SuggestionCategory Category;
    private int Difficulty;
    private boolean IsChecked = false;
    private boolean IsTracked = false;
    private boolean IsVideoYoutubeClicked = false;
    @Nullable
    private String jsonReferences;
    @Nullable
    private String videoYoutube;
    private String imageUrl;


    public SuggestionViewModel(Suggestion suggestion) {
        this.Id = suggestion.getId();
        Title = suggestion.getTitle();
        Text = suggestion.getText();
        Date = suggestion.getDate();
        Recompense = suggestion.getRecompense();
        Category = suggestion.getCategory();
        Difficulty = suggestion.getDifficulty();

        List<ReferenceViewModel> referenceViewModels = new ArrayList<>();
        for (Reference reference:suggestion.getReferences()
             ) {
            referenceViewModels.add(new ReferenceViewModel(reference));
        }

        Gson json = new Gson();
        jsonReferences = json.toJson(referenceViewModels);

        this.videoYoutube = suggestion.getVideoYoutube();
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

    @Override
    public int compareTo(@NonNull com.example.macbook.splash.ViewModels.SuggestionViewModel o) {
    return getDate().compareTo(o.getDate());
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
    public List<com.example.macbook.splash.ViewModels.ReferenceViewModel> getReferences() {

    Gson json = new Gson();
    Type type = new TypeToken<List<ReferenceViewModel>>(){}.getType();
    return json.fromJson(jsonReferences,type);

    }

    public void setReferences(List<ReferenceViewModel> reference) {
        Gson json = new Gson();
        jsonReferences = json.toJson(reference);
    }

    @Nullable
    public String getReferencesJson() {
        return jsonReferences;
    }

    @Nullable
    public boolean isChecked() {
        return IsChecked;
    }

    public void setChecked(@Nullable boolean checked) {
        IsChecked = checked;
    }

    @Nullable
    public boolean isTracked() {
        return IsTracked;
    }

    public void setTracked(@Nullable boolean tracked) {
        IsTracked = tracked;
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

    public boolean isVideoYoutubeClicked() {
        return IsVideoYoutubeClicked;
    }

    public void setVideoYoutubeClicked(boolean videoYoutubeClicked) {
        this.IsVideoYoutubeClicked = videoYoutubeClicked;
    }
}





