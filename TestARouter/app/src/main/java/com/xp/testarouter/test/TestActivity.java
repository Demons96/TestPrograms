package com.xp.testarouter.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xp.testarouter.ARouterConfig;
import com.xp.testarouter.R;

@Route(path = ARouterConfig.APP_TEST_ACT)
public class TestActivity extends AppCompatActivity {
    private static final String TAG = "TestActivity";
    @Autowired
    String name;
    @Autowired
    int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ARouter.getInstance().inject(this);

        Log.e(TAG, "onCreate: " + name + age);
    }
}
