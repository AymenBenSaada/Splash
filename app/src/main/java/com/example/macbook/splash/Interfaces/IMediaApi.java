package com.example.macbook.splash.Interfaces;

import com.example.macbook.splash.Models.Group;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by macbook on 16/02/2018.
 */

public interface IMediaApi {

    @GET("{photoName}")
    Call<ResponseBody> getPhoto(@Path("photoName") String photoName);


    @Multipart
    @POST("File/UploadFile")
    Call<ResponseBody> uploadAttachment(@Part MultipartBody.Part filePart);


}
