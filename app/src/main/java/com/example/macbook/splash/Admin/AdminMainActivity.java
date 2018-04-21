package com.example.macbook.splash.Admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macbook.splash.Interfaces.ApiClient;
import com.example.macbook.splash.Interfaces.IAdminsApi;
import com.example.macbook.splash.MainActivity;
import com.example.macbook.splash.Models.Admin;
import com.example.macbook.splash.R;

import java.io.File;

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
        SharedPreferences sharedisConnecterdPereferences = getSharedPreferences("AccountStatus",MODE_PRIVATE);

        int userId = sharedisConnecterdPereferences.getInt("userId",-1);

        int adminId = userId;
        IAdminsApi apiService = ApiClient.getClient().create(IAdminsApi.class);
        apiService.getAdmin(adminId).enqueue(new Callback<Admin>() {
            @Override
            public void onResponse(Call<Admin> call, Response<Admin> response) {
                Admin admin = response.body();
                try
                {
                    image.setImageURI(loadAdminPictureURIFromTheInternalStorage(admin.getKindergartenId()));
                }
                catch (Exception e)
                {
                    image.setImageResource(R.drawable.genericprofile);
                }
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
                SharedPreferences sharedPreferences = getSharedPreferences("AccountStatus",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("AccountType","");
                editor.putBoolean("isConnected",false);
                editor.putInt("userId",0);
                editor.apply();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
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
