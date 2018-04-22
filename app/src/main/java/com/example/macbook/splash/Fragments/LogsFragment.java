package com.example.macbook.splash.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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

import com.example.macbook.splash.Adapters.ChildAdapter;
import com.example.macbook.splash.Adapters.NewLogChildrenListAdapter;
import com.example.macbook.splash.LogsPerChildActivity;
import com.example.macbook.splash.Models.Child;
import com.example.macbook.splash.Models.Comment;
import com.example.macbook.splash.Models.Group;
import com.example.macbook.splash.Models.Log;
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
import java.io.ObjectInputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class LogsFragment extends Fragment {


    //region khorma


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LogsFragment.
     */
    public static LogsFragment newInstance(String param1, String param2) {
        LogsFragment fragment = new LogsFragment();
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

    private String TeacherFile = "teacher.dat";
    private String readLogsFileName = "readLogsPerChild.dat";
    private List<Child> childrenList;
    private Activity activity;
    private TextWatcher textWatcher;
    private EditText et_child_name_in_newlog;
    private List<Child> changingChildrenList;
    private EditText et_log_content_in_newlog;
    private EditText et_log_title_in_newlog;
    private AlertDialog dialog;
    private Child childToAddALog;
    private NewLogChildrenListAdapter newLogChildrenListAdapter;
    private boolean IsChildSelected;
    private int selectedChildId;


    public LogsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_logs, container, false);
        ListView listView = (ListView) view.findViewById(R.id.listview);

        childrenList = loadChildren();
        ChildAdapter childAdapter = new ChildAdapter(childrenList, activity, getLayoutInflater(), loadReadLogs());

        listView.setAdapter(childAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //upadte the readllogsfile
                //call updateadapter
                PassingToTheNextActivity(i);
            }
        });

        //FLOATING BUTTON
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(activity);
                View mView = activity.getLayoutInflater().inflate(R.layout.customlayout_newlog,null);
                changingChildrenList = loadChildren();
                final List<Child> defaultListofChildren = loadChildren();
                IsChildSelected = false;


                //region Initalisation
                et_child_name_in_newlog = mView.findViewById(R.id.et_child_name_in_newlog);
                et_log_title_in_newlog = mView.findViewById(R.id.et_log_title_in_newlog);
                et_log_content_in_newlog = mView.findViewById(R.id.et_log_content_in_newlog);
                final ListView listView_children_in_newlog = mView.findViewById(R.id.listView_children_in_newlog);
                Button btn_send_in_newlog = mView.findViewById(R.id.btn_send_in_newlog);

                Typeface myCustomFont = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Thin.ttf");
                Typeface myCustomFont1 = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Light.ttf");

                et_child_name_in_newlog.setTypeface(myCustomFont1);
                et_log_title_in_newlog.setTypeface(myCustomFont1);
                et_log_content_in_newlog.setTypeface(myCustomFont1);

                newLogChildrenListAdapter = new NewLogChildrenListAdapter(changingChildrenList,getActivity(),getLayoutInflater());
                listView_children_in_newlog.setAdapter(newLogChildrenListAdapter);
                listView_children_in_newlog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        childToAddALog = changingChildrenList.get(position);
                        selectedChildId = childToAddALog.getId();
                        et_child_name_in_newlog.setText("");
                        et_child_name_in_newlog.setText((childToAddALog.getName()+" "+childToAddALog.getLastName()));
                        IsChildSelected = true;
                    }
                });

                textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        changingChildrenList = getTheProperList(defaultListofChildren,et_child_name_in_newlog.getText().toString());
                        IsChildSelected = false;
                        newLogChildrenListAdapter.update(changingChildrenList);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                };
                et_child_name_in_newlog.addTextChangedListener(textWatcher);

                mBuilder.setView(mView);
                dialog = mBuilder.create();

                btn_send_in_newlog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (et_child_name_in_newlog.getText().toString().trim().isEmpty()){
                            et_child_name_in_newlog.setError("Champs nécessaire");
                            et_child_name_in_newlog.requestFocus();
                        }else if (et_log_title_in_newlog.getText().toString().trim().isEmpty()){
                            et_log_title_in_newlog.setError("Champs nécessaire");
                            et_log_title_in_newlog.requestFocus();
                        }else if (et_log_content_in_newlog.getText().toString().trim().isEmpty()){
                            et_log_content_in_newlog.setError("Champs nécessaire");
                            et_log_content_in_newlog.requestFocus();
                        }else{
                            if(IsChildSelected){
                                Date currentTime = Calendar.getInstance().getTime();
                                Teacher teacher = loadTeacher();
                                Log log = new Log(0,et_log_title_in_newlog.getText().toString(),et_log_content_in_newlog.getText().toString(),currentTime,(teacher.getName()+" "+teacher.getLastName()),new ArrayList<Comment>());
                                ////////////////////////////////////NADERRRRR
                                //TODO: Fix the POST log API
                                    //REQUEST POST LOG(CHILDID,LOG)
                                    //onResponse
                                    teacher.getGroups().get(0).getChildren().get(getTheChildIndexInTheList(defaultListofChildren,selectedChildId)).getLogs().add(log);
                                    Gson gson = new GsonBuilder()
                                            .create();
                                    String tempString = gson.toJson(teacher);
                                    saveTeacher(tempString);
                                    dialog.dismiss();
                                    Toast toast = Toast.makeText(getActivity(), "Observation enregistrée !", Toast.LENGTH_SHORT); toast.show();
                                    //OnResponseEnd
                            }else{
                                et_child_name_in_newlog.setError("Sélectionnez un enfant de la liste");
                                et_child_name_in_newlog.requestFocus();
                            }

                        }
                    }
                });

                dialog.show();
            }
        });


        return view;
    }

    //////////////////////// METHODS ////////////////////////

    public void PassingToTheNextActivity(int c){
        Intent i = new Intent(getActivity(),LogsPerChildActivity.class);
        /*Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new GsonTimeAdapter())
                .create();
        String childJsonToSendToTheNextActivity = gson.toJson(childrenList.get(c));
        i.putExtra("Child",childJsonToSendToTheNextActivity);*/
        i.putExtra("index",c);
        startActivity(i);
    }

    private List<Child> loadChildren(){
        List<Child> childrenFromInternalStorage = new ArrayList<>();
        FileInputStream fis = null;
        //CODE

        try {
            fis = getActivity().openFileInput(TeacherFile);
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
            for (Group group:teacher.getGroups()
                    ) {
                for (Child child:group.getChildren()
                        ) {
                    childrenFromInternalStorage.add(child);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
        return childrenFromInternalStorage;
    }

    private HashMap<Integer,ArrayList<Integer>> loadReadLogs(){
        HashMap<Integer,ArrayList<Integer>> hmReadLogs = new HashMap<>();
        FileInputStream fis = null;
        //CODE

        try {
            fis = getActivity().openFileInput(readLogsFileName);
            ObjectInputStream ois=new ObjectInputStream(fis);
            hmReadLogs=(HashMap<Integer, ArrayList<Integer>>) ois.readObject();
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

    private List<Child> getTheProperList(List<Child> childList, String s){
        List<Child> new_childList = new ArrayList<>();
        for (Child child:childList
             ) {
            if (child.getNickName().toUpperCase().contains(s.toUpperCase()) || child.getLastName().toUpperCase().contains(s.toUpperCase()) || child.getName().toUpperCase().contains(s.toUpperCase())){
                new_childList.add(child);
            }
        }
        return new_childList;
    }

    private Teacher loadTeacher(){
        FileInputStream fis = null;
        //CODE

        try {
            fis = getActivity().openFileInput(TeacherFile);
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

    private int getTheChildIndexInTheList(List<Child> children,int childID){
        int resultat = 0;
        boolean found = false;
        while(resultat<children.size() && (found == false)){
            if (children.get(resultat).getId() == childID){
                found = true;
            }else{
                resultat++;
            }
        }
        if(found){
            return resultat;
        }else{
            return -1;
        }
    }

    private void saveTeacher(String jsonTeacher){
        File file = new File(getActivity().getFilesDir() + "/"+TeacherFile);
        file.delete();
        FileOutputStream fos = null;

        try {
            fos = getActivity().openFileOutput(TeacherFile,Context.MODE_PRIVATE);
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
