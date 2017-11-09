package com.silence.simpleappdome.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.silence.simpleappdome.fragment.FragmentFactory;

/**
 * @作者: PJ
 * @创建时间: 2017/11/3 / 15:09
 * @描述: 这是一个 MainPagerAdapter 类.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.createById(position);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
