package com.example.testphotoview.act;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.testphotoview.R;
import com.example.testphotoview.view.adapter.SamplePagerAdapter;
import com.github.chrisbanes.photoview.PhotoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * 相片查看
 *
 * @date 19.3.25
 */
public class PhotoActivity extends AppCompatActivity {

    private static final int[] sDrawables = {
            R.drawable.wallpaper,
            R.drawable.wallpaper,
            R.drawable.wallpaper,
            R.drawable.wallpaper,
            R.drawable.wallpaper,
            R.drawable.wallpaper};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new SamplePagerAdapter(sDrawables));
    }

}
