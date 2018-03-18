package com.example.macbook.splash.Interfaces;

import com.example.macbook.splash.Models.Child;
import com.example.macbook.splash.Models.Group;
import com.example.macbook.splash.Models.Parent;
import com.example.macbook.splash.Models.Suggestion;
import com.example.macbook.splash.Models.Teacher;

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

/**
 * Created by Master on 09/02/2018.
 */

public interface IParentsApi {

    @GET("/api/Parents/{parentId}")
    Call<Parent> getParent(@Path("parentId") int parentId);

    @GET("api/Parents/{parentId}/ProfilePicture")
    Call<ResponseBody> getParentProfilePicture(@Path("parentId") int parentID);

    @GET("api/Parents/{parentId}/Children")
    Call<List<Child>> getParentChildren(@Path("parentId") int parentID);

    @GET("api/Parents/{parentId}/All")
    Call<Parent> getParentWithChildrenWithLogsWithComments(@Path("parentId") int parentId);

    @POST("api/Parents/{parentId}/FcmToken")
    Call<ResponseBody> postParentToken(@Path("parentId")int parentID, @Body String parentToken);

    @PUT("api/parents/{parentId}")
    Call<ResponseBody> putParent(@Path("parentId")int parentID, @Body Parent parent);

}
