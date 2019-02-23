package cn.demon96.testinformationtransfer;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.jeremyliao.liveeventbus.LiveEventBus;

/**
 * LiveEventBus 的使用
 *
 * @author Demons96
 * create at 19/2/23 23:25
 */
public class LiveEventBusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_event_bus);

        // 订阅消息 observe 生命周期感知，不需要手动取消订阅
        LiveEventBus.get()
                .with("key_name", String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        Toast.makeText(LiveEventBusActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 发送消息
     *
     * @param view
     */
    public void btnSendClick(View view) {
        // postValue 在后台线程发送消息，订阅者会在主线程收到消息
        LiveEventBus.get().with("key_name").postValue("Hello LiveEventBus");
    }
}
