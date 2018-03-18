package com.example.macbook.splash.Admin;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macbook.splash.CommunSuggestionsActivity;
import com.example.macbook.splash.Interfaces.ApiClient;
import com.example.macbook.splash.Interfaces.IAdminsApi;
import com.example.macbook.splash.Models.Admin;
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
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminMainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nvDrawer;
    private String AdminFile = "admin.dat";
    private CircleImageView image;
    private TextView name;
    private  TextView email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        //region drawer initialization
        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);

        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupDrawerContent(nvDrawer);
        //endregion

        //region DrawerHeader
        View nvDrawerHeaderView = nvDrawer.getHeaderView(0);
        image = (CircleImageView) nvDrawerHeaderView.findViewById(R.id.profile_image);
        name = (TextView) nvDrawerHeaderView.findViewById(R.id.profile_name);
        email = (TextView) nvDrawerHeaderView.findViewById(R.id.profile_email);

        Typeface RegularRobotoFont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf");

        name.setTypeface(RegularRobotoFont);
        email.setTypeface(RegularRobotoFont);

        Intent i = getIntent();
        int adminId = i.getIntExtra("userId",0);
        IAdminsApi apiService = ApiClient.getClient().create(IAdminsApi.class);
        apiService.getAdmin(adminId).enqueue(new Callback<Admin>() {
            @Override
            public void onResponse(Call<Admin> call, Response<Admin> response) {
                Admin admin = response.body();
                image.setImageURI(loadAdminPictureURIFromTheInternalStorage(admin.getKindergartenId()));
                name.setText((admin.getName()+" "+admin.getLastName()));
                email.setText(admin.getEmail());
            }

            @Override
            public void onFailure(Call<Admin> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "La connexion au serveur a echoué!", Toast.LENGTH_SHORT); toast.show();
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
                Intent i = new Intent(this,CommunSuggestionsActivity.class);
                startActivity(i);
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

    /*
    private Admin loadAdminModelFromInternalStorage(String file_name){
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
            Type type = new TypeToken<Admin>(){}.getType();
            Gson gson = new GsonBuilder()
                    .create();
            Admin admin = gson.fromJson(Json,type);
            return admin;


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

*/
    private Uri loadAdminPictureURIFromTheInternalStorage(int kg_ID){
        File file = new File(this.getFilesDir() + "/" +"kg_profile_picture_"+kg_ID+".dat");
        return Uri.fromFile(file);
    }

    //region OnClickMethods

    public void admin_On_Groups_Click(View view) {
        Intent i = new Intent(this,Admin_Groupes_Activity.class);
        startActivity(i);
    }

    public void admin_On_Messages_Click(View view) {
    }

    public void admin_On_Notifications_Click(View view) {
        Intent i = new Intent(this,Admin_Demandes_Activity.class);
        startActivity(i);
    }

    public void admin_On_Enseignants_Click(View view) {
        Intent i = new Intent(this,Admin_Enseignants_Activity.class);
        startActivity(i);
    }

    public void admin_On_Enfants_Click(View view) {
        Intent i = new Intent(this,Admin_ChildrenList_Activity.class);
        startActivity(i);
    }


    //endregion
}
