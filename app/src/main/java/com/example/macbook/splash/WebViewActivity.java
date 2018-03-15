package com.example.macbook.splash;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.macbook.splash.ViewModels.SuggestionViewModel;

import java.util.ArrayList;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;
    String url;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();
        url = intent.getExtras().getString("website");
        index = intent.getIntExtra("index",0) ;
        Log.e("debug",String.valueOf(index));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = (WebView)findViewById(R.id.websiteid);
        webView.setWebViewClient(new MyBrowser());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);
    }

    private class MyBrowser extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView,String url){
            webView.loadUrl(url);
            return true;
        }
    }


    //MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main2,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ouvrir_avec){
            String url1 = webView.getUrl();
            webView.loadUrl(url1);
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url1)));

//      return super.onOptionsItemSelected(item);
        }
        /*else if (id == android.R.id.home){
            Intent i = new Intent(this,CommunSuggestionPerUserActivity.class);
            i.putExtra("index",index);
            setResult(RESULT_OK, i);
            finish();
        }*/
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this,CommunSuggestionPerUserActivity.class);
        i.putExtra("index",index);
        startActivity(i);
    }
}


