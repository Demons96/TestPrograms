package com.example.testphotoview.act;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.testphotoview.R;
import com.example.testphotoview.view.Glide4Engine;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class ChooseActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_CHOOSE = 23;
    private TextView tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        tvShow = findViewById(R.id.tv_show);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);

        findViewById(R.id.btn_choose_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTest(null);

                Matisse.from(ChooseActivity.this)
//                        .choose(MimeType.ofImage())
                        .choose(MimeType.ofAll(), false)
                        .countable(true)
                        .maxSelectable(9)
//                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new Glide4Engine())
                        .forResult(REQUEST_CODE_CHOOSE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_CHOOSE) {
                setTest(data);
            }
        }
    }

    private void setTest(Intent data) {
        if (data == null) {
            tvShow.setText("");
            return;
        }
        tvShow.append(String.format("*1*obtainResult:%s%n%n" +
                        "*2*obtainPathResult:%s%n%n" +
                        "*3*obtainOriginalState:%b%n%n",
                Matisse.obtainResult(data),
                Matisse.obtainPathResult(data),
                Matisse.obtainOriginalState(data)));
    }
}
