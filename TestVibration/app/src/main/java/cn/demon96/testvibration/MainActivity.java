package cn.demon96.testvibration;

import android.app.Service;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    /**
     * 震动对象
     */
    private Vibrator mVibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 创建震动服务对象
        mVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);


    }

    public void click(View view) {
        view.setSelected(!view.isSelected());
        if (view.isSelected()) {
            start();
        } else {
            stop();
        }
    }

    /**
     * 震动
     */
    public void start() {
        // 震动1s停1s再震1s
        mVibrator.vibrate(new long[]{0, 500, 500, 500}, 1);
    }

    private void stop() {
        mVibrator.cancel();
    }
}
