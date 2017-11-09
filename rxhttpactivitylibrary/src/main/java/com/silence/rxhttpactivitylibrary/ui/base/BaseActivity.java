package com.silence.rxhttpactivitylibrary.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.silence.rxhttpactivitylibrary.R;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * @作者: PJ
 * @创建时间: 2017/11/8 / 16:48
 * @描述: 这是一个 BaseActivity 类.
 */
public abstract class BaseActivity extends RxAppCompatActivity {

    public static final String TAG = BaseActivity.class.getSimpleName();
    public Context mContext;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());

        mContext = BaseActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }

        initViews();
    }

    protected abstract int setLayoutId();

    protected abstract void initViews();

    /**
     * Get toolbar
     *
     * @return support.v7.widget.Toolbar.
     */
    public final Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }


    /**
     * 设置头部标题
     *
     * @param title
     */
    public void setToolBarTitle(CharSequence title) {
        getToolbar().setTitle(title);
        setSupportActionBar(getToolbar());
    }

    /**
     * 版本号小于21的后退按钮图片
     */
    private void showBack() {
        getToolbar().setNavigationIcon(R.drawable.ic_back);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); //返回事件
            }
        });
    }

    /**
     * 是否显示后退按钮,默认显示,可在子类重写该方法.
     *
     * @return
     */
    protected boolean isShowBacking() {
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (null != getToolbar() && isShowBacking()) {
            showBack();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
