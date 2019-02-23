package cn.demon96.testinformationtransfer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * RxBus 的简单实现
 *
 * @author Demons96
 * create at 19/2/23 23:12
 */
public class RxBusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_bus);

        // 接收事件
        RxBus2.getInstance().toObservable().subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                String s = (String) o;
                Toast.makeText(RxBusActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 发送事件
     *
     * @param view
     */
    public void btnSendClick(View view) {
        RxBus2.getInstance().post("Hello RxBus");
    }

    /**
     * 定义事件
     */
    public static final class RxBus2 {
        private final Subject<Object> bus;

        private RxBus2() {
            bus = PublishSubject.create().toSerialized();
        }

        public static RxBus2 getInstance() {
            return Holder.BUS;
        }

        private static class Holder {
            private static final RxBus2 BUS = new RxBus2();
        }

        public void post(Object obj) {
            bus.onNext(obj);
        }

        public <T> Observable<T> toObservable(Class<T> tClass) {
            return bus.ofType(tClass);
        }

        public Observable<Object> toObservable() {
            return bus;
        }

        public boolean hasObservers() {
            return bus.hasObservers();
        }
    }
}
