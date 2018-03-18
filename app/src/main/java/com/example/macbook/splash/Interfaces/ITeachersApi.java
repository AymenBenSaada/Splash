package com.example.macbook.splash.Interfaces;

import com.example.macbook.splash.Models.Group;
import com.example.macbook.splash.Models.Suggestion;
import com.example.macbook.splash.Models.Teacher;
import com.example.macbook.splash.Models.TeacherInscriptionRequestSubmitViewModel;

import java.util.List;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
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

public interface ITeachersApi {

    //region TeachersApi

    @GET("api/Teachers/{teacherId}/All")
    Call<Teacher> getTeacherWithAll(@Path("teacherId") int teacherId);

    @GET("/api/Teachers/{teacherId}")
    Call<Teacher> getTeacher(@Path("teacherId") int teacherId);

    @GET("/api/Teachers/{teacherId}")
    Call<Teacher> getTeacherSync(@Path("teacherId") int teacherId);

    @POST("/api/Teachers")
    Call<Teacher> postTeacher(@Body Teacher teacher);

    @PUT("/api/Teachers/{teacherId}")
    Call<Void> putTeacher(@Path ("teacherId") int teacherId, @Body Teacher teacher);

    @DELETE("/api/Teacher/{teacherId}")
    Call<Void> deleteTeacher(@Path ("teacherId") int teacherId);

    //endregion

    //region TeacherKinderGarten

    @POST("api/Teacher/{teacherId}/TeacherInscriptionRequests")
    Call<ResponseBody> requestLinkTeacherToKG(
            @Path("teacherId") int Id,
            @Body TeacherInscriptionRequestSubmitViewModel teacherInscriptionRequestSubmitViewModel);
    //endregion



    //region TeacherGroupsApi

    @GET("/api/Teachers/{teacherId}/Groups")
    Call<List<Group>> getTeacherGroups(@Path("teacherId") int teacherId);

    @GET("/api/Teachers/{teacherId}/Groups/{groupId}")
    Call<Group> getTeacherGroup(@Path("teacherId") int teacherId, @Path("groupId") int groupId);

    @POST("/api/Teachers/{teacherId}/Groups/{groupId}")
    Call<Void> linkGroupToTeacher(@Path("teacherId") int teacherId, @Path("groupId") int groupId);

    @DELETE("/api/Teachers/{teacherId}/Groups/{groupId}")
    Call<Void> unlinkGroupAndTeacher(@Path ("teacherId") int teacherId, @Path("groupId") int groupId);

    //endregion

    //region TeacherSuggestionsApi

    @GET("/api/Teachers/{teacherId}/Suggestions")
    Call<List<Suggestion>> getTeacherSuggestions(@Path("teacherId") int teacherId);

    @GET("/api/Teachers/{teacherId}/Suggestions/{suggestionId}")
    Call<Suggestion> getTeacherSuggestion(@Path("teacherId") int teacherId, @Path("suggestionId") int suggestionId);

    //endregion

    //region TeacherProfilePicture

    @Multipart
    @POST("/api/Teachers/{teacherId}/ProfilePicture")
    Call<ResponseBody> postTeacherProfilePicture(
            @Path("teacherId") int teacherId,
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file
    );

    @GET("/api/Teachers/{teacherId}/ProfilePicture")
    Call<ResponseBody> getTeacherProfilePicture(
            @Path("teacherId") int teacherId
    );

    @DELETE("/api/Teacher/{teacherId}/ProfilePicture")
    Call<ResponseBody> deleteTeacherProfilePicture(
            @Path("teacherId") int teacherId
            );

    @PUT("/api/Teachers/{teacherId}/ProfilePicture")
    Call<ResponseBody> putTeacherProfilePicture(
            @Path("teacherId") int teacherId,
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file
    );

    //endregion

}
