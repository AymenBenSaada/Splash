package com.example.macbook.splash.Interfaces;

import android.util.Log;

import com.example.macbook.splash.Models.Child;
import com.example.macbook.splash.Models.Group;
import com.example.macbook.splash.Models.Parent;
import com.example.macbook.splash.Models.Post;
import com.example.macbook.splash.Models.TeacherInscriptionRequestSubmitViewModel;

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

public interface IChildrenApi{

    //region ChildrenApi

    @GET("/api/Children/{childId}")
    Call<Child> getChild(@Path("childId") int childId);

    @POST("/api/Children")
    Call<Child> postChild(@Body Child child);

    @PUT("/api/Children/{childId}")
    Call<Void> putChild(@Path ("id") int id, @Body Child child);

    @DELETE("/api/Children/{childId}")
    Call<Void> deleteChild(@Path ("childId") int childId);

    //endregion

    //region ChildrenParentsApi

    @GET("/api/Children/{childId}/Parents")
    Call<List<Parent>> getChildParents(@Path("childId") int childId);

    @GET("/api/Children/{childId}/Parents/{parentId}")
    Call<Parent> getChildParent(@Path("childId") int childId, @Path("parentId") int parentId);

    @POST("/api/Children/{childId}/Parents/{parentId}")
    Call<Void> linkParentToChild(@Path("childId") int childId, @Path("parentId") int parentId);

    @DELETE("/api/Children/{childId}/Parents/{parentId}")
    Call<Void> unlinkParentAndChild(@Path ("childId") int childId, @Path("parentId") int parentId);

    //endregion

    //region ChildLogsApi

    @GET("/api/Children/{childId}/Logs")
    Call<List<com.example.macbook.splash.Models.Log>> getChildLogs(@Path("childId") int childId);

    @GET("/api/Children/{childId}/Logs/{logId}")
    Call<com.example.macbook.splash.Models.Log> getChildLog(@Path("childId") int childId, @Path("logId") int logId);

    @POST("/api/Children/{childId}/Logs")
    Call<com.example.macbook.splash.Models.Log> addChildLog(@Path("childId") int childId, @Body com.example.macbook.splash.Models.Log log);

    @PUT("/api/Children/{childId}/Logs/{logId}")
    Call<Void> putChildLog(@Path("childId") int childId, @Path("logId") int logId, @Body com.example.macbook.splash.Models.Log log);

    @DELETE("/api/Children/{childId}/Logs/{logId}")
    Call<Void> deleteChildLog(@Path ("childId") int childId, @Path("logId") int logId);

    //endregion

    //region ChildGroupsApi

    @GET("/api/Children/{childId}/Groups")
    Call<List<Group>> getChildGroups(@Path("childId") int childId);

    @GET("/api/Children/{childId}/Groups/{groupId}")
    Call<Group> getChildGroup(@Path("childId") int childId, @Path("groupId") int groupId);

    @POST("/api/Children/{childId}/Groups/{groupId}")
    Call<Void> linkGroupToChild(@Path("childId") int childId, @Path("groupId") int groupId);

    @DELETE("/api/Children/{childId}/Groups/{groupId}")
    Call<Void> unlinkGroupAndChild(@Path ("childId") int childId, @Path("groupId") int groupId);

    //endregion

    //region ChildPosts

    @GET("/api/Children/{childId}/Posts")
    Call<List<Post>> getChildPosts(@Path("childId") int childId);

    @GET("/api/Children/{childId}/Posts/{postId}")
    Call<Post> getChildPost(@Path("childId") int childId, @Path("postId") int postId);

    @POST("/api/Children/{childId}/Posts/{postId}")
    Call<Void> linkPostToChild(@Path("childId") int childId, @Path("postId") int postId);

    @DELETE("/api/Children/{childId}/Posts/{postId}")
    Call<Void> unlinkPostAndChild(@Path ("childId") int childId, @Path("postId") int postssId);

    //endregion

    //region ChildProfilePicture

    @Multipart
    @POST("/api/Children/{childId}/ProfilePicture")
    Call<ResponseBody> postChildProfilePicture(
            @Path("childId") int childId,
           // @Part("description") RequestBody description,
            @Part MultipartBody.Part file
    );

    @GET("/api/Children/{childId}/ProfilePicture")
    Call<ResponseBody> getChildProfilePicture(
            @Path("childId") int childId
    );

    @DELETE("/api/Children/{childId}/ProfilePicture")
    Call<ResponseBody> deleteChildProfilePicture(
            @Path("childId") int childId
    );

    @PUT("/api/Children/{childId}/ProfilePicture")
    Call<ResponseBody> purChildProfilePicture(
            @Path("childId") int childId,
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file
    );

    //endregion

    //region LinkChildToKindergarten

    //region TeacherKinderGarten

    @POST("api/Parents/{parentId}/ChildAdoptionRequests")
    Call<ResponseBody> requestLinkChildToKG(
            @Path("parentId") int ParentId,
            @Body TeacherInscriptionRequestSubmitViewModel teacherInscriptionRequestSubmitViewModel
            );
    //endregion

    //endregion

}

