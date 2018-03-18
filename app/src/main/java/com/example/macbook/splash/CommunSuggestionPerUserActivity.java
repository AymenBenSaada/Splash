package com.example.macbook.splash;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macbook.splash.Enum.SuggestionCategory;
import com.example.macbook.splash.Models.Reference;
import com.example.macbook.splash.ViewModels.ReferenceViewModel;
import com.example.macbook.splash.ViewModels.SuggestionViewModel;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommunSuggestionPerUserActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    public static final String API_KEY = "AIzaSyDNvogEn7jF2BfLwZswK0Ws5kPkLbYJ2J4";
    SuggestionViewModel suggestionViewModel;
    int index = -2;
    List<ReferenceViewModel> referenceList = new ArrayList<>();
    public static final String SuggestionFileName = "suggestions.dat";
    List<SuggestionViewModel> suggestionViewModelArrayListToUpdate = new ArrayList<>();
    Button btnSuggestionSuivre;
    Button btnSuggestionTermier;
    Animation animAlpha;
    Typeface myCustomFont;
    ImageView etoile2;
    ImageView etoile3;
    ImageView etoile4;
    ImageView etoile5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent i = getIntent();
        if (index == -2) {
            index = i.getIntExtra("index",-1);
        }

        suggestionViewModel = load().get(index);
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_commun_suggestion_per_user);

        //BUTTON ONCLICK ANIMATION
        animAlpha = AnimationUtils.loadAnimation(this,R.anim.anim_alpha);

        //CONTENT
        SettingContent();

        /** Initializing YouTube Player View **/
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.videoYoutube);
        youTubePlayerView.initialize(API_KEY, this);

        //region btnTerminer
        btnSuggestionTermier = (Button) findViewById(R.id.btnSuggestionTermier);
        if(suggestionViewModel.isChecked()){
            btnSuggestionTermier.setText("Terminé!");
            btnSuggestionTermier.setBackgroundColor(getResources().getColor(R.color.grisvert));
            btnSuggestionTermier.setEnabled(false);
        }else{
            if (SuggestionIsFinished(suggestionViewModel)){
                btnSuggestionTermier.setEnabled(true);
                btnSuggestionTermier.setBackgroundColor(getResources().getColor(R.color.violet4));
            }
        }

        btnSuggestionTermier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suggestionViewModel.setChecked(true);
                GoBackActivity();
            }
        });
        //endregion

        //region btnSuivre
        btnSuggestionSuivre = (Button) findViewById(R.id.btnSuggestionSuivre);
        if(suggestionViewModel.isTracked()){
            btnSuggestionSuivre.setText("ABANDONNER");
        }else {
            btnSuggestionSuivre.setText("SUIVRE");
        }
        btnSuggestionSuivre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);
                if(suggestionViewModel.isTracked()){
                    suggestionViewModel.setTracked(false);
                }else{
                    suggestionViewModel.setTracked(true);
                }
                GoBackActivity();
            }
        });
        //endregion
    }


    ///////////////////////////YOUTUBE///////////////////

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        /** add listeners to YouTubePlayer instance **/
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);
        /** Start buffering **/
        if (!b) {
            youTubePlayer.cueVideo(suggestionViewModel.getVideoYoutube());
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Failured to Initialize!", Toast.LENGTH_LONG).show();
    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onBuffering(boolean arg0) {
        }

        @Override
        public void onPaused() {
        }

        @Override
        public void onPlaying() {
            suggestionViewModel.setVideoYoutubeClicked(true);
            if (ReferencesAreAllClicked(suggestionViewModel.getReferences())){
                btnSuggestionTermier.setEnabled(true);
                btnSuggestionTermier.setBackgroundColor(getResources().getColor(R.color.violet4));
            }
        }
        @Override
        public void onSeekTo(int arg0) {
        }

        @Override
        public void onStopped() {
        }
    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onAdStarted() {
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }

        @Override
        public void onLoaded(String arg0) {
        }

        @Override
        public void onLoading() {
        }

        @Override
        public void onVideoEnded() {
        }

        @Override
        public void onVideoStarted() {
        }
    };


    ///////////////////FUNCTIONS////////////////////////

    private void SettingReferences(List<ReferenceViewModel> referenceArrayList) {
        LinearLayout linearLayoutLiens = (LinearLayout) findViewById(R.id.LinearLayoutLiens);
        for (final ReferenceViewModel reference : referenceArrayList
                ) {
            Button btn = new Button(this);
            btn.setText(reference.getTitle());
            btn.setTextSize(13);
            btn.setTypeface(myCustomFont);
            btn.setPaintFlags(btn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            btn.setBackgroundResource(android.R.color.transparent);
            btn.setTextColor(getResources().getColor(R.color.bleu5));
            btn.setAllCaps(false);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            btn.setLayoutParams(params);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reference.setClicked(true);
                    Transition(reference.getLink());
                }
            });
            linearLayoutLiens.addView(btn);
        }
    }


    private void SettingEtoils(int difficulty) {
        switch (difficulty) {
            case 1:
                etoile2.setImageResource(R.drawable.staruncolored);
                etoile3.setImageResource(R.drawable.staruncolored);
                etoile4.setImageResource(R.drawable.staruncolored);
                etoile5.setImageResource(R.drawable.staruncolored);
                break;
            case 2:
                etoile3.setImageResource(R.drawable.staruncolored);
                etoile4.setImageResource(R.drawable.staruncolored);
                etoile5.setImageResource(R.drawable.staruncolored);
                break;
            case 3:
                etoile4.setImageResource(R.drawable.staruncolored);
                etoile5.setImageResource(R.drawable.staruncolored);
                break;
            case 4:
                etoile5.setImageResource(R.drawable.staruncolored);
                break;
            default:
        }
    }


    private void SettingContent() {
        //CONTENT
        ImageView imageView = (ImageView) findViewById(R.id.suggestion_cover_picture);
        ImageView imageView1 = (ImageView) findViewById(R.id.imgSuggestion);
        TextView title = (TextView) findViewById(R.id.tv_suggestionTitle);
        TextView recompenses = (TextView) findViewById(R.id.tv_recompenses);
        TextView recu_le = (TextView) findViewById(R.id.tv_suggestionRecuLe);
        etoile2 = (ImageView) findViewById(R.id.etoile2);
        etoile3 = (ImageView) findViewById(R.id.etoile3);
        etoile4 = (ImageView) findViewById(R.id.etoile4);
        etoile5 = (ImageView) findViewById(R.id.etoile5);
        TextView paragraphe = (TextView) findViewById(R.id.paragrapheid);

        //DATE FORMAT
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        //SETTING VIEWS

        //region imageView.setBackgroundResource(R.drawable.suggestion_couver_picture);
        //REQUEST getLogPhoto(id)
        //onResponse imageView.setBackgroundResource(R.drawable.suggestion_couver_picture);
        imageView.setBackgroundResource(R.drawable.suggestion_couver_picture);
        //endregion
        paragraphe.setText(suggestionViewModel.getText());
        imageView1.setImageResource(getTheCategoryImage(suggestionViewModel.getCategory()));
        title.setText(suggestionViewModel.getTitle());
        recompenses.setText("Récompenses : " + Integer.toString(suggestionViewModel.getRecompense()) + " xp");
        recu_le.setText("Reçu le : " + df.format(suggestionViewModel.getDate()).toString());

        referenceList = suggestionViewModel.getReferences();

        SettingReferences(referenceList);
        SettingEtoils(suggestionViewModel.getDifficulty());

        //SETTING FONTS
        myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        title.setTypeface(myCustomFont);
        recompenses.setTypeface(myCustomFont);
        recu_le.setTypeface(myCustomFont);
        paragraphe.setTypeface(myCustomFont);
    }


    public void Transition(String url) {
        Intent intent = new Intent(this, WebViewActivity.class);

        suggestionViewModelArrayListToUpdate = load();
        suggestionViewModelArrayListToUpdate.remove(index);
        suggestionViewModel.setReferences(referenceList);
        suggestionViewModelArrayListToUpdate.add(index,suggestionViewModel);
        save(suggestionViewModelArrayListToUpdate);

        intent.putExtra("website", url);
        intent.putExtra("index",index);
        startActivity(intent);
    }


    private int getTheCategoryImage(SuggestionCategory suggestionCategory) {
        int result = 0;
        switch (suggestionCategory) {
            case Sport:
                result = R.drawable.suggestion_s;
                break;
            case Family:
                result = R.drawable.suggestion_f;
                break;
            case Academic:
                result = R.drawable.suggestion_a;
                break;
            case Leisure:
                result = R.drawable.suggestion_l;
                break;
            default:
        }
        return result;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /* if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Intent i = getIntent();
                index = i.getIntExtra("index",-1);
            }
        }*/
    }


    /////////////////////////SAVE AND LOAD//////////////////////////

    public void save(List<SuggestionViewModel> suggestionViewModelList){
        Gson gson = new Gson();
        String text = gson.toJson(suggestionViewModelList);
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(SuggestionFileName, Context.MODE_PRIVATE);
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

    public ArrayList<SuggestionViewModel> load() {
        FileInputStream fis = null;
        ArrayList<SuggestionViewModel> suggestionViewModelliste = new ArrayList<SuggestionViewModel>();

        try {
            fis = openFileInput(SuggestionFileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            String Json = sb.toString();
            Type type = new TypeToken<ArrayList<SuggestionViewModel>>(){}.getType();
            Gson gson = new Gson();
            suggestionViewModelliste = gson.fromJson(Json,type);

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
        return suggestionViewModelliste;
    }


    //////////////////////////////////DELETE INTERNAL STORAGE FILE//////////////////////

    private void deleteSuggestionFile(String s){
        File dir = getFilesDir();
        File file = new File(dir, s);
        file.delete();
    }

    /////////////////////////////////ON BACK //////////////////////////////


    @Override
    public void onBackPressed() {
        GoBackActivity();
    }

    private void GoBackActivity(){
        Intent i = new Intent(this,LoggedTeacherMainActivity.class);
        suggestionViewModelArrayListToUpdate = load();
        suggestionViewModelArrayListToUpdate.remove(index);
        suggestionViewModelArrayListToUpdate.add(index,suggestionViewModel);
        save(suggestionViewModelArrayListToUpdate);
        i.putExtra("position",0);
        startActivity(i);
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
        Log.e("DEBUFG","HI");
        return result;
    }

    private boolean SuggestionIsFinished(SuggestionViewModel suggestionViewModel){
        boolean result = true;
        if(!(ReferencesAreAllClicked(suggestionViewModel.getReferences()) && suggestionViewModel.isVideoYoutubeClicked())){
            result = false;
        }
        return result;
    }

    private boolean ReferencesAreAllClicked(List<ReferenceViewModel> referenceViewModelList){
        boolean result = true;
        for (ReferenceViewModel referenceViewModel:referenceViewModelList
             ) {
            if(!referenceViewModel.isClicked()){
                result=false;
                break;
                }
            }
        return result;
    }

}
