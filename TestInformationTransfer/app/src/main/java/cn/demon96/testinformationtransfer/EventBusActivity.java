package cn.demon96.testinformationtransfer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * EventBus 使用
 *
 * @author Demons96
 * create at 19/2/23 21:20
 */
public class EventBusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
    }

    @Override
    public void onStart() {
        super.onStart();
        // 注册事件
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        // 注销事件
        EventBus.getDefault().unregister(this);
    }

    /**
     * 定义事件
     */
    public static class MessageEvent {
        private String msg;

        public MessageEvent(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

    /**
     * 发送事件
     *
     * @param view
     */
    public void btnSendClick(View view) {
        EventBus.getDefault().post(new MessageEvent("Hello EventBus"));
    }

    /**
     * 接收事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        Toast.makeText(this, event.getMsg(), Toast.LENGTH_SHORT).show();
    }
}
