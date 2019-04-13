package com.example.testphotoview;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testphotoview.view.adapter.SamplePagerAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class PhotoFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String SELECT_ITEM = "selectItem";

    private ViewPager viewPager;
    private TextView textView;

    private int[] sDrawables;
    /**
     * 传入的下标为0
     */
    private int selectItem;

    public static PhotoFragment newInstance(int[] drawables) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putIntArray(ARG_PARAM1, drawables);
        fragment.setArguments(args);
        return fragment;
    }

    public static PhotoFragment newInstance(int[] drawables, int selectItem) {
        PhotoFragment fragment = new PhotoFragment();
        Bundle args = new Bundle();
        args.putIntArray(ARG_PARAM1, drawables);
        args.putInt(SELECT_ITEM, selectItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sDrawables = getArguments().getIntArray(ARG_PARAM1);
            selectItem = getArguments().getInt(SELECT_ITEM);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);

        textView = view.findViewById(R.id.tv_show_index);
        viewPager = view.findViewById(R.id.view_pager);

        viewPager.setAdapter(new SamplePagerAdapter(sDrawables));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setItem(selectItem);
        return view;
    }

    @SuppressLint("SetTextI18n")
    private void setItem(int item) {
        viewPager.setCurrentItem(item);
        textView.setText("" + (item + 1) + "/" + sDrawables.length);
    }

}
