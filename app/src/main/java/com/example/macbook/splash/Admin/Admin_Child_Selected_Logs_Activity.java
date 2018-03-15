package com.example.macbook.splash.Admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macbook.splash.Adapters.Admin_Adapters.ChildLogsAdapter;
import com.example.macbook.splash.Adapters.GsonTimeAdapter;
import com.example.macbook.splash.Adapters.LogAdapter;
import com.example.macbook.splash.Adapters.LogsAdapter;
import com.example.macbook.splash.Interfaces.ApiClient;
import com.example.macbook.splash.Interfaces.IChildrenApi;
import com.example.macbook.splash.Interfaces.ILogsApi;
import com.example.macbook.splash.LogsPerChildActivity;
import com.example.macbook.splash.Models.Child;
import com.example.macbook.splash.Models.Comment;
import com.example.macbook.splash.Models.Log;
import com.example.macbook.splash.Models.Teacher;
import com.example.macbook.splash.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_Child_Selected_Logs_Activity extends AppCompatActivity {

    private Child child;
    private List<Log> resultat;
    private ChildLogsAdapter logsAdapter;
    private LogAdapter logAdapter;
    private Bitmap compressedImageBitmap;
    private ListView listview_log_comment_in_log_per_child;
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_child_selected_logs);

        Intent i = getIntent();
        String jsonChild = i.getStringExtra("jsonChild");
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new GsonTimeAdapter())
                .create();
        Type type = new TypeToken<Child>(){}.getType();
        child = gson.fromJson(jsonChild,type);

        updateChildLogs(child.getId());

        CircleImageView child_profilpicture_in_logs = (CircleImageView)findViewById(R.id.child_profilpicture_in_logs);
        TextView child_name_in_logs = (TextView)findViewById(R.id.child_name_in_logs);
        TextView child_nickname_in_logs = (TextView)findViewById(R.id.child_nickname_in_logs);

        File f=new File(this.getFilesDir(), "child_profile_picture_"+ child.getId()+".dat");
        try {
            compressedImageBitmap = new Compressor(this).compressToBitmap(f);
            child_profilpicture_in_logs.setImageBitmap(compressedImageBitmap);
        }catch (Exception e){
            child_profilpicture_in_logs.setImageResource(R.drawable.genericprofile);
        }

        child_name_in_logs.setText((child.getName()+" "+child.getLastName()));
        child_nickname_in_logs.setText(("( "+child.getNickName()+" )"));

        resultat = new ArrayList<>();
        ListView logs_list_per_child = findViewById(R.id.logs_list_per_child);
        logsAdapter = new ChildLogsAdapter(resultat,this);
        logs_list_per_child.setAdapter(logsAdapter);
        logs_list_per_child.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Log log = resultat.get(position);
                ApiClient.getClient().create(ILogsApi.class).getLogComments(resultat.get(position).getId()).enqueue(new Callback<List<Comment>>() {
                    @Override
                    public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                        if (response.isSuccessful()){
                            settingLogCommentsView(response.body(),log);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Comment>> call, Throwable t) {
                        Toast.makeText(getBaseContext(),"Erreur au cours du telechargement des commentaires",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void updateChildLogs(int child_id) {
        ApiClient.getClient().create(IChildrenApi.class).getChildLogs(child_id).enqueue(new Callback<List<Log>>() {
            @Override
            public void onResponse(Call<List<Log>> call, Response<List<Log>> response) {
                if (response.isSuccessful()) {
                    resultat = response.body();
                    logsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Log>> call, Throwable t) {
                Toast.makeText(getBaseContext(),"Impossible de voir les observations",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void settingLogCommentsView(List<Comment> comments,Log log){
        //region OnItemClickShowLogView
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.customlayout_log_per_child,null);

        //region Traitement
        CircleImageView photo_child_per_log = (CircleImageView)mView.findViewById(R.id.photo_child_per_log);
        TextView log_name_in_log_per_child = (TextView)mView.findViewById(R.id.log_name_in_log_per_child);
        TextView log_date_in_log_per_child = (TextView)mView.findViewById(R.id.log_date_in_log_per_child);
        TextView log_writer_in_log_per_child = (TextView)mView.findViewById(R.id.log_writer_in_log_per_child);
        TextView log_content_in_log_per_child = (TextView)mView.findViewById(R.id.log_content_in_log_per_child);
        listview_log_comment_in_log_per_child = (ListView)mView.findViewById(R.id.listview_log_comment_in_log_per_child);
        TextView tv_commentaire_in_log_per_child = (TextView)mView.findViewById(R.id.tv_commentaire_in_log_per_child);
        EditText et_commentaire_in_log_per_child = (EditText) mView.findViewById(R.id.et_commentaire_in_log_per_child);
        Button btn_commentaire_in_log_per_child = (Button)mView.findViewById(R.id.btn_commentaire_in_log_per_child);



        //SETTING FONTS
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        Typeface myCustomFont1 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        log_name_in_log_per_child.setTypeface(myCustomFont1);
        log_date_in_log_per_child.setTypeface(myCustomFont);
        log_writer_in_log_per_child.setTypeface(myCustomFont);
        log_content_in_log_per_child.setTypeface(myCustomFont1);
        tv_commentaire_in_log_per_child.setTypeface(myCustomFont1);

        et_commentaire_in_log_per_child.setVisibility(View.INVISIBLE);
        btn_commentaire_in_log_per_child.setVisibility(View.INVISIBLE);

        photo_child_per_log.setImageBitmap(compressedImageBitmap);

        log_name_in_log_per_child.setText(log.getTitle());

        log_date_in_log_per_child.setText(("Modifié le : "+dateFormat.format(log.getDate())));

        log_writer_in_log_per_child.setText(("Rédigé par : "+log.getWriter()));

        log_content_in_log_per_child.setText(log.getContent());

        logAdapter = new LogAdapter(comments,this,getLayoutInflater());
        listview_log_comment_in_log_per_child.setAdapter(logAdapter);

        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) listview_log_comment_in_log_per_child.getLayoutParams();
        float pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 75, getResources().getDisplayMetrics());
        int listSize = 1;
        if (comments != null)
        {
            listSize = comments.size();
        }
        lp.height =(int)(pixels*listSize);
        listview_log_comment_in_log_per_child.setLayoutParams(lp);

        //endregion

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
        //endregion
    }

}
