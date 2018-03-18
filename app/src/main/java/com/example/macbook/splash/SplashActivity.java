package com.example.macbook.splash;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.androidnetworking.AndroidNetworking;
import com.example.macbook.splash.Adapters.GsonTimeAdapter;
import com.example.macbook.splash.Enum.Gender;
import com.example.macbook.splash.Enum.Status;
import com.example.macbook.splash.Interfaces.ApiClient;
import com.example.macbook.splash.Interfaces.IAdminsApi;
import com.example.macbook.splash.Interfaces.IChildrenApi;
import com.example.macbook.splash.Interfaces.IGroupsApi;
import com.example.macbook.splash.Interfaces.IKindergartensApi;
import com.example.macbook.splash.Interfaces.IMediaApi;
import com.example.macbook.splash.Interfaces.IParentsApi;
import com.example.macbook.splash.Interfaces.ITeachersApi;
import com.example.macbook.splash.Models.Admin;
import com.example.macbook.splash.Models.Child;
import com.example.macbook.splash.Models.Comment;
import com.example.macbook.splash.Models.Group;
import com.example.macbook.splash.Models.Kindergarten;
import com.example.macbook.splash.Models.LoginModelView;
import com.example.macbook.splash.Models.Media;
import com.example.macbook.splash.Models.Message;
import com.example.macbook.splash.Models.Parent;
import com.example.macbook.splash.Models.Post;
import com.example.macbook.splash.Models.Suggestion;
import com.example.macbook.splash.Models.Teacher;
import com.example.macbook.splash.SwipePagers.TeacherPager;
import com.example.macbook.splash.ViewModels.SuggestionViewModel;
import com.felipecsl.gifimageview.library.GifImageView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.nio.file.WatchEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import id.zelory.compressor.Compressor;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.internal.Internal.instance;

public class SplashActivity extends AppCompatActivity {

    //region global variables

    private GifImageView gifImageView;

    private String TeacherFile="teacher.dat";
    private String ParentFile="parent.dat";
    private String AdminFile="admin.dat";

    private ITeachersApi iTeachersApi;
    private IParentsApi iParentsApi;
    private IAdminsApi iAdminsApi;

    private Media tempMedia;

    private Teacher teacher;
    private Parent parent;
    private Admin admin;

    private LoginModelView user;
    private String userFile="user.dat";

    //endregion

    /*
    main
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //region verification user
        if(fileExist(userFile)){
            user = loadUser();
            if(user.getGender().equals("parent")){
                new ParentAsyncRefresh().execute();
            }else if(user.getGender().equals("teacher")){
                new TeacherAsyncRefresh().execute();
            }else{
                new AdminAsyncRefresh().execute();
            }
        }else {
            new TeacherAsyncRefresh().execute();
        }
        //endregion

        //region set GIFImageView Resource

        gifImageView = (GifImageView) findViewById(R.id.gifImageView);
        try{
            InputStream inputStream = getAssets().open("splash_screen.gif");
            byte [] bytes = IOUtils.toByteArray(inputStream);
            gifImageView.setBytes(bytes);
            gifImageView.startAnimation();
        }
        catch (IOException ex){
        }

        //wait for 4,333 seconds and start Activity Main
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashActivity.this.startActivity(new Intent( SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        }, 4333);

        //endregion

    }

    //region Asynchronous internal storage refresh

    private class TeacherAsyncRefresh extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            iTeachersApi = ApiClient.getClient().create(ITeachersApi.class);
            iTeachersApi.getTeacher(2).enqueue(new Callback<Teacher>() {
                @Override
                public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                    try{
                        //REQUEST GET TEACHER WITH GROUPS
                        teacher = response.body();

                        Log.e("debug","entring respons body");
                        //region Initialization
                        //region date formater (contains date1,date2,date3,date4)
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                        Date date1 = new Date();
                        Date date2 = new Date();
                        Date date3 = new Date();
                        Date date4 = new Date();
                        try {
                            date1 = dateFormat.parse("2017-02-17T07:12:00");
                            date2 = dateFormat.parse("2012-03-16T17:12:00");
                            date3 = dateFormat.parse("2013-09-15T00:00:00");
                            date4 = dateFormat.parse("2015-12-14T17:12:00");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        //endregion

                        Comment comment1 = new Comment(1,"chnowa hedha ki zebi",date1, "Zac Effron");
                        Comment comment2 = new Comment(2,"chnowa hedha ki nami",date1, "Nadoura");
                        Comment comment3 = new Comment(3,"zabour",date2, "Mokded");
                       // Comment comment4 = new Comment(4,"ynik f rass",date3, "Tarzan");
                        List<Comment> comments = new ArrayList<>();
                        comments.add(comment1);comments.add(comment2);comments.add(comment3);//comments.add(comment4);

                        com.example.macbook.splash.Models.Log log1 = new com.example.macbook.splash.Models.Log(1,"weldek ki zebi lyoum","tla3 miboun chwaya rahou rod belek alih",date1,"zabour el fahs",comments);
                        com.example.macbook.splash.Models.Log log2 = new com.example.macbook.splash.Models.Log(2,"weldek ki nami lyoum",getResources().getString(R.string.testText),date2,"zabour el mourouj",comments);
                        com.example.macbook.splash.Models.Log log3 = new com.example.macbook.splash.Models.Log(3,"weldek ki zebi lyoum","tla3 miboun chwaya rahou rod belek alih",date3,"zabour el ennasr",comments);
                        com.example.macbook.splash.Models.Log log4 = new com.example.macbook.splash.Models.Log(4,"weldek ki nami lyoum","tla3 tahane chwaya rahou rod belek alih",date4,"zabour el andalos",comments);
                        com.example.macbook.splash.Models.Log log5 = new com.example.macbook.splash.Models.Log(5,"weldek ki zebi lyoum","tla3 miboun chwaya rahou rod belek alih",date4,"zabour hay el mileha",comments);
                        List<com.example.macbook.splash.Models.Log> logs = new ArrayList<>();
                        logs.add(log1);logs.add(log2);logs.add(log3);logs.add(log4);logs.add(log5);
                        List<com.example.macbook.splash.Models.Log> logs2 = new ArrayList<>();
                        logs2.add(log1);logs2.add(log2);logs2.add(log3);logs2.add(log4);
                        Log.e("debug","entring second respons body phase");


                        Child child1 = new Child(1,"Aymen","Gharbi","Nawfel",date1, Gender.Male,logs,1,0,"");
                        Child child2 = new Child(2,"Zac","Hattira","Tarzan",date2,Gender.Male,logs2,2,0,"");
                        Child child3 = new Child(3,"Nader","Zouaoui","Schneider",date3, Gender.Male,logs,3,0,"");
                        Child child4 = new Child(4,"Miras","Ayed","Mokded",date4,Gender.Male,logs2,4,0,"");
                        List<Child> children = new ArrayList<>();
                        children.add(child1);children.add(child2);children.add(child3);children.add(child4);
                        children.add(child1);children.add(child2);children.add(child3);children.add(child4);
                        children.add(child1);children.add(child2);children.add(child3);children.add(child4);
                        children.add(child1);children.add(child2);children.add(child3);children.add(child4);
                        children.add(child1);children.add(child2);children.add(child3);children.add(child4);

                        Group group1 = new Group(1,children,"Batmans");
                        List<Group> groups = new ArrayList<>();
                        groups.add(group1);
                        Log.e("debug","entring third respons body phase");

                        teacher.setGroups(groups);

                        //endregion
                        Log.e("debug","entring Fourth respons body phase");

                        //region Initalization of Children MediaList
                        Media media1 = new Media(1,"dObtwfF.jpg",0,null);
                        Media media2 = new Media(2,"Oq17met.jpg",0,null);
                        Media media3 = new Media(3,"nXrHjaG.jpg",0,null);
                        Media media4 = new Media(4,"cXfaKdc.jpg",0,null);
                        List<Media> mediaList = new ArrayList<>();
                        mediaList.add(media1);mediaList.add(media2);mediaList.add(media3);mediaList.add(media4);
                        //endregion

                        //region download Children MEDIA
                        Retrofit retrofit1 = new Retrofit.Builder()
                                .baseUrl("https://i.imgur.com/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        IMediaApi iMediaApi = retrofit1.create(IMediaApi.class);
                        for (Group group:teacher.getGroups()
                                ) {
                            for (Child child:group.getChildren()
                                    ) {
                                //getPhoto(id : child.getProfilpictureID)
                                //onResponse bech yjini objet Media nekhedh menou el path
                                tempMedia = mediaList.get(child.getProfil_pictureID()-1);
                                iMediaApi.getPhoto(tempMedia.getPath()).enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            FileOutputStream fos = null;
                                            InputStream fis = response.body().byteStream();

                                            byte[] fileReader = new byte[4096];
                                            try {
                                                String url = response.raw().request().url().toString();
                                                //normalement nekhedh mel url l id mta3 e child w nzidou l name donc twali child_profil_picture_1(childID)
                                                fos = openFileOutput("child_profil_picture_" + ConvertTheName(url.substring(20,url.length())) + ".dat", Context.MODE_PRIVATE);
                                                while (true) {
                                                    int read = fis.read(fileReader);

                                                    if (read == -1) {
                                                        break;
                                                    }
                                                    fos.write(fileReader, 0, read);
                                                }
                                            } catch (FileNotFoundException e) {
                                                e.printStackTrace();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            } finally {
                                                if (fos != null) {
                                                    try {
                                                        fos.close();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            Log.e("DEBUG", "FAILURE DOWNLOADING CHILDREN PHOTOS");
                                        }
                                    });
                            }
                        }
                        //endregion
                        Log.e("debug","entring fifth respons body phase");

                        //region download Teacher MEDIA
                        iMediaApi.getPhoto("nXrHjaG.jpg").enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                FileOutputStream fos = null;
                                InputStream fis = response.body().byteStream();

                                byte[] fileReader = new byte[4096];
                                try {
                                    fos = openFileOutput("teacher_profil_picture_" + teacher.getId()+".dat", Context.MODE_PRIVATE);
                                    while (true) {
                                        int read = fis.read(fileReader);

                                        if (read == -1) {
                                            break;
                                        }
                                        fos.write(fileReader, 0, read);
                                    }
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } finally {
                                    if (fos != null) {
                                        try {
                                            fos.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                        //endregion
                        Log.e("debug","entring sixth respons body phase");

                        saveTeacher(teacher);

                        Log.e("DEBUG","FINISHED DOWNLOADING FILES");

                    }catch (Exception e){
                        e.printStackTrace();
                        Log.e("debug","entring exeption catch of respons body");

                    }
                }

                @Override
                public void onFailure(Call<Teacher> call, Throwable t) {
                    Log.e("DEBUG","FAILED TO GET THE TEACHER OBJECT");
                }
            });
            return null;
        }
    }

    private class ParentAsyncRefresh extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {super.onPreExecute();}

        @Override
        protected void onPostExecute(String s) {super.onPostExecute(s);}

        @Override
        protected String doInBackground(String... strings) {
            iParentsApi = ApiClient.getClient().create(IParentsApi.class);
            iParentsApi.getParent(1).enqueue(new Callback<Parent>() {
                @Override
                public void onResponse(Call<Parent> call, Response<Parent> response) {
                    try{
                        //REQUEST GET PARENT
                        parent = response.body();

                        //region Initialization
                        //region date formater (contains date1,date2,date3,date4)
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                        Date date1 = new Date();
                        Date date2 = new Date();
                        Date date3 = new Date();
                        Date date4 = new Date();
                        try {
                            date1 = dateFormat.parse("2017-02-17T07:12:00");
                            date2 = dateFormat.parse("2012-03-16T17:12:00");
                            date3 = dateFormat.parse("2013-09-15T00:00:00");
                            date4 = dateFormat.parse("2015-12-14T17:12:00");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        //endregion



                        Comment comment1 = new Comment(1,"khayeb",date1, "Zac Effron");
                        Comment comment2 = new Comment(2,"mezyen",date1, "Nadoura");
                        Comment comment3 = new Comment(3,"ma39oul",date2, "Mokded");
                        // Comment comment4 = new Comment(4,"ynik f rass",date3, "Tarzan");
                        List<Comment> comments = new ArrayList<>();
                        comments.add(comment1);comments.add(comment2);comments.add(comment3);//comments.add(comment4);

                        com.example.macbook.splash.Models.Log log1 = new com.example.macbook.splash.Models.Log(1,"weldek ki zebi lyoum","rod belek alih",date1,"mezyen el fahs",comments);
                        com.example.macbook.splash.Models.Log log2 = new com.example.macbook.splash.Models.Log(2,"weldek ki nami lyoum",getResources().getString(R.string.testText),date2,"zabour el mourouj",comments);
                        com.example.macbook.splash.Models.Log log3 = new com.example.macbook.splash.Models.Log(3,"weldek ki zebi lyoum","tla3 miboun chwaya rahou rod belek alih",date3,"zabour el ennasr",comments);
                        com.example.macbook.splash.Models.Log log5 = new com.example.macbook.splash.Models.Log(5,"weldek ki zebi lyoum","tla3 miboun chwaya rahou rod belek alih",date4,"zabour hay el mileha",comments);
                        List<com.example.macbook.splash.Models.Log> logs = new ArrayList<>();
                        logs.add(log1);logs.add(log2);logs.add(log3);logs.add(log5);
                        List<com.example.macbook.splash.Models.Log> logs2 = new ArrayList<>();
                        logs2.add(log1);logs2.add(log2);logs2.add(log3);


                        Post post1 = new Post(1,"hi",date1,"Sport",3);
                        Post post2 = new Post(2,"hi",date1,"Music",3);
                        Post post3 = new Post(3,"hi",date3,"Dessin",2);
                        Post post4 = new Post(4,"hi",date2,"Apprentissage",2);
                        List<Post> postList = new ArrayList<>();
                        postList.add(post1);postList.add(post2);postList.add(post3);postList.add(post4);


                        Child child1 = new Child(1,"Aymen","Gharbi","Nawfel",date1, Gender.Male,logs,1,0,"");
                        child1.setPosts(postList);
                        Child child2 = new Child(2,"Zac","Hattira","Tarzan",date2,Gender.Male,logs2,2,0,"");
                        child2.setPosts(postList);
                        Child child3 = new Child(3,"Nader","Zouaoui","Schneider",date3, Gender.Male,logs,3,0,"");
                        child3.setPosts(postList);
                        Child child4 = new Child(4,"Miras","Ayed","Mokded",date4,Gender.Male,logs2,4,0,"");
                        child4.setPosts(postList);
                        List<Child> children = new ArrayList<>();
                        children.add(child1);children.add(child2);children.add(child3);children.add(child4);

                        parent.setChildren(children);


                        //endregion

                        //region MediaList
                        Media child1_profil_picture = new Media(1,"dObtwfF.jpg",0,"image");
                        Media child2_profil_picture = new Media(2,"Oq17met.jpg",0,"image");
                        Media child3_profil_picture = new Media(3,"nXrHjaG.jpg",0,"image");
                        Media child4_profil_picture = new Media(4,"cXfaKdc.jpg",0,"image");
                        List<Media> mediaList = new ArrayList<>();
                        mediaList.add(child1_profil_picture);mediaList.add(child2_profil_picture);mediaList.add(child3_profil_picture);mediaList.add(child4_profil_picture);
                        //endregion

                        Retrofit retrofit1 = new Retrofit.Builder()
                                .baseUrl("https://i.imgur.com/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        IMediaApi iMediaApi = retrofit1.create(IMediaApi.class);

                        //region getting every child profil picture
                        for (final Child child:parent.getChildren()
                                    ) {
                                //getPhoto(id : child.getProfilpictureID)
                                //onResponse bech yjini objet Media nekhedh menou el path
                                tempMedia = mediaList.get(child.getProfil_pictureID()-1);
                                iMediaApi.getPhoto(tempMedia.getPath()).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        FileOutputStream fos = null;
                                        InputStream fis = response.body().byteStream();

                                        byte[] fileReader = new byte[4096];
                                        try {
                                            fos = openFileOutput("child_profil_picture_" + child.getId(), Context.MODE_PRIVATE);
                                            while (true) {
                                                int read = fis.read(fileReader);

                                                if (read == -1) {
                                                    break;
                                                }
                                                fos.write(fileReader, 0, read);
                                            }
                                        } catch (FileNotFoundException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        } finally {
                                            if (fos != null) {
                                                try {
                                                    fos.close();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Log.e("DEBUG", "FAILURE DOWNLOADING CHILDREN PHOTOS");
                                    }
                                });
                        }
                        //endregion

                        //region gettting the parent profil picture
                        iParentsApi.getParentProfilePicture(parent.getId()).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                FileOutputStream fos = null;
                                InputStream fis = response.body().byteStream();

                                byte[] fileReader = new byte[4096];
                                try {
                                    fos = openFileOutput("parent_profil_picture_" + parent.getId()+".dat", Context.MODE_PRIVATE);
                                    while (true) {
                                        int read = fis.read(fileReader);

                                        if (read == -1) {
                                            break;
                                        }
                                        fos.write(fileReader, 0, read);
                                    }
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } finally {
                                    if (fos != null) {
                                        try {
                                            fos.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Log.e("DEBUG","FAILED TO GET THE PARENT TOF");

                            }
                        });
                        //endregion

                        saveParent(parent);

                        Log.e("DEBUG","FINISHED DOWNLOADING FILES");

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<Parent> call, Throwable t) {
                    Log.e("DEBUG","FAILED TO GET THE PARENT OBJECT");
                }
            });
            return null;
        }
    }

    private class AdminAsyncRefresh extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            iAdminsApi = ApiClient.getClient().create(IAdminsApi.class);
            //region getting the admin model + kindergarten with groups
            iAdminsApi.getAdmin(16).enqueue(new Callback<Admin>() {
                @Override
                public void onResponse(Call<Admin> call, Response<Admin> response) {
                    try {
                        admin = response.body();
                        saveAdmin(admin);

                        IKindergartensApi iKindergartensApi = ApiClient.getClient().create(IKindergartensApi.class);
                        iKindergartensApi.getKindergarten(admin.getKindergartenId()).enqueue(new Callback<Kindergarten>() {
                            @Override
                            public void onResponse(Call<Kindergarten> call, Response<Kindergarten> response) {

                                final Kindergarten kindergarten = response.body();
                                saveKindergartenModelInTheInternalStorage(kindergarten);

                                //region get kindergarten profile picture
                                IKindergartensApi iKindergartensApi = ApiClient.getClient().create(IKindergartensApi.class);
                                iKindergartensApi.getKindergartenProfilePicture(kindergarten.getId()).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                        InputStream fis = response.body().byteStream();
                                        saveKindergartenProfilePictureInTheInternalStorage(fis, kindergarten.getId());
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Log.e("DEBUG", "FAILED TO GET THE KINDERGARTEN PROFILE PIC");
                                    }
                                });
                                //endregion

                                //region get kindergarten messages
                                iKindergartensApi.getKindergartenMessages(kindergarten.getId()).enqueue(new Callback<List<Message>>() {
                                    @Override
                                    public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                                        List<Message> messages = response.body();
                                        saveKindergartenMessagesInTheInternalStorage(messages, kindergarten.getId());
                                    }

                                    @Override
                                    public void onFailure(Call<List<Message>> call, Throwable t) {
                                        Log.e("DEBUG", "FAILED TO GET THE KINDERGARTEN MESSAGES");
                                    }
                                });
                                //endregion

                                //region get kindergarten groups

                                iKindergartensApi.getKindergartenGroups(kindergarten.getId()).enqueue(new Callback<List<Group>>() {
                                    @Override
                                    public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {

                                        List<Group> groups = response.body();
                                        saveKindergartenGroupsInTheInternalStorage(groups, kindergarten.getId());

                                        IGroupsApi iGroupsApi = ApiClient.getClient().create(IGroupsApi.class);

                                        for (final Group group : groups
                                                ) {
                                            //region get group children
                                            iGroupsApi.getGroupChildren(group.getId()).enqueue(new Callback<List<Child>>() {

                                                @Override
                                                public void onResponse(Call<List<Child>> call, Response<List<Child>> response) {
                                                    List<Child> children = response.body();
                                                    saveGroupChildrenInTheInternalStorage(children,group.getId());
                                                    //region get every child profil picture
                                                    IChildrenApi iChildrenApi = ApiClient.getClient().create(IChildrenApi.class);

                                                    for (final Child child:children
                                                         ) {
                                                        iChildrenApi.getChildProfilePicture(child.getId()).enqueue(new Callback<ResponseBody>() {
                                                            @Override
                                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                                                InputStream fis = response.body().byteStream();
                                                                saveChildProfilePictureInTheInternalStorage(fis,child.getId());
                                                            }

                                                            @Override
                                                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                                Log.e("DEBUG","FAILED TO GET THE CHILD "+ child.getId()+" PROFILE PIC");
                                                            }
                                                        });
                                                    }
                                                    //endregion
                                                }

                                                @Override
                                                public void onFailure(Call<List<Child>> call, Throwable t) {
                                                    Log.e("DEBUG","FAILED TO GET THE GROUP "+ group.getId()+" CHILDREN");
                                                }

                                            });
                                            //endregion

                                            //region get group teachers
                                            iGroupsApi.getGroupTeachers(group.getId()).enqueue(new Callback<List<Teacher>>() {

                                                @Override
                                                public void onResponse(Call<List<Teacher>> call, Response<List<Teacher>> response) {
                                                    List<Teacher> teachers = response.body();
                                                    saveGroupTeachersInTheInternalStorage(teachers,group.getId());
                                                    //region get every teacher profil picture
                                                    ITeachersApi iTeachersApi = ApiClient.getClient().create(ITeachersApi.class);

                                                    for (final Teacher teacher:teachers
                                                            ) {
                                                        iTeachersApi.getTeacherProfilePicture(teacher.getId()).enqueue(new Callback<ResponseBody>() {
                                                            @Override
                                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                                                InputStream fis = response.body().byteStream();
                                                                saveTeacherProfilePictureInTheInternalStorage(fis,teacher.getId());
                                                            }

                                                            @Override
                                                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                                Log.e("DEBUG","FAILED TO GET THE TEACHER "+ teacher.getId()+" PROFILE PIC");
                                                            }
                                                        });
                                                    }
                                                    //endregion
                                                }

                                                @Override
                                                public void onFailure(Call<List<Teacher>> call, Throwable t) {
                                                    Log.e("DEBUG","FAILED TO GET THE GROUP "+ group.getId()+" TEACHERS");
                                                }

                                            });
                                            //endregion
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<Group>> call, Throwable t) {
                                        Log.e("DEBUG","FAILED TO GET THE GROUPS LIST");
                                    }
                                });

                                //endregion

                                //region get kindergarten children
                                iKindergartensApi.getKindergartenChildren(kindergarten.getId()).enqueue(new Callback<List<Child>>() {
                                    @Override
                                    public void onResponse(Call<List<Child>> call, Response<List<Child>> response) {
                                        List<Child> children = response.body();
                                        saveKindergartenChildrenInTheInternalStorage(children,kindergarten.getId());
                                        IChildrenApi iChildrenApi = ApiClient.getClient().create(IChildrenApi.class);
                                        for (final Child child:children
                                             ) {
                                            if(!fileExist("child_profile_picture_"+child.getId()+".dat")){
                                                iChildrenApi.getChildProfilePicture(child.getId()).enqueue(new Callback<ResponseBody>() {
                                                    @Override
                                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                        InputStream fis = response.body().byteStream();
                                                        saveChildProfilePictureInTheInternalStorage(fis,child.getId());
                                                    }

                                                    @Override
                                                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                                                    }
                                                });
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<Child>> call, Throwable t) {

                                    }
                                });
                                //endregion

                                //region get kindergarten teachers
                                iKindergartensApi.getKindergartenTeachers(kindergarten.getId()).enqueue(new Callback<List<Teacher>>() {
                                    @Override
                                    public void onResponse(Call<List<Teacher>> call, Response<List<Teacher>> response) {
                                        List<Teacher> teachers = response.body();
                                        saveKindergartenTeachersInTheInternalStorage(teachers,kindergarten.getId());
                                        iTeachersApi = ApiClient.getClient().create(ITeachersApi.class);
                                        for (final Teacher teacher:teachers
                                             ) {
                                            if(!fileExist("teacher_profile_picture_"+teacher.getId()+".dat")){
                                                iTeachersApi.getTeacherProfilePicture(teacher.getId()).enqueue(new Callback<ResponseBody>() {
                                                    @Override
                                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                        InputStream fis = response.body().byteStream();
                                                        saveTeacherProfilePictureInTheInternalStorage(fis,teacher.getId());
                                                    }

                                                    @Override
                                                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                                                    }
                                                });
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<List<Teacher>> call, Throwable t) {

                                    }
                                });
                                //endregion
                            }

                            @Override
                            public void onFailure(Call<Kindergarten> call, Throwable t) {

                            }
                        });
                    } catch (Exception e) {
                        Log.e("DEBUG", e.toString());
                    }
                }

                @Override
                public void onFailure(Call<Admin> call, Throwable t) {
                    Log.e("DEBUG","FAILED TO GET THE ADMIN MODEL");
                }
            });
            //endregion
            return null;
        }

    }

    //endregion

    private LoginModelView loadUser() {
        FileInputStream fis = null;
        LoginModelView user = null;

        try {
            fis = openFileInput(userFile);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            String Json = sb.toString();
            Type type = new TypeToken<LoginModelView>(){}.getType();
            Gson gson = new GsonBuilder()
                    .create();

            user = gson.fromJson(Json,type);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return user;
    }

    //region save models in internal storage
    private void saveTeacher(Teacher teacher){ saveObjectAsFileInTheInternalStorage(teacher,TeacherFile);}

    private void saveParent(Parent parent){ saveObjectAsFileInTheInternalStorage(parent,ParentFile);}

    private void saveAdmin(Admin admin){ saveObjectAsFileInTheInternalStorage(admin,AdminFile);}
    //endregion

    private boolean fileExist(String fname){
        File file = getFileStreamPath(fname);
        return file.exists();
    }

    //region Admin Account Method

    // NB : ALL FILES ARE IN THE JSON FORMAT
    private void saveKindergartenModelInTheInternalStorage(Kindergarten kindergarten){
           saveObjectAsFileInTheInternalStorage(kindergarten,"kg_model_"+kindergarten.getId()+".dat");
    }

    private void saveKindergartenGroupsInTheInternalStorage(List<Group> groups,int kindergarten_id){
        saveObjectAsFileInTheInternalStorage(groups,"kg_groups_"+kindergarten_id+".dat");
    }

    private void saveKindergartenProfilePictureInTheInternalStorage(InputStream fis,int kindergarten_id){
        savePictureAsFileInTheInternalStorage(fis,"kg_profile_picture_" + kindergarten_id+".dat");
    }

    private void saveKindergartenMessagesInTheInternalStorage(List<Message> messages,int kindergarten_id){
        saveObjectAsFileInTheInternalStorage(messages,"kg_messages_"+kindergarten_id+".dat");
    }

    private void saveKindergartenChildrenInTheInternalStorage(List<Child> children,int kindergarten_id){
        saveObjectAsFileInTheInternalStorage(children,"kg_children_"+kindergarten_id+".dat");
    }

    private void saveKindergartenTeachersInTheInternalStorage(List<Teacher> teachers,int kindergarten_id){
        saveObjectAsFileInTheInternalStorage(teachers,"kg_teachers_"+kindergarten_id+".dat");
    }

    private void saveGroupChildrenInTheInternalStorage(List<Child> children,int group_id){
        saveObjectAsFileInTheInternalStorage(children,"kg_group_children_"+group_id+".dat");
    }

    private void saveGroupTeachersInTheInternalStorage(List<Teacher> teachers,int group_id){
        saveObjectAsFileInTheInternalStorage(teachers,"kg_group_teachers_"+group_id+".dat");
    }

    private void saveChildProfilePictureInTheInternalStorage(InputStream fis ,int child_id){
        savePictureAsFileInTheInternalStorage(fis,"child_profile_picture_" + child_id+".dat");
    }

    private void saveTeacherProfilePictureInTheInternalStorage(InputStream fis, int teacher_id){
        savePictureAsFileInTheInternalStorage(fis,"teacher_profile_picture_" + teacher_id+".dat");
    }

    //endregion

    private int ConvertTheName(String s){
        int result = 0;
        switch (s){
            case "dObtwfF.jpg":
                result = 1;
                break;
            case "Oq17met.jpg":
                result = 2;
                break;
            case "nXrHjaG.jpg":
                result = 3;
                break;
            case "cXfaKdc.jpg":
                result = 4;
                break;
        }
        return result;
    }

    //region save an object or a picture in the internal storage

    private void saveObjectAsFileInTheInternalStorage(Object object,String file_name){
        File file = new File(getFilesDir() + "/"+file_name);
        file.delete();
        FileOutputStream fos = null;

        try {
            fos = this.openFileOutput(file_name,Context.MODE_PRIVATE);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new GsonTimeAdapter())
                    .create();
            String json = gson.toJson(object);
            fos.write(json.getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void savePictureAsFileInTheInternalStorage(InputStream fis,String file_name){
        FileOutputStream fos = null;

        byte[] fileReader = new byte[4096];
        try {
            fos = openFileOutput(file_name, Context.MODE_PRIVATE);
            while (true) {
                int read = fis.read(fileReader);

                if (read == -1) {
                    break;
                }
                fos.write(fileReader, 0, read);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //endregion
}


