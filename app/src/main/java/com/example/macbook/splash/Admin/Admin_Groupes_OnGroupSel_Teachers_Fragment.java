package com.example.macbook.splash.Admin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.macbook.splash.Adapters.Admin_Adapters.Admin_Groupes_Children_List_Adapter;
import com.example.macbook.splash.Adapters.Admin_Adapters.Enseignants_List_Adapter;
import com.example.macbook.splash.Adapters.GsonTimeAdapter;
import com.example.macbook.splash.Interfaces.ApiClient;
import com.example.macbook.splash.Interfaces.IGroupsApi;
import com.example.macbook.splash.Models.Child;
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
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Admin_Groupes_OnGroupSel_Teachers_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Admin_Groupes_OnGroupSel_Teachers_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Admin_Groupes_OnGroupSel_Teachers_Fragment extends Fragment {

    //region khorm
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Admin_Groupes_OnGroupSel_Teachers_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Admin_Groupes_OnGroupSel_Teachers_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Admin_Groupes_OnGroupSel_Teachers_Fragment newInstance(String param1, String param2) {
        Admin_Groupes_OnGroupSel_Teachers_Fragment fragment = new Admin_Groupes_OnGroupSel_Teachers_Fragment();
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    //endregion

    private Admin_Groupes_OnGroupSelected_Activity mActivity;
    private List<Teacher> teachers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_groupes_on_group_sel_teachers, container, false);

        mActivity = (Admin_Groupes_OnGroupSelected_Activity)getActivity();
        final int groupID = mActivity.getGroupID();
        final int kindergartenID = mActivity.getKindergartenID();

        teachers = loadGroupTeachersInTheInternalStorage(groupID);

        ListView listview_groupes_on_grp_sel_teachers = (ListView) view.findViewById(R.id.listview_groupes_on_grp_sel_teachers);
        final Enseignants_List_Adapter enseignants_list_adapter = new Enseignants_List_Adapter(teachers,mActivity,groupID);
        listview_groupes_on_grp_sel_teachers.setAdapter(enseignants_list_adapter);

        FloatingActionButton fab_add_group = (FloatingActionButton) view.findViewById(R.id.fab_add_teacher);
        fab_add_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //region floating button
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(mActivity);
                View mView = getLayoutInflater().inflate(R.layout.customlayout_dialog_new_teacher_in_grp,null);

                final List<Teacher> new_teachers_list = loadNewTeachersForTheGroup(kindergartenID,groupID);

                ListView listView_teachers_in_a_group = (ListView) mView.findViewById(R.id.listView_teachers_in_a_group);
                Enseignants_List_Adapter new_teachers_list_adapter = new Enseignants_List_Adapter(new_teachers_list,mActivity,groupID);
                listView_teachers_in_a_group.setAdapter(new_teachers_list_adapter);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();

                listView_teachers_in_a_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final Teacher new_teacher = new_teachers_list.get(position);

                        IGroupsApi iGroupsApi = ApiClient.getClient().create(IGroupsApi.class);
                        iGroupsApi.linkTeacherToGroup(new_teacher.getId(), groupID).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if(response.isSuccessful()){
                                    teachers.add(new_teacher);
                                    saveNewGroupTeachersList(teachers,groupID);
                                    enseignants_list_adapter.notifyDataSetChanged();
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
                //endregion
            }
        });

        listview_groupes_on_grp_sel_teachers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                transitionToEnseignantSelectedActivity(teachers.get(position));
            }
        });

        return view;
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

    private List<Teacher> loadKindergartenTeachersFromInternalStorage(int kindergarten_id){
        List<Teacher> teachers = null;
        if(fileExist("kg_teachers_"+kindergarten_id+".dat")) {
            //region getChildrenfromInternalStorage
            FileInputStream fis = null;
            try {
                fis = mActivity.openFileInput("kg_teachers_"+kindergarten_id+".dat");
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

    private List<Teacher> loadNewTeachersForTheGroup (int kindergarten_id, int group_id){

        List<Teacher> kg_teachers =new ArrayList<>();
        if(loadKindergartenTeachersFromInternalStorage(kindergarten_id) != null){
            kg_teachers = loadKindergartenTeachersFromInternalStorage(kindergarten_id);
        }
        List<Teacher> grp_teachers = new ArrayList<>();
        if(loadGroupTeachersInTheInternalStorage(group_id) != null){
            grp_teachers = loadGroupTeachersInTheInternalStorage(group_id);
        }

        for (int i = 0; i<kg_teachers.size();i++)
                 {
            if(CheckIfTeacherExistInGroup(kg_teachers.get(i),grp_teachers)){
                kg_teachers.remove(i);
                i--;
            }
        }
        return kg_teachers;
    }

    private void saveNewGroupTeachersList(List<Teacher> teachers,int group_id){
        File file = new File(mActivity.getFilesDir() + "/"+"kg_group_teachers_"+group_id+".dat");
        file.delete();
        FileOutputStream fos = null;

        try {
            fos = mActivity.openFileOutput("kg_group_teachers_"+group_id+".dat",Context.MODE_PRIVATE);

            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new GsonTimeAdapter())
                    .create();
            String json = gson.toJson(teachers);
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

    private boolean CheckIfTeacherExistInGroup(Teacher teacher,List<Teacher> teacherList){
        boolean resultat = false;
        int i=0;
        while (!resultat && (i<teacherList.size())){
            if(teacher.getId() == teacherList.get(i).getId()){
                resultat = true;
            }else {
                i++;
            }
        }
        return resultat;
    }

    private void transitionToEnseignantSelectedActivity(Teacher teacher){
        Intent i = new Intent(getActivity(),Admin_Enseignant_Selected_Activity.class);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new GsonTimeAdapter())
                .create();
        String jsonTeacher = gson.toJson(teacher);
        i.putExtra("jsonTeacher",jsonTeacher);

        startActivity(i);
    }

}
