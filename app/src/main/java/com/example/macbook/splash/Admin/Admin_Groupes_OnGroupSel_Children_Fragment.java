package com.example.macbook.splash.Admin;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.macbook.splash.Adapters.Admin_Adapters.Admin_Groupes_Children_List_Adapter;
import com.example.macbook.splash.Adapters.GsonTimeAdapter;
import com.example.macbook.splash.Adapters.NewLogChildrenListAdapter;
import com.example.macbook.splash.Interfaces.ApiClient;
import com.example.macbook.splash.Interfaces.IChildrenApi;
import com.example.macbook.splash.Interfaces.IGroupsApi;
import com.example.macbook.splash.Interfaces.IKindergartensApi;
import com.example.macbook.splash.Models.Child;
import com.example.macbook.splash.Models.Group;
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
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Admin_Groupes_OnGroupSel_Children_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Admin_Groupes_OnGroupSel_Children_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Admin_Groupes_OnGroupSel_Children_Fragment extends Fragment {
    //region khrom
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Admin_Groupes_OnGroupSel_Children_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Admin_Groupes_OnGroupSel_Children_Fragment.
     */
    public static Admin_Groupes_OnGroupSel_Children_Fragment newInstance(String param1, String param2) {
        Admin_Groupes_OnGroupSel_Children_Fragment fragment = new Admin_Groupes_OnGroupSel_Children_Fragment();
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



    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
    //endregion

    private Admin_Groupes_OnGroupSelected_Activity mActivity;
    private List<Child> children;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_groupes_on_group_sel_children, container, false);

        mActivity= (Admin_Groupes_OnGroupSelected_Activity) getActivity();
        final int groupID = mActivity.getGroupID();
        final int kindergartenID = mActivity.getKindergartenID();

        children = loadGroupChildrenFromTheInternalStorage(groupID);
        ListView listview_groupes_on_grp_sel_children = (ListView) view.findViewById(R.id.listview_groupes_on_grp_sel_children);
        final Admin_Groupes_Children_List_Adapter children_list_adapter = new Admin_Groupes_Children_List_Adapter(children,mActivity,groupID);
        listview_groupes_on_grp_sel_children.setAdapter(children_list_adapter);


        FloatingActionButton fab_add_group = (FloatingActionButton) view.findViewById(R.id.fab_add_child);
        fab_add_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(mActivity);
                View mView = getLayoutInflater().inflate(R.layout.customlayout_dialog_new_child_in_grp,null);

                final List<Child> new_children_list = loadNewChildrenForTheGroup(kindergartenID,groupID);

                ListView listView_children_in_a_group = (ListView) mView.findViewById(R.id.listView_children_in_a_group);
                Admin_Groupes_Children_List_Adapter new_children_list_adapter = new Admin_Groupes_Children_List_Adapter(new_children_list,mActivity,groupID);
                listView_children_in_a_group.setAdapter(new_children_list_adapter);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();

                listView_children_in_a_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final Child new_child = new_children_list.get(position);

                        IGroupsApi iGroupsApi = ApiClient.getClient().create(IGroupsApi.class);
                        iGroupsApi.linkChildToGroup(new_child.getId(), groupID).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.isSuccessful()){
                                    children.add(new_child);
                                    saveNewGroupChildrenList(children,groupID);
                                    children_list_adapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });

                        dialog.dismiss();
                    }
                });


                dialog.show();
            }
        });


        return view;
    }

    private List<Child> loadGroupChildrenFromTheInternalStorage(int group_id){
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

    private List<Child> loadKindergartenChildrenFromInternalStorage(int kindergarten_id){
        List<Child> children = null;
        if(fileExist("kg_children_"+kindergarten_id+".dat")) {
            //region getChildrenfromInternalStorage
            FileInputStream fis = null;
            try {
                fis = mActivity.openFileInput("kg_children_"+kindergarten_id+".dat");
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

    private List<Child> loadNewChildrenForTheGroup (int kindergarten_id, int group_id){
        List<Child> kg_children = new ArrayList<>();
        if (loadKindergartenChildrenFromInternalStorage(kindergarten_id) != null ){
            kg_children = loadKindergartenChildrenFromInternalStorage(kindergarten_id) ;
        }
        List<Child> grp_children = new ArrayList<>();
        if ( loadGroupChildrenFromTheInternalStorage(group_id) != null){
            grp_children = loadGroupChildrenFromTheInternalStorage(group_id);
        }

        int i = 0;
        for (Child child:kg_children
             ) {
            if(CheckIfChildExistInGroup(child,grp_children)){
                kg_children.remove(i);
            }else {
                i++;
            }
        }
    return kg_children;
    }

    private void saveNewGroupChildrenList(List<Child> children,int group_id){
        File file = new File(mActivity.getFilesDir() + "/"+"kg_group_children_"+group_id+".dat");
        file.delete();
        FileOutputStream fos = null;

        try {
            fos = mActivity.openFileOutput("kg_group_children_"+group_id+".dat",Context.MODE_PRIVATE);

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

    private boolean fileExist(String fname){
        File file = mActivity.getFileStreamPath(fname);
        return file.exists();
    }

    private boolean CheckIfChildExistInGroup(Child child,List<Child> childList){
        boolean resultat = false;
        int i=0;
        while (!resultat && (i<childList.size())){
            if(child.getId() == childList.get(i).getId()){
                resultat = true;
            }else {
                i++;
            }
        }
    return resultat;
    }
}
