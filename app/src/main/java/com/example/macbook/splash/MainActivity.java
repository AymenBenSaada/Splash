package com.example.macbook.splash;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.macbook.splash.Admin.AdminMainActivity;
import com.example.macbook.splash.Registration.SecondScreenSignUpActivity;
import com.example.macbook.splash.SwipePagers.TeacherPager;

public class MainActivity extends Activity {


    EditText etEmail;
    EditText etMdp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TO HIDE THE TITLE BAR
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //HIDE THE ANDROID KEYBOARD BY DEFAULT
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);




        /*

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
         */
        //TO DISABLE THE BAR AT THE BOTTOM
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        Typeface RegularRobotoFont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        Typeface MediumRobotoFont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Medium.ttf");

        TextView tvEmail = (TextView)findViewById(R.id.tvEmail);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etMdp = (EditText)findViewById(R.id.etMdp);
        TextView tvMdp = (TextView)findViewById(R.id.tvMdp);
        Button btnInscription = (Button)findViewById(R.id.btnInscription);
        TextView dejaMembre = (TextView)findViewById(R.id.dejaMembre);
        Button btnSIdentifier = (Button)findViewById(R.id.btnSIdentifier);

        btnSIdentifier.setPaintFlags(btnSIdentifier.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        tvEmail.setTypeface(RegularRobotoFont);
        etEmail.setTypeface(RegularRobotoFont);
        tvMdp.setTypeface(RegularRobotoFont);
        btnInscription.setTypeface(MediumRobotoFont);
        dejaMembre.setTypeface(RegularRobotoFont);

    }




    public void hideKeyboard(View view)
    {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    public void Inscription (View v){
        Intent intent = new Intent(this, SecondScreenSignUpActivity.class);

        String email = etEmail.getText().toString().trim();
        if (email.isEmpty()){
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }
        if(email.indexOf('@')==0)
        {
            etEmail.setError("Veuillez insérer un Email valid");
        }
        if(etMdp.getText().toString().trim().isEmpty())
            etMdp.setError("Le mot de passe est obligatior!");
        if(etMdp.getText().toString().trim().length()<8)
            etMdp.setError("Le mot dois contenire au moins 8 charactère");

        ImageView edonecLogo = (ImageView)findViewById(R.id.edonecLogo);
        Pair pair = new Pair<View,String>(edonecLogo,"imgTransition");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pair);

        intent.putExtra("email",etEmail.getText().toString());
        intent.putExtra("mdp",etMdp.getText().toString());


        startActivity(intent, options.toBundle());
    }

    public void SIdentifier (View v){
        Intent intent = new Intent(this, LoggedMainActivity.class);
        //Intent intent = new Intent(this, AdminMainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
        return;
    }
}
