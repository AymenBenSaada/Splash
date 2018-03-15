package com.example.macbook.splash.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.macbook.splash.Adapters.SuggestionsAdapter;
import com.example.macbook.splash.CommunSuggestionPerUserActivity;
import com.example.macbook.splash.Enum.SuggestionCategory;
import com.example.macbook.splash.Models.Suggestion;
import com.example.macbook.splash.R;
import com.example.macbook.splash.Models.Reference;
import com.example.macbook.splash.ViewModels.SuggestionViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
//import javafx.collections.FXCollections;


public class SuggestionsFragment extends Fragment {

    //region global var

    List<SuggestionViewModel> suggestionViewModelList;
    List<Suggestion> suggestionViewModelArrayListDownloaded;
    String Jsondowload;
    List<Reference> referenceList;
    String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
    private String SuggestionFileName = "suggestions.dat";
    private String TeacherFileName = "teacher.dat";
    ProgressBar progressBarSuggestions;
    //endregion

    //region khorma
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    private OnFragmentInteractionListener mListener;

    public SuggestionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SuggestionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SuggestionsFragment newInstance(String param1, String param2) {
        SuggestionsFragment fragment = new SuggestionsFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_suggestions, container, false);
        setHasOptionsMenu(true);
        ListView listView = (ListView) view.findViewById(R.id.listviewSuggestions);



        suggestionViewModelList = new ArrayList<>();
        suggestionViewModelList = load();


        /////////////////////PROGRESS BAR/////////////////////

        progressBarSuggestions = (ProgressBar)view.findViewById(R.id.progressBarSuggestions);

        /////////////////////TEST////////////////////////
        //region TEST -> MUST BE REPLACED WITH A REQUEST GET TEACHERSUGGESTIONS AND STORE THE IN Jsondownload

        referenceList = new ArrayList<Reference>();
        suggestionViewModelArrayListDownloaded = new ArrayList<Suggestion>();

        Reference reference1 = new Reference("Link1","https://www.youtube.com/");
        Reference reference2 = new Reference("Link2","https://www.google.com/");
        referenceList.add(reference1);
        referenceList.add(reference2);



        Suggestion suggestionViewModel1= new Suggestion(1,"Il est temps de manger!", text, CustomFormatDate("21/10/2018"), 2300, SuggestionCategory.Family, 1, referenceList,"R__QavaVUdA");
        Suggestion suggestionViewModel2= new Suggestion(2,"Lire un bouquin?", text, CustomFormatDate("15/10/2018"), 8000, SuggestionCategory.Academic, 3, referenceList,"dKencoTIoqY");
        Suggestion suggestionViewModel3= new Suggestion(3,"Football ou Basketball?", text, CustomFormatDate("03/08/2018"), 4100, SuggestionCategory.Sport, 2, referenceList,"ZB1q8oc4Rjw");
        Suggestion suggestionViewModel4= new Suggestion(4,"Les jeux de société", text, CustomFormatDate("22/07/2018"), 1100, SuggestionCategory.Leisure, 2, referenceList,"HhIuxZt09CM");
        Suggestion suggestionViewModel5= new Suggestion(5,"Danser Salsa", text, CustomFormatDate("19/10/2017"), 12000, SuggestionCategory.Family, 1, referenceList,"Pybo3Gl21KE");
        Suggestion suggestionViewModel6= new Suggestion(6,"Les jeux de société", text, CustomFormatDate("24/08/2017"), 300, SuggestionCategory.Sport, 5, referenceList,"ylSy9Zh55tI");
        Suggestion suggestionViewModel7= new Suggestion(7,"Il est temps de manger!", text, CustomFormatDate("21/10/2018"), 2300, SuggestionCategory.Family, 1, referenceList,"R__QavaVUdA");
        Suggestion suggestionViewModel8= new Suggestion(8,"Lire un bouquin?", text, CustomFormatDate("15/10/2018"), 8000, SuggestionCategory.Academic, 3, referenceList,"dKencoTIoqY");
        Suggestion suggestionViewModel9= new Suggestion(9,"Football ou Basketball?", text, CustomFormatDate("03/08/2018"), 4100, SuggestionCategory.Sport, 2, referenceList,"ZB1q8oc4Rjw");
        Suggestion suggestionViewModel10= new Suggestion(10,"Les jeux de société", text, CustomFormatDate("22/07/2018"), 1100, SuggestionCategory.Leisure, 2, referenceList,"HhIuxZt09CM");
        Suggestion suggestionViewModel11= new Suggestion(11,"Danser Salsa", text, CustomFormatDate("19/10/2017"), 12000, SuggestionCategory.Family, 1, referenceList,"Pybo3Gl21KE");
       /*  SuggestionViewModel suggestionViewModel12= new SuggestionViewModel(12,"Les jeux de société", text, CustomFormatDate("24/08/2017"), 300, SuggestionCategory.Sport, 5, jsonReferenceList,"ylSy9Zh55tI");
        SuggestionViewModel suggestionViewModel13= new SuggestionViewModel(13,"Il est temps de manger!", text, CustomFormatDate("21/10/2018"), 2300, SuggestionCategory.Family, 1, jsonReferenceList,"R__QavaVUdA");
        SuggestionViewModel suggestionViewModel14= new SuggestionViewModel(14,"Lire un bouquin?", text, CustomFormatDate("15/10/2018"), 8000, SuggestionCategory.Academic, 3, jsonReferenceList,"dKencoTIoqY");
        SuggestionViewModel suggestionViewModel15= new SuggestionViewModel(15,"Football ou Basketball?", text, CustomFormatDate("03/08/2018"), 4100, SuggestionCategory.Sport, 2, jsonReferenceList,"ZB1q8oc4Rjw");
        SuggestionViewModel suggestionViewModel16= new SuggestionViewModel(16,"Les jeux de société", text, CustomFormatDate("22/07/2018"), 1100, SuggestionCategory.Leisure, 2, jsonReferenceList,"HhIuxZt09CM");
        SuggestionViewModel suggestionViewModel17= new SuggestionViewModel(17,"Danser Salsa", text, CustomFormatDate("19/10/2017"), 12000, SuggestionCategory.Family, 1, jsonReferenceList,"Pybo3Gl21KE");
        SuggestionViewModel suggestionViewModel18= new SuggestionViewModel(18,"Les jeux de société", text, CustomFormatDate("24/08/2017"), 300, SuggestionCategory.Sport, 5, jsonReferenceList,"ylSy9Zh55tI");
*/


       // suggestionViewModel1.setChecked(true);
       // suggestionViewModel1.setTracked(true);
        suggestionViewModelArrayListDownloaded.add(suggestionViewModel1);
        suggestionViewModelArrayListDownloaded.add(suggestionViewModel2);
        suggestionViewModelArrayListDownloaded.add(suggestionViewModel3);
        suggestionViewModelArrayListDownloaded.add(suggestionViewModel4);
        suggestionViewModelArrayListDownloaded.add(suggestionViewModel5);
        suggestionViewModelArrayListDownloaded.add(suggestionViewModel6);
        suggestionViewModelArrayListDownloaded.add(suggestionViewModel7);
        suggestionViewModelArrayListDownloaded.add(suggestionViewModel8);
        suggestionViewModelArrayListDownloaded.add(suggestionViewModel9);
        suggestionViewModelArrayListDownloaded.add(suggestionViewModel10);
        suggestionViewModelArrayListDownloaded.add(suggestionViewModel11);
      /*  suggestionViewModelArrayListDownloaded.add(suggestionViewModel12);
        suggestionViewModelArrayListDownloaded.add(suggestionViewModel13);
        suggestionViewModelArrayListDownloaded.add(suggestionViewModel14);
         suggestionViewModelArrayListDownloaded.add(suggestionViewModel15);
        suggestionViewModelArrayListDownloaded.add(suggestionViewModel16);
        suggestionViewModelArrayListDownloaded.add(suggestionViewModel17);
        suggestionViewModelArrayListDownloaded.add(suggestionViewModel18);*/

        //CONVERT IT TO JSON//

        Gson gsondownload = new Gson();
        Jsondowload = gsondownload.toJson(suggestionViewModelArrayListDownloaded);

        //endregion
        /////////////////////FIN TEST////////////////////////
        ////////////////////MUST BE REPLACED WITH THE JSON COMING FROM THE SERVER///////////////////////////////////


        SuggestionsAdapter suggestionsAdapter = new SuggestionsAdapter(suggestionViewModelList,getActivity(),getLayoutInflater());
        listView.setAdapter(suggestionsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OnSuggestionChosen(i);
            }
        });

        new  SuggestionAsyncRefresh().execute();

        return view;
    }


    //////////////////////////////FUNCTIONS////////////////////////////////

    public Date CustomFormatDate (String dateString){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return convertedDate;
    }

    public void OnSuggestionChosen(int c){
        Intent i = new Intent(getActivity(),CommunSuggestionPerUserActivity.class);
        i.putExtra("index",c);
        startActivity(i);
        }

    /////////////////////////SAVE AND LOAD//////////////////////////

    public void save(List<SuggestionViewModel> suggestionViewModelList){
            Gson gson = new Gson();
            String text = gson.toJson(suggestionViewModelList);
            FileOutputStream fos = null;


            try {
                fos = getContext().openFileOutput(SuggestionFileName, Context.MODE_PRIVATE);
                fos.write(text.getBytes());

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

    private List<SuggestionViewModel> load() {
        FileInputStream fis = null;
        List<SuggestionViewModel> suggestionViewModelLoad = new ArrayList<>();

        try {
            fis = getContext().openFileInput(SuggestionFileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            String Json = sb.toString();
            Type type = new TypeToken<List<SuggestionViewModel>>(){}.getType();
            Gson gson = new Gson();
            suggestionViewModelLoad = gson.fromJson(Json,type);

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

        return SortSuggestionListByChecked(SortSuggestionListByTracked(suggestionViewModelLoad));
    }

    private void updateStorage (List<SuggestionViewModel> new_suggestions){
        List<SuggestionViewModel> old_suggestions;
        old_suggestions = load();
        if (old_suggestions == null){
            save(new_suggestions);
        }else{

            for (SuggestionViewModel sugg:new_suggestions
                    ) {
                if(!SuggestionIfExistInList(old_suggestions,sugg)){
                    old_suggestions.add(sugg);
                }
            }
            File dir = getContext().getFilesDir();
            File file = new File(dir, SuggestionFileName);
            file.delete();
            save(old_suggestions);
        }
    }

    ////////////////////AUTO REFRESH//////////////////////

    public class SuggestionAsyncRefresh extends AsyncTask<String,String,String> {
        private List<SuggestionViewModel> suggestionViewModelArrayList = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            updateStorage(SortSuggestionListByChecked(SortSuggestionListByTracked(suggestionViewModelArrayList)));
            progressBarSuggestions.setVisibility(View.GONE);
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            //GETTING THE NEW FILE FROM THE JSON
            //PARSE THE JSON
            Type type = new TypeToken<List<Suggestion>>(){}.getType();
            Gson gson = new Gson();
            List<Suggestion> suggestionArrayList = gson.fromJson(Jsondowload,type);

            for (Suggestion suggestion:suggestionArrayList
                 ) {
                suggestionViewModelArrayList.add(new SuggestionViewModel(suggestion));
                Log.e("DEBUG","HI");
            }
            return null;
        }
    }

    ////////////////////////MENU////////////////////////

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.suggestionsmenu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.suggestionsRefresh){
            Toast.makeText(getContext(),"Les suggestions sont à jours",Toast.LENGTH_SHORT).show();
            Fragment currentFragment = getFragmentManager().findFragmentById(R.id.flContent);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(currentFragment).attach(currentFragment).commit();
        }
        return true;
    }

    private boolean SuggestionIfExistInList(List<SuggestionViewModel> listofsuggestions, SuggestionViewModel suggestionViewModel){
        boolean result = false;
        int i=0;
        while(result==false && i<listofsuggestions.size()){
            if(listofsuggestions.get(i).getId() == suggestionViewModel.getId()){
                result = true;
            }
            else{
                i++;
            }
        }
        return result;
    }

    private List<SuggestionViewModel> SortSuggestionListByTracked(List<SuggestionViewModel> suggestionViewModelDefaultList){
        List<SuggestionViewModel> sList1 = new ArrayList<>();
        List<SuggestionViewModel> sList2 = new ArrayList<>();
        List<SuggestionViewModel> result = new ArrayList<>();
        for (SuggestionViewModel suggestion:suggestionViewModelDefaultList
                ) {
            if(suggestion.isTracked()){
                sList1.add(suggestion);
            }else{
                sList2.add(suggestion);
            }
        }
        Collections.sort(sList1,Collections.reverseOrder());
        Collections.sort(sList2,Collections.reverseOrder());
        result.addAll(sList1);
        result.addAll(sList2);
        return result;
    }

    private List<SuggestionViewModel> SortSuggestionListByChecked(List<SuggestionViewModel> suggestionViewModelDefaultList){
        List<SuggestionViewModel> sList1 = new ArrayList<>();
        List<SuggestionViewModel> sList2 = new ArrayList<>();
        List<SuggestionViewModel> result = new ArrayList<>();
        for (SuggestionViewModel suggestion:suggestionViewModelDefaultList
                ) {
            if(!suggestion.isChecked()){
                sList1.add(suggestion);
            }else{
                sList2.add(suggestion);
            }
        }
        result.addAll(sList1);
        result.addAll(sList2);
        return result;
    }


}



