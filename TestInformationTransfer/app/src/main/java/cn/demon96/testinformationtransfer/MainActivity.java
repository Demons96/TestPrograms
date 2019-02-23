package cn.demon96.testinformationtransfer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnLocalBroadcastClick(View view) {
        startActivity(new Intent(MainActivity.this, LocalBroadcastActivity.class));
    }

    public void btnEventBusClick(View view) {
        startActivity(new Intent(MainActivity.this, EventBusActivity.class));
    }

    public void btnRxBusClick(View view) {
        startActivity(new Intent(MainActivity.this, RxBusActivity.class));
    }

    public void btnLiveEventBusClick(View view) {
        startActivity(new Intent(MainActivity.this, LiveEventBusActivity.class));
    }
}
