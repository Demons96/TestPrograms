package cn.demon96.testtablayoutandviewpager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OneActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        initTabLayout();
        initViewPager();
    }

    private void initTabLayout() {
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(OneActivity.this, tab.getText(), Toast.LENGTH_SHORT).show();
                Log.e("TAO", "onTabSelected第一次点击选中" + tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.e("TAO", "onTabUnselected刚取消" + tab.getText());

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.e("TAO", "onTabReselected重复" + tab.getText());

            }
        });
    }

    private void initViewPager() {
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // 关联TabLayout与ViewPager，且适配器必须重写getPageTitle()方法
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            //获得到对应位置的Tab
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            //设置自定义的标题
            if (tab != null) {
                tab.setCustomView(adapter.getTabView(i));
            }
        }

    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        private final String[] title = new String[]{
                "推荐", "热点", "视频", "深圳", "通信",
                "互联网", "问答", "图片", "电影",
                "网络安全", "软件"};

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = TextFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("title", title[i]);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }

        public View getTabView(int position) {
            View view = LayoutInflater.from(OneActivity.this).inflate(R.layout.tab_layout, null);
            ImageView iv_tab = (ImageView) view.findViewById(R.id.iv_tab);
            TextView tv_tab = (TextView) view.findViewById(R.id.tv_tab);
            return view;
        }
    }
}
