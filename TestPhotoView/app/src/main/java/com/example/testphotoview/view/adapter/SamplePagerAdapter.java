package com.example.testphotoview.view.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

/**
 * 相片查看的适配器
 *
 * @date 19.3.25
 */
public class SamplePagerAdapter  extends PagerAdapter {
    private int[] sDrawables;

    public SamplePagerAdapter(int[] sDrawables) {
        SamplePagerAdapter.this.sDrawables = sDrawables;
    }

    @Override
    public int getCount() {
        return sDrawables.length;
    }

    @NonNull
    @Override
    public View instantiateItem(@NonNull ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        photoView.setImageResource(sDrawables[position]);
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}