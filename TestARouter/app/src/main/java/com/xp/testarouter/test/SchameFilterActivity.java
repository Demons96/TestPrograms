package com.xp.testarouter.test;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xp.testarouter.R;

public class SchameFilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri uri = getIntent().getData();
        ARouter.getInstance().build(uri).navigation();
        finish();
//        setContentView(R.layout.activity_schame_filter);
    }
}
