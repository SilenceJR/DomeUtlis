package com.silence.simpleappdome;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.silence.simpleappdome.activity.BaseActivity;
import com.silence.simpleappdome.adapter.MainPagerAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @InjectView(R.id.toolbar_btn)
    TextView mToolbarBtn;
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.appbarlayout)
    AppBarLayout mAppbarlayout;
    @InjectView(R.id.viewpager)
    ViewPager mViewpager;
    @InjectView(R.id.bottom_main)
    RadioButton mBottomMain;
    @InjectView(R.id.bottom_park)
    RadioButton mBottomPark;
    @InjectView(R.id.bottom_me)
    RadioButton mBottomMe;
    @InjectView(R.id.radio_group)
    RadioGroup mRadioGroup;
    private MainPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        initView();
    }

    private void initView() {
        mAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mViewpager.setAdapter(mAdapter);
        mViewpager.setCurrentItem(0);
        mViewpager.setOffscreenPageLimit(5);
        mToolbarTitle.setText("首页");
        mBottomMain.setChecked(true);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.bottom_main:
                        mViewpager.setCurrentItem(0, true);
                        mToolbarTitle.setText("首页");
                        break;
                    case R.id.bottom_park:
                        mViewpager.setCurrentItem(1, true);
                        mToolbarTitle.setText("详情");
                        break;
                    case R.id.bottom_me:
                        mViewpager.setCurrentItem(2, true);
                        mToolbarTitle.setText("我");
                        break;
                }
            }
        });
    }
}
