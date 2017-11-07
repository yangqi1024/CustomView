package com.yq.customview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;


/**
 * Description ...
 *
 * @author gsz
 * @create 2017/11/7
 * @since V1.0.1
 */
public class ViewPageAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mPage;

    public ViewPageAdapter(FragmentManager fm,ArrayList<Fragment> page) {
        super(fm);
        this.mPage = page;
    }

    @Override
    public int getCount() {
        return mPage != null ? mPage.size() : 0;
    }

    @Override
    public Fragment getItem(int position) {
        return mPage.get(position);
    }

}
