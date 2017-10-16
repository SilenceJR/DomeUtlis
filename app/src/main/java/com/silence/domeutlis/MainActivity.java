package com.silence.domeutlis;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.main_bt)
    Button mMainBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置允许使用转场动画
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);


    }

    @OnClick(R.id.main_bt)
    public void onViewClicked() {
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, mMainBt, "searchview");
        Intent intent = new Intent(this, SearchViewActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(intent, optionsCompat.toBundle());
        } else {
            startActivity(intent);
        }
    }
}
