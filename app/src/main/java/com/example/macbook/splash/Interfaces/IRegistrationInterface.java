package com.example.macbook.splash.Interfaces;

import com.example.macbook.splash.Models.AccountRegistrationModel;
import com.example.macbook.splash.Models.Child;
import com.example.macbook.splash.Models.Post;
import com.example.macbook.splash.Models.RegistrationResponseModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

/**
 * Created by nader on 3/17/18.
 */

public interface IRegistrationInterface {

    @POST("/auth/registerParent")
    Call<RegistrationResponseModel> registerParent(@Body AccountRegistrationModel Account);

    @POST("/auth/registerTeacher")
    Call<RegistrationResponseModel> registerTeacher(@Body AccountRegistrationModel Account);

    @GET("/api/EmailExists?email={email}")
    Call<Boolean> checkEmail(@Path("email") String email);


}
