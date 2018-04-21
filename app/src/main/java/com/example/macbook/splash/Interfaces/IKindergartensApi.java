package com.example.macbook.splash.Interfaces;

import com.example.macbook.splash.Models.Child;
import com.example.macbook.splash.Models.ChildAdoptionResponseSubmitViewModel;
import com.example.macbook.splash.Models.ChildInscriptionResponse;
import com.example.macbook.splash.Models.ChildInscriptionResponseSubmitViewModel;
import com.example.macbook.splash.Models.Group;
import com.example.macbook.splash.Models.Kindergarten;
import com.example.macbook.splash.Models.Message;
import com.example.macbook.splash.Models.Teacher;
import com.example.macbook.splash.Models.TeacherInscriptionRequest;
import com.example.macbook.splash.Models.TeacherInscriptionResponse;
import com.example.macbook.splash.Models.TeacherInscriptionResponseSubmitViewModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by macbook on 02/03/2018.
 */

public interface IKindergartensApi {

    @GET("/api/Kindergartens/{kindergartenId}")
    Call<Kindergarten> getKindergarten(@Path("kindergartenId") int kindergartenId);

    @GET("/api/Kindergartens/{kindergartenId}/Messages")
    Call<List<Message>> getKindergartenMessages(@Path("kindergartenId") int kindergartenId);

    @GET("/api/Kindergartens/{kindergartenId}/ProfilePicture")
    Call<ResponseBody> getKindergartenProfilePicture(@Path("kindergartenId") int kindergartenId);

    @GET("/api/Kindergartens/{kindergartenId}/Groups")
    Call<List<Group>> getKindergartenGroups(@Path("kindergartenId") int kindergartenId);

    @GET("/api/Kindergartens/{kindergartenId}/Teachers")
    Call<List<Teacher>> getKindergartenTeachers(@Path("kindergartenId") int kindergartenId);

    @GET("/api/Kindergartens/{kindergartenId}/Children")
    Call<List<Child>> getKindergartenChildren(@Path("kindergartenId") int kindergartenId);



    //POST
    @POST("/api/Kindergartens/{kindergartenId}/Groups")
    Call<Group> postKindergartenGroups(@Path("kindergartenId") int kindergartenId, @Body Group group);


    //DELETE
    @DELETE("/api/Kindergartens/{kindergartenId}/Teachers/{teacherId}")
    Call<Void> deleteKindergartenTeacher(@Path("kindergartenId") int kindergartenId, @Path("teacherId") int teacherId);

    @DELETE("/api/Kindergartens/{kindergartenId}/Children/{childId}")
    Call<Void> deleteKindergartenChild(@Path("kindergartenId") int kindergartenId ,@Path("childId") int childId);


    //region KindergartenResponses

    @POST("/api/Kindergartens/{kidnergartenId}/ChildInscriptonRequests")
    Call<ChildInscriptionResponse> postChildInscriptionResponse(
            @Path("kindergartenId") int kindergartenId,
            @Body ChildInscriptionResponseSubmitViewModel viewModel
    );

    @POST("/api/Kindergartens/{kidnergartenId}/TeacherInscriptionRequests")
    Call<TeacherInscriptionResponse> postTeacherInscriptionResponse(
            @Path("kindergartenId") int kindergartenId,
            @Body TeacherInscriptionResponseSubmitViewModel viewModel
    );

    @GET("/api/Kindergartens/{kindergartenId}/teacherinscriptionrequests")
    Call<List<TeacherInscriptionRequest>> getTeacherInscriptionRequest(
            @Path("kindergartenId") int kindergartenId
    );

    //endregion
}
