package com.example.teacher;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 何盛昌 on 2018/12/11.
 */




public class Tch_FragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments; //Fragment集合

    public Tch_FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public Tch_FragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }
    //当前显示的是第几个
    @Override
    public Fragment getItem(int arg0) {
        return fragments.get(arg0);
    }
    //计算需要几个item
    @Override
    public int getCount() {
        return fragments.size();
    }
}


