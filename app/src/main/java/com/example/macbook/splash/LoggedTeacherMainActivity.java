package com.example.macbook.splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


import com.example.macbook.splash.Interfaces.ApiClient;
import com.example.macbook.splash.Interfaces.ITeachersApi;
import com.example.macbook.splash.Models.Admin;
import com.example.macbook.splash.Models.Parent;
import com.example.macbook.splash.Models.Teacher;
import com.example.macbook.splash.SwipePagers.TeacherPager;
import com.example.macbook.splash.ViewModels.SuggestionViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import junit.framework.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.lang.reflect.Type;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import io.reactivex.internal.operators.observable.ObservableElementAt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoggedTeacherMainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nvDrawer;
    private ViewPager viewPager;
    private String TeacherFile = "teacher.dat";
    private String ParentFile="parent.dat";
    private String AdminFile="admin.dat";

    private Parent parent;
    private Teacher teacher;
    //   private GenericRepository repository = new GenericRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_main);

        //DRAWER
        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);

        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(nvDrawer);

        ITeachersApi apiService = ApiClient.getClient().create(ITeachersApi.class);
        Intent intent = getIntent();
        int teacherId = intent.getIntExtra("userId",0);
        apiService.getTeacherWithAll(teacherId).enqueue(new Callback<Teacher>() {
            @Override
            public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                teacher = response.body();
    //            repository.saveTeacher(getApplicationContext(),teacher);
                Log.d("Getting Teacher", "Success");
            }

            @Override public void onFailure(Call<Teacher> call, Throwable t) {
                Log.d("Getting Teacher", "Failure");
            }
        });

        //region Font
        Typeface RegularRobotoFont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf");
        Typeface ThinRobotoFont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        //endregion

        //region DrawerHeader
        View nvDrawerHeaderView = nvDrawer.getHeaderView(0);
        CircleImageView image = (CircleImageView) nvDrawerHeaderView.findViewById(R.id.profile_image);
        TextView name = (TextView) nvDrawerHeaderView.findViewById(R.id.profile_name);
        TextView email = (TextView) nvDrawerHeaderView.findViewById(R.id.profile_email);

        name.setTypeface(RegularRobotoFont);
        email.setTypeface(RegularRobotoFont);

        /*
        try
        {
            File file = new File(this.getFilesDir() + "/" +"parent_profil_picture_"+parent.getId()+".dat");
            image.setImageURI(Uri.fromFile(file));
            name.setText((parent.getName()+" "+parent.getLastName()));
            email.setText(parent.getEmail());

        }catch (NullPointerException e)
        {
        */





        //endregion

        //region SWIPE

        TeacherPager teacherPager = new TeacherPager(getSupportFragmentManager(), 3);
        viewPager = (ViewPager) findViewById(R.id.flContent);
        viewPager.setAdapter(teacherPager);
        Intent i = getIntent();
        int f = i.getIntExtra("position",1);
        switch (f){
            case 0:
                setTitle("Suggestions");
                break;
            case 1:
                getSupportActionBar().hide();
                setTitle("Camera");
                break;
            case 2:
                setTitle("Logs");
                break;
        }
        viewPager.setCurrentItem(f);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setTitle("Suggestions");
                        getSupportActionBar().show();
                        break;
                    case 1:
                        setTitle("Camera");
                        getSupportActionBar().hide();
                        break;
                    case 2:
                        setTitle("Logs");
                        getSupportActionBar().show();
                        break;
                    default:
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //endregion
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectItemDrawer(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.logout:
                SharedPreferences sharedPreferences = getSharedPreferences("isConnected",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("AccountType","");
                editor.putBoolean("isConnected",false);
                editor.putInt("userId",0);
                break;
            default:
                return;

        }
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawerlayout.closeDrawers();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
    }

    /*



    //region load models from internal storage
    private Teacher loadTeacher(){
        Type teacher_type = new TypeToken<Teacher>(){}.getType();
        return (Teacher) loadObjectFromInternalStorage(TeacherFile,teacher_type);
    }

    private Parent loadParent(){
        Type parent_type = new TypeToken<Parent>(){}.getType();
        return (Parent) loadObjectFromInternalStorage(ParentFile,parent_type);
    }

    private Admin loadAdmin(){
        Type admin_type = new TypeToken<Admin>(){}.getType();
        return (Admin) loadObjectFromInternalStorage(AdminFile,admin_type);
    }

    private Object loadObjectFromInternalStorage(String file_name,Type type){
        FileInputStream fis = null;

        //CODE

        try {
            fis = this.openFileInput(file_name);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            String Json = sb.toString();
            Gson gson = new GsonBuilder()
                    .create();
            Object object = gson.fromJson(Json,type);
            return object;


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
    //endregion


    */
}

