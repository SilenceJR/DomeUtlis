package com.silence.androidmvprxjavadome.lifecycle;

import android.os.Bundle;

import com.silence.androidmvprxjavadome.BaseActivity;
import com.silence.androidmvprxjavadome.R;

public class MyLifecycleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_my_lifecycle;
    }

    @Override
    protected void initViews() {

    }
}
