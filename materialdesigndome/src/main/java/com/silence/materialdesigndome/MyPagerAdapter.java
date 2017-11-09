package com.silence.materialdesigndome;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @作者: PJ
 * @创建时间: 2017/10/24 / 14:07
 * @描述: 这是一个 MyPagerAdapter 类.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private FragmentManager mSupportFragmentManager;
    SuperAwesomeCardFragment mSuperAwesomeCardFragment;
    private String tabTitles[] = new String[]{"one", "two", "three", "flow"};


    public MyPagerAdapter(FragmentManager fm, FragmentManager supportFragmentManager) {
        super(fm);
        mSupportFragmentManager = supportFragmentManager;
    }

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        return SuperAwesomeCardFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
