package com.xp.testarouter.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xp.testarouter.R;

import cn.demon.base.ARouterConfig;

/**
 * 测试跳转的界面
 */
@Route(path = ARouterConfig.APP_TEST_ACT)
public class TestActivity extends AppCompatActivity {
    /**
     * 为每一个参数声明一个字段，并使用 @Autowired 标注
     */
    @Autowired
    String name;
    @Autowired
    int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // ARouter 会自动对字段进行赋值，无需主动获取
        ARouter.getInstance().inject(this);

        ((TextView) findViewById(R.id.tv_show)).setText(String.format(" name: %s%n age:%s", name, age));
    }
}
