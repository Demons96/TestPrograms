package com.example.rydemo2.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.rydemo2.R;
import com.example.rydemo2.util.RongYunUtil;
import com.example.rydemo2.util.UserInfo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void user1Click(View view) {
        RongYunUtil.connect(MainActivity.this, UserInfo.UserTokenId1);
    }

    public void user2Click(View view) {
        RongYunUtil.connect(MainActivity.this, UserInfo.UserTokenId2);
    }
}
