package com.example.cbcdemo.demo;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cbcdemo.R;
import com.example.cbcdemo.mvp.LifeModel;

public class RegiesActivity extends AppCompatActivity {

    private EditText mEtChangeString;
    private Button mBtnChangeConfirm;
    private LifeModel mLifeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regies);

        mEtChangeString = findViewById(R.id.et_change_string);
        mBtnChangeConfirm = findViewById(R.id.btn_change_confirm);


        mLifeModel = ViewModelProviders.of(this).get(LifeModel.class);

        initListener();
    }

    private void initListener() {
        View.OnClickListener btnChangeConfirmOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mEtChangeString.getText().toString().trim();
                if (!TextUtils.isEmpty(s)) {
                    mLifeModel.setString(s);
                }
            }
        };
        mBtnChangeConfirm.setOnClickListener(btnChangeConfirmOnClickListener);
    }
}
