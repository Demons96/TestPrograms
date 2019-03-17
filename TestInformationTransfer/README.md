# 简介
> Android 提供了很多种不同的信息传递方式，本篇内容是衡量每种传递方式的效率和选择最适合的传递方式用于组件化。

# 目录
- [1.本地广播](#1)
- [2.事件总线](#2)
- [3.参考资料](#3)
- [4.源码](#4)

# <span id="1">1.本地广播</span>

## 1.1 什么是本地广播
> Android 四大组件中的 BroadcastRecevier 是用的比较多的全局广播，即发出的广播可以被其他任何应用程序接收到，我们也可以接收来自其他应用程序的广播，所以全局广播用于组件化容易引起安全问题。为了解决安全问题 Android 引入了本地广播 (Local) 机制

## 1.2 本地广播的使用
```
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
```
![](https://upload-images.jianshu.io/upload_images/3304008-c84574370af01386.gif?imageMogr2/auto-orient/strip)

## 1.3 本地广播对比全局广播
- 本地广播要比全局广播更快
- 经过了多个 support 库迭代，稳定性和兼容性大大的提高。
- 通信安全性、保密性和通信效率远高于全局广播。

# <span id="2">2.事件总线</span>
## 2.1 什么是事件总线
> Android 中 Activity、Fragment、Service 之间的信息传递相对复杂，一开始还能使用广播来代替，但系统级别的广播有时候用来传递消息还是很耗时的。于是就有了更高效的传递信息工具，事件总线机制。

> 事件总线机制通过记录对象、使用监听者模式来通知对象各种事件，目前主流的事件总线框架有以下几种。
- [EventBus](https://github.com/greenrobot/EventBus)
- [RxBus](https://github.com/AndroidKnife/RxBus)
- [LiveEventBus](https://github.com/JeremyLiao/LiveEventBus)

## 2.2 EventBus
**2.2.1 简介**
> EventBus 是针对 Android 优化的发布/订阅事件总线，主要功能是代替 Intent、Handler、BroadCast，在 Activity、Fragment、Service、线程之间传递消息。

**2.2.2 主要用法**
> implementation 'org.greenrobot:eventbus:3.1.1'
```
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
```

**2.2.3 注意事项**
- 优点：简化通信，实现解耦
- 缺点：类似策略模式的问题，每个事件都必须自定义一个事件类，造成事件太多加大了维护成本。
- EventBus2.0 使用运行时注解、EventBus3.0 使用的是编译时注解，后者效率更高。

## 2.3 RxBus
**2.3.1 简介**
> RxBus 不是一个库，而是一个文件，实现只有短短几十行代码。在 RxJava 中有个 Subject 类，它继承 Observable 类，同时实现了 Observer 接口，因此 Subject 可以同时担当订阅者和被订阅者的角色。

**2.3.2 RxBus 的简单实现以及使用**
> implementation 'io.reactivex.rxjava2:rxjava:2.2.6'
> implementation 'io.reactivex.rxjava2:rxandroid:2.1.0'
```
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
```

**2.3.3 RxBus 的开源实现**
- [AndroidKnife/RxBus]( https://github.com/AndroidKnife/RxBus)
- [Blankj/RxBus]( https://github.com/Blankj/RxBus)

## 2.4 LiveEventBus
**2.4.1 简介**
> LiveDataBus 只需要调用注册回调方法，而不需要显示的调用反注册方法。这样带来的好处不仅可以编写更少的代码，而且可以完全杜绝其他通信总线类框架（如EventBus、RxBus）忘记调用反注册所带来的内存泄漏的风险。

**2.4.2 使用**
```
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
```

# <span id="3">3.参考资料</span>
- [Android 组件化架构](http://product.dangdang.com/25251262.html)
- [Android 消息总线的演进之路：用 LiveDataBus 替代 RxBus、EventBus](https://tech.meituan.com/2018/07/26/android-livedatabus.html)


# <span id="4">4.源码</span>
[TestInformationTransfer](https://github.com/Demons96/TestPrograms/tree/master/TestInformationTransfer)

