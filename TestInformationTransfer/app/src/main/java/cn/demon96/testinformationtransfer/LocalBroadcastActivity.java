package cn.demon96.testinformationtransfer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * 本地广播
 *
 * @author Demons96
 * create at 19/2/23 15:11
 */
public class LocalBroadcastActivity extends AppCompatActivity {

    /**
     * 声明广播接收器
     */
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String s = intent.getStringExtra("key");
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 声明广播管理器
     */
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_broadcast);

        // 初始化广播管理器
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        // 注册接收器以及本地广播的类型
        localBroadcastManager.registerReceiver(broadcastReceiver, new IntentFilter("LOCAL_ACTION"));
    }

    public void btnSendClick(View view) {
        // 发送广播
        Intent intent = new Intent("LOCAL_ACTION");
        intent.putExtra("key", "Hello LocalBroadcast");
        localBroadcastManager.sendBroadcast(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解绑广播
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}
