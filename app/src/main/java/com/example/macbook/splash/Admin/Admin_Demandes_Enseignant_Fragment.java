package com.example.macbook.splash.Admin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.macbook.splash.Adapters.Admin_Adapters.Senders_Enseignants_List_Adapter;
import com.example.macbook.splash.Interfaces.ApiClient;
import com.example.macbook.splash.Interfaces.IKindergartensApi;
import com.example.macbook.splash.Interfaces.IParentsApi;
import com.example.macbook.splash.Interfaces.ITeachersApi;
import com.example.macbook.splash.Models.Log;
import com.example.macbook.splash.Models.Teacher;
import com.example.macbook.splash.Models.TeacherInscriptionRequest;
import com.example.macbook.splash.R;
import android.support.v7.widget.LinearLayoutManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Admin_Demandes_Enseignant_Fragment extends Fragment {

//region khorm

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Admin_Demandes_Enseignant_Fragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Admin_Groupes_Enseignant_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Admin_Demandes_Enseignant_Fragment newInstance(String param1, String param2) {
        Admin_Demandes_Enseignant_Fragment fragment = new Admin_Demandes_Enseignant_Fragment();
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

    private ITeachersApi iTeachersApi;
    private IKindergartensApi iKindergartensApi;
    //TODO : FIX the kinderGarten ID
    private int kindergartenId = 11;
    private final List<Teacher> listOfTeacherSenders = new ArrayList<>();
    private int teacherId;
    private Senders_Enseignants_List_Adapter senders_enseignants_list_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View mView = inflater.inflate(R.layout.fragment_admin_demandes_enseignant, container, false);

        ListView listview_demandes_enseignants = (ListView) mView.findViewById(R.id.listview_demandes_enseignants);

        getKindergartenRequests();

        senders_enseignants_list_adapter = new Senders_Enseignants_List_Adapter(listOfTeacherSenders, getActivity());

        listview_demandes_enseignants.setAdapter(senders_enseignants_list_adapter);

        return  mView;
    }

    //region GetRequestFromBackEnd

    private void getKindergartenRequests(){
        iKindergartensApi = ApiClient.getClient().create(IKindergartensApi.class);
        //TODO: fix the kinder Garten ID
        iKindergartensApi.getTeacherInscriptionRequest(11).enqueue(new Callback<List<TeacherInscriptionRequest>>() {
            @Override
            public void onResponse(Call<List<TeacherInscriptionRequest>> call, Response<List<TeacherInscriptionRequest>> response) {
               getTeachersFromList(0,response.body());
            }

            @Override
            public void onFailure(Call<List<TeacherInscriptionRequest>> call, Throwable t) {
                android.util.Log.e("KG","failed");

            }
        });
    }
    private final ArrayList<Teacher> listOfTeacherSenderLocal = new ArrayList<>();


    private void getTeachersFromList(final int i, final List<TeacherInscriptionRequest> teacherInscriptionRequests){

        iTeachersApi = ApiClient.getClient().create(ITeachersApi.class);
        if(i<teacherInscriptionRequests.size()){
            iTeachersApi.getTeacher(teacherInscriptionRequests.get(i).senderId).enqueue(new Callback<Teacher>() {
                @Override
                public void onResponse(Call<Teacher> call, Response<Teacher> response) {
                    listOfTeacherSenderLocal.add(response.body());
                    getTeachersFromList(i+1,teacherInscriptionRequests);
                    android.util.Log.e("Teacher:","added");
                }

                @Override
                public void onFailure(Call<Teacher> call, Throwable t) {
                    android.util.Log.e("Teacher","Failed");
                }

            });
        }
        else {
            listOfTeacherSenders.clear();
            listOfTeacherSenders.addAll(listOfTeacherSenderLocal);
            senders_enseignants_list_adapter.notifyDataSetChanged();

        }

    }
    //endregion

}
