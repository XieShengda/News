package com.sender.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class WebActivity extends AppCompatActivity implements View.OnTouchListener {
    private WebView webView;
    private LinearLayout progress;
    private ProgressBar progressBar;

    private float start, end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        getSupportActionBar().hide();
        webView = (WebView) findViewById(R.id.content_view);
        progress = (LinearLayout) findViewById(R.id.progress);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        Intent intent = getIntent();
        String url = intent.getStringExtra("news_content");
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                webView.loadUrl(url);
                return true;
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setOnTouchListener(this);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 80){
                    progress.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE);
                }
                else {
                    progressBar.setProgress(newProgress);
                }
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch(motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                start = motionEvent.getX();
                break;
            case MotionEvent.ACTION_UP:
                end = motionEvent.getX();
                if (end - start > 300){
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                }
                break;
        }
        return false;
    }
}
