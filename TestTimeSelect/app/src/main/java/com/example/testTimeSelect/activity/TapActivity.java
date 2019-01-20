package com.example.testTimeSelect.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testTimeSelect.DataGenerator;
import com.example.testTimeSelect.R;

public class TapActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ContentPagerAdapter contentPagerAdapter;
    private Fragment[] mFragmensts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap);

        mFragmensts = DataGenerator.getFragments("TabLayout Tab");

        initView();
    }

    private void initView() {
        mViewPager = findViewById(R.id.vp_content);
        contentPagerAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(contentPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout_bottom);
        mTabLayout.setupWithViewPager(mViewPager);


        for (int i = 0; i < 4; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.home_tab_content, null);
            ImageView tabIcon = (ImageView) view.findViewById(R.id.tab_content_image);
            tabIcon.setImageResource(DataGenerator.mTabRes[i]);
            TextView tabText = (TextView) view.findViewById(R.id.tab_content_text);
            tabText.setText(DataGenerator.mTabTitle[i]);
            mTabLayout.getTabAt(i).setCustomView(view);
//            mTabLayout.addTab(mTabLayout.newTab().setCustomView(DataGenerator.getTabView(this, i)));
        }

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onTabItemSelected(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        onTabItemSelected(0);   // 手动选择
    }

    class ContentPagerAdapter extends FragmentPagerAdapter {

        ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmensts[position];
        }

        @Override
        public int getCount() {
            return mFragmensts.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return DataGenerator.mTabTitle[position];
        }
    }

    private void onTabItemSelected(int position) {

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            View view = mTabLayout.getTabAt(i).getCustomView();
            ImageView icon = (ImageView) view.findViewById(R.id.tab_content_image);
            TextView text = (TextView) view.findViewById(R.id.tab_content_text);
            if (i == position) {
                icon.setImageResource(DataGenerator.mTabResPressed[i]);
                text.setTextColor(getResources().getColor(R.color.yellow));
            } else {
                icon.setImageResource(DataGenerator.mTabRes[i]);
                text.setTextColor(getResources().getColor(R.color.white));
            }
        }
    }
}
