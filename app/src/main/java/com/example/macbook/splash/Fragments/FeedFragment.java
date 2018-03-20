package com.example.macbook.splash.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.macbook.splash.Adapters.FeedAdapter;
import com.example.macbook.splash.Adapters.GsonTimeAdapter;
import com.example.macbook.splash.Interfaces.ApiClient;
import com.example.macbook.splash.Interfaces.IMediaApi;
import com.example.macbook.splash.Interfaces.ITeachersApi;
import com.example.macbook.splash.Models.Child;
import com.example.macbook.splash.Models.Parent;
import com.example.macbook.splash.Models.Post;
import com.example.macbook.splash.Models.Teacher;
import com.example.macbook.splash.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FeedFragment extends Fragment {

    private String ParentFileName = "parent.dat";
    private List<Post> posts;
    private ITeachersApi iTeachersApi;
    private FeedAdapter feedAdapter;


    //region khormouloujya


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    public FeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedFragment newInstance(String param1, String param2) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mview =  inflater.inflate(R.layout.fragment_feed, container, false);

        RecyclerView recyclerView = (RecyclerView) mview.findViewById(R.id.feedRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        posts = loadPosts();

        feedAdapter = new FeedAdapter(getContext(),DeleteRedundantPosts(posts));
        recyclerView.setAdapter(feedAdapter);

        new PostAsyncRefresh().execute();


        return mview;


    }


    //region Utilities Storage Methods

    private class PostAsyncRefresh extends AsyncTask<String,String,String> {

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
            for (Post post:DeleteRedundantPosts(posts)
                    ) {
                saveTeacher(post.getTeacherId());
                saveTeacherProfilPicture(post.getTeacherId());
                savePostMedia(post.getId());
            }
            return null;
        }
    }

    private List<Post> loadPosts() {
        FileInputStream fis = null;
        Parent parent;
        List<Post> postLoad = new ArrayList<>();

        try {
            fis = getContext().openFileInput(ParentFileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            String Json = sb.toString();
            Type type = new TypeToken<Parent>(){}.getType();
            Gson gson = new GsonBuilder()
                    .create();

            parent = gson.fromJson(Json,type);
            for (Child child:parent.getChildren()
                 ) {
                postLoad.addAll(child.getPosts());
            }

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

        return postLoad;
    }

    private void saveTeacher(final int teacherID){
        if(!fileExist("teacher_"+teacherID+".dat"))
        {
            iTeachersApi = ApiClient.getClient().create(ITeachersApi.class);
            iTeachersApi.getTeacher(teacherID).enqueue(new Callback<Teacher>() {
                @Override
                public void onResponse(Call<Teacher> call, Response<Teacher> response) {

                        Gson gson = new GsonBuilder()
                                .registerTypeAdapter(Date.class, new GsonTimeAdapter())
                                .create();
                        Teacher teacher = response.body();
                        String text = gson.toJson(teacher);
                        FileOutputStream fos = null;

                        //region save the teacher object as a json
                        try {
                            fos = getContext().openFileOutput("teacher_"+teacherID+".dat", Context.MODE_PRIVATE);
                            fos.write(text.getBytes());

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
                        //endregion

                        feedAdapter.notifyDataSetChanged();




                }

                @Override
                public void onFailure(Call<Teacher> call, Throwable t) {

                }
            });
        }
    }

    private void saveTeacherProfilPicture(final int teacherID){
        if(!fileExist("teacher_profile_picture_"+teacherID+".dat"))
        {
            ////// NADERR REPLACE THIS WITH OUR OWN ADRESS SERVER AND MODIFY THE PARAMETER OF iMediaApi.getPhoto("...")
            ////// OPTIMIZATION : USE ONLY ONE iMedia INTERFACE (declared as a global var) FOR THIS METHOD AND THE NEXT METHOD
            //region intializing media API
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://i.imgur.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            IMediaApi iMediaApi = retrofit.create(IMediaApi.class);
            //endregion
            iMediaApi.getPhoto(ConvertTeacherIDToPhotoName(teacherID)).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    FileOutputStream fos = null;
                    InputStream fis = response.body().byteStream();

                    byte[] fileReader = new byte[4096];
                    try {
                        fos = getActivity().openFileOutput("teacher_profile_picture_" + teacherID+".dat", Context.MODE_PRIVATE);
                        while (true) {
                            int read = fis.read(fileReader);

                            if (read == -1) {
                                break;
                            }
                            fos.write(fileReader, 0, read);
                        }

                        //notfiying the adapter that a new file has been downloaded
                        feedAdapter.notifyDataSetChanged();

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
        }
    }

    private void savePostMedia(final int postID){
        if(!fileExist("post_media_"+postID+".dat"))
        {
            ////// NADERR REPLACE THIS WITH OUR OWN ADRESS SERVER AND MODIFY THE PARAMETER OF iMediaApi.getPhoto("...")
            //region intializing media API
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://i.imgur.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            IMediaApi iMediaApi = retrofit.create(IMediaApi.class);
            //endregion
            iMediaApi.getPhoto(ConvertPostIDtoPhotoName(postID)).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    FileOutputStream fos = null;
                    InputStream fis = response.body().byteStream();

                    byte[] fileReader = new byte[4096];
                    try {
                        fos = getActivity().openFileOutput("post_media_" + postID+".dat", Context.MODE_PRIVATE);
                        while (true) {
                            int read = fis.read(fileReader);

                            if (read == -1) {
                                break;
                            }
                            fos.write(fileReader, 0, read);
                        }

                        //notfiying the adapter that a new file has been downloaded
                        feedAdapter.notifyDataSetChanged();

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
        }
    }

    private boolean fileExist(String fname){
        File file = getActivity().getFileStreamPath(fname);
        return file.exists();
    }

    //region to be replaced and removed after

    private String ConvertTeacherIDToPhotoName(int i){
        String s = "Oq17met.jpg";
        switch (i%2){
            case 0:
                s= "nXrHjaG.jpg";
                break;
            case 1:
                s= "Oq17met.jpg";
                break;
        }
        return s;
    }

    private String ConvertPostIDtoPhotoName(int i){
        String s = "UaBbfMh.jpg";
        switch (i%2){
            case 0:
                s = "UaBbfMh.jpg";
                break;
            case 1:
                s ="3xXKmMb.jpg";
                break;
        }
        return s;
    }

    //endregion

    private List<Post> DeleteRedundantPosts(List<Post> posts){
        List<Post> new_postList = new ArrayList<>();
        for (Post post:posts
             ) {
            if(!(PostIfExistInList(new_postList,post))){
                new_postList.add(post);
            }
        }
        return new_postList;
    }

    private boolean PostIfExistInList(List<Post> postList, Post post){
        boolean result = false;
        int i=0;
        while(result==false && i<postList.size()){
            if(postList.get(i).getId() == post.getId()){
                result = true;
            }
            else{
                i++;
            }
        }
        return result;
    }



}


