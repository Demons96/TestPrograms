package com.xp.testarouter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;

import cn.demon.base.ARouterConfig;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    public void onTestActivityClick(View view) {
        ARouter.getInstance()
                .build(ARouterConfig.APP_TEST_ACT)
                .navigation();
    }

    public void onTestPClick(View view) {
        ARouter.getInstance()
                .build(ARouterConfig.APP_TEST_ACT)
                .withString("name", "Demon")
                .withInt("age", 18)
                .navigation();
    }
}
