package com.example.testphotoview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.testphotoview.act.ChooseActivity;
import com.example.testphotoview.act.PhotoActivity;
import com.example.testphotoview.act.JCameraViewActivity;
import com.example.testphotoview.act.SurfaceViewVideoActivity;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_photo:
                startActivity(new Intent(MainActivity.this, PhotoActivity.class));
                break;
            case R.id.btn_choose:
                startActivity(new Intent(MainActivity.this, ChooseActivity.class));
                break;
            case R.id.btn_j_camera:
                startActivity(new Intent(MainActivity.this, JCameraViewActivity.class));
                break;
            case R.id.btn_surface_view_view:
                startActivity(new Intent(MainActivity.this, SurfaceViewVideoActivity.class));
                break;
            default:
                break;
        }
    }
}
