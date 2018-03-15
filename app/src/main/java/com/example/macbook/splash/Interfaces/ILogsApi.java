package com.example.macbook.splash.Interfaces;

import com.example.macbook.splash.Models.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by macbook on 11/03/2018.
 */

public interface ILogsApi {

    @GET("/api/Logs/{logId}/Comments")
    Call<List<Comment>> getLogComments(@Path("logId") int logId);

}
