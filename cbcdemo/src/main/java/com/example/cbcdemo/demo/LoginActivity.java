package com.example.cbcdemo.demo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cbcdemo.R;
import com.example.cbcdemo.mvp.LifeModel;

public class LoginActivity extends AppCompatActivity {

    private TextView mTvLogin;
    private Button mBtnStart;
    private LifeModel mLifeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mTvLogin = findViewById(R.id.tv_ligon);

        mBtnStart = findViewById(R.id.btn_start);

        mLifeModel = ViewModelProviders.of(this).get(LifeModel.class);

        initListener();
    }

    private void initListener() {
        Observer<String> stringObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                mTvLogin.setText(s);
            }
        };
        mLifeModel.getData().observe(this, stringObserver);

        View.OnClickListener btnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegiesActivity.class);
                startActivity(intent);
            }
        };
        mBtnStart.setOnClickListener(btnClickListener);
    }

}
