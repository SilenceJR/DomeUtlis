package com.duimy.tokendemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tencent.bugly.beta.Beta;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvResult;
    private Button mBtnCheck;

    static {
        Beta.loadLibrary("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTvResult = findViewById(R.id.tv_result);
        mBtnCheck = findViewById(R.id.btn_check);

        mBtnCheck.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {



//        HttpCall.load(HttpCall.getDuimyApi().check(SPUtil.getToken()), new BaseObserver<String>() {
//            @Override
//            protected void onSuccess(String data) {
//                if (BuildConfig.DEBUG) Log.d("MainActivity", data + "验签成功");
//            }
//        });
    }
}
