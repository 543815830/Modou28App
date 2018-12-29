package com.ruixin.administrator.ruixinapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 作者：Created by ${李丽} on 2018/3/15.
 * 邮箱：543815830@qq.com
 * fragment的适配器
 */
public class OneFmAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    public Fragment currentFragment;
    public OneFmAdapter(FragmentManager fm, List<Fragment> newsList) {
        super(fm);
        this.list = newsList;
    }

    @Override
    public Fragment getItem(int position) {
       return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        this.currentFragment= (Fragment) object;
        super.setPrimaryItem(container, position, object);
    }

   /* public int getFragment(){

    }*/
}
