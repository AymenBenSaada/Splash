package com.example.macbook.splash.Interfaces;

import com.example.macbook.splash.Models.Admin;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Master on 09/02/2018.
 */

public interface IAdminsApi {

    @GET("/api/Admins/{adminId}")
    Call<Admin> getAdmin(@Path("adminId") int adminId);

}
