package com.example.rydemo2.util;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import io.rong.imkit.RongIM;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RongIM.init(this);
        // 注册自定义消息
        RongIM.registerMessageType(VideoRequestMessage.class);
        // 消息展示
        RongIM.registerMessageTemplate(new VideoRequestMessageItemProvider());
    }

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
