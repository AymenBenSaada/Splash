package com.example.macbook.splash.Interfaces;

import com.example.macbook.splash.Models.Admin;
import com.example.macbook.splash.Models.Kindergarten;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Master on 09/02/2018.
 */

public interface IAdminsApi {

    @GET("/api/Admins/{adminId}")
    Call<Admin> getAdmin(@Path("adminId") int adminId);

    @POST("api/Admins/{adminId}/FcmToken")
    Call<Void> postAdminToken(@Path("adminId") int adminId, @Body String FcmToken);

    @POST("api/Admins/{adminId}/Kindergartens")
    Call<Kindergarten> postAdminKindergarten(@Path("adminId") int adminId, @Body Kindergarten kindergarten);

}
