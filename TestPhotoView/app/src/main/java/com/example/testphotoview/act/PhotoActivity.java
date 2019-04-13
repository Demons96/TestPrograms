package com.example.testphotoview.act;

import android.os.Bundle;
import android.widget.TextView;

import com.example.testphotoview.PhotoFragment;
import com.example.testphotoview.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 相片查看
 *
 * @date 19.3.25
 */
public class PhotoActivity extends AppCompatActivity {

    private static final int[] sDrawables = {
            R.drawable.wallpaper,
            R.drawable.wallpaper,
            R.drawable.ic_launcher_background,
            R.drawable.wallpaper,
            R.drawable.wallpaper,
            R.drawable.wallpaper};

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, PhotoFragment.newInstance(sDrawables, 1)).commit();

    }

}
