package com.example.macbook.splash.Interfaces;

import com.example.macbook.splash.Models.Child;
import com.example.macbook.splash.Models.Group;
import com.example.macbook.splash.Models.Teacher;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Master on 09/02/2018.
 */

public interface IGroupsApi {

    //region GroupsApi

    @GET("/api/Groups")
    Call<List<Group>> getGroups();

    @GET("/api/Groups/{groupId}")
    Call<Group> getGroup(@Path("groupId") int groupId);

    @POST("/api/Groups")
    Call<Group> postGroup (@Body Group group);

    @PUT("/api/Groups/{groupId}")
    Call<Void> putGroup(@Path("groupId") int groupId, @Body  Group group);

    @DELETE("/api/Groups/{groupId}")
    Call<Void> deleteGroup (@Path("groupId") int groupId);

    //endregion

    //region GroupTeacher

    @GET("/api/Groups/{group_id}/Teachers")
    Call<List<Teacher>> getGroupTeachers(@Path("group_id") int group_id);

    @POST("/api/Groups/{group_id}/Teachers/{teacherId}")
    Call<Void> linkTeacherToGroup(@Path("teacherId") int teacherId, @Path("group_id") int group_id);

    @DELETE("/api/Groups/{group_id}/Teachers/{teacherId}")
    Call<Void> unLinkTeacherToGroup(@Path("teacherId") int teacherId, @Path("group_id") int group_id);

    //endregion

    //region GroupChildren

    @GET("/api/Groups/{group_id}/Children")
    Call<List<Child>> getGroupChildren(@Path("group_id") int group_id);

    @POST("/api/Groups/{group_id}/Children/{childId}")
    Call<Void> linkChildToGroup(@Path("childId") int childId, @Path("group_id") int group_id);

    @DELETE("/api/Groups/{group_id}/Children/{childId}")
    Call<Void> unLinkChildToGroup(@Path("childId") int childId, @Path("group_id") int group_id);
    //endregion


}
