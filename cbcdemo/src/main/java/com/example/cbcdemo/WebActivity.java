package com.example.cbcdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

public class WebActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mWebView = findViewById(R.id.web);

        String submit = getIntent().getStringExtra("submit");

        if (BuildConfig.DEBUG) Log.d("WebActivity", submit);

        mWebView.loadUrl(submit);

    }
}
