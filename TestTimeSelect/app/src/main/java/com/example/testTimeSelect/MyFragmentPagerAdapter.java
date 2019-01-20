package com.example.testTimeSelect;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.testTimeSelect.fragment.AppFragment;
import com.example.testTimeSelect.fragment.MainFragment;
import com.example.testTimeSelect.fragment.MineFragment;
import com.example.testTimeSelect.fragment.WorkFragment;

/**
 * Created by Demon on 18/9/16.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles = new String[]{"首页", "发现", "进货单", "我的"};

    MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new AppFragment();
        } else if (position == 2) {
            return new MainFragment();
        } else if (position == 3) {
            return new MineFragment();
        }
        return new WorkFragment();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
