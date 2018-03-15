package com.example.macbook.splash.Adapters.Admin_Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macbook.splash.Adapters.FeedAdapter;
import com.example.macbook.splash.Adapters.GsonTimeAdapter;
import com.example.macbook.splash.Adapters.NewLogChildrenListAdapter;
import com.example.macbook.splash.Admin.Admin_Groupes_Activity;
import com.example.macbook.splash.Admin.Admin_Groupes_OnGroupSelected_Activity;
import com.example.macbook.splash.Interfaces.ApiClient;
import com.example.macbook.splash.Interfaces.IGroupsApi;
import com.example.macbook.splash.Models.Child;
import com.example.macbook.splash.Models.Comment;
import com.example.macbook.splash.Models.Group;
import com.example.macbook.splash.Models.Kindergarten;
import com.example.macbook.splash.Models.Log;
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
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by macbook on 03/03/2018.
 */

public class Group_List_Adapter extends RecyclerView.Adapter<Group_List_Adapter.GroupListViewHolder>{

    private Activity mActivity;
    private List<Group> groupList;
    @Nullable
    private Integer KindergartenID;

    public Group_List_Adapter(Activity mActivity, List<Group> groupList,@Nullable Integer KindergartenID) {
        this.mActivity = mActivity;
        this.groupList = groupList;
        this.KindergartenID = KindergartenID;
    }

    public class GroupListViewHolder extends RecyclerView.ViewHolder {

        private TextView grp_children_nbr_in_grp_list_in_teacher_profile, grp_teachers_nbr_in_grp_list_in_teacher_profile, grp_name_in_grp_list_in_teacher_profile;
        private ImageView declinebtn_in_grps_list;

        private GroupListViewHolder(View view) {
            super(view);
            grp_children_nbr_in_grp_list_in_teacher_profile = (TextView) view.findViewById(R.id.grp_children_nbr_in_grp_list_in_teacher_profile);
            grp_teachers_nbr_in_grp_list_in_teacher_profile = (TextView) view.findViewById(R.id.grp_teachers_nbr_in_grp_list_in_teacher_profile);
            grp_name_in_grp_list_in_teacher_profile = (TextView) view.findViewById(R.id.grp_name_in_grp_list_in_teacher_profile);
            declinebtn_in_grps_list = (ImageView) view.findViewById(R.id.declinebtn_in_grps_list);
        }
    }


    @Override
    public int getItemCount() {
        return groupList.size();
    }

    @Override
    public GroupListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customlayout_admin_enseignant_selected_listofgroups_adapter,parent ,false);

        return new GroupListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupListViewHolder holder, int position) {

        final Group group = groupList.get(position);

        final Typeface Roboto_Thin_Font = Typeface.createFromAsset(mActivity.getAssets(), "fonts/Roboto-Light.ttf");
        final Typeface Roboto_Regular_Font = Typeface.createFromAsset(mActivity.getAssets(), "fonts/Roboto-Regular.ttf");

        holder.grp_children_nbr_in_grp_list_in_teacher_profile.setTypeface(Roboto_Regular_Font);
        holder.grp_name_in_grp_list_in_teacher_profile.setTypeface(Roboto_Thin_Font);
        holder.grp_teachers_nbr_in_grp_list_in_teacher_profile.setTypeface(Roboto_Regular_Font);

        holder.grp_name_in_grp_list_in_teacher_profile.setText(group.getName());
        holder.grp_children_nbr_in_grp_list_in_teacher_profile.setText((getChildrenCountInAGroupStoredInTheInternalStorage(group.getId()) + " enfants"));
        holder.grp_teachers_nbr_in_grp_list_in_teacher_profile.setText((getTeachersCountInAGroupStoredInTheInternalStorage(group.getId()) + " enseignants"));

        if(mActivity instanceof Admin_Groupes_Activity){

            holder.declinebtn_in_grps_list.setVisibility(View.VISIBLE);
            holder.declinebtn_in_grps_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(mActivity);
                    View mView = mActivity.getLayoutInflater().inflate(R.layout.customlayout_dialog_validating_action,null);

                    TextView etes_vous_sur = (TextView) mView.findViewById(R.id.etes_vous_sur);
                    CardView cardView_ok = (CardView) mView.findViewById(R.id.cardview_ok);
                    CardView cardView_not_ok = (CardView) mView.findViewById(R.id.cardview_not_ok);

                    etes_vous_sur.setTypeface(Roboto_Thin_Font);

                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();

                    cardView_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteGroupRequest(group.getId());
                            dialog.dismiss();
                        }
                    });

                    cardView_not_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });

            holder.grp_name_in_grp_list_in_teacher_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    transition(group.getId(),group.getName());
                }
            });
        }else {
            holder.declinebtn_in_grps_list.setVisibility(View.INVISIBLE);
        }
    }

    //region Methods

    private int getChildrenCountInAGroupStoredInTheInternalStorage (int group_ID){

        List<Child> children = loadGroupChildrenInTheInternalStorage(group_ID);

        if(children == null){
            return 0;
        }else {
            return children.size();
        }
    }

    private List<Child> loadGroupChildrenInTheInternalStorage(int group_id){
        List<Child> children = null;
        if(fileExist("kg_group_children_"+group_id+".dat")) {
            //region getChildrenfromInternalStorage
            FileInputStream fis = null;
            try {
                fis = mActivity.openFileInput("kg_group_children_"+group_id+".dat");
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String text;

                while ((text = br.readLine()) != null) {
                    sb.append(text).append("\n");
                }

                String Json = sb.toString();
                Type type = new TypeToken<List<Child>>() {
                }.getType();
                Gson gson = new GsonBuilder()
                        .create();

                children = gson.fromJson(Json, type);
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
        return children;
    }

    private int getTeachersCountInAGroupStoredInTheInternalStorage (int group_ID){

        List<Teacher> teachers = loadGroupTeachersInTheInternalStorage(group_ID);

        if(teachers == null){
            return 0;
        }else {
            return teachers.size();
        }
    }

    private List<Teacher> loadGroupTeachersInTheInternalStorage(int group_id){
        List<Teacher> teachers = null;
        if(fileExist("kg_group_teachers_"+group_id+".dat")) {
            //region getTeachersfromInternalStorage
            FileInputStream fis = null;
            try {
                fis = mActivity.openFileInput("kg_group_teachers_"+group_id+".dat");
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String text;

                while ((text = br.readLine()) != null) {
                    sb.append(text).append("\n");
                }

                String Json = sb.toString();
                Type type = new TypeToken<List<Teacher>>() {
                }.getType();
                Gson gson = new GsonBuilder()
                        .create();

                teachers = gson.fromJson(Json, type);
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
        return teachers;
    }

    private boolean fileExist(String fname){
        File file = mActivity.getFileStreamPath(fname);
        return file.exists();
    }


    private void deleteGroupRequest (final int groupID){
        IGroupsApi iGroupsApi = ApiClient.getClient().create(IGroupsApi.class);
        iGroupsApi.deleteGroup(groupID).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    int k = getTheIndexInTheListFromID(groupID,groupList);
                    groupList.remove(k);
                    saveKindergartenGroupsInTheInternalStorage(groupList,KindergartenID);
                    notifyDataSetChanged();
                    Toast.makeText(mActivity,"Groupe supprimé avec succès !",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private int getTheIndexInTheListFromID (int groupID, List<Group> groupList){
        int i = 0;
        while (i<groupList.size()){
            if(groupList.get(i).getId() == groupID){
                break;
            }else {
                i++;
            }
        }
        return i;
    }

    private void saveKindergartenGroupsInTheInternalStorage(List<Group> groups,int kindergarten_id){
        File file = new File(mActivity.getFilesDir() + "/"+"kg_groups_"+kindergarten_id+".dat");
        file.delete();
        FileOutputStream fos = null;

        try {
            fos = mActivity.openFileOutput("kg_groups_"+kindergarten_id+".dat",Context.MODE_PRIVATE);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new GsonTimeAdapter())
                    .create();
            String json = gson.toJson(groups);
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

    private void transition(int groupID, String groupName){
        Intent i = new Intent(mActivity, Admin_Groupes_OnGroupSelected_Activity.class);
        i.putExtra("groupID", groupID);
        i.putExtra("groupName" , groupName);
        i.putExtra("kindergartenID",KindergartenID);
        mActivity.startActivity(i);
    }

    //endregion

}
