package com.sender.news;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by XieShengda on 2016/12/18.
 */

public class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> pagerTitles;

    public MyFragmentStatePagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> pagerTitles) {
        super(fm);
        this.fragmentList = fragmentList;
        this.pagerTitles = pagerTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }


    @Override
    public int getCount() {
        return fragmentList.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return pagerTitles.get(position);
    }
}
