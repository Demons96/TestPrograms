package cn.demon96.testrxjava2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class TwoActivity extends AppCompatActivity {
    private static final String TAG = "TwoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        all();

//        Observable.zip(timeObservable, weatherObservable.map(changeMap), allZip)
//                .subscribeOn(Schedulers.io())               // 指定被观察者发生在 IO 线程
//                .observeOn(AndroidSchedulers.mainThread())  // 指定观察者的回调发生在主线程
//                .subscribe(userObserver);
    }

    private void all() {
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
    }

    /**
     * 合并事件
     */
    BiFunction<String, String, String> allZip = new BiFunction<String, String, String>() {
        @Override
        public String apply(@NonNull String s, @NonNull String s2) throws Exception {
            return s + ":" + s2;
        }
    };

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

    /**
     * 时间被观察者
     */
    Observable<String> timeObservable = Observable.create(new ObservableOnSubscribe<String>() {
        @Override
        public void subscribe(ObservableEmitter<String> emitter) throws Exception {
            emitter.onNext("周一");
            emitter.onNext("周二");
            emitter.onNext("周三");
            emitter.onComplete();
        }
    });

    /**
     * 天气被观察者
     */
    Observable<Integer> weatherObservable = Observable.create(new ObservableOnSubscribe<Integer>() {
        @Override
        public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
            emitter.onComplete();
        }
    });

    /**
     * 观察者
     */
    Observer<String> userObserver = new Observer<String>() {
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
}
