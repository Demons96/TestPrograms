package com.example.mywebviewtext;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * 原生的方法
 */
public class TestJsActivity extends AppCompatActivity {

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_js);

        WebView webView = findViewById(R.id.web_view);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.loadUrl("http://10.0.0.155:8080/miyue/index.html?sessionId=69e2de0955a04ad18fffca85bb279fe3");
//        webView.loadUrl("file:///android_asset/lucky/index.html");
//        webView.loadUrl("file:///android_asset/AndoidAnd.html");
        webView.addJavascriptInterface(this, "android");
    }

    @JavascriptInterface
    public void goToAndroid() {
        Toast.makeText(this, "充值", Toast.LENGTH_LONG).show();
    }

    @JavascriptInterface
    public void goToLogin() {
        Toast.makeText(this, "showWebContentActivity类中的goToLogin方法被调用", Toast.LENGTH_LONG).show();
    }

}
