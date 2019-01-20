package cn.demon96.testrxjava2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class OneActivity extends AppCompatActivity {
    private static final String TAG = "OneActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        Weather.subscribe(user);
    }

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
}
