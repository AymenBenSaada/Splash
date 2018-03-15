package com.example.macbook.splash.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.macbook.splash.Interfaces.ApiClient;
import com.example.macbook.splash.Interfaces.IMediaApi;
import com.example.macbook.splash.Interfaces.ITeachersApi;
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
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnCompressListener;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by macbook on 24/02/2018.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder> {

    private Context mContext;
    private List<Post> postList;

    //region GlobalVariablesForHolder
    private Post post;
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    private MyViewHolder myViewHolder;

    //endregion


    public FeedAdapter(Context mContext, List<Post> postList) {
        this.mContext = mContext;
        this.postList = postList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView feed_legend, feed_sender_name, feed_date;
        private ImageView feed_image;
        private Button btnDownload, btnShare;
        private CircleImageView feed_sender_image;

        private MyViewHolder(View view) {
            super(view);
            feed_legend = (TextView) view.findViewById(R.id.feed_legend);
            feed_sender_name = (TextView) view.findViewById(R.id.feed_sender_name);
            feed_date = (TextView) view.findViewById(R.id.feed_date);
            feed_image = (ImageView) view.findViewById(R.id.feed_image);
            feed_sender_image = (CircleImageView) view.findViewById(R.id.feed_sender_image);
            btnDownload = (Button) view.findViewById(R.id.btnDownload);
            btnShare = (Button) view.findViewById(R.id.btnShare);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customlayout_feed,parent ,false);

        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        post = postList.get(position);

        Typeface ThinRobotoFont=Typeface.createFromAsset(mContext.getAssets(),"fonts/Roboto-Thin.ttf");

        holder.feed_legend.setTypeface(ThinRobotoFont);
        holder.feed_sender_name.setTypeface(ThinRobotoFont);
        holder.feed_date.setTypeface(ThinRobotoFont);


        holder.feed_legend.setText(post.getLegend());
        holder.feed_date.setText(("Envoyé le "+dateFormat.format(post.getDate()))) ;

        Teacher teacher = loadTeacher(post.getTeacherId());
        if (teacher != null) {
            holder.feed_sender_name.setText((teacher.getName() + " " + teacher.getLastName()));

            if (fileExist("teacher_profil_picture_" + teacher.getId()+".dat")) {
                File file = new File(mContext.getFilesDir() + "/" + "teacher_profil_picture_" + teacher.getId()+".dat");
                holder.feed_sender_image.setImageURI(Uri.fromFile(file));
            }
        }

        //region setting post_media
        if (fileExist("post_media_"+post.getId()+".dat")){
            File file = new File(mContext.getFilesDir()+"/"+"post_media_"+post.getId()+".dat");

            Log.e("DEBUG","1- before everything : "+ file.length());
            holder.feed_image.setImageURI(Uri.fromFile(file));
        }
        //endregion
    }

    //region Methods

    private Teacher loadTeacher(int teacherID){
        Teacher teacher = null;
        if(fileExist("teacher_"+teacherID+".dat")) {
            //region getTeacherfromInternalStorage
            FileInputStream fis = null;
            try {
                fis = mContext.openFileInput("teacher_" + teacherID + ".dat");
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String text;

                while ((text = br.readLine()) != null) {
                    sb.append(text).append("\n");
                }

                String Json = sb.toString();
                Type type = new TypeToken<Teacher>() {
                }.getType();
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(Date.class, new GsonTimeAdapter())
                        .create();

                teacher = gson.fromJson(Json, type);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            //endregion
        }
        return teacher;
    }

    private boolean fileExist(String fname){
        File file = mContext.getFileStreamPath(fname);
        return file.exists();
    }

    //endregion
}
