package com.example.macbook.splash.Adapters.Admin_Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.macbook.splash.Adapters.GsonTimeAdapter;
import com.example.macbook.splash.Admin.Admin_ChildrenList_Activity;
import com.example.macbook.splash.Interfaces.ApiClient;
import com.example.macbook.splash.Interfaces.IGroupsApi;
import com.example.macbook.splash.Interfaces.IKindergartensApi;
import com.example.macbook.splash.Models.Child;
import com.example.macbook.splash.Models.Group;
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
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by macbook on 04/03/2018.
 */

public class Admin_Groupes_Children_List_Adapter extends BaseAdapter {

    List<Child> children;
    Activity activity;
    @Nullable
    int group_id;

    public Admin_Groupes_Children_List_Adapter(List<Child> childList, Activity activity, @Nullable int group_id) {
        this.children = childList;
        this.activity = activity;
        this.group_id = group_id;
    }

    @Override
    public int getCount() {
        return children.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = activity.getLayoutInflater().inflate(R.layout.customlayout_adapter_for_admin_children_list,null);

        final Child child = children.get(position);

        CircleImageView child_profilpicture_in_newlog = (CircleImageView)convertView.findViewById(R.id.child_profilpicture_in_newlog);
        TextView childfullname_in_newlog = (TextView)convertView.findViewById(R.id.childfullname_in_newlog);
        TextView childnickname_in_newlog = (TextView)convertView.findViewById(R.id.childnickname_in_newlog);
        ImageView declinebtn_in_children_list = (ImageView) convertView.findViewById(R.id.declinebtn_in_children_list);

        //SETTING FONTS
        final Typeface myCustomFont = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Thin.ttf");
        childfullname_in_newlog.setTypeface(myCustomFont);
        childnickname_in_newlog.setTypeface(myCustomFont);

        //SETTING CONTENT
        File file=new File(activity.getFilesDir(), "child_profile_picture_"+ children.get(position).getId()+".dat");

        child_profilpicture_in_newlog.setImageURI(Uri.fromFile(file));

        childfullname_in_newlog.setText((children.get(position).getName()+" "+children.get(position).getLastName()));
        childnickname_in_newlog.setText(("( "+children.get(position).getNickName()+" )"));

        if (activity instanceof Admin_ChildrenList_Activity){
            declinebtn_in_children_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
                    View mView = activity.getLayoutInflater().inflate(R.layout.customlayout_dialog_validating_action,null);

                    TextView etes_vous_sur = (TextView) mView.findViewById(R.id.etes_vous_sur);
                    CardView cardView_ok = (CardView) mView.findViewById(R.id.cardview_ok);
                    CardView cardView_not_ok = (CardView) mView.findViewById(R.id.cardview_not_ok);

                    etes_vous_sur.setTypeface(myCustomFont);

                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();

                    cardView_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DeleteChildFromKindergartenRequest(child.getKindergartenId(), child.getId());
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
        }else{
            declinebtn_in_children_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
                    View mView = activity.getLayoutInflater().inflate(R.layout.customlayout_dialog_validating_action,null);

                    TextView etes_vous_sur = (TextView) mView.findViewById(R.id.etes_vous_sur);
                    CardView cardView_ok = (CardView) mView.findViewById(R.id.cardview_ok);
                    CardView cardView_not_ok = (CardView) mView.findViewById(R.id.cardview_not_ok);

                    etes_vous_sur.setTypeface(myCustomFont);

                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();

                    cardView_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            UnlinkChildRequest(child.getId());
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
        }


        return convertView;
    }


    //region Methods


    private void DeleteChildFromKindergartenRequest(final int kindergartenID, final int childID){
        IKindergartensApi iKindergartensApi =ApiClient.getClient().create(IKindergartensApi.class);
        iKindergartensApi.deleteKindergartenChild(kindergartenID,childID).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    int k = getTheIndexInTheListFromID(childID,children);
                    children.remove(k);
                    saveKindergartenChildrenList(children,kindergartenID);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void UnlinkChildRequest(final int child_id){
        IGroupsApi iGroupsApi = ApiClient.getClient().create(IGroupsApi.class);
        iGroupsApi.unLinkChildToGroup(child_id,group_id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    int k = getTheIndexInTheListFromID(child_id,children);
                    children.remove(k);
                    saveGroupChildrenList(children,group_id);
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private int getTheIndexInTheListFromID (int child_id, List<Child> childList){
        int i = 0;
        while (i<childList.size()){
            if(childList.get(i).getId() == child_id){
                break;
            }else {
                i++;
            }
        }
        return i;
    }

    private void saveGroupChildrenList(List<Child> children,int group_id){
        File file = new File(activity.getFilesDir() + "/"+"kg_group_children_"+group_id+".dat");
        file.delete();
        FileOutputStream fos = null;

        try {
            fos = activity.openFileOutput("kg_group_children_"+group_id+".dat", Context.MODE_PRIVATE);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new GsonTimeAdapter())
                    .create();
            String json = gson.toJson(children);
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

    private void saveKindergartenChildrenList(List<Child> children, int kindergarten_id){
        File file = new File(activity.getFilesDir() + "/"+"kg_children_"+kindergarten_id+".dat");
        file.delete();
        FileOutputStream fos = null;

        try {
            fos = activity.openFileOutput("kg_children_"+kindergarten_id+".dat", Context.MODE_PRIVATE);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new GsonTimeAdapter())
                    .create();
            String json = gson.toJson(children);
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

    //endregion
}
