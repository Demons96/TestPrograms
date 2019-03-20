package com.example.mywebviewtext;

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

    public void AgentWebClick(View view) {
        startActivity(new Intent(MainActivity.this, TestAgentWebActivity.class));
    }

    public void JsClick(View view) {
        startActivity(new Intent(MainActivity.this, TestJsActivity.class));
    }

    public void AgentJsClick(View view) {
        startActivity(new Intent(MainActivity.this, TestAgentJsActivity.class));
    }
}
