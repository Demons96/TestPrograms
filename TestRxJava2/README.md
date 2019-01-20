## 简介

我们常用的面向对象编程是一种**命令式编程**，命令式编程是面向计算机硬件的抽象，有变量、赋值语句、表达式和控制语句。而 RxJava 是一种**响应式编程**，它是一种面向数据流和变化传播的编程范式，数据的更新是相关联的。

## RxJava 的用法

RxJava 的用法和 Java 自带的观察者模式很相似，在 Java 中设置被观察者需要继承 `java.util.Observable` ,而在 RxJava 里创建被观察者则是通过 `io.reactivex.Observable` 的静态方法创建的，以下通过用户观察天气的例子实现该用法。

1.  配置
```
    implementation 'io.reactivex.rxjava2:rxjava:2.2.5'
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.0'
```

2. 设置被观察者
```
    /**
     * 被观察者
     */
    Observable<String> Weather = Observable.create(new ObservableOnSubscribe<String>() {
        @Override
        public void subscribe(ObservableEmitter<String> emitter) throws Exception {
            emitter.onNext("周一：阵雨");
            emitter.onNext("周二：多云");
            emitter.onNext("周三：晴天");
            emitter.onComplete();
        }
    });
```

3. 设置观察者
```
    /**
     * 观察者
     */
    Observer<String> user = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {
            Log.e(TAG, "onSubscribe");
        }

        @Override
        public void onNext(String value) {
            Log.e(TAG, "onNext:" + value);
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "onError=" + e.getMessage());
        }

        @Override
        public void onComplete() {
            Log.e(TAG, "onComplete");
        }
    };
```

4. 设置订阅关系
```
        Weather.subscribe(user);
```

![](https://upload-images.jianshu.io/upload_images/3304008-90ad430e80996b22.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 进阶用法

实际编程时我们会发现像上面那个例子这么简单的场景还是很少见的，我们更多的会碰到以下问题。

- 数据是从服务器获取的，所以操作不能都放到主线程。
- 获取的数据不能直接显示，需要根据状态码解析成需显示的数据。
- 有时候一个显示的数据需要从不同的两个接口获取，比如当前时间、天气。

1. `Map` 操作符，解决针对根据状态码转文字的问题
```
    /**
     * 类型转换
     */
    Function<Integer, String> changeMap = new Function<Integer, String>() {
        @Override
        public String apply(@NonNull Integer integer) throws Exception {
            String s = "";
            switch (integer) {
                case 1:
                    s = "阵雨";
                    break;
                case 2:
                    s = "多云";
                    break;
                case 3:
                    s = "晴天";
                    break;
            }
            return s;
        }
    };
```

2. `zip` 操作符，解决多个接口合并数据的问题
```
    /**
     * 合并事件
     */
    BiFunction<String, String, String> allZip = new BiFunction<String, String, String>() {
        @Override
        public String apply(@NonNull String s, @NonNull String s2) throws Exception {
            return s + ":" + s2;
        }
    };
```

3. `Scheduler` 解决线程控制问题
```
        Observable.zip(timeObservable, weatherObservable.map(changeMap), allZip)
                .subscribeOn(Schedulers.io())               // 指定被观察者发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread())  // 指定观察者的回调发生在主线程
                .subscribe(userObserver);
```

4. 全部代码链式写法
```
        Observable.zip(Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("周一");
                emitter.onNext("周二");
                emitter.onNext("周三");
                emitter.onComplete();
            }
        }), Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                String s = "";
                switch (integer) {
                    case 1:
                        s = "阵雨";
                        break;
                    case 2:
                        s = "多云";
                        break;
                    case 3:
                        s = "晴天";
                        break;
                }
                return s;
            }
        }), new BiFunction<String, String, String>() {
            @Override
            public String apply(@NonNull String s, @NonNull String s2) throws Exception {
                return s + ":" + s2;
            }
        }).subscribeOn(Schedulers.io())                     // 指定被观察者发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread())  // 指定观察者的回调发生在主线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(String value) {
                        Log.e(TAG, "onNext:" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError=" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                    }
                });
```

![](https://upload-images.jianshu.io/upload_images/3304008-bd23b2f18eebafa6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 项目官网
[RxJava GitHub](https://github.com/ReactiveX/RxJava)
