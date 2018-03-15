package com.example.macbook.splash;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.macbook.splash.Adapters.ChildAdapter;
import com.example.macbook.splash.Adapters.GsonTimeAdapter;
import com.example.macbook.splash.Adapters.LogAdapter;
import com.example.macbook.splash.Adapters.LogsAdapter;
import com.example.macbook.splash.Models.Child;
import com.example.macbook.splash.Models.Comment;
import com.example.macbook.splash.Models.Log;
import com.example.macbook.splash.Models.Teacher;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class LogsPerChildActivity extends AppCompatActivity {

    private String readLogsFileName = "readLogsPerChild.dat";
    private String TeacherFile="teacher.dat";
    private Child child;
    private int index;
    private Bitmap compressedImageBitmap;
    private float pixels;
    private ListView listview_log_comment_in_log_per_child;
    private EditText et_commentaire_in_log_per_child;
    private int pos;
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
    private LogsAdapter logsAdapter;
    private ListView logs_list_per_child;
    private LogAdapter logAdapter;
    private int listSize;
    private RelativeLayout.LayoutParams lp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        Intent i = getIntent();
       // String jsonChild = i.getExtras().getString("Child");
        index = i.getIntExtra("index",0);

        if(loadTeacher() != null){
            child = loadTeacher().getGroups().get(0).getChildren().get(index);
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs_per_child);

        //region SettingView
        //INITIALIZING THE VIEWS
        CircleImageView child_profilpicture_in_logs = (CircleImageView)findViewById(R.id.child_profilpicture_in_logs);
        TextView child_name_in_logs = (TextView)findViewById(R.id.child_name_in_logs);
        TextView child_nickname_in_logs = (TextView)findViewById(R.id.child_nickname_in_logs);
        TextView child_age_in_logs = (TextView)findViewById(R.id.child_age_in_logs);
        TextView child_nbrobservations_in_logs = (TextView)findViewById(R.id.child_nbrobservations_in_logs);

        File f=new File(this.getFilesDir(), "child_profile_picture_"+ child.getId()+".dat");
        try {
            compressedImageBitmap = new Compressor(this).compressToBitmap(f);
            child_profilpicture_in_logs.setImageBitmap(compressedImageBitmap);
        }catch (Exception e){
            child_profilpicture_in_logs.setImageResource(R.drawable.ic_launcher_foreground);
        }

        child_name_in_logs.setText((child.getName()+" "+child.getLastName()));
        child_nickname_in_logs.setText(("( "+child.getNickName()+" )"));

        child_age_in_logs.setText((Integer.toString(CalculateAge(dateFormat.format(child.getBirth())))+" Ans"));
        child_nbrobservations_in_logs.setText((Integer.toString(child.getLogs().size())+" Observations"));
        //endregion
        //INTIALIZING THE LIST VIEW
        logs_list_per_child = (ListView)findViewById(R.id.logs_list_per_child);
        logsAdapter = new LogsAdapter(child.getLogs(),this,getLayoutInflater(),child.getId(),loadReadLogs());
        logs_list_per_child.setAdapter(logsAdapter);

        logs_list_per_child.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                updateReadLogs(child.getId(),child.getLogs().get(position).getId());
                //region OnItemClickShowLogView
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(LogsPerChildActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.customlayout_log_per_child,null);

                //region Traitement
                CircleImageView photo_child_per_log = (CircleImageView)mView.findViewById(R.id.photo_child_per_log);
                TextView log_name_in_log_per_child = (TextView)mView.findViewById(R.id.log_name_in_log_per_child);
                TextView log_date_in_log_per_child = (TextView)mView.findViewById(R.id.log_date_in_log_per_child);
                TextView log_writer_in_log_per_child = (TextView)mView.findViewById(R.id.log_writer_in_log_per_child);
                TextView log_content_in_log_per_child = (TextView)mView.findViewById(R.id.log_content_in_log_per_child);
                listview_log_comment_in_log_per_child = (ListView)mView.findViewById(R.id.listview_log_comment_in_log_per_child);
                TextView tv_commentaire_in_log_per_child = (TextView)mView.findViewById(R.id.tv_commentaire_in_log_per_child);
                et_commentaire_in_log_per_child = (EditText) mView.findViewById(R.id.et_commentaire_in_log_per_child);
                Button btn_commentaire_in_log_per_child = (Button)mView.findViewById(R.id.btn_commentaire_in_log_per_child);
                pos = position;


                //SETTING FONTS
                Typeface myCustomFont = Typeface.createFromAsset(LogsPerChildActivity.this.getAssets(), "fonts/Roboto-Thin.ttf");
                Typeface myCustomFont1 = Typeface.createFromAsset(LogsPerChildActivity.this.getAssets(), "fonts/Roboto-Light.ttf");
                log_name_in_log_per_child.setTypeface(myCustomFont1);
                log_date_in_log_per_child.setTypeface(myCustomFont);
                log_writer_in_log_per_child.setTypeface(myCustomFont);
                log_content_in_log_per_child.setTypeface(myCustomFont1);
                tv_commentaire_in_log_per_child.setTypeface(myCustomFont1);
                et_commentaire_in_log_per_child.setTypeface(myCustomFont);
                btn_commentaire_in_log_per_child.setTypeface(myCustomFont);


                photo_child_per_log.setImageBitmap(compressedImageBitmap);

                log_name_in_log_per_child.setText(child.getLogs().get(position).getTitle());

                log_date_in_log_per_child.setText(("Modifié le : "+dateFormat.format(child.getLogs().get(position).getDate())));

                log_writer_in_log_per_child.setText(("Rédigé par : "+child.getLogs().get(position).getWriter()));

                log_content_in_log_per_child.setText(child.getLogs().get(position).getContent());

                logAdapter = new LogAdapter(child.getLogs().get(position).getComments(),LogsPerChildActivity.this,getLayoutInflater());
                listview_log_comment_in_log_per_child.setAdapter(logAdapter);

                lp = (RelativeLayout.LayoutParams) listview_log_comment_in_log_per_child.getLayoutParams();
                pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 75, getResources().getDisplayMetrics());
                listSize = 1;
                if (child.getLogs().get(position).getComments() != null)
                {
                    listSize = child.getLogs().get(position).getComments().size();
                }
                lp.height =(int)(pixels*listSize);
                listview_log_comment_in_log_per_child.setLayoutParams(lp);


                btn_commentaire_in_log_per_child.setOnClickListener(new View.OnClickListener() {
                    //region ButtonCommentaire
                    @Override
                    public void onClick(View v) {
                        Date today = Calendar.getInstance().getTime();
                        Teacher teacher = loadTeacher();
                        Comment comment = new Comment(5,et_commentaire_in_log_per_child.getText().toString(),today,teacher.getName()+" "+teacher.getLastName());
                        //REQUEST POST COMMENT AND GET THE COMMENT IN RESPONSE
                        child.getLogs().get(pos).getComments().add(comment);
                        logAdapter.updateComments(child.getLogs().get(pos).getComments());

                        if (child.getLogs().get(pos).getComments() != null)
                        {
                            listSize = child.getLogs().get(pos).getComments().size();
                        }
                        lp.height =(int)(pixels*listSize);
                        listview_log_comment_in_log_per_child.setLayoutParams(lp);

                        //WE ONLY WORKED WITH ONE GROUP NEED TO BE CHANGED
                        teacher.getGroups().get(0).getChildren().remove(index);
                        teacher.getGroups().get(0).getChildren().add(index,child);
                        Gson gson = new GsonBuilder()
                                .create();
                        String tempString = gson.toJson(teacher);
                        saveTeacher(tempString);
                        et_commentaire_in_log_per_child.setText("");
                    }
                });
                    //endregion
                //endregion

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
                //endregion
                logsAdapter.update(loadReadLogs());
            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,LoggedMainActivity.class);
        i.putExtra("position",2);
        startActivity(i);
    }

    private HashMap<Integer,ArrayList<Integer>> loadReadLogs(){
        HashMap<Integer,ArrayList<Integer>> hmReadLogs = new HashMap<>();
        FileInputStream fis = null;
        //CODE

        try {
            fis = this.openFileInput(readLogsFileName);
            ObjectInputStream ois=new ObjectInputStream(fis);
            hmReadLogs=(HashMap<Integer,ArrayList<Integer>>)ois.readObject() ;
            ois.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
        return hmReadLogs;
    }

    private void saveReadLogs(HashMap<Integer,ArrayList<Integer>> hmReadLogs){
        FileOutputStream fos = null;
        try {
            fos = this.openFileOutput(readLogsFileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(hmReadLogs);

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

    private void updateReadLogs(int childID,int logID){
        HashMap<Integer,ArrayList<Integer>> listHashMapLogs = loadReadLogs();
        if (listHashMapLogs == null){
            listHashMapLogs = new HashMap<>();
            ArrayList<Integer> tab = new ArrayList<>();
            tab.add(logID);
            listHashMapLogs.put(childID,tab);
            saveReadLogs(listHashMapLogs);
        }else{
            if (listHashMapLogs.get(childID) == null){
                ArrayList<Integer> tab = new ArrayList<>();
                tab.add(logID);
                listHashMapLogs.put(childID,tab);
                saveReadLogs(listHashMapLogs);
            }else{//Liste mawjouda wel child mawjoud
                if(listHashMapLogs.get(childID).contains(logID)){
                    //do nothing
                }else{
                    listHashMapLogs.get(childID).add(logID);
                    saveReadLogs(listHashMapLogs);
                }
            }
        }
    }

    private int CalculateAge(String birthDate){
        int a = Calendar.getInstance().get(Calendar.YEAR);
        int b = Integer.parseInt(birthDate.substring(6,10));
        return a-b;
    }

    private Teacher loadTeacher(){
        FileInputStream fis = null;
        //CODE

        try {
            fis = this.openFileInput(TeacherFile);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            String Json = sb.toString();
            Type type = new TypeToken<Teacher>(){}.getType();
            Gson gson = new GsonBuilder()
                    .create();
            Teacher teacher = gson.fromJson(Json,type);
            return teacher;


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
    }

    private void saveTeacher(String jsonTeacher){
        File file = new File(getFilesDir() + "/"+TeacherFile);
        file.delete();
        FileOutputStream fos = null;

        try {
            fos = this.openFileOutput(TeacherFile,Context.MODE_PRIVATE);
            fos.write(jsonTeacher.getBytes());
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
}
