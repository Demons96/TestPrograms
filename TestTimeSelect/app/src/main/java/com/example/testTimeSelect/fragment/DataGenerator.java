package com.example.testTimeSelect.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testTimeSelect.R;

/**
 * Created by Demon on 18/9/16.
 */
public class DataGenerator {

    public static final int[] mTabRes = new int[]{
            R.mipmap.d20,
            R.mipmap.home_15,
            R.mipmap.home_16,
            R.mipmap.home_17};
    public static final int[] mTabResPressed = new int[]{
            R.mipmap.home_14,
            R.mipmap.d19,
            R.mipmap.h13,
            R.mipmap.i20};

    public static final String[] mTabTitle = new String[]{
            "主页",
            "排行榜",
            "消息中心",
            "个人中心"};

    public static Fragment[] getFragments(String from) {
        Fragment fragments[] = new Fragment[4];
        fragments[0] = new AppFragment();
        fragments[1] = new MainFragment();
        fragments[2] = new WorkFragment();
        fragments[3] = new MineFragment();
        return fragments;
    }

    /**
     * 获取Tab 显示的内容 图片和文字
     *
     * @param context
     * @param position
     * @return
     */
    public static View getTabView(Context context, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_main_content, null);
        ImageView tabIcon = (ImageView) view.findViewById(R.id.tab_content_image);
        tabIcon.setImageResource(DataGenerator.mTabRes[position]);
        TextView tabText = (TextView) view.findViewById(R.id.tab_content_text);
        tabText.setText(mTabTitle[position]);
        return view;
    }
}
