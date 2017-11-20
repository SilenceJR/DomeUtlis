package com.example.gankiouilife;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;

/**
 * @作者: PJ
 * @创建时间: 2017/11/16 / 15:17
 * @描述: 这是一个 BaseActivity 类.
 */
public abstract class BaseActivity extends AppCompatActivity implements IActivity{

    private Context mContext;

    @BindView(R.id.toolabr_title)
    TextView mToolabrTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        initToolBar();

        //去掉虚拟按键
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION //隐藏虚拟按键栏
//                | View.SYSTEM_UI_FLAG_IMMERSIVE); //防止点击屏幕时,隐藏虚拟按键栏又弹了出来

    }

    private void initToolBar() {
        if (mToolbar != null) {
            mToolbar.setTitle("");
            setSupportActionBar(mToolbar);
        }
    }

    public void setToolabrTitle(CharSequence title) {
        mToolabrTitle.setText(title);
    }

}
