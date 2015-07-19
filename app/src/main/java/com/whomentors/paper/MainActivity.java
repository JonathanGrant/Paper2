package com.whomentors.paper;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;


public class MainActivity extends ActionBarActivity {

    private WebView mWebView;
    private ProgressBar progress;

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progress.setVisibility(View.GONE);
            MainActivity.this.progress.setProgress(100);
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progress.setVisibility(View.VISIBLE);
            MainActivity.this.progress.setProgress(0);
            super.onPageStarted(view, url, favicon);
        }
    }

    /*protected void onShareClick() {
        // Create Refernece of AndroidJavaClass class for intent
        AndroidJavaClass intentClass = newAndroidJavaClass("android.content.Intent");
        // Create Refernece of AndroidJavaObject class intent
        AndroidJavaObject intentObject = newAndroidJavaObject("android.content.Intent");
        // Set action for intent
        intentObject.Call("setAction", intentClass.GetStatic("ACTION_SEND"));
        intentObject.Call<AndroidJavaObject>("setType", "text/plain");
        //Set Subject of action
        intentObject.Call("putExtra", intentClass.GetStatic("EXTRA_SUBJECT"), "Text Sharing ");
        //Set title of action or intent
        intentObject.Call("putExtra", intentClass.GetStatic("EXTRA_TITLE"), "Text Sharing ");
        // Set actual data which you want to share
        intentObject.Call("putExtra", intentClass.GetStatic("EXTRA_TEXT"), "Text Sharing Android Demo");
        AndroidJavaClass unity = newAndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject currentActivity = unity.GetStatic("currentActivity");
        // Invoke android activity for passing intent to share data
        currentActivity.Call("startActivity", intentObject);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Remove toolbar
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Ads
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mWebView = (WebView) findViewById(R.id.activity_main_webview);

        //Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //Stay on the app
        mWebView.setWebViewClient(new MyWebViewClient());

        //Load website
        mWebView.loadUrl("http://whomentors.com/project/campaign/1.html?layout=single#myTab");

        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setVisibility(View.GONE);

        //Share button
        Button shareButton = (Button)findViewById(R.id.share_text_button);
        shareButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String userEntry = "Hey! Please check out and donate to my campaign app here: http://whomentors.com/project/campaign/1.html?layout=single#";

                Intent textShareIntent = new Intent(Intent.ACTION_SEND);
                textShareIntent.putExtra(Intent.EXTRA_TEXT, userEntry);
                textShareIntent.setType("text/plain");
                startActivity(Intent.createChooser(textShareIntent, "Share text with..."));
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }



    public void setValue(int progress) {
        this.progress.setProgress(progress);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}