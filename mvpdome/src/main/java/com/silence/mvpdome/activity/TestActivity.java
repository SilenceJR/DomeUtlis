package com.silence.mvpdome.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.silence.mvpdome.R;
import com.silence.mvpdome.inter.TestContract;
import com.silence.mvpdome.model.TestModel;
import com.silence.mvpdome.presneter.TestPresenter;

/**
 * @作者: PJ
 * @创建时间: 2017/10/19 / 14:33
 * @描述: 这是一个 TestActivity 类.
 */
public class TestActivity extends AppCompatActivity implements TestContract.View {

    private TestContract.Presenter mPresenter;
    private TextView mTestTv;
    private Button mTestBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mTestTv = ((TextView) findViewById(R.id.test_tv));
        mTestBtn = ((Button) findViewById(R.id.test_btn));

        mPresenter = new TestPresenter(TestModel.getInstance(), this);


        mTestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getData();
                mPresenter.getIntData();
            }
        });
    }

    @Override
    public void showData(String str) {
        mTestTv.setText(str);
    }
}
