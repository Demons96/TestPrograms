package com.xp.testarouter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onTest1Click(View view) {
        // 1. 应用内简单的跳转(通过URL跳转在'进阶用法'中)
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

    public void onTestSurfaceViewClick(View view) {
        ARouter.getInstance()
                .build(ARouterConfig.APP_SV_ACT)
                .navigation();
    }
}
