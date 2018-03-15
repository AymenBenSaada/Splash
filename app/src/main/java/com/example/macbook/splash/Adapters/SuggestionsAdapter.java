package com.example.macbook.splash.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.macbook.splash.Enum.SuggestionCategory;
import com.example.macbook.splash.R;
import com.example.macbook.splash.ViewModels.SuggestionViewModel;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by macbook on 08/02/2018.
 */

public class SuggestionsAdapter extends BaseAdapter {


    List<SuggestionViewModel> suggestionViewModelList;
    private Context activity;
    private LayoutInflater layoutInflater;

    public SuggestionsAdapter(List<SuggestionViewModel> suggestionViewModelList, Context activity, LayoutInflater layoutInflater) {
        this.suggestionViewModelList = suggestionViewModelList;
        this.activity = activity;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        return suggestionViewModelList.toArray().length ;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = layoutInflater.inflate(R.layout.customlayout_suggestions, null);

        //SETTING VIEWS
        ImageView imageView = (ImageView) view.findViewById(R.id.imgSuggestion);
        TextView title = (TextView) view.findViewById(R.id.tv_suggestionTitle);
        TextView recompenses = (TextView) view.findViewById(R.id.tv_recompenses);
        TextView recu_le = (TextView) view.findViewById(R.id.tv_suggestionRecuLe);
        ImageView suggestionEye = (ImageView) view.findViewById(R.id.suggestionEye);
        ImageView etoile2 = (ImageView) view.findViewById(R.id.etoile2);
        ImageView etoile3 = (ImageView) view.findViewById(R.id.etoile3);
        ImageView etoile4 = (ImageView) view.findViewById(R.id.etoile4);
        ImageView etoile5 = (ImageView) view.findViewById(R.id.etoile5);

        //SETTING FONTS
        Typeface myCustomFont = Typeface.createFromAsset(activity.getAssets(), "fonts/Roboto-Thin.ttf");
        title.setTypeface(myCustomFont);
        recompenses.setTypeface(myCustomFont);
        recu_le.setTypeface(myCustomFont);

        //DATE FORMAT
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        //SETTING CONTENT
        imageView.setImageResource(getTheCategoryImage(suggestionViewModelList.get(i).getCategory()));
        title.setText(suggestionViewModelList.get(i).getTitle());
        recompenses.setText("Récompenses : " + Integer.toString(suggestionViewModelList.get(i).getRecompense())+" xp");
        recu_le.setText("Reçu le : " + df.format(suggestionViewModelList.get(i).getDate()).toString());


        if (suggestionViewModelList.get(i).isTracked() == false) {
            suggestionEye.setVisibility(View.INVISIBLE);
        } else {
            suggestionEye.setVisibility(View.VISIBLE);
        }

        if (suggestionViewModelList.get(i).isChecked()){
            imageView.setImageResource(R.drawable.suggestion_checked);
        }

        switch (suggestionViewModelList.get(i).getDifficulty()){
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

        return view;
    }



    private int getTheCategoryImage(SuggestionCategory suggestionCategory){
        int result = 0;
        switch (suggestionCategory){
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


}