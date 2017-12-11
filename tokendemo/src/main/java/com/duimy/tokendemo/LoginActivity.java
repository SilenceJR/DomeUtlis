package com.duimy.tokendemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.duimy.tokendemo.network.BaseObserver;
import com.duimy.tokendemo.network.HttpCall;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtUser;
    private EditText mEtPsw;
    private Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEtUser = findViewById(R.id.et_user);
        mEtPsw = findViewById(R.id.et_psw);
        mBtnLogin = findViewById(R.id.btn_login);

        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String src = null;

        Toast.makeText(this, src, Toast.LENGTH_SHORT).show();

        String user = mEtUser.getText().toString().trim();
        String psw = mEtPsw.getText().toString().trim();

        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(psw)) {
            Toast.makeText(this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        HttpCall.load(HttpCall.getDuimyApi().login(user), new BaseObserver<String>() {
            @Override
            protected void onSuccess(String data) {
                Toast.makeText(LoginActivity.this, data, Toast.LENGTH_SHORT)
                        .show();

                SPUtil.saveToken(data);
                start();
            }
        });
    }

    private void start() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
